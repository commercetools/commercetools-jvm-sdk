package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

final class DiscountCodeQueryModelImpl extends ResourceQueryModelImpl<DiscountCode> implements DiscountCodeQueryModel {

    DiscountCodeQueryModelImpl(QueryModel<DiscountCode> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<DiscountCode> code() {
        return stringModel("code");
    }
}
