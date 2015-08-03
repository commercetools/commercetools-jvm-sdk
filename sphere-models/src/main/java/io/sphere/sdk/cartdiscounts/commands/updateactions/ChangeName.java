package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedStrings;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandTest#changeName()}
 */
public class ChangeName extends UpdateActionImpl<CartDiscount> {
    private final LocalizedStrings name;

    private ChangeName(final LocalizedStrings name) {
        super("changeName");
        this.name = name;
    }

    public static ChangeName of(final LocalizedStrings name) {
        return new ChangeName(name);
    }

    public LocalizedStrings getName() {
        return name;
    }
}
