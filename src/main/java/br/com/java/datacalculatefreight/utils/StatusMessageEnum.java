package br.com.java.datacalculatefreight.utils;

public enum StatusMessageEnum {
    SUCCESS("Sucesso");

    private String value;

    StatusMessageEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
