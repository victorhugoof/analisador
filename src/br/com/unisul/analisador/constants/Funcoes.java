package br.com.unisul.analisador.constants;

import br.com.unisul.analisador.dto.Token;

import java.lang.reflect.Field;
import java.util.Objects;

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

    private final String key;
    private final String descricao;

    EnumTerminais(String key, String descricao) {
        this.key = key;
        this.descricao = descricao;
    }

    public static EnumTerminais getByKey(String key) {
        for (EnumTerminais enun : EnumTerminais.values()) {
            if (key.equalsIgnoreCase(enun.key)) {
                return enun;
            }
        }

        return null;
    }

    public String getDescricao() {
        return this.descricao + " [ " + this.key + " ]";
    }
}

public interface Funcoes extends Constants {

    default boolean isTerminal(int token) {
        return token < FIRST_NON_TERMINAL;
    }

    default boolean nonTerminal(int token) {
        return token >= FIRST_NON_TERMINAL && token < FIRST_SEMANTIC_ACTION;
    }

    default int getFuncaoSemantica(int token) {
        return token - FIRST_SEMANTIC_ACTION;
    }

    /**
     * Método que busca descrição de um token terminal
     *
     * @return
     */
    default String getDescricaoTerminal(String key, int token) {
        String descricao = getDescTerminal(key);
        String tokenName = getTokenName(token);

        return descricao + tokenName;
    }
    default String getDescTerminal(String key) {
        EnumTerminais enumTerminais = EnumTerminais.getByKey(key);

        if (Objects.nonNull(enumTerminais)) {
            return enumTerminais.getDescricao().toUpperCase();

        } else {
            try {
                Integer.parseInt(key);
                return "INTEIRO";
            } catch (Exception e) {
            }
        }

        return "TERMINAL";
    }

    default String getTokenName(int token) {
        try {
            for (Field declaredField : Constants.class.getDeclaredFields()) {
                int value = (int) declaredField.get(null);
                if (value == token) {
                    if (!declaredField.getName().contains("t_TOKEN_")) {
                        return " - " + declaredField.getName().replace("t_", "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Busca mensagem padrão de erro
     *
     * @param anterior
     * @param atual
     * @return
     */
    default String getMensagemErro(Token anterior, Token atual) {
        String texto = "";

        if (Objects.nonNull(anterior)) {
            texto += "Após a sintaxe '" + anterior.getToken() + "' ";
        }

        if (Objects.nonNull(atual)) {
            texto += " consta erro na escrita '" + atual.getToken() + "' ";
        }

        return texto;
    }
}
