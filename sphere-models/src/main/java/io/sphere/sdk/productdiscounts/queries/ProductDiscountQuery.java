package io.sphere.sdk.productdiscounts.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;

/**
 {@doc.gen summary product discounts}
 */
public interface ProductDiscountQuery extends MetaModelQueryDsl<ProductDiscount, ProductDiscountQuery, ProductDiscountQueryModel, ProductDiscountExpansionModel<ProductDiscount>> {
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
    static TypeReference<PagedQueryResult<ProductDiscount>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<ProductDiscount>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ProductDiscount>>";
            }
        };
    }

    static ProductDiscountQuery of() {
        return new ProductDiscountQueryImpl();
    }
}
