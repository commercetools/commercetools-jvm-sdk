package io.sphere.sdk.payments.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;

final class PaymentDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<Payment, PaymentDeleteCommand, PaymentExpansionModel<Payment>> implements PaymentDeleteCommand {

    PaymentDeleteCommandImpl(final Versioned<Payment> versioned) {
        super(versioned, PaymentEndpoint.ENDPOINT, PaymentExpansionModel.of(), PaymentDeleteCommandImpl::new);
    }

    PaymentDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<Payment, PaymentDeleteCommand, PaymentExpansionModel<Payment>> builder) {
        super(builder);
    }
}
