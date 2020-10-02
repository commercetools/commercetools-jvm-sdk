package io.sphere.sdk.products.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.CountryQueryModel;
import io.sphere.sdk.queries.MoneyQueryModel;
import io.sphere.sdk.queries.ReferenceOptionalQueryModel;
import io.sphere.sdk.queries.StringQueryModel;
import io.sphere.sdk.types.queries.WithCustomQueryModel;


public interface PriceQueryModel<T> extends WithCustomQueryModel<T> {
    DiscountedPriceOptionalQueryModel<T> discounted();

    StringQueryModel<T> id();

    MoneyQueryModel<T> value();

    CountryQueryModel<T> country();

    ReferenceOptionalQueryModel<T, CustomerGroup> customerGroup();

    ReferenceOptionalQueryModel<T, Channel> channel();

    PriceTierQueryModel<T> tiers();
}
