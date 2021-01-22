package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

public interface CartInStoreByKeyGet extends MetaModelGetDsl<Cart, Cart, CartInStoreByKeyGet, CartExpansionModel<Cart>> {

    static CartInStoreByKeyGet of(final String storeKey, final String cartKey){
        return new CartInStoreByKeyGetImpl(storeKey, cartKey);
    }
    
    @Override
    List<ExpansionPath<Cart>> expansionPaths();

    @Override
    CartInStoreByKeyGet plusExpansionPaths(final ExpansionPath<Cart> expansionPath);

    @Override
    CartInStoreByKeyGet withExpansionPaths(final ExpansionPath<Cart> expansionPath);

    @Override
    CartInStoreByKeyGet withExpansionPaths(final List<ExpansionPath<Cart>> expansionPaths);

}