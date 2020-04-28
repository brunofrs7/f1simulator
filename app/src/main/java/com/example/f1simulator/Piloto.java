package com.example.f1simulator;

public class Piloto {

    String nome;
    int numero;
    String nacionalidade;
    int idade;
    double valor;
    String email;
    String foto;
    String equipa;

    public Piloto() {
        nome = "";
        numero = 0;
        idade = 0;
        nacionalidade = "";
        foto = "";
        numero = 0;
        valor = 0;
        email = "";
    }

    public Piloto(String nome, int numero, String nacionalidade, int idade, double valor, String email, String foto, String equipa) {
        this.nome = nome;
        this.numero = numero;
        this.nacionalidade = nacionalidade;
        this.idade = idade;
        this.valor = valor;
        this.email = email;
        this.foto = foto;
        this.equipa = equipa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEquipa() {
        return equipa;
    }

    public void setEquipa(String equipa) {
        this.equipa = equipa;
    }

}