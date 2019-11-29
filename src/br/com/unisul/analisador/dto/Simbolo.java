package br.com.unisul.analisador.dto;

import java.util.Objects;

public class Simbolo {

    private String nome;
    private Categoria categoria;
    private Integer nivel;
    private Integer geralA;
    private Integer geralB;
    private Integer index;
    private Simbolo proximo;
    private Simbolo anterior;

    public Simbolo() {
    }

    public Simbolo(String n) {
        this.nome = n;
    }

    public Simbolo(String n, Categoria c, Integer nivel, Integer ga, Integer gb) {
        this.nome = n;
        this.categoria = c;
        this.nivel = nivel;
        this.geralA = ga;
        this.geralB = gb;
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Integer getNivel() {
        return nivel;
    }

    public Integer getGeralA() {
        return geralA;
    }

    public void setGeralA(Integer geralA) {
        this.geralA = geralA;
    }

    public Integer getGeralB() {
        return geralB;
    }

    public void setGeralB(Integer geralB) {
        this.geralB = geralB;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Simbolo getProximo() {
        return proximo;
    }

    public void setProximo(Simbolo proximo) {
        this.proximo = proximo;
    }

    public Simbolo getAnterior() {
        return anterior;
    }

    public void setAnterior(Simbolo anterior) {
        this.anterior = anterior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simbolo symbol = (Simbolo) o;
        return Objects.equals(nome, symbol.nome) &&
                Objects.equals(nivel, symbol.nivel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, nivel);
    }
}
