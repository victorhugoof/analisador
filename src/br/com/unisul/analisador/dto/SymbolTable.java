package br.com.unisul.analisador.dto;

import java.util.ArrayList;
import java.util.Objects;

public class SymbolTable {

	private ArrayList<Symbol> table;
	private Integer tableSize;
	private Boolean error;

	public SymbolTable(Integer elements) {
		this.table = new ArrayList<Symbol>();
		this.tableSize = elements;
		this.error = false;
		this.initialize();
	}

	public void initialize() {
		for (int i = 0; i < this.tableSize; i++) {
			this.table.add(new Symbol());
		}
	}

	public void insert(Symbol s) {

		Integer index = this.hashFunction(s);
		Symbol tmp = new Symbol();

		tmp = this.table.get(index);

		if (this.isFirst(tmp)) {

			s.setNext(new Symbol());
			s.getNext().setPrevious(s);

			this.table.set(index, s);
			// System.out.println("> Inserindo na tabela de símbolos: \"" + s.getName() + "\" Level " + s.getLevel());

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

				// System.out.println("> Inserindo na tabela de símbolos: " + s.getName() + " Level " + s.getLevel());
			}
		}

		this.error = false;
	}

	public void delete(Symbol s) {

		Integer index = this.hashFunction(s);
		Symbol tmp = this.table.get(index);

		if (tmp.getNext().getName() == null) {

			if (this.isEquals(s, tmp)) {

				this.table.set(index.intValue(), new Symbol());
				// System.out.println("> Removendo da tabela de símbolos: " + s.getName() + " Level " + s.getLevel());
			}

		} else if (tmp.getPrevious() != null) {

			while (tmp.getName() != null) {

				if (this.isEquals(s, tmp)) {

					tmp.getPrevious().setNext(tmp.getNext());
					// System.out.println("> Removendo da tabela de símbolos: " + s.getName() + " Level " + s.getLevel());

					break;

				} else {
					tmp.getNext().setPrevious(tmp);
					tmp = tmp.getNext();
				}
			}

		} else {

			this.table.set(index, tmp.getNext());
			// System.out.println("> Removendo da tabela de símbolos: " + s.getName() + " Level " + s.getLevel());

		}
	}

	public void update(Symbol s, Symbol old) {

		Integer index = this.hashFunction(old);
		Symbol tmp = this.table.get(index);

		while (tmp.getName() != null) {

			if (tmp.equals(old)) {

				this.delete(old);
				this.insert(s);

				break;

			} else {

				tmp.getNext().setPrevious(tmp);
				tmp = tmp.getNext();

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

				// this.printSymbol(s);
				s.setIndex(index);
				return s;

			} else {

				while (s.getName() != null) {

					s = s.getNext();

					if (s.getName() != null && s.getName().equals(search)) {
						// this.printSymbol(s);
						s.setIndex(index);
						return s;
					}
				}
			}
		}

		// System.err.println("Elemento \"" + search + "\" não encontrado! \n");

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

		if (hashValue < 0)
			hashValue += this.tableSize;

		return hashValue;

	}

	private Boolean isValid(Symbol s1, Symbol s2) {

		if (s1.getName().equals(s2.getName())) {

			if (s1.getLevel() == s2.getLevel()) {
				// System.err.println("Símbolo com mesmo nome e nível já existente! \n");
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

		if (s.getNext() != null)
			return false;
		else
			return true;
	}

	private Boolean isEquals(Symbol s1, Symbol s2) {

		if (s1.getName().equals(s2.getName())) {

			if (s1.getLevel() == s2.getLevel()) {
				return true;

			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	public Symbol clone(Symbol s) {

		Symbol tmp = new Symbol();

		tmp.setName(s.getName());
		tmp.setLevel(s.getLevel());
		tmp.setCategory(s.getCategory());
		tmp.setGeneralA(s.getGeneralA());
		tmp.setGeneralB(s.getGeneralB());
		tmp.setNext(s.getNext());
		tmp.setPrevious(s.getPrevious());

		return tmp;
	}

	public void print() {

		String msg = "\n";
		Symbol aux;

		for (int i = 0; i < this.table.size(); i++) {

			aux = this.table.get(i);
			msg += i + " - ";

			if (aux != null) {

				while (aux.getNext() != null) {

					msg += aux.getName() + " Level " + aux.getLevel() + " > ";
					aux = aux.getNext();
				}
			}
			msg += "\n";
		}

		System.out.println(msg);
	}

	// private void printSymbol(Symbol s) {

	// 	System.out.println("Name: " + s.getName());
	// 	System.out.println("Category: " + s.getCategory());
	// 	System.out.println("General A: " + s.getGeneralA());
	// 	System.out.println("General B: " + s.getGeneralB());
	// 	System.out.println("Level: " + s.getLevel());
	// 	System.out.println("Next " + s.getNext().getName());
	// 	System.out.println("Previous: " + s.getPrevious().getName());
	// 	System.out.println();

	// }

	public ArrayList<Symbol> getTable() {
		return table;
	}

	public void setTable(ArrayList<Symbol> table) {
		this.table = table;
	}

	public Integer getTableSize() {
		return tableSize;
	}

	public void setTableSize(Integer tableSize) {
		this.tableSize = tableSize;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}
}
