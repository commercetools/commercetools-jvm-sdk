package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

public class SetAmountPaid extends UpdateActionImpl<Payment> {
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
