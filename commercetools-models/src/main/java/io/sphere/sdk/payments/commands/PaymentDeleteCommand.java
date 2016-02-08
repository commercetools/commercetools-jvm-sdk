package io.sphere.sdk.payments.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;


public interface PaymentDeleteCommand extends MetaModelReferenceExpansionDsl<Payment, PaymentDeleteCommand, PaymentExpansionModel<Payment>>, DeleteCommand<Payment> {

    static PaymentDeleteCommand of(final Versioned<Payment> versioned) {
        return new PaymentDeleteCommandImpl(versioned);
    }
}
