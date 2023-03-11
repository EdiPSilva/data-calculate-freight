package br.com.java.datacalculatefreight.configuration;

public enum MessageCodeEnum {

    INVALID_REQUEST_DEFAULT_MESSAGE("invalid.request.default.message"),
    NAME_NOT_BLANK("name.not.blank");

    private String value;

    MessageCodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
