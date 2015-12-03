package io.sphere.sdk.products.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.*;

public interface PriceQueryModel<T> {
    DiscountedPriceOptionalQueryModel<T> discounted();

    StringQueryModel<T> id();

    MoneyQueryModel<T> value();

    CountryQueryModel<T> country();

    ReferenceOptionalQueryModel<T, CustomerGroup> customerGroup();

    ReferenceOptionalQueryModel<T, CustomerGroup> channel();
}
