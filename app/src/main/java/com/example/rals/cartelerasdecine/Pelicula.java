package com.example.rals.cartelerasdecine;

import android.graphics.Bitmap;

/**
 * Created by rals1_000 on 29/11/2014.
 */
public class Pelicula {

    private String titulo;
    private String sinopsis;
    private Bitmap cartel;
    private String urlImg;

    public Pelicula() {
    }

    public Pelicula(String titulo, String sinopsis, Bitmap cartel) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.cartel = cartel;
    }

    public Pelicula(String titulo, String sinopsis) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Bitmap getCartel() {
        return cartel;
    }

    public void setCartel(Bitmap cartel) {
        this.cartel = cartel;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
