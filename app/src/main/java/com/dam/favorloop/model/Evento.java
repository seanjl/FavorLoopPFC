package com.dam.favorloop.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Evento implements Parcelable {

    private String id;
    private String titulo;
    private String descripcion;
    private String lugar;
    private String imageUrl;
    private String publisher;

    public Evento() {
    }

    public Evento(String id, String titulo, String descripcion, String lugar, String imageUrl, String publisher) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.imageUrl = imageUrl;
        this.publisher = publisher;
    }

    protected Evento(Parcel in) {
        id = in.readString();
        titulo = in.readString();
        descripcion = in.readString();
        lugar = in.readString();
        imageUrl = in.readString();
        publisher = in.readString();
    }

    public static final Creator<Evento> CREATOR = new Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };

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

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(titulo);
        dest.writeString(descripcion);
        dest.writeString(lugar);
        dest.writeString(imageUrl);
        dest.writeString(publisher);
    }
}
