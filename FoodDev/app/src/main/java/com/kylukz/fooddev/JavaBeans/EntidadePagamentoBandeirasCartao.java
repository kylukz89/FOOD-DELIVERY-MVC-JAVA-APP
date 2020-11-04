package com.kylukz.fooddev.JavaBeans;

public class EntidadePagamentoBandeirasCartao {

    private int id;
    private String bandeira;
    private int imagemBandeira;

    public EntidadePagamentoBandeirasCartao(String bandeira, int imagemBandeira) {
        this.bandeira = bandeira;
        this.imagemBandeira = imagemBandeira;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public int getImagemBandeira() {
        return imagemBandeira;
    }

    public void setImagemBandeira(int imagemBandeira) {
        this.imagemBandeira = imagemBandeira;
    }
}
