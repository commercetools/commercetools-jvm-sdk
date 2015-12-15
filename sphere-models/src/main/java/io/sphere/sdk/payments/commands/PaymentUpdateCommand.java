package io.sphere.sdk.payments.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 <p>Updates a payment.</p>

 {@doc.gen list actions}

 @see Payment
 */
public interface PaymentUpdateCommand extends UpdateCommandDsl<Payment, PaymentUpdateCommand>, MetaModelExpansionDsl<Payment, PaymentUpdateCommand, PaymentExpansionModel<Payment>> {
    static PaymentUpdateCommand of(final Versioned<Payment> versioned, final UpdateAction<Payment> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }

    static PaymentUpdateCommand of(final Versioned<Payment> versioned, final List<? extends UpdateAction<Payment>> updateActions) {
        return new PaymentUpdateCommandImpl(versioned, updateActions);
    }

    @Override
    PaymentUpdateCommand withExpansionPaths(final List<ExpansionPath<Payment>> expansionPaths);

    @Override
    PaymentUpdateCommand withExpansionPaths(final ExpansionPath<Payment> expansionPath);

    @Override
    PaymentUpdateCommand plusExpansionPaths(final List<ExpansionPath<Payment>> expansionPaths);

    @Override
    PaymentUpdateCommand plusExpansionPaths(final ExpansionPath<Payment> expansionPath);

    @Override
    PaymentUpdateCommand withVersion(final Versioned<Payment> versioned);
}
