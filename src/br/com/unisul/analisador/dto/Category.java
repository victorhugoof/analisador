package br.com.unisul.analisador.dto;

public enum Category {
	
	VAR("Variável"),
	CONSTANT("Constante"),
	PROCEDURE("Procedure"),
	PARAMETER("Parâmetro");

	
    private String label;
    
    Category(String l) {
        this.label = l;
    }
 
	public String getLabel() {
        return this.label;
    }
    
}
