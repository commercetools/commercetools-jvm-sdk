package io.sphere.sdk.payments.commands;

import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.PaymentDraft;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;

/**
 * Creates a payment.
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentCreateCommandIntegrationTest#payingPerCreditCart()}
 *
 * @see Payment
 * @see io.sphere.sdk.payments.messages.PaymentCreatedMessage
 */
public interface PaymentCreateCommand extends DraftBasedCreateCommand<Payment, PaymentDraft>, MetaModelReferenceExpansionDsl<Payment, PaymentCreateCommand, PaymentExpansionModel<Payment>> {

    static PaymentCreateCommand of(final PaymentDraft PaymentDraft) {
        return new PaymentCreateCommandImpl(PaymentDraft);
    }
}
