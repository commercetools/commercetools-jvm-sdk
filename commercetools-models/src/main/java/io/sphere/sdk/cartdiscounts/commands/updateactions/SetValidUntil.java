package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * Sets the valid until property.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandTest#setValidUntil()}
 */
public final class SetValidUntil extends UpdateActionImpl<CartDiscount> {
    @Nullable
    private final ZonedDateTime validUntil;

    private SetValidUntil(@Nullable final ZonedDateTime validUntil) {
        super("setValidUntil");
        this.validUntil = validUntil;
    }

    public static SetValidUntil of(@Nullable final ZonedDateTime validUntil) {
        return new SetValidUntil(validUntil);
    }

    @Nullable
    public ZonedDateTime getValidUntil() {
        return validUntil;
    }
}
