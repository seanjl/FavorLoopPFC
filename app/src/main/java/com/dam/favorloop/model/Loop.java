package com.dam.favorloop.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Loop implements Parcelable {

    private String id;
    private String titulo;
    private String descripcion;
    private String imageUrl;
    private String publisher;
    private String fecha;

    public Loop() {
    }

    public Loop(String id, String titulo, String descripcion, String imageUrl, String publisher, String fecha) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imageUrl = imageUrl;
        this.publisher = publisher;
        this.fecha = fecha;
    }

    protected Loop(Parcel in) {
        id = in.readString();
        titulo = in.readString();
        descripcion = in.readString();
        imageUrl = in.readString();
        publisher = in.readString();
        fecha = in.readString();
    }

    public static final Creator<Loop> CREATOR = new Creator<Loop>() {
        @Override
        public Loop createFromParcel(Parcel in) {
            return new Loop(in);
        }

        @Override
        public Loop[] newArray(int size) {
            return new Loop[size];
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Loop{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
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
        dest.writeString(imageUrl);
        dest.writeString(publisher);
        dest.writeString(fecha);
    }
}
