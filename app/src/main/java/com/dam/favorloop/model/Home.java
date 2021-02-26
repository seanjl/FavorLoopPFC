package com.dam.favorloop.model;

public class Home {

    int id;
    String nombre;
    String lugar;
    String imagen;
    String like;
    String coment;
    String nombreAmigo;
    String comentAmigo;

    public Home(int id, String nombre, String lugar, String imagen, String like, String coment, String nombreAmigo, String comentAmigo) {
        this.id = id;
        this.nombre = nombre;
        this.lugar = lugar;
        this.imagen = imagen;
        this.like = like;
        this.coment = coment;
        this.nombreAmigo = nombreAmigo;
        this.comentAmigo = comentAmigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public String getNombreAmigo() {
        return nombreAmigo;
    }

    public void setNombreAmigo(String nombreAmigo) {
        this.nombreAmigo = nombreAmigo;
    }

    public String getComentAmigo() {
        return comentAmigo;
    }

    public void setComentAmigo(String comentAmigo) {
        this.comentAmigo = comentAmigo;
    }
}
