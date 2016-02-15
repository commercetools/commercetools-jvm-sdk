package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

/**
 * Sets the name of the discount code.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandIntegrationTest#setName()}
 */
public final class SetName extends UpdateActionImpl<DiscountCode> {
    @Nullable
    private final LocalizedString name;

    private SetName(@Nullable final LocalizedString name) {
        super("setName");
        this.name = name;
    }

    public static SetName of(@Nullable final LocalizedString name) {
        return new SetName(name);
    }

    @Nullable
    public LocalizedString getName() {
        return name;
    }
}