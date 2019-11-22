package br.com.unisul.analisador.dto;

public enum GeneralA {

    DISPLACEMENT("Deslocamento"),
    DECIMAL_VALUE("Valor na base decimal"),
    INSTRUCTIONS_AREA("Endereço de alocação na área de instruções");

    private final String label;

    GeneralA(String l) {
        this.label = l;
    }

    public String getLabel() {
        return this.label;
    }

}
