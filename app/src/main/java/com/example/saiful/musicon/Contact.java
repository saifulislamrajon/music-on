package com.example.saiful.musicon;


public class Contact {
    String song_name,artist_name,album_name,path;
    String name,type;

    public Contact(String song_name, String artist_name, String album_name, String path) {
        this.song_name = song_name;
        this.artist_name = artist_name;
        this.album_name = album_name;
        this.path = path;
    }

    public Contact(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Contact(String song_name){
        this.song_name=song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSong_name() {
        return song_name;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public String getPath() {
        return path;
    }
}
