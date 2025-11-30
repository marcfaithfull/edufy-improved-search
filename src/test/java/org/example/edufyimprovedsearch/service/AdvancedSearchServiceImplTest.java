package org.example.edufyimprovedsearch.service;

import org.example.edufyimprovedsearch.ClientCredentialsTokenProvider;
import org.example.edufyimprovedsearch.dto.SongDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdvancedSearchServiceImplTest {
    @Mock
    private RestClient musicClient;
    @Mock
    private RestClient videoClient;
    @Mock
    private RestClient audioBookClient;
    @Mock
    private RestClient podcastClient;
    @Mock
    private ClientCredentialsTokenProvider tokenProvider;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private RestClient.ResponseSpec responseSpec;

    @InjectMocks
    private AdvancedSearchServiceImpl advancedSearchServiceImpl;

    @BeforeEach
    void setUp() {
        when(tokenProvider.getAccessToken()).thenReturn("test-token");
    }

    @Test
    @SuppressWarnings("unchecked")
    void searchSongs() {

        SongDto songDto = new SongDto();
        songDto.setTitle("Test Song");

        String search = "Test Song";

        when(musicClient.get()).thenReturn(requestHeadersUriSpec);
        when(videoClient.get()).thenReturn(requestHeadersUriSpec);
        when(audioBookClient.get()).thenReturn(requestHeadersUriSpec);
        when(podcastClient.get()).thenReturn(requestHeadersUriSpec);

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersUriSpec.uri((URI) any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(eq(HttpHeaders.AUTHORIZATION), anyString()))
                .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.body(any(ParameterizedTypeReference.class)))
                .thenReturn(List.of(songDto))
                .thenReturn(List.of())
                .thenReturn(List.of())
                .thenReturn(List.of())
                .thenReturn(List.of())
                .thenReturn(List.of())
                .thenReturn(List.of());

        List<SongDto> result = advancedSearchServiceImpl.getEverything(search).getSongs();

        assertEquals(1, result.size());
        assertEquals("Test Song", result.get(0).getTitle());
    }
}