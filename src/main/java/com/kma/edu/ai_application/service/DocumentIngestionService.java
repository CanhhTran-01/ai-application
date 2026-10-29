package com.kma.edu.ai_application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DocumentIngestionService {
    private final VectorStore vectorStore;

    public DocumentIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public int ingest(Resource resource) {
        // TextReader đọc nội dung file thô và lưu lại trong list documents
        TextReader textReader = new TextReader(resource);
        List<Document> documents = textReader.get();
        log.info("Đọc được {} document(s) từ file: {}", documents.size(), resource.getFilename());

        // gắn tên file thôi làm metadata (source) phục vụ truy xuất nguồn của documents
        textReader.getCustomMetadata().put("source", Objects.requireNonNull(resource.getFilename()));

        //        TokenTextSplitter splitter = TokenTextSplitter.builder()
        //                .withChunkSize(500) // số token tối đa mỗi chunk
        //                .withMinChunkSizeChars(200) // độ dài tối thiểu (ký tự) trước khi cân nhắc cắt ở dấu câu
        //                .withMinChunkLengthToEmbed(10) // bỏ chunk quá ngắn
        //                .withMaxNumChunks(5000) // giới hạn tổng số chunk
        //                .withKeepSeparator(true) // giữ lại ký tự xuống dòng khi cắt
        //                .build();
        //        List<Document> chunks = splitter.apply(documents);

        // Chia nhỏ (chunking) toàn bộ documents theo "Điều <số>." thành các chunks
        List<Document> chunks = new ArrayList<>();

        for (Document document : documents) {
            String text = Objects.requireNonNull(document.getText(), "Nội dung tài liệu không được null");

            for (String part : text.split("(?=Điều \\d+\\.)")) {
                if (part.isBlank()) continue;

                chunks.add(Document.builder()
                        .text(part.strip())
                        .metadata(Map.of("source", Objects.requireNonNull(resource.getFilename())))
                        .build());
            }
        }
        log.info("Chia thành {} chunk(s)", chunks.size());

        // Lưu kết quả: id - content - metadata - embedding
        vectorStore.delete("source == '" + resource.getFilename() + "'"); // tránh dữ liệu cũ bị trùng
        vectorStore.add(chunks); // Spring AI tự động embed các chunk và lưu vào vector store

        log.info("Đã nạp thành công {} chunk(s) vào vector store", chunks.size());
        return chunks.size();
    }
}
