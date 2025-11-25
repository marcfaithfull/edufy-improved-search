package org.example.edufyimprovedsearch.controller;

import org.example.edufyimprovedsearch.dto.MediaSearchResponseDto;
import org.example.edufyimprovedsearch.dto.SearchDto;
import org.example.edufyimprovedsearch.service.AdvancedSearchServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/improved-search")
public class AdvancedSearchController {
    private final AdvancedSearchServiceImpl advancedSearchServiceImpl;

    public AdvancedSearchController(AdvancedSearchServiceImpl advancedSearchServiceImpl) {
        this.advancedSearchServiceImpl = advancedSearchServiceImpl;
    }

    @GetMapping("/general")
    public ResponseEntity<MediaSearchResponseDto> getAllMedia(@RequestBody SearchDto search) {
        MediaSearchResponseDto response = advancedSearchServiceImpl.getEverything(search);
        System.out.println(">>> BACK TO THE CONTROLLER <<<");
        return ResponseEntity.ok(response);
    }
}
