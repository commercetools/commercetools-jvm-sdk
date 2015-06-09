package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateAction;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandTest#setValidFrom()}
 */
public class SetValidFrom extends UpdateAction<CartDiscount> {
    @Nullable
    private final ZonedDateTime validFrom;

    private SetValidFrom(final Optional<ZonedDateTime> validFrom) {
        super("setValidFrom");
        this.validFrom = validFrom.orElse(null);
    }

    public static SetValidFrom of(final Optional<ZonedDateTime> validFrom) {
        return new SetValidFrom(validFrom);
    }

    public static SetValidFrom of(final ZonedDateTime validFrom) {
        return of(Optional.of(validFrom));
    }

    public Optional<ZonedDateTime> getValidFrom() {
        return Optional.ofNullable(validFrom);
    }
}
