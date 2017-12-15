package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

/**
 * Sets the amount of money that has been paid by the customer. If no amount is provided, the amount is unset.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#setAuthorization()} <!-- sic! -->
 *
 * @see Payment
 *
 * @deprecated This update action will be removed with the next major SDK update.
 */
@Deprecated
public final class SetAmountPaid extends UpdateActionImpl<Payment> {
    @Nullable
    private final MonetaryAmount amount;

    private SetAmountPaid(@Nullable final MonetaryAmount amount) {
        super("setAmountPaid");
        this.amount = amount;
    }

    public static SetAmountPaid of(@Nullable final MonetaryAmount amount) {
        return new SetAmountPaid(amount);
    }

    @Nullable
    public MonetaryAmount getAmount() {
        return amount;
    }
}
