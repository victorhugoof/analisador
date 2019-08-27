package br.com.unisul.analisador.exception;

public class SemanticoExcepion extends AnalisadorException {

    public SemanticoExcepion(String msg, int position) {
        super(msg, position);
    }

    public SemanticoExcepion(String msg) {
        super(msg);
    }
}
