package org.example.edufyimprovedsearch.controller;

import org.example.edufyimprovedsearch.entity.SongDto;
import org.example.edufyimprovedsearch.service.SongSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
public class SongSearchController {
    private final SongSearchService songSearchService;

    public SongSearchController(SongSearchService songSearchService) {
        this.songSearchService = songSearchService;
    }

    @GetMapping("/songs")
    public ResponseEntity<List<SongDto>> retrieveAllSongs() {
        List<SongDto> songs = songSearchService.getSongs();
        return ResponseEntity.ok(songs);
    }
}
