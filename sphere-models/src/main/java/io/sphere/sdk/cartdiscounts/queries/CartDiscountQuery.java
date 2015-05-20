package io.sphere.sdk.cartdiscounts.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;

import java.util.List;

/**

 {@doc.gen summary cart discounts}

 */
public class CartDiscountQuery extends DefaultModelQuery<CartDiscount> {
    private CartDiscountQuery() {
        super(CartDiscountEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<CartDiscount>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<CartDiscount>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<CartDiscount>>";
            }
        };
    }

    public static CartDiscountQuery of() {
        return new CartDiscountQuery();
    }

    public static CartDiscountQueryModel model() {
        return CartDiscountQueryModel.get();
    }
}
