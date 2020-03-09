package com.example.f1simulator;

public class Piloto {

    String nome;
    double orcamento;
    int nivel;
    String website;
    String foto;
    int numero;

    public Piloto() {
        nome = "";
        orcamento = 0;
        nivel = 0;
        website = "";
        foto = "";
        numero = 0;
    }

    public Piloto(String nome, double orcamento, int nivel, String website, String foto) {
        this.nome = nome;
        this.orcamento = orcamento;
        this.nivel = nivel;
        this.website = website;
        this.foto = foto;
        numero = 0;
    }

    public Piloto(String nome, double orcamento, int nivel, String website, String foto, int numero) {
        this.nome = nome;
        this.orcamento = orcamento;
        this.nivel = nivel;
        this.website = website;
        this.foto = foto;
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(double orcamento) {
        this.orcamento = orcamento;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }


}