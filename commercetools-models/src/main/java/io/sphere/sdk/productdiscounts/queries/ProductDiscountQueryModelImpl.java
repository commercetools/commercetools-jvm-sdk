package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.queries.*;

final class ProductDiscountQueryModelImpl extends ResourceQueryModelImpl<ProductDiscount> implements ProductDiscountQueryModel {
    ProductDiscountQueryModelImpl(final QueryModel<ProductDiscount> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public BooleanQueryModel<ProductDiscount> isActive() {
        return booleanModel("isActive");
    }

    @Override
    public BooleanQueryModel<ProductDiscount> active() {
        return isActive();
    }

    @Override
    public LocalizedStringQuerySortingModel<ProductDiscount> name() {
        return localizedStringQuerySortingModel("name");
    }
}
