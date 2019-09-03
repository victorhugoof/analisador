package br.com.unisul.analisador.motor;

import br.com.unisul.analisador.constants.Constants;
import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.exception.LexicoException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class AnalisadorLexico implements Constants {

    private int posicaoAtual;
    private String entrada;

    public AnalisadorLexico() {
        this(new StringReader(""));
    }

    public AnalisadorLexico(Reader input) {
        setEntrada(input);
    }

    public Token proximoToken() throws LexicoException {

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
            estado = proximoEstado(proximoChar(), estado);

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
     * @param c
     * @param state
     * @return
     */
    private int proximoEstado(char c, int state) {
        int next = SCANNER_TABLE[state][c];
        return next;
    }

    /**
     * Método responsável por buscar o token de um estado
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
     * @param base
     * @param key
     * @return 1-> token
     *         2-> descricao
     */
    public Object[] buscarToken(int base, String key) {
        int start = SPECIAL_CASES_INDEXES[base];
        int end   = SPECIAL_CASES_INDEXES[base+1]-1;

        while (start <= end) {
            int half = (start+end)/2;
            int comp = SPECIAL_CASES_KEYS[half].compareTo(key);

            if (comp == 0) {
                return new Object[]{SPECIAL_CASES_VALUES[half], "PALAVRA RESERVADA"};
            } else if (comp < 0) {
                start = half + 1;
            } else { //(comp > 0)
                end = half - 1;
            }
        }

        return new Object[]{base, isTerminal(base) ? "TERMINAL" : (isNonTerminal(base) ? "NÃO TERMINAL" : "SEMANTICO")};
    }

    /**
     * Verifica se possui o char na entrada
     * @return
     */
    private boolean possuiChar() {
        return posicaoAtual < entrada.length();
    }

    /**
     * Busca o proximo char da entrada
     * @return
     */
    private char proximoChar() {
        if (possuiChar()) {
            return entrada.charAt(posicaoAtual++);
        } else {
            return (char) -1;
        }
    }

    public void setEntrada(Reader input) {
        StringBuffer bfr = new StringBuffer();

        try {
            int c = input.read();
            while (c != -1) {
                bfr.append((char)c);
                c = input.read();
            }
            this.entrada = bfr.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        setPosicaoAtual(0);
    }

    public void setPosicaoAtual(int pos) {
        posicaoAtual = pos;
    }
}
