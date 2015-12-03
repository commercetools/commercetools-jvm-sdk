package io.sphere.sdk.products.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.*;

import javax.annotation.Nullable;

public final class PriceCollectionQueryModel<T> extends QueryModelImpl<T> implements CollectionQueryModel<T>, PriceQueryModel<T> {

    public PriceCollectionQueryModel(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public DiscountedPriceOptionalQueryModel<T> discounted() {
        return new DiscountedPriceOptionalQueryModel<>(this, "discounted");
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
    public ReferenceOptionalQueryModel<T, CustomerGroup> channel() {
        return referenceOptionalModel("channel");
    }
}

