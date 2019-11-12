package br.com.unisul.analisador.exception;

public class SemanticoException extends AnalisadorException {

    public SemanticoException(String msg, int position) {
        super(msg, position);
    }

    public SemanticoException(String msg) {
        super(msg);
    }

    public SemanticoException(Throwable throwable) {
        super(throwable);
    }
}
