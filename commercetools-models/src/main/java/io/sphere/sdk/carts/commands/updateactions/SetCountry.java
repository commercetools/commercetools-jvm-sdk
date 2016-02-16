package io.sphere.sdk.carts.commands.updateactions;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

/**
 Sets the country of the cart. When the country is set, the line item prices are updated.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#setCountry()}

 */
public final class SetCountry extends UpdateActionImpl<Cart> {
    @Nullable
    private final CountryCode country;

    private SetCountry(@Nullable final CountryCode country) {
        super("setCountry");
        this.country = country;
    }

    public static SetCountry of(@Nullable final CountryCode country) {
        return new SetCountry(country);
    }

    @Nullable
    public CountryCode getCountry() {
        return country;
    }
}
