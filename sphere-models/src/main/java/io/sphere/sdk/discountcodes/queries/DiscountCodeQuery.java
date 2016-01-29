package io.sphere.sdk.discountcodes.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;

/**

 {@doc.gen summary discount codes}

 */
public interface DiscountCodeQuery extends MetaModelQueryDsl<DiscountCode, DiscountCodeQuery, DiscountCodeQueryModel, DiscountCodeExpansionModel<DiscountCode>> {
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
    static TypeReference<PagedQueryResult<DiscountCode>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<DiscountCode>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<DiscountCode>>";
            }
        };
    }

    static DiscountCodeQuery of() {
        return new DiscountCodeQueryImpl();
    }
}
