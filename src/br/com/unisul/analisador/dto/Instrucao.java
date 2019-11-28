package br.com.unisul.analisador.dto;

public class Instrucao {

    private Integer posicao;
    private String nome;
    private String operador1;
    private String operador2;

    public Instrucao(Integer posicao, String nome, String op1, String op2) {

        this.posicao = posicao;
        this.nome = nome;
        this.operador1 = op1;
        this.operador2 = op2;
    }

    public Integer getPosicao() {
        return this.posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOperador1() {
        return this.operador1;
    }

    public void setOperador1(String operador1) {
        this.operador1 = operador1;
    }

    public Integer getOp1() {
        return Integer.parseInt(this.operador1);
    }

    public String getOperador2() {
        return this.operador2;
    }

    public void setOperador2(String operador2) {
        this.operador2 = operador2;
    }

    public Integer getOp2() {
        return Integer.parseInt(this.operador2);
    }
    
    @Override
    public String toString() {
    	
    	return nome + "-" + operador1 + "-" + operador2;
    }
}
