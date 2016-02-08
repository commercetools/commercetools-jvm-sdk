package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.queries.BooleanQueryModel;
import io.sphere.sdk.queries.CountryQueryModel;
import io.sphere.sdk.queries.StringQueryModel;

public interface TaxRateQueryModel<T> {
    StringQueryModel<T> name();

    BooleanQueryModel<T> includedInPrice();

    CountryQueryModel<T> country();

    StringQueryModel<T> state();
}
