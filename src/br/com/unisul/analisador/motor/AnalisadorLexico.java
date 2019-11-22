package br.com.unisul.analisador.motor;

import br.com.unisul.analisador.constants.Funcoes;
import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.exception.LexicoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnalisadorLexico implements Funcoes {

    private int posicaoAtual;
    private final String entrada;

    private AnalisadorLexico(String input) {
        this.entrada = input;
        this.posicaoAtual = 0;
    }

    public static List<Token> executa(String input) throws LexicoException {
        try {
            List<Token> tokens = new ArrayList<>();
            AnalisadorLexico lexico = new AnalisadorLexico(input);

            Token token = lexico.proximoToken();

            while (Objects.nonNull(token)) {
                tokens.add(token);
                token = lexico.proximoToken();
            }

            /**
             * Adiciona ultimo token nulo para identificar fina
             */
            tokens.add(lexico.proximoToken());

            return tokens;
        } catch (LexicoException lx) {
            lx.printStackTrace();
            throw lx;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LexicoException("Erro inesperado: " + e.getMessage());
        }
    }

    private Token proximoToken() throws LexicoException {

        if (!possuiChar()) {
            return null;
        }

        int start = posicaoAtual;

        int estado = 0;
        int ultimoEstado = 0;
        int estadoFinal = -1;
        int fim = -1;

        while (possuiChar()) {
            ultimoEstado = estado;
            if (fim > 0) {
                System.out.println();
            }
            try {
                estado = proximoEstado(proximoChar(), estado);
            } catch (Exception e) {
                throw new LexicoException("Erro inesperado no token: " + this.entrada.substring(start, fim), posicaoAtual);
            }

            if (estado < 0) {
                break;
            } else {
                if (tokenPorEstado(estado) >= 0) {
                    estadoFinal = estado;
                    fim = posicaoAtual;
                }
            }
        }

        if (estadoFinal < 0 || (estadoFinal != estado && tokenPorEstado(ultimoEstado) == -2)) {
            System.out.println(entrada.substring(posicaoAtual - 10, posicaoAtual));
            throw new LexicoException(SCANNER_ERROR[ultimoEstado], start);
        }

        posicaoAtual = fim;

        int token = tokenPorEstado(estadoFinal);

        if (token == 0) {
            return proximoToken();
        } else {
            String nome = entrada.substring(start, fim);

            Object[] tokenArray = buscarToken(token, nome);
            token = (int) tokenArray[0];
            return new Token(token, nome, start, (String) tokenArray[1]);
        }
    }

    /**
     * Método responsável por buscar o proximo estado do char
     *
     * @param c
     * @param state
     * @return
     */
    private int proximoEstado(char c, int state) {
        return SCANNER_TABLE[state][c];
    }

    /**
     * Método responsável por buscar o token de um estado
     *
     * @param state
     * @return
     */
    private int tokenPorEstado(int state) {
        if (state < 0 || state >= TOKEN_STATE.length) {
            return -1;
        }

        return TOKEN_STATE[state];
    }

    /**
     * Busca o valor de um token
     *
     * @param base
     * @param key
     * @return 0-> token
     * 1-> descricao
     */
    private Object[] buscarToken(int base, String key) {
        int start = SPECIAL_CASES_INDEXES[base];
        int end = SPECIAL_CASES_INDEXES[base + 1] - 1;

        while (start <= end) {
            int half = (start + end) / 2;
            String special = SPECIAL_CASES_KEYS[half];
            int comp = special.compareToIgnoreCase(key);

            if (comp == 0) {
                return new Object[]{SPECIAL_CASES_VALUES[half], "PALAVRA RESERVADA [ " + special + " ]"};
            } else if (comp < 0) {
                start = half + 1;
            } else {
                end = half - 1;
            }
        }

        return new Object[]{base, isTerminal(base) ? getDescricaoTerminal(key, base) : (nonTerminal(base) ? "NÃO TERMINAL" : "SEMANTICO")};
    }

    /**
     * Verifica se possui o char na entrada
     *
     * @return
     */
    private boolean possuiChar() {
        return posicaoAtual < entrada.length();
    }

    /**
     * Busca o proximo char da entrada
     *
     * @return
     */
    private char proximoChar() {
        if (possuiChar()) {
            return entrada.charAt(posicaoAtual++);
        } else {
            return (char) -1;
        }
    }
}
