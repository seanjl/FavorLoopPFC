package com.dam.favorloop.model;

public class Usuario {

    private String id;
    private String fullname;
    private String username;
    private String fotoPerfilUrl;
    private String bio;

    public Usuario() {
    }

    public Usuario(String id, String fullname, String username, String fotoPerfilUrl, String bio) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.fotoPerfilUrl = fotoPerfilUrl;
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
