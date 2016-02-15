package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

import java.time.ZonedDateTime;

/**
 *Changes timestamp of a transaction. If this transaction represents an action at the PSP, the time returned by the PSP should be used.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#changeTransactionTimestamp()}
 *
 */
public final class ChangeTransactionTimestamp extends UpdateActionImpl<Payment> {
    private final ZonedDateTime timestamp;
    private final String transactionId;

    private ChangeTransactionTimestamp(final ZonedDateTime timestamp, final String transactionId) {
        super("changeTransactionTimestamp");
        this.timestamp = timestamp;
        this.transactionId = transactionId;
    }

    public static ChangeTransactionTimestamp of(final ZonedDateTime timestamp, final String transactionId) {
        return new ChangeTransactionTimestamp(timestamp, transactionId);
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
