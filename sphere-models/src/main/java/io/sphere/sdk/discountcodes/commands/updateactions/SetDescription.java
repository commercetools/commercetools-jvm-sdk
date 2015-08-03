package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.LocalizedStrings;

import javax.annotation.Nullable;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandTest#setDescription()}
 */
public class SetDescription extends UpdateActionImpl<DiscountCode> {
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