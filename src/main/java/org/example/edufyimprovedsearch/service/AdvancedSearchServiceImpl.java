package org.example.edufyimprovedsearch.service;

import org.example.edufyimprovedsearch.model.dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AdvancedSearchServiceImpl implements AdvancedSearchService {
    @Qualifier("musicClient")
    private final RestClient musicClient;
    private final RestClient videoClient;
    private final RestClient audiobookClient;
    private final RestClient podcastClient;

    public AdvancedSearchServiceImpl(RestClient musicClient, RestClient videoClient, RestClient audiobookClient, RestClient podcastClient) {
        this.musicClient = musicClient;
        this.videoClient = videoClient;
        this.audiobookClient = audiobookClient;
        this.podcastClient = podcastClient;
    }

    public MediaSearchResponseDto getEverything(SearchDto search) {
        String searchToLowerCase = search.getSearch().toLowerCase();

        MediaSearchResponseDto response = new MediaSearchResponseDto();

        response.setSearchField(search.getSearch());
        response.setSongs(searchSongs(searchToLowerCase));
        response.setArtists(searchArtists(searchToLowerCase));
        response.setMusicians(searchMusicians(searchToLowerCase));
        response.setAlbums(searchAlbums(searchToLowerCase));

        response.setVideos(searchVideos(searchToLowerCase));
        response.setDirectors(searchDirectors(searchToLowerCase));

        return response;
    }

    // SONGS
    private List<SongDto> searchSongs(String searchToLowerCase) {
        List<SongDto> songs = musicClient.get()
                .uri("/songs")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (songs == null) return List.of();

        return songs.stream()
                .filter(song -> song.getTitle() != null &&
                        song.getTitle().toLowerCase().contains(searchToLowerCase))
                .toList();
    }

    // ARTISTS
    private List<ArtistDto> searchArtists(String searchToLowerCase) {
        List<ArtistDto> artists = musicClient.get()
                .uri("/artists")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArtistDto>>() {
                });

        if (artists == null) return List.of();

        return artists.stream()
                .filter(artist -> artist.getName() != null &&
                        artist.getName().toLowerCase().contains(searchToLowerCase))
                .toList();
    }

    // MEMBERS
    private List<MemberArtistDto> searchMusicians(String searchToLowerCase) {
        List<MemberArtistDto> members = musicClient.get()
                .uri("/members")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (members == null) return List.of();

        return members.stream()
                .filter(memberArtist -> memberArtist.getMusician().getName().toLowerCase().contains(searchToLowerCase))
                .toList();
    }

    // ALBUMS
    private List<AlbumDto> searchAlbums(String searchToLowerCase) {
        /*String json = musicClient.get()
                .uri("/albums")
                .retrieve()
                .body(String.class);*/

        List<AlbumDto> albums = musicClient.get()
                .uri("/albums")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ALBUM HIT <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        if (albums == null) return List.of();

        return albums.stream()
                .filter(album -> album.getTitle().toLowerCase().contains(searchToLowerCase))
                .toList();
    }

    // VIDEOS
    private List<VideoDto> searchVideos(String searchToLowerCase) {
        List<VideoDto> videos = videoClient.get()
                .uri("")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (videos == null) return List.of();

        return videos.stream()
                .filter(video -> video.getTitle().toLowerCase().contains(searchToLowerCase))
                .toList();
    }

    // DIRECTORS
    private List<String> searchDirectors(String searchToLowerCase) {
        List<VideoDto> videos = videoClient.get()
                .uri("")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        List<String> directors = videos.stream()
                .map(VideoDto::getArtists)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .filter(artist -> artist.toLowerCase().contains(searchToLowerCase))
                .distinct()
                .collect(Collectors.toList());

        return directors;
    }
}
