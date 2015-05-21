package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandTest#setDescription()}
 */
public class SetDescription extends UpdateAction<CartDiscount> {
    @Nullable
    private final LocalizedStrings description;

    private SetDescription(final Optional<LocalizedStrings> description) {
        super("setDescription");
        this.description = description.orElse(null);
    }

    public static SetDescription of(final Optional<LocalizedStrings> description) {
        return new SetDescription(description);
    }

    public static SetDescription of(final LocalizedStrings description) {
        return of(Optional.of(description));
    }

    public Optional<LocalizedStrings> getDescription() {
        return Optional.ofNullable(description);
    }
}
