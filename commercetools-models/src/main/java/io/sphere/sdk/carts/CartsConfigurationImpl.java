package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

class CartsConfigurationImpl extends Base implements CartsConfiguration {
    
    private Integer deleteDaysAfterLastModification;
    private Boolean countryTaxRateFallbackEnabled;

    @JsonCreator
    public CartsConfigurationImpl(Integer deleteDaysAfterLastModification) {
        this.deleteDaysAfterLastModification = deleteDaysAfterLastModification;
    }

    public CartsConfigurationImpl(Boolean countryTaxRateFallbackEnabled) {
        this.countryTaxRateFallbackEnabled = countryTaxRateFallbackEnabled;
    }

    @Override
    public Integer getDeleteDaysAfterLastModification() {
        return this.deleteDaysAfterLastModification;
    }

    @Override
    public Boolean getCountryTaxRateFallbackEnabled() {
        return this.countryTaxRateFallbackEnabled;
    }
    
    static CartsConfiguration of(final Integer deleteDaysAfterLastModification) {
        return new CartsConfigurationImpl(deleteDaysAfterLastModification);
    }

    static CartsConfiguration of(final Boolean countryTaxRateFallbackEnabled) {
        return new CartsConfigurationImpl(countryTaxRateFallbackEnabled);
    }
}
