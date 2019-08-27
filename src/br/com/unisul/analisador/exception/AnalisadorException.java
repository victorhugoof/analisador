package br.com.unisul.analisador.exception;

import java.util.Objects;

public abstract class AnalisadorException extends Exception {
    private int position;

    public AnalisadorException(String msg, int position) {
        super(msg);
        this.position = position;
    }

    public AnalisadorException(String msg) {
        super(msg);
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
