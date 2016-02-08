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
    /**
     * Creates a container which contains the full Java type information to deserialize the query result (NOT this class) from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
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
