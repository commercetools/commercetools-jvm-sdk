package io.sphere.sdk.shippingmethods.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.shippingmethods.ShippingMethod;

import java.util.Optional;

/**
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommandTest#setDescription()}
 */
public class SetDescription extends UpdateAction<ShippingMethod> {
    private final Optional<String> description;

    private SetDescription(final Optional<String> description) {
        super("setDescription");
        this.description = description;
    }

    public static SetDescription of(final String description) {
        return of(Optional.of(description));
    }

    public static SetDescription of(final Optional<String> description) {
        return new SetDescription(description);
    }

    public Optional<String> getDescription() {
        return description;
    }
}
