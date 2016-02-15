package io.sphere.sdk.payments.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

/**
 * Retrieves a payment by a known ID.
 *
 * {@include.example io.sphere.sdk.payments.queries.PaymentByIdGetIntegrationTest#execution()}
 */
public interface PaymentByIdGet extends MetaModelGetDsl<Payment, Payment, PaymentByIdGet, PaymentExpansionModel<Payment>> {
    static PaymentByIdGet of(final Identifiable<Payment> Payment) {
        return of(Payment.getId());
    }

    static PaymentByIdGet of(final String id) {
        return new PaymentByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<Payment>> expansionPaths();

    @Override
    PaymentByIdGet plusExpansionPaths(final ExpansionPath<Payment> expansionPath);

    @Override
    PaymentByIdGet withExpansionPaths(final ExpansionPath<Payment> expansionPath);

    @Override
    PaymentByIdGet withExpansionPaths(final List<ExpansionPath<Payment>> expansionPaths);
}
