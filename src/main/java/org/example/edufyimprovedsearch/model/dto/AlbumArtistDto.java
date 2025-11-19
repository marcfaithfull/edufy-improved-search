package org.example.edufyimprovedsearch.model.dto;

public class AlbumArtistDto {
    private AlbumDto album;
    private ArtistDto artist;

    public AlbumDto getAlbum() {
        return album;
    }

    public void setAlbum(AlbumDto album) {
        this.album = album;
    }

    public ArtistDto getArtist() {
        return artist;
    }

    public void setArtist(ArtistDto artist) {
        this.artist = artist;
    }
}
