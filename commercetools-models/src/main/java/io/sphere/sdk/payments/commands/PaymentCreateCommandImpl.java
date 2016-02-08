package io.sphere.sdk.payments.commands;

import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.PaymentDraft;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;

final class PaymentCreateCommandImpl extends MetaModelCreateCommandImpl<Payment, PaymentCreateCommand, PaymentDraft, PaymentExpansionModel<Payment>> implements PaymentCreateCommand {

    PaymentCreateCommandImpl(final PaymentDraft PaymentDraft) {
        super(PaymentDraft, PaymentEndpoint.ENDPOINT, PaymentExpansionModel.of(), PaymentCreateCommandImpl::new);
    }

    PaymentCreateCommandImpl(final MetaModelCreateCommandBuilder<Payment, PaymentCreateCommand, PaymentDraft, PaymentExpansionModel<Payment>> builder) {
        super(builder);
    }
}
