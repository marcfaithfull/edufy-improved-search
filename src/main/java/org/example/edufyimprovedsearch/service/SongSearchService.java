package org.example.edufyimprovedsearch.service;

import org.example.edufyimprovedsearch.entity.SongDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class SongSearchService {
    private final RestClient restClient;

    public SongSearchService(RestClient  restClient) {
        this.restClient =  restClient;
    }

    public List<SongDto> getSongs() {
        return restClient.get()
                .uri("/api/v1/music/songs")
                .retrieve()
                .body(new ParameterizedTypeReference<List<SongDto>>() {});
    }
}
