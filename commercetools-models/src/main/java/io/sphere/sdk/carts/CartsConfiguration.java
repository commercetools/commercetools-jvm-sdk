package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = CartsConfigurationImpl.class)
public interface CartsConfiguration {
    
    Integer getDeleteDaysAfterLastModification();
    Boolean getCountryTaxRateFallbackEnabled();

    static CartsConfiguration of(final Integer deleteDaysAfterLastModification) {
        return CartsConfigurationImpl.of(deleteDaysAfterLastModification);
    }

    static CartsConfiguration of(final Boolean countryTaxRateFallbackEnabled) {
        return CartsConfigurationImpl.of(countryTaxRateFallbackEnabled);
    }
}