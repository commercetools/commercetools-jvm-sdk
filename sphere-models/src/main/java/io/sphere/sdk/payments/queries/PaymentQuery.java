package io.sphere.sdk.payments.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;

/**
 * Queries payments.
 *
 * {@doc.gen summary payments}
 *
 *
 */
public interface PaymentQuery extends MetaModelQueryDsl<Payment, PaymentQuery, PaymentQueryModel, PaymentExpansionModel<Payment>> {

    static TypeReference<PagedQueryResult<Payment>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Payment>>() {
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Payment>>";
            }
        };
    }

    static PaymentQuery of() {
        return new PaymentQueryImpl();
    }
}
