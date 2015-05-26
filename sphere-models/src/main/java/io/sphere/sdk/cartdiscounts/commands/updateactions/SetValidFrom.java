package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateAction;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandTest#setValidFrom()}
 */
public class SetValidFrom extends UpdateAction<CartDiscount> {
    @Nullable
    private final Instant validFrom;

    private SetValidFrom(final Optional<Instant> validFrom) {
        super("setValidFrom");
        this.validFrom = validFrom.orElse(null);
    }

    public static SetValidFrom of(final Optional<Instant> validFrom) {
        return new SetValidFrom(validFrom);
    }

    public static SetValidFrom of(final Instant validFrom) {
        return of(Optional.of(validFrom));
    }

    public Optional<Instant> getValidFrom() {
        return Optional.ofNullable(validFrom);
    }
}
