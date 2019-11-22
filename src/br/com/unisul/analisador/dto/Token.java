package br.com.unisul.analisador.dto;

public class Token {

    private final int id;
    private final String token;
    private final int position;
    private final String descricao;

    public Token(int id, String token, int position, String descricao) {
        this.id = id;
        this.token = token;
        this.position = position;
        this.descricao = descricao;
    }

    public final int getId() {
        return id;
    }

    public final String getToken() {
        return token;
    }

    public final int getPosition() {
        return position;
    }

    public final String getDescricao() {
        return descricao;
    }

    public final String toString() {
        return id + " ( '" + token + "' ) - " + descricao + " @ " + position;
    }

    public final String getName() {
        return getToken();
    }

    public final Integer getValue() {
        return Integer.parseInt(getName());
    }

}
