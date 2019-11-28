package br.com.unisul.analisador.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TabelaSimbolos {

    private final List<Simbolo> table;
    private final Integer tableSize;
    private Boolean error;

    public TabelaSimbolos(Integer elements) {
        this.table = new ArrayList<>();
        this.tableSize = elements;
        this.error = false;
        this.inicializa();
    }

    private void inicializa() {
        for (int i = 0; i < this.tableSize; i++) {
            this.table.add(new Simbolo());
        }

    }

    public void adiciona(Simbolo s) {

        Integer index = this.hashFunction(s);
        Simbolo tmp = this.table.get(index);

        if (this.isFirst(tmp)) {

            s.setProximo(new Simbolo());
            s.getProximo().setAnterior(s);

            this.table.set(index, s);

        } else {

            while (tmp.getNome() != null) {

                if (this.isValid(tmp, s)) {
                    tmp.getProximo().setAnterior(tmp);
                    tmp = tmp.getProximo();

                } else {
                    break;
                }
            }

            if (!this.error) {
                s.setAnterior(tmp.getAnterior());
                tmp.getAnterior().setProximo(s);
                s.setProximo(new Simbolo());
            }
        }

        this.error = false;

    }

    public void exclui(Simbolo s) {

        Integer index = this.hashFunction(s);
        Simbolo tmp = this.table.get(index);

        if (tmp.getProximo().getNome() == null) {
            if (s.equals(tmp)) {
                this.table.set(index.intValue(), new Simbolo());
            }
        } else if (tmp.getAnterior() != null) {
            while (tmp.getNome() != null) {
                if (s.equals(tmp)) {
                    tmp.getAnterior().setProximo(tmp.getProximo());
                    break;
                } else {
                    tmp.getProximo().setAnterior(tmp);
                    tmp = tmp.getProximo();
                }
            }
        } else {
            if (tmp.getNome().equals(s.getNome())) {
            }
        }
    }

    public Simbolo buscar(String search) {
        return buscar(search, null);
    }

    public Simbolo buscar(String search, Integer level) {

        Simbolo s = new Simbolo(search);
        Integer index = this.hashFunction(s);

        s = this.table.get(index);

        if (isNotNull(search, s) && mesmoNivel(level, s)) {
            if (s.getNome().equals(search)) {
                s.setIndex(index);
                return s;
            } else {
                while (s.getNome() != null) {
                    s = s.getProximo();
                    if (s.getNome() != null && s.getNome().equals(search)) {
                        s.setIndex(index);
                        return s;
                    }
                }
            }
        }

        return null;
    }

    private boolean isNotNull(String search, Simbolo s) {
        return (Objects.nonNull(search) && Objects.nonNull(s.getNome()));
    }

    private boolean mesmoNivel(Integer level, Simbolo s) {
        return Objects.isNull(level) || s.getNivel().compareTo(level) == 0;
    }

    private int hashFunction(Simbolo s) {

        int hashValue = 0;
        for (int i = 0; i < s.getNome().length(); i++) {
            hashValue = 37 * hashValue + s.getNome().charAt(i);
        }
        hashValue %= this.tableSize;
        if (hashValue < 0) {
            hashValue += this.tableSize;
        }

        return hashValue;

    }

    private Boolean isValid(Simbolo s1, Simbolo s2) {
        if (s1.getNome().equals(s2.getNome())) {
            if (s1.getNivel() == s2.getNivel()) {
                this.error = true;
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private Boolean isFirst(Simbolo s) {
        return s.getProximo() == null;
    }

    public List<Simbolo> getTable() {
        return table;
    }

}
