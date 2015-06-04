package io.sphere.sdk.cartdiscounts.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.UltraQueryDsl;

/**

 {@doc.gen summary cart discounts}

 */
public interface CartDiscountQuery extends UltraQueryDsl<CartDiscount, CartDiscountQuery, CartDiscountQueryModel<CartDiscount>, CartDiscountExpansionModel<CartDiscount>> {

    static TypeReference<PagedQueryResult<CartDiscount>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<CartDiscount>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<CartDiscount>>";
            }
        };
    }

    static CartDiscountQuery of() {
        return new CartDiscountQueryImpl();
    }
}
