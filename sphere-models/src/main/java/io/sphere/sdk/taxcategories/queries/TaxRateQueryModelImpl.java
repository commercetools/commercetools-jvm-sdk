package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.queries.*;

/**
 * internal query model class
 * @param <T> context
 */
public final class TaxRateQueryModelImpl<T> extends QueryModelImpl<T> implements TaxRateQueryModel<T> {
    public TaxRateQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public CountryQueryModel<T> country() {
        return countryQueryModel("country");
    }

    @Override
    public StringQueryModel<T> name() {
        return stringModel("name");
    }

    @Override
    public BooleanQueryModel<T> includedInPrice() {
        return booleanModel("includedInPrice");
    }

    @Override
    public StringQueryModel<T> state() {
        return stringModel("state");
    }
}
