package br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.persistence;

public enum CalculationTypeEnum {

    CUBAGE("CUBAGE"),
    WEIGHT("WEIGHT");

    private String value;

    CalculationTypeEnum(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
