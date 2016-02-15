package io.sphere.sdk.carts.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;

/**
  {@doc.gen summary carts}

  <p>Example for query a cart by customer email:</p>

 {@include.example io.sphere.sdk.carts.queries.CartQueryIntegrationTest#byCustomerEmail()}
 */
public interface CartQuery extends MetaModelQueryDsl<Cart, CartQuery, CartQueryModel, CartExpansionModel<Cart>> {

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
    static TypeReference<PagedQueryResult<Cart>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Cart>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Cart>>";
            }
        };
    }

    static CartQuery of() {
        return new CartQueryImpl();
    }
}
