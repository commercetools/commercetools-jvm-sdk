package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

/**
 * Sets the amount of money that has been refunded to the customer. If no amount is provided, the amount is unset.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#refunded()}
 *
 * <p>Example with multiple refunds:</p>
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#multiRefund()}
 *
 * @see Payment
 *
 * @deprecated This update action will be removed with the next major SDK update.
 */
public final class SetAmountRefunded extends UpdateActionImpl<Payment> {
    @Nullable
    private final MonetaryAmount amount;

    private SetAmountRefunded(@Nullable final MonetaryAmount amount) {
        super("setAmountRefunded");
        this.amount = amount;
    }

    public static SetAmountRefunded of(@Nullable final MonetaryAmount amount) {
        return new SetAmountRefunded(amount);
    }

    @Nullable
    public MonetaryAmount getAmount() {
        return amount;
    }
}
