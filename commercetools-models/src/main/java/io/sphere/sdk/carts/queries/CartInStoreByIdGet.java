package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

public interface CartInStoreByIdGet extends MetaModelGetDsl<Cart, Cart, CartInStoreByIdGet, CartExpansionModel<Cart>> {

    static CartInStoreByIdGet of(final String storeKey, final String cartId){
        return new CartInStoreByIdGetImpl(storeKey, cartId);
    }
    
    @Override
    List<ExpansionPath<Cart>> expansionPaths();

    @Override
    CartInStoreByIdGet plusExpansionPaths(final ExpansionPath<Cart> expansionPath);

    @Override
    CartInStoreByIdGet withExpansionPaths(final ExpansionPath<Cart> expansionPath);

    @Override
    CartInStoreByIdGet withExpansionPaths(final List<ExpansionPath<Cart>> expansionPaths);

}