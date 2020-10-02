package io.sphere.sdk.products.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;

import javax.annotation.Nullable;

final class PriceCollectionQueryModelImpl<T> extends QueryModelImpl<T> implements PriceCollectionQueryModel<T> {

    PriceCollectionQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public DiscountedPriceOptionalQueryModel<T> discounted() {
        return new DiscountedPriceOptionalQueryModelImpl<>(this, "discounted");
    }

    @Override
    public QueryPredicate<T> isEmpty() {
        return isEmptyCollectionQueryPredicate();
    }

    @Override
    public QueryPredicate<T> isNotEmpty() {
        return isNotEmptyCollectionQueryPredicate();
    }

    @Override
    public StringQueryModel<T> id() {
        return stringModel("id");
    }

    @Override
    public MoneyQueryModel<T> value() {
        return moneyModel("value");
    }

    @Override
    public CountryQueryModel<T> country() {
        return countryQueryModel("country");
    }

    @Override
    public ReferenceOptionalQueryModel<T, CustomerGroup> customerGroup() {
        return referenceOptionalModel("customerGroup");
    }

    @Override
    public ReferenceOptionalQueryModel<T, Channel> channel() {
        return referenceOptionalModel("channel");
    }


    @Override
    public CustomQueryModel<T> custom() {
        return CustomQueryModel.of(this, "custom");
    }

    @Override
    public PriceTierQueryModel<T> tiers() {
        return new PriceTierQueryModelImpl<T>(this, "tiers");
    }
}

