package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.LocalizedStrings;

import javax.annotation.Nullable;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandTest#setName()}
 */
public class SetName extends UpdateActionImpl<DiscountCode> {
    @Nullable
    private final LocalizedStrings name;

    private SetName(@Nullable final LocalizedStrings name) {
        super("setName");
        this.name = name;
    }

    public static SetName of(@Nullable final LocalizedStrings name) {
        return new SetName(name);
    }

    @Nullable
    public LocalizedStrings getName() {
        return name;
    }
}