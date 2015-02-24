package io.sphere.sdk.errors;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SphereError extends Base {
    private final String code;
    private final String message;
    @JsonIgnore
    private final Map<String, JsonNode> furtherFields = new HashMap<>();

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

    public <T extends SphereError> Optional<T> as(final Class<T> errorClass) {
        final ObjectMapper objectMapper = JsonUtils.newObjectMapper();
        final JsonNode jsonNode = objectMapper.createObjectNode()
                .put("code", code)
                .put("message", message)
                .setAll(furtherFields);
        Optional<T> result = Optional.empty();
        try {
            final T object = JsonUtils.readObject(errorClass, jsonNode);
            result = Optional.of(object);
        } catch (final JsonException e) {
            result = Optional.empty();
        }
        return result;
    }

    @JsonAnySetter
    private void set(final String key, final JsonNode value) {
        furtherFields.put(key, value);
    }
}
