package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateAction;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandTest#setValidUntil()}
 */
public class SetValidUntil extends UpdateAction<CartDiscount> {
    @Nullable
    private final Instant validUntil;

    private SetValidUntil(final Optional<Instant> validUntil) {
        super("setValidUntil");
        this.validUntil = validUntil.orElse(null);
    }

    public static SetValidUntil of(final Optional<Instant> validUntil) {
        return new SetValidUntil(validUntil);
    }

    public static SetValidUntil of(final Instant validUntil) {
        return of(Optional.of(validUntil));
    }

    public Optional<Instant> getValidUntil() {
        return Optional.ofNullable(validUntil);
    }
}
