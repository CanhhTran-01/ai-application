package com.kma.edu.ai_application.controller;

import com.kma.edu.ai_application.dto.ApiResponse;
import com.kma.edu.ai_application.dto.SearchResultItem;
import com.kma.edu.ai_application.service.DocumentIngestionService;
import com.kma.edu.ai_application.service.DocumentSearchService;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/documents")
public class DocumentController {
    private final DocumentIngestionService ingestionService;
    private final DocumentSearchService searchService;

    public DocumentController(DocumentIngestionService ingestionService, DocumentSearchService searchService) {
        this.ingestionService = ingestionService;
        this.searchService = searchService;
    }

    @PostMapping("/ingest-sample")
    public ApiResponse<String> ingestSample() {
        Resource resource = new ClassPathResource("documents/quy-che-dao-tao-mau.txt");
        int chunkCount = ingestionService.ingest(resource);
        return ApiResponse.success("Đã nạp thành công " + chunkCount + " chunk(s)");
    }

    @GetMapping("/search")
    public ApiResponse<List<SearchResultItem>> search(
            @RequestParam String query, @RequestParam(defaultValue = "3") int topK) {

        List<SearchResultItem> results = searchService.search(query, topK).stream()
                .map(doc -> new SearchResultItem(doc.getText(), doc.getScore(), doc.getMetadata()))
                .toList();

        return ApiResponse.success(results);
    }
}
