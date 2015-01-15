package io.sphere.sdk.zones;

import com.neovisionaries.i18n.CountryCode;

import java.util.Optional;

/**
 * A geographical location representing a country with an optional state.
 */
public class Location {
    private final CountryCode countryCode;
    private final Optional<String> state;

    private Location(final CountryCode countryCode, final Optional<String> state) {
        this.countryCode = countryCode;
        this.state = state;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public Optional<String> getState() {
        return state;
    }

    public static Location of(final CountryCode countryCode, final Optional<String> state) {
        return new Location(countryCode, state);
    }

    public static Location of(final CountryCode countryCode, final String state) {
        return new Location(countryCode, Optional.of(state));
    }

    public static Location of(final CountryCode countryCode) {
        return of(countryCode, Optional.empty());
    }
}
