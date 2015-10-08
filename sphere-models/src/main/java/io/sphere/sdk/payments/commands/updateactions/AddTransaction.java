package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.Transaction;

public class AddTransaction extends UpdateActionImpl<Payment> {
    private final Transaction transaction;

    private AddTransaction(final Transaction transaction) {
        super("addTransaction");
        this.transaction = transaction;
    }

    public static AddTransaction of(final Transaction transaction) {
        return new AddTransaction(transaction);
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
