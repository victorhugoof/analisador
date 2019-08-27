package br.com.unisul.analisador.exception;

public class SintaticoException extends AnalisadorException {

    public SintaticoException(String msg, int position) {
        super(msg, position);
    }

    public SintaticoException(String msg) {
        super(msg);
    }
}
