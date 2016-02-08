package io.sphere.sdk.payments.commands;

import io.sphere.sdk.commands.MetaModelUpdateCommandDslBuilder;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslImpl;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;

import java.util.List;

final class PaymentUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<Payment, PaymentUpdateCommand, PaymentExpansionModel<Payment>> implements PaymentUpdateCommand {

    PaymentUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<Payment, PaymentUpdateCommand, PaymentExpansionModel<Payment>> builder) {
        super(builder);
    }

    PaymentUpdateCommandImpl(final Versioned<Payment> versioned, final List<? extends UpdateAction<Payment>> updateActions) {
        super(versioned, updateActions, PaymentEndpoint.ENDPOINT, PaymentUpdateCommandImpl::new, PaymentExpansionModel.of());
    }
}
