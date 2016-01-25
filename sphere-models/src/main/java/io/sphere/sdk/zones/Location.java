package io.sphere.sdk.zones;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

/**
 * A geographical location representing a country with an optional state.
 */
public class Location extends Base {
    private final CountryCode country;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final String state;

    @JsonCreator
    private Location(final CountryCode country, @Nullable final String state) {
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

    @JsonIgnore
    public static Location of(final CountryCode countryCode, @Nullable final String state) {
        return new Location(countryCode, state);
    }

    @JsonIgnore
    public static Location of(final CountryCode countryCode) {
        return of(countryCode, null);
    }
}
