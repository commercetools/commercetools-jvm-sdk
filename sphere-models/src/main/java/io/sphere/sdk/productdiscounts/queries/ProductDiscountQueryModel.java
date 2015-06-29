package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.queries.BooleanQueryModel;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;

import java.util.Optional;

public class ProductDiscountQueryModel extends DefaultModelQueryModelImpl<ProductDiscount> {
    private ProductDiscountQueryModel(final Optional<? extends QueryModel<ProductDiscount>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static ProductDiscountQueryModel of() {
        return new ProductDiscountQueryModel(Optional.empty(), Optional.<String>empty());
    }

    public BooleanQueryModel<ProductDiscount> isActive() {
        return booleanModel("isActive");
    }

    public BooleanQueryModel<ProductDiscount> active() {
        return isActive();
    }
}
