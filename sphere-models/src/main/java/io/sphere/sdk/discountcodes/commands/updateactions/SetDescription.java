package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.LocalizedStrings;

import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandTest#setDescription()}
 */
public class SetDescription extends UpdateAction<DiscountCode> {
    private final Optional<LocalizedStrings> description;

    private SetDescription(final Optional<LocalizedStrings> description) {
        super("setDescription");
        this.description = description;
    }

    public static SetDescription of(final LocalizedStrings description) {
        return of(Optional.of(description));
    }

    public static SetDescription of(final Optional<LocalizedStrings> description) {
        return new SetDescription(description);
    }

    public Optional<LocalizedStrings> getDescription() {
        return description;
    }
}