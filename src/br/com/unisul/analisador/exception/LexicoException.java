package br.com.unisul.analisador.exception;

public class LexicoException extends AnalisadorException
{
    public LexicoException(String msg, int position) {
        super(msg, position);
    }

    public LexicoException(String msg) {
        super(msg);
    }
}
