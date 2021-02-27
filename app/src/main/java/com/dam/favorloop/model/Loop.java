package com.dam.favorloop.model;

public class Loop {

    private String id;
    private String titulo;
    private String descripcion;
    private String imageUrl;
    private String publisher;

    public Loop() {
    }

    public Loop(String id, String titulo, String descripcion, String imageUrl, String publisher) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imageUrl = imageUrl;
        this.publisher = publisher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
