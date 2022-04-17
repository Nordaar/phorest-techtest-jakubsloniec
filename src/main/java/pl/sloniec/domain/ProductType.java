package pl.sloniec.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat
public enum ProductType {
    SERVICE("service"),
    PURCHASE("purchase");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ProductType fromValue(String value) {
        return ProductType.valueOf(value.toUpperCase());
    }
}
