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
