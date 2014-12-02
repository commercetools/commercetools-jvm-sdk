package io.sphere.sdk.carts.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;

/**

 {@doc.gen summary carts}

 */
public class CartQuery extends DefaultModelQuery<Cart> {
    private CartQuery(){
        super(CartsEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<Cart>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Cart>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Cart>>";
            }
        };
    }

    public static CartQuery of() {
        return new CartQuery();
    }
}
