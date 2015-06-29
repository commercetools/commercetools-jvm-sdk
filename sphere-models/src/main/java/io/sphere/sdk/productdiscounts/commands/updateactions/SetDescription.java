package io.sphere.sdk.productdiscounts.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.productdiscounts.ProductDiscount;

import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommandTest#setDescription()}
 */
public class SetDescription extends UpdateAction<ProductDiscount> {
    private final Optional<LocalizedStrings> description;

    private SetDescription(final Optional<LocalizedStrings> description) {
        super("setDescription");
        this.description = description;
    }

    public static SetDescription of(final Optional<LocalizedStrings> description) {
        return new SetDescription(description);
    }

    public static SetDescription of(final LocalizedStrings description) {
        return of(Optional.of(description));
    }

    public Optional<LocalizedStrings> getDescription() {
        return description;
    }
}
