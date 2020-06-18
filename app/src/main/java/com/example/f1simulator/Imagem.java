package com.example.f1simulator;

public class Imagem {

    private String imagemUrl;

    public Imagem(String imagemUrl) {
        this.setImagemUrl(imagemUrl);
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
