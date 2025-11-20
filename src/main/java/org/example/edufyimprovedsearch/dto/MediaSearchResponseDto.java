package org.example.edufyimprovedsearch.dto;

import java.util.ArrayList;
import java.util.List;

public class MediaSearchResponseDto {
    private List<SongDto> songs = new ArrayList<>();
    private List<ArtistDto> artists = new ArrayList<>();
    private List<AlbumDto> albums = new ArrayList<>();
    private List<MemberArtistDto> musicians = new ArrayList<>();
    private List<VideoDto> videos  = new ArrayList<>();
    //private List<String> directors = new ArrayList<>();
    private List<AudiobookDto> audiobooks  = new ArrayList<>();
    private List<PodcastDto> podcasts  = new ArrayList<>();

    public List<SongDto> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDto> songs) {
        this.songs = songs;
    }

    public List<ArtistDto> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistDto> artists) {
        this.artists = artists;
    }

    public List<AlbumDto> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumDto> albums) {
        this.albums = albums;
    }

    public List<MemberArtistDto> getMusicians() {
        return musicians;
    }

    public void setMusicians(List<MemberArtistDto> musicians) {
        this.musicians = musicians;
    }

    public List<VideoDto> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoDto> videos) {
        this.videos = videos;
    }

    /*public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }*/

    public List<AudiobookDto> getAudiobooks() {
        return audiobooks;
    }

    public void setAudiobooks(List<AudiobookDto> audiobooks) {
        this.audiobooks = audiobooks;
    }

    public List<PodcastDto> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(List<PodcastDto> podcasts) {
        this.podcasts = podcasts;
    }
}
