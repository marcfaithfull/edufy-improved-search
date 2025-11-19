package org.example.edufyimprovedsearch.controller;

import org.example.edufyimprovedsearch.model.dto.MediaSearchResponseDto;
import org.example.edufyimprovedsearch.model.dto.SearchDto;
import org.example.edufyimprovedsearch.service.AdvancedSearchServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
public class AdvancedSearchController {
    private final AdvancedSearchServiceImpl advancedSearchServiceImpl;

    public AdvancedSearchController(AdvancedSearchServiceImpl advancedSearchServiceImpl) {
        this.advancedSearchServiceImpl = advancedSearchServiceImpl;
    }

    @GetMapping("/everything")
    public ResponseEntity<MediaSearchResponseDto> retrieveAllSongs(@RequestBody SearchDto search) {
        System.out.println(">>> CONTROLLER HIT <<<");
        MediaSearchResponseDto songs = advancedSearchServiceImpl.getEverything(search);
        return ResponseEntity.ok(songs);
    }
}
