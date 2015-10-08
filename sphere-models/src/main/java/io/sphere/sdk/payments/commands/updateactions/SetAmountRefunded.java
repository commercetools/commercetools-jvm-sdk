package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

public class SetAmountRefunded extends UpdateActionImpl<Payment> {
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
