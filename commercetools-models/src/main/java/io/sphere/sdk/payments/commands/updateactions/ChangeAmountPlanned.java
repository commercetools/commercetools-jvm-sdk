package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

/**
 * Changes how much money this payment intends to receive from the customer.
 * The value usually matches the cart or order gross total.
 * Updating the amountPlanned may be required after a customer changed the cart or added/removed a cart discount during the checkout.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#changeAmountPlanned()}
 *
 * @see Payment
 *
 *
 */
public final class ChangeAmountPlanned extends UpdateActionImpl<Payment> {
    @Nullable
    private final MonetaryAmount amount;

    private ChangeAmountPlanned(@Nullable final MonetaryAmount amount) {
        super("changeAmountPlanned");
        this.amount = amount;
    }

    public static ChangeAmountPlanned of(@Nullable final MonetaryAmount amount) {
        return new ChangeAmountPlanned(amount);
    }

    @Nullable
    public MonetaryAmount getAmount() {
        return amount;
    }
}
