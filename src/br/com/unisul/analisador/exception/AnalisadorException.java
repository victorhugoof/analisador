package br.com.unisul.analisador.exception;

import java.util.Objects;

public abstract class AnalisadorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int position;

    AnalisadorException(String msg, int position) {
        super(msg);
        this.position = position;
    }

    AnalisadorException(String msg) {
        super(msg);
    }

    AnalisadorException(Throwable throwable) {
        super(throwable);
    }

    public int getPosition() {
        return position;
    }

    public String toString() {
        if (Objects.nonNull(this.position)) {
            return position + ": " + super.toString();
        }

        return super.toString();
    }
}
