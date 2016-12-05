package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.queries.BooleanQueryModel;
import io.sphere.sdk.queries.LocalizedStringQuerySortingModel;
import io.sphere.sdk.queries.ResourceQueryModel;

public interface ProductDiscountQueryModel extends ResourceQueryModel<ProductDiscount> {
    default BooleanQueryModel<ProductDiscount> isActive() {
        return active();
    }

    BooleanQueryModel<ProductDiscount> active();

    LocalizedStringQuerySortingModel<ProductDiscount> name();

    static ProductDiscountQueryModel of() {
        return new ProductDiscountQueryModelImpl(null, null);
    }
}
