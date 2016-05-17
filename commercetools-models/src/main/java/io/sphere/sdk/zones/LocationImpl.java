package io.sphere.sdk.zones;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

final class LocationImpl extends Base implements Location {
    private final CountryCode country;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final String state;

    @JsonCreator
    LocationImpl(final CountryCode country, @Nullable final String state) {
        this.country = country;
        this.state = state;
    }

    public CountryCode getCountry() {
        return country;
    }

    @Nullable
    public String getState() {
        return state;
    }
}
