package br.com.unisul.analisador.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SymbolTable {

    private final List<Symbol> table;
    private final Integer tableSize;
    private Boolean error;

    public SymbolTable(Integer elements) {
        this.table = new ArrayList<>();
        this.tableSize = elements;
        this.error = false;
        this.initialize();
    }

    private void initialize() {
        for (int i = 0; i < this.tableSize; i++) {
            this.table.add(new Symbol());
        }

    }

    public void insert(Symbol s) {

        Integer index = this.hashFunction(s);
        Symbol tmp = this.table.get(index);

        if (this.isFirst(tmp)) {

            s.setNext(new Symbol());
            s.getNext().setPrevious(s);

            this.table.set(index, s);

        } else {

            while (tmp.getName() != null) {

                if (this.isValid(tmp, s)) {
                    tmp.getNext().setPrevious(tmp);
                    tmp = tmp.getNext();

                } else {
                    break;
                }
            }

            if (!this.error) {
                s.setPrevious(tmp.getPrevious());
                tmp.getPrevious().setNext(s);
                s.setNext(new Symbol());
            }
        }

        this.error = false;

    }

    public void delete(Symbol s) {

        Integer index = this.hashFunction(s);
        Symbol tmp = this.table.get(index);

        if (tmp.getNext().getName() == null) {
            if (s.equals(tmp)) {
                this.table.set(index.intValue(), new Symbol());
            }
        } else if (tmp.getPrevious() != null) {
            while (tmp.getName() != null) {
                if (s.equals(tmp)) {
                    tmp.getPrevious().setNext(tmp.getNext());
                    break;
                } else {
                    tmp.getNext().setPrevious(tmp);
                    tmp = tmp.getNext();
                }
            }
        } else {
            if (tmp.getName().equals(s.getName())) {
            }
        }
    }

    public Symbol search(String search) {
        return search(search, null);
    }

    public Symbol search(String search, Integer level) {

        Symbol s = new Symbol(search);
        Integer index = this.hashFunction(s);

        s = this.table.get(index);

        if (isNotNull(search, s) && isSameLevel(level, s)) {
            if (s.getName().equals(search)) {
                s.setIndex(index);
                return s;
            } else {
                while (s.getName() != null) {
                    s = s.getNext();
                    if (s.getName() != null && s.getName().equals(search)) {
                        s.setIndex(index);
                        return s;
                    }
                }
            }
        }

        return null;
    }

    private boolean isNotNull(String search, Symbol s) {
        return (Objects.nonNull(search) && Objects.nonNull(s.getName()));
    }

    private boolean isSameLevel(Integer level, Symbol s) {
        return Objects.isNull(level) || s.getLevel().compareTo(level) == 0;
    }

    private int hashFunction(Symbol s) {

        int hashValue = 0;
        for (int i = 0; i < s.getName().length(); i++) {
            hashValue = 37 * hashValue + s.getName().charAt(i);
        }
        hashValue %= this.tableSize;
        if (hashValue < 0) {
            hashValue += this.tableSize;
        }

        return hashValue;

    }

    private Boolean isValid(Symbol s1, Symbol s2) {
        if (s1.getName().equals(s2.getName())) {
            if (s1.getLevel() == s2.getLevel()) {
                this.error = true;
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private Boolean isFirst(Symbol s) {
        return s.getNext() == null;
    }

    public List<Symbol> getTable() {
        return table;
    }

}
