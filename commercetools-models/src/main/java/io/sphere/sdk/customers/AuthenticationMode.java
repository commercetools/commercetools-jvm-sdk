package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonValue;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * AuthenticationMode.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 */
public enum AuthenticationMode {
    PASSWORD("Password"),
    EXTERNAL_AUTH("ExternalAuth");

    private final String jsonValue;

    private AuthenticationMode(final String json) {
        this.jsonValue = json;
    }

    @JsonValue
    public String jsonValue() {
        return this.jsonValue;
    }
}
