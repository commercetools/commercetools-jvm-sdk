package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class SphereError extends Base {
    @JsonIgnore//forbid direct access to make error casting possible
    private final String code;
    private final String message;
    @JsonIgnore
    private final Map<String, JsonNode> furtherFields = new HashMap<>();

    @JsonCreator
    protected SphereError(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static SphereError of(final String code, final String message) {
        return new SphereError(code, message);
    }

    public <T extends SphereError> T as(final Class<T> errorClass) {
        final Optional<String> classErrorCodeOption = codeValueOf(errorClass);
        if (classErrorCodeOption.map(classErrCode -> classErrCode.equals(code)).orElse(true)) {
            final ObjectMapper objectMapper = SphereJsonUtils.newObjectMapper();
            final JsonNode jsonNode = objectMapper.createObjectNode()
                    .put("code", code)
                    .put("message", message)
                    .setAll(furtherFields);
            return SphereJsonUtils.readObject(jsonNode, errorClass);
        } else {
            throw new IllegalArgumentException(classErrorCodeOption.map(
                    code -> "Codes not matching: " + code + " is not " + getCode())
                    .orElse("error class not mathing code " + getCode()));
        }
    }

    private Optional<String> codeValueOf(final Class<?> errorClass) {
        final String fieldName = "CODE";
        final Field[] fields = errorClass.getFields();
        final Optional<Field> codeField = Arrays.stream(fields)
                .filter(field -> fieldName.equals(field.getName()) && field.getType().equals(String.class))
                .findAny();
        final Function<Field, Optional<String>> fieldUFunction = field -> {
            try {
                return Optional.ofNullable((String) field.get(errorClass));
            } catch (IllegalAccessException e) {
                return Optional.empty();
            }
        };
        return codeField.flatMap(fieldUFunction);
    }

    @JsonAnySetter
    private void set(final String key, final JsonNode value) {
        furtherFields.put(key, value);
    }
}
