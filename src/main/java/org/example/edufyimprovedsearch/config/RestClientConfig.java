package org.example.edufyimprovedsearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient musicClient(RestClient.Builder builder) {
        return builder.baseUrl("http://localhost:8888/api/v1/music").build();
    }

    @Bean
    public RestClient videoClient(RestClient.Builder builder) {
        return builder.baseUrl("http://localhost:8081/api/videos").build();
    }

    @Bean
    public RestClient podcastClient(RestClient.Builder builder) {
        return builder.baseUrl("http://localhost:8889/api/episodes").build();
    }

    @Bean
    public RestClient audiobookClient(RestClient.Builder builder) {
        return builder.baseUrl("http://localhost:8090/api/audiobooks").build();
    }
}
