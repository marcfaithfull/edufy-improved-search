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
        return builder.baseUrl("http://localhost:8888/api/v1/video").build();
    }

    @Bean
    public RestClient podcastClient(RestClient.Builder builder) {
        return builder.baseUrl("http://localhost:8888/api/v1/podcast").build();
    }

    @Bean
    public RestClient audioBookClient(RestClient.Builder builder) {
        return builder.baseUrl("http://localhost:8888/api/v1/audiobook").build();
    }
}
