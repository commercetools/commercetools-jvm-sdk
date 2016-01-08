package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.queries.*;

public class ProductDiscountQueryModel extends ResourceQueryModelImpl<ProductDiscount> {
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

    public LocalizedStringQuerySortingModel<ProductDiscount> name() {
        return localizedStringQuerySortingModel("name");
    }
}
