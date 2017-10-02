package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.*;
import io.sphere.sdk.taxcategories.queries.TaxRateQueryModel;

/**
 * internal query model class
 * @param <T> context
 */
final class ShippingRateInputQueryModelImpl<T> extends QueryModelImpl<T> implements ShippingRateInputQueryModel<T> {
    public ShippingRateInputQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }


    @Override
    public QueryPredicate<T> isNotPresent() {
        return isNotPresentPredicate();
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return isPresentPredicate();
    }

    @Override
    public StringQuerySortingModel<T> key(){
        return stringQuerySortingModel("key");
    }

}
