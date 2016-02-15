package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

/**
 *
 * Sets the description.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandIntegrationTest#setDescription()}
 */
public final class SetDescription extends UpdateActionImpl<DiscountCode> {
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