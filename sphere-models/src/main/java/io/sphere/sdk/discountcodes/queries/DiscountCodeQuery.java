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
