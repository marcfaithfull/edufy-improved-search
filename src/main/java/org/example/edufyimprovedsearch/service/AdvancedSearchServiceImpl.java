package org.example.edufyimprovedsearch.service;

import org.example.edufyimprovedsearch.ClientCredentialsTokenProvider;
import org.example.edufyimprovedsearch.dto.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AdvancedSearchServiceImpl implements AdvancedSearchService {
    private final RestClient musicClient;
    private final RestClient videoClient;
    private final RestClient audiobookClient;
    private final RestClient podcastClient;
    private final ClientCredentialsTokenProvider tokenProvider;

    public AdvancedSearchServiceImpl(RestClient musicClient, RestClient videoClient, RestClient audiobookClient,
                                     RestClient podcastClient, ClientCredentialsTokenProvider tokenProvider) {
        this.musicClient = musicClient;
        this.videoClient = videoClient;
        this.audiobookClient = audiobookClient;
        this.podcastClient = podcastClient;
        this.tokenProvider = tokenProvider;
    }

    public MediaSearchResponseDto getEverything(SearchDto search) {
        String searchToLowerCase = search.getSearch().toLowerCase();

        MediaSearchResponseDto response = new MediaSearchResponseDto();

        response.setSongs(searchSongs(searchToLowerCase));
        response.setArtists(searchArtists(searchToLowerCase));
        response.setMusicians(searchMusicians(searchToLowerCase));
        response.setAlbums(searchAlbums(searchToLowerCase));

        response.setVideos(searchVideos(searchToLowerCase));
        //response.setDirectors(searchDirectors(searchToLowerCase));

        response.setPodcasts(searchPodcasts(searchToLowerCase));

        response.setAudiobooks(searchAudiobooks(searchToLowerCase));

        return response;
    }

    // SONGS
    private List<SongDto> searchSongs(String searchToLowerCase) {
        try {
            String token = tokenProvider.getAccessToken();
            List<SongDto> songs = musicClient.get()
                    .uri("/songs")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            if (songs == null) return List.of();

            return songs.stream()
                    .filter(song -> song.getTitle() != null &&
                            song.getTitle().toLowerCase().contains(searchToLowerCase))
                    .toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    // ARTISTS
    private List<ArtistDto> searchArtists(String searchToLowerCase) {
        try {
            String token = tokenProvider.getAccessToken();
            List<ArtistDto> artists = musicClient.get()
                    .uri("/artists")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return artists.stream()
                    .filter(artist -> artist.getName() != null &&
                            artist.getName().toLowerCase().contains(searchToLowerCase))
                    .toList();
        } catch (Exception e) {
            System.out.println(">>> ARTISTS CANNOT BE REACHED <<<");
            return List.of();
        }
    }

    // MEMBERS
    private List<MemberArtistDto> searchMusicians(String searchToLowerCase) {
        try {
            String token = tokenProvider.getAccessToken();
            List<MemberArtistDto> members = musicClient.get()
                    .uri("/members")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            if (members == null) return List.of();

            return members.stream()
                    .filter(memberArtist -> memberArtist.getMusician().getName().toLowerCase().contains(searchToLowerCase))
                    .toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    // ALBUMS
    private List<AlbumDto> searchAlbums(String searchToLowerCase) {
        try {
            /*String response = musicClient.get()
                    .uri("/albums")
                    .retrieve()
                    .body(String.class);
            System.out.println(response);*/

            String token = tokenProvider.getAccessToken();
            List<AlbumDto> albums = musicClient.get()
                    .uri("/albums")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            if (albums == null) return List.of();


            return albums.stream()
                    .filter(album -> album.getTitle().toLowerCase().contains(searchToLowerCase))
                    .toList();
        } catch (Exception e) {
            System.out.println(">>> ALBUM CANNOT BE REACHED <<<");
            return List.of();
        }
    }

    // VIDEOS
    private List<VideoDto> searchVideos(String searchToLowerCase) {
        try {
            String token = tokenProvider.getAccessToken();
            List<VideoDto> videos = videoClient.get()
                    .uri("")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            if (videos == null) return List.of();

            return videos.stream()
                    .filter(video ->
                            video.getTitle().toLowerCase().contains(searchToLowerCase) ||
                                    video.getDirectors().stream().anyMatch(artist ->
                                            artist.toLowerCase().contains(searchToLowerCase)))
                    .toList();
        } catch (Exception e) {
            System.out.println(">>> VIDEO CANNOT BE REACHED <<<");
            return List.of();
        }
    }

    // DIRECTORS
    private List<String> searchDirectors(String searchToLowerCase) {
        try {
            String token = tokenProvider.getAccessToken();
            List<DirectorDto> videos = videoClient.get()
                    .uri("")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            assert videos != null;
            return videos.stream()
                    .map(DirectorDto::getDirectors)
                    .filter(Objects::nonNull)
                    .flatMap(List::stream)
                    .filter(artist -> artist.toLowerCase().contains(searchToLowerCase))
                    .distinct()
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println(">>> DIRECTOR CANNOT BE REACHED <<<");
            return List.of();
        }
    }

    // PODCASTS
    private List<PodcastDto> searchPodcasts(String searchToLowerCase) {
        try {
            String token = tokenProvider.getAccessToken();
            List<PodcastDto> podcasts = podcastClient.get()
                    .uri("")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            if (podcasts == null) return List.of();

            return podcasts.stream()
                    .filter(podcast ->
                            podcast.getTitle().toLowerCase().contains(searchToLowerCase) ||
                                    podcast.getHost().toLowerCase().contains(searchToLowerCase))
                    .toList();
        } catch (Exception e) {
            System.out.println(">>> PODCAST CANNOT BE REACHED <<<");
            return List.of();
        }
    }

    // AUDIOBOOKS
    private List<AudiobookDto> searchAudiobooks(String searchToLowerCase) {
        try {
            String token = tokenProvider.getAccessToken();
            List<AudiobookDto> audiobooks = audiobookClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("title", searchToLowerCase)
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            if (audiobooks == null) return List.of();

            return audiobooks;
        } catch (Exception e) {
            System.out.println(">>> AUDIOBOOK CANNOT BE REACHED <<<");
            return List.of();
        }
    }
}