package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;

/**
 * Changes the name of the discount.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandIntegrationTest#changeName()}
 */
public final class ChangeName extends UpdateActionImpl<CartDiscount> {
    private final LocalizedString name;

    private ChangeName(final LocalizedString name) {
        super("changeName");
        this.name = name;
    }

    public static ChangeName of(final LocalizedString name) {
        return new ChangeName(name);
    }

    public LocalizedString getName() {
        return name;
    }
}
