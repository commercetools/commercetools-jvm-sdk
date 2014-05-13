package io.sphere.client.shop.model;

import javax.annotation.Nonnull;
import com.neovisionaries.i18n.CountryCode;

/** The location represents a country or a country with a state. */
public class Location {
    @Nonnull private CountryCode country;
    private String state;

    // for JSON deserializer
    private Location() {}

    public Location(@Nonnull CountryCode country, String state) {
        this.country = country;
        this.state = state;
    }

    public Location(@Nonnull CountryCode country) {
        this.country = country;
        this.state = null;
    }

    /** The country of the location. */
    @Nonnull public CountryCode getCountry() { return country; }

    /** The state of the location. */
    public String getState() { return state; }
    
    /** @return true if both the country and state match.. */
    public boolean matches(Location location) {
        boolean statesMatch;
        if (this.state == null || this.state.isEmpty())
            statesMatch = location.getState() == null || location.getState().isEmpty();
        else 
            statesMatch = this.state.equals(location.getState());
                
        return this.country == location.country && statesMatch;
    }

    @Override
    public String toString() {
        return "Location{" +
                "country=" + country +
                ", state='" + state + '\'' +
                '}';
    }

    /** The location of the address. */
    public static Location of(Address address) {
        return new Location(address.getCountry(), address.getState());
    }
}
