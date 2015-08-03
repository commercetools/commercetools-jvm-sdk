package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedStrings;

import javax.annotation.Nullable;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandTest#setDescription()}
 */
public class SetDescription extends UpdateActionImpl<CartDiscount> {
    @Nullable
    private final LocalizedStrings description;

    private SetDescription(@Nullable final LocalizedStrings description) {
        super("setDescription");
        this.description = description;
    }

    public static SetDescription of(@Nullable final LocalizedStrings description) {
        return new SetDescription(description);
    }

    @Nullable
    public LocalizedStrings getDescription() {
        return description;
    }
}
