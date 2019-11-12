package br.com.unisul.analisador.dto;

public class Instruction {

    private Integer index;
    private String name;
    private String operator1;
    private String operator2;

    public Instruction (Integer i, String n, String op1, String op2) {

        this.index = i;
        this.name = n;
        this.operator1 = op1;
        this.operator2 = op2;
    }

    public Integer getIndex() {
		return this.index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOperator1() {
        return this.operator1;
    }
    public Integer getOp1() {
        return Integer.parseInt(this.operator1);
    }
    public void setOperator1(String operator1) {
        this.operator1 = operator1;
    }
    public String getOperator2() {
        return this.operator2;
    }
    public Integer getOp2() {
        return Integer.parseInt(this.operator2);
    }
    public void setOperator2(String operator2) {
        this.operator2 = operator2;
    }
}
