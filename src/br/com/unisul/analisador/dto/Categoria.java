package br.com.unisul.analisador.dto;

public enum Categoria {

    VARIAVEL("Variável"),
    CONSTANT("Constante"),
    PROCEDURE("Procedure"),
    PARAMETRO("Parâmetro");

    private final String nome;

    Categoria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

}
