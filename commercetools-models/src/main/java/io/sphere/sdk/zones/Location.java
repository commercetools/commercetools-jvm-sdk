package io.sphere.sdk.zones;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nullable;

/**
 * A geographical location representing a country with an optional state.
 */
@JsonDeserialize(as = LocationImpl.class)
public interface Location {
    CountryCode getCountry();

    @Nullable
    String getState();

    @JsonIgnore
    static Location of(final CountryCode countryCode, @Nullable final String state) {
        return new LocationImpl(countryCode, state);
    }

    @JsonIgnore
    static Location of(final CountryCode countryCode) {
        return of(countryCode, null);
    }
}
