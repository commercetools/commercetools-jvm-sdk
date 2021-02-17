package io.sphere.sdk.customers.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.customers.commands.CustomerChangePasswordCommand;
import io.sphere.sdk.models.errors.SphereError;

public final class CustomerMissingTaxRateForCountry extends SphereError {
    public static final String CODE = "MissingTaxRateForCountry";

    @JsonCreator
    private CustomerMissingTaxRateForCountry(final String message) {
        super(CODE, message);
    }

    public static CustomerMissingTaxRateForCountry of(final String message) {
        return new CustomerMissingTaxRateForCountry(message);
    }
}
