package io.sphere.sdk.orders.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class MissingTaxRateForCountryError extends SphereError {
    public static final String CODE = "MissingTaxRateForCountry";
    private final String taxCategoryId;
    private final String country;
    private final String state;


    @JsonCreator
    private MissingTaxRateForCountryError(final String message, final String taxCategoryId, final String country, final String state) {
        super(CODE, message);
        this.taxCategoryId = taxCategoryId;
        this.country = country;
        this.state = state;
    }

    public static MissingTaxRateForCountryError of(final String message, final String taxCategoryId, final String country, final String state) {
        return new MissingTaxRateForCountryError(message, taxCategoryId, country, state);
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getTaxCategoryId() {
        return taxCategoryId;
    }

}
