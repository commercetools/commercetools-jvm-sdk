package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

/**
 * Sets the description of the discount.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandTest#setDescription()}
 */
public final class SetDescription extends UpdateActionImpl<CartDiscount> {
    @Nullable
    private final LocalizedString description;

    private SetDescription(@Nullable final LocalizedString description) {
        super("setDescription");
        this.description = description;
    }

    public static SetDescription of(@Nullable final LocalizedString description) {
        return new SetDescription(description);
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }
}
