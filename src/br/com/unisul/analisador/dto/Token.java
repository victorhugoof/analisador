package br.com.unisul.analisador.dto;

public class Token {

	private int id;
	private String token;
	private int position;
	private String descricao;

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

	public String getDescricao() {
		return descricao;
	}

	public String toString() {
		return id + " ( '" + token + "' ) - " + descricao + " @ " + position;
	}

}
