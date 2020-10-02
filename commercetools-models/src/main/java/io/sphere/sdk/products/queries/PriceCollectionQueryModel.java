package io.sphere.sdk.products.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;

public interface PriceCollectionQueryModel<T> extends CollectionQueryModel<T>, PriceQueryModel<T> {
    @Override
    DiscountedPriceOptionalQueryModel<T> discounted();

    @Override
    QueryPredicate<T> isEmpty();

    @Override
    QueryPredicate<T> isNotEmpty();

    @Override
    StringQueryModel<T> id();

    @Override
    MoneyQueryModel<T> value();

    @Override
    CountryQueryModel<T> country();

    @Override
    ReferenceOptionalQueryModel<T, CustomerGroup> customerGroup();

    @Override
    ReferenceOptionalQueryModel<T, Channel> channel();

    @Override
    CustomQueryModel<T> custom();

    static <T> PriceCollectionQueryModel<T> of(final QueryModel<T> parent, final String path) {
        return new PriceCollectionQueryModelImpl<>(parent, path);
    }
}
