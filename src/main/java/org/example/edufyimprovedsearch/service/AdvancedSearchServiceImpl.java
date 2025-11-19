package org.example.edufyimprovedsearch.service;

import org.example.edufyimprovedsearch.model.dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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

        response.setPodcasts(searchPodcasts(searchToLowerCase));

        response.setAudiobooks(searchAudiobooks(searchToLowerCase));

        return response;
    }

    // SONGS
    private List<SongDto> searchSongs(String searchToLowerCase) {
        try {
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
        }  catch (Exception e) {
            return null;
        }
    }

    // ARTISTS
    private List<ArtistDto> searchArtists(String searchToLowerCase) {
        try {
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
        }  catch (Exception e) {
            return null;
        }
    }

    // MEMBERS
    private List<MemberArtistDto> searchMusicians(String searchToLowerCase) {
        try {
            List<MemberArtistDto> members = musicClient.get()
                    .uri("/members")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            if (members == null) return List.of();

            return members.stream()
                    .filter(memberArtist -> memberArtist.getMusician().getName().toLowerCase().contains(searchToLowerCase))
                    .toList();
        }  catch (Exception e) {
            return null;
        }
    }

    // ALBUMS
    private List<AlbumDto> searchAlbums(String searchToLowerCase) {
        try {
            List<AlbumDto> albums = musicClient.get()
                    .uri("/albums")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            System.out.println(">>> ALBUM HIT <<<");

            if (albums == null) return List.of();

            return albums.stream()
                    .filter(album -> album.getTitle().toLowerCase().contains(searchToLowerCase))
                    .toList();
        } catch (Exception e) {
            System.out.println(">>> ERROR HIT <<<");
            return null;
        }
    }

    // VIDEOS
    private List<VideoDto> searchVideos(String searchToLowerCase) {
        try {
            List<VideoDto> videos = videoClient.get()
                    .uri("")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            if (videos == null) return List.of();

            return videos.stream()
                    .filter(video -> video.getTitle().toLowerCase().contains(searchToLowerCase))
                    .toList();
        } catch (Exception e) {
            System.out.println(">>> CATCH REACHED <<<");
            return null;
        }
    }

    // DIRECTORS
    private List<String> searchDirectors(String searchToLowerCase) {
        try {
            List<DirectorDto> videos = videoClient.get()
                    .uri("")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            assert videos != null;
            return videos.stream()
                    .map(DirectorDto::getArtists)
                    .filter(Objects::nonNull)
                    .flatMap(List::stream)
                    .filter(artist -> artist.toLowerCase().contains(searchToLowerCase))
                    .distinct()
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println(">>> CATCH REACHED <<<");
            return null;
        }
    }

    // PODCASTS
    private List<PodcastDto> searchPodcasts(String searchToLowerCase) {
        try {
            List<PodcastDto> podcasts = podcastClient.get()
                    .uri("")
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
            System.out.println(">>> PODCAST EPISODE CATCH REACHED <<<");
            return null;
        }
    }

    // AUDIOBOOKS
    private List<AudiobookDto> searchAudiobooks(String searchToLowerCase) {
        try {
            List<AudiobookDto> audiobooks = audiobookClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("title", searchToLowerCase)
                    .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            if (audiobooks == null) return List.of();

            return audiobooks;
        }  catch (Exception e) {
            System.out.println(">>> AUDIOBOOK CATCH REACHED <<<");
            return null;
        }
    }
}