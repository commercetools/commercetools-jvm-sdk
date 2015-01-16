package io.sphere.sdk.zones;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;

import java.util.Optional;

/**
 * A geographical location representing a country with an optional state.
 */
public class Location extends Base {
    private final CountryCode country;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Optional<String> state;

    @JsonCreator
    private Location(final CountryCode country, final Optional<String> state) {
        this.country = country;
        this.state = state;
    }

    public CountryCode getCountry() {
        return country;
    }

    public Optional<String> getState() {
        return state;
    }

    @JsonIgnore
    public static Location of(final CountryCode countryCode, final Optional<String> state) {
        return new Location(countryCode, state);
    }

    @JsonIgnore
    public static Location of(final CountryCode countryCode, final String state) {
        return new Location(countryCode, Optional.of(state));
    }

    @JsonIgnore
    public static Location of(final CountryCode countryCode) {
        return of(countryCode, Optional.empty());
    }
}
