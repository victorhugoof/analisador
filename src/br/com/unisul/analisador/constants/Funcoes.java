package br.com.unisul.analisador.constants;

import java.util.Objects;

public interface Funcoes extends Constants {

	default boolean isTerminal(int token) {
		return token < FIRST_NON_TERMINAL;
	}

	default boolean nonTerminal(int token) {
		return token >= FIRST_NON_TERMINAL && token < FIRST_SEMANTIC_ACTION;
	}

	/**
	 * M�todo que busca descri��o de um token terminal
	 * @return
	 */
	default String getDescricaoTerminal(String key) {
		EnumTerminais enumTerminais = EnumTerminais.getByKey(key);

		if (Objects.nonNull(enumTerminais)) {
			return enumTerminais.getDescricao().toUpperCase();

		} else {
			try {
				int number = Integer.parseInt(key);
				return "INTEIRO";
			} catch (Exception e) {}
		}

		return "TERMINAL";
	}
}

enum EnumTerminais {

	SINAL_MAIS("+", "Sinal de Adição"),
	SINAL_MENOS("-", "Sinal de Subtração"),
	SINAL_MULTIPLICACAO("*", "Sinal de Multiplicação"),
	SINAL_DIVISAO("/", "Sinal de Divisão"),
	ABRE_PARENTESES(")", "Abre Parenteses"),
	FECHA_PARENTESES(")", "Fecha Parenteses"),
	DOIS_PONTOS_IGUAL(":=", "Dois Pontos Igual"),
	DOIS_PONTOS(":", "Dois Pontos"),
	IGUAL("=", "Sinal de Igual"),
	MAIOR(">", "Sinal de Maior"),
	MENOR("<", "Sinal de Menor"),
	MENOR_IGUAL("<=", "Sinal de Menor ou Igual"),
	MAIOR_IGUAL(">=", "Sinal de Maior ou Igual"),
	DIFERENTE("<>", "Sinal de Diferente"),
	VIRGULA(",", "Virgula"),
	PONTO_VIRGULA(";", "Ponto e Virgula"),
	PONTO(".", "Ponto");

	private String key;
	private String descricao;

	public static EnumTerminais getByKey(String key) {
		for (EnumTerminais enun : EnumTerminais.values()) {
			if (key.equalsIgnoreCase(enun.key)) {
				return enun;
			}
		}

		return null;
	}

	private EnumTerminais(String key, String descricao) {
		this.key = key;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao + " [ " + this.key + " ]";
	}
}
