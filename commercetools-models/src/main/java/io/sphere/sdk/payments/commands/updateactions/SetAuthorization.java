package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

/**
 * Sets the amount of money that has been authorized and optionally until when the authorization is valid. If no amount is provided, the authorization is unset.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#setAuthorization()}
 *
 * @see Payment
 *
 * @deprecated This update action will be removed with the next major SDK update.
 */
public final class SetAuthorization extends UpdateActionImpl<Payment> {
    @Nullable
    private final MonetaryAmount amount;
    @Nullable
    private final ZonedDateTime until;

    private SetAuthorization(@Nullable final MonetaryAmount amount, @Nullable final ZonedDateTime until) {
        super("setAuthorization");
        this.amount = amount;
        this.until = until;
    }

    public static SetAuthorization of(@Nullable final MonetaryAmount amount) {
        return of(amount, null);
    }

    public static SetAuthorization of(@Nullable final MonetaryAmount amount, @Nullable final ZonedDateTime until) {
        return new SetAuthorization(amount, until);
    }

    @Nullable
    public MonetaryAmount getAmount() {
        return amount;
    }

    @Nullable
    public ZonedDateTime getUntil() {
        return until;
    }

    public static SetAuthorization ofRemove() {
        return of(null, null);
    }
}
