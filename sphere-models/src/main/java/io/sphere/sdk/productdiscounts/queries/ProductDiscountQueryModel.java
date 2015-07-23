package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.queries.BooleanQueryModel;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;

public class ProductDiscountQueryModel extends DefaultModelQueryModelImpl<ProductDiscount> {
    private ProductDiscountQueryModel(final QueryModel<ProductDiscount> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static ProductDiscountQueryModel of() {
        return new ProductDiscountQueryModel(null, null);
    }

    public BooleanQueryModel<ProductDiscount> isActive() {
        return booleanModel("isActive");
    }

    public BooleanQueryModel<ProductDiscount> active() {
        return isActive();
    }
}
