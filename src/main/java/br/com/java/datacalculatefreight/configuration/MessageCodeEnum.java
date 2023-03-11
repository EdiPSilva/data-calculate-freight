package br.com.java.datacalculatefreight.configuration;

public enum MessageCodeEnum {

    INVALID_REQUEST_DEFAULT_MESSAGE("invalid.request.default.message"),
    INVALID_ID("invalid.id"),
    REGISTER_NOT_FOUND("register.not.found");

    private String value;

    MessageCodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
