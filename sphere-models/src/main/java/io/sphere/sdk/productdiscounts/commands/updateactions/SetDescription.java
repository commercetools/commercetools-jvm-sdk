package io.sphere.sdk.productdiscounts.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.productdiscounts.ProductDiscount;

import javax.annotation.Nullable;

/**
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommandTest#setDescription()}
 */
public class SetDescription extends UpdateAction<ProductDiscount> {
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
