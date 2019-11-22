package br.com.unisul.analisador.dto;

public class Symbol {

	private String name;
	private Category category;
	private Integer level;
	private Integer generalA;
	private Integer generalB;
	private Integer index;
	private Symbol next;
	private Symbol previous;

	public Symbol() {
	}

	public Symbol(String n) {
		this.name = n;
	}

	public Symbol(String n, Category c, Integer l, Integer ga, Integer gb) {
		this.name = n;
		this.category = c;
		this.level = l;
		this.generalA = ga;
		this.generalB = gb;
	}

	public String getName() {
		return name;
	}

	public Category getCategory() {
		return category;
	}

	public Integer getLevel() {
		return level;
	}

	public Integer getGeneralA() {
		return generalA;
	}

	public Integer getGeneralB() {
		return generalB;
	}

	public Integer getIndex() {
		return index;
	}

	public Symbol getNext() {
		return next;
	}

	public Symbol getPrevious() {
		return previous;
	}

	public void setNext(Symbol next) {
		this.next = next;
	}

	public void setPrevious(Symbol previous) {
		this.previous = previous;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void setGeneralA(Integer generalA) {
		this.generalA = generalA;
	}

	public void setGeneralB(Integer generalB) {
		this.generalB = generalB;
	}
}
