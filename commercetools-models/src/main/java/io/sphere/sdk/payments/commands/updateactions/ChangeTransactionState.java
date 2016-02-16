package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.TransactionState;


/**
 * Changes state of a transaction. If a transaction has been executed asynchronously, it’s state can be updated. E.g. if a Refund was executed, the state can be set to Success.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#changeTransactionState()}
 *
 */
public final class ChangeTransactionState extends UpdateActionImpl<Payment> {
    private final TransactionState state;
    private final String transactionId;

    private ChangeTransactionState(final TransactionState state, final String transactionId) {
        super("changeTransactionState");
        this.state = state;
        this.transactionId = transactionId;
    }

    public static ChangeTransactionState of(final TransactionState state, final String transactionId) {
        return new ChangeTransactionState(state, transactionId);
    }

    public TransactionState getState() {
        return state;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
