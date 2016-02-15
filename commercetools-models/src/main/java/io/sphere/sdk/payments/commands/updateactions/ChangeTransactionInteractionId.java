package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

/**
 * Changes the interactionId of a transaction. It should correspond to an Id of an interface interaction.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#changeTransactionInteractionId()}
 */
public final class ChangeTransactionInteractionId extends UpdateActionImpl<Payment> {
    private String interactionId;
    private String transactionId;

    private ChangeTransactionInteractionId(final String interactionId, final String transactionId) {
        super("changeTransactionInteractionId");
        this.interactionId = interactionId;
        this.transactionId = transactionId;
    }

    public static ChangeTransactionInteractionId of(final String interactionId, final String transactionId) {
        return new ChangeTransactionInteractionId(interactionId, transactionId);
    }

    public String getInteractionId() {
        return interactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
