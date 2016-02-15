package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.TransactionDraft;

/**
 * Adds a new financial transaction. Transactions should only be set if a notification from the PSP was received that a transaction was completed successfully. E.g. in the case of a refund, the transaction is not added when the refund-request is being sent to the PSP or the PSP acknowledges the request (the interfaceInteractions may be used for these), but when the PSP sends a notification that the refund has been executed.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#transActions()}
 *
 *  @see Payment#getTransactions()
 *  @see io.sphere.sdk.payments.messages.PaymentTransactionAddedMessage
 */
public final class AddTransaction extends UpdateActionImpl<Payment> {
    private final TransactionDraft transaction;

    private AddTransaction(final TransactionDraft transaction) {
        super("addTransaction");
        this.transaction = transaction;
    }

    public static AddTransaction of(final TransactionDraft transaction) {
        return new AddTransaction(transaction);
    }

    public TransactionDraft getTransaction() {
        return transaction;
    }
}
