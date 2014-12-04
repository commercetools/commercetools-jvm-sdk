package io.sphere.sdk.carts.commands.updateactions;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateAction;

import java.util.Optional;

/**
 Sets the country of the cart. When the country is set, the line item prices are updated.

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#setCountry()}

 */
public class SetCountry extends UpdateAction<Cart> {
    private final Optional<CountryCode> country;

    private SetCountry(final Optional<CountryCode> country) {
        super("setCountry");
        this.country = country;
    }

    public static SetCountry of(final Optional<CountryCode> country) {
        return new SetCountry(country);
    }

    public static SetCountry of(final CountryCode country) {
        return of(Optional.of(country));
    }

    public Optional<CountryCode> getCountry() {
        return country;
    }
}
