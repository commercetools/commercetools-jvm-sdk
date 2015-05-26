package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQuerySortingModel;

import java.util.Optional;

public class DiscountCodeQueryModel extends DefaultModelQueryModelImpl<DiscountCode> {
    private static final DiscountCodeQueryModel instance = new DiscountCodeQueryModel(Optional.<QueryModelImpl<DiscountCode>>empty(), Optional.<String>empty());

    static DiscountCodeQueryModel get() {
        return instance;
    }

    private DiscountCodeQueryModel(Optional<? extends QueryModel<DiscountCode>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }


    public StringQuerySortingModel<DiscountCode> code() {
        return new StringQuerySortingModel<>(Optional.of(this), "code");
    }
}
