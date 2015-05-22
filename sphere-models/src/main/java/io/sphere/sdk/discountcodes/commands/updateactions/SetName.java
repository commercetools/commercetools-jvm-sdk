package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.LocalizedStrings;

import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandTest#setName()}
 */
public class SetName extends UpdateAction<DiscountCode> {
    private final Optional<LocalizedStrings> name;

    private SetName(final Optional<LocalizedStrings> name) {
        super("setName");
        this.name = name;
    }

    public static SetName of(final LocalizedStrings name) {
        return of(Optional.of(name));
    }

    public static SetName of(final Optional<LocalizedStrings> name) {
        return new SetName(name);
    }

    public Optional<LocalizedStrings> getName() {
        return name;
    }
}