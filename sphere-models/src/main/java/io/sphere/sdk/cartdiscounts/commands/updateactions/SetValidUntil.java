package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateAction;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandTest#setValidUntil()}
 */
public class SetValidUntil extends UpdateAction<CartDiscount> {
    @Nullable
    private final ZonedDateTime validUntil;

    private SetValidUntil(final Optional<ZonedDateTime> validUntil) {
        super("setValidUntil");
        this.validUntil = validUntil.orElse(null);
    }

    public static SetValidUntil of(final Optional<ZonedDateTime> validUntil) {
        return new SetValidUntil(validUntil);
    }

    public static SetValidUntil of(final ZonedDateTime validUntil) {
        return of(Optional.of(validUntil));
    }

    public Optional<ZonedDateTime> getValidUntil() {
        return Optional.ofNullable(validUntil);
    }
}
