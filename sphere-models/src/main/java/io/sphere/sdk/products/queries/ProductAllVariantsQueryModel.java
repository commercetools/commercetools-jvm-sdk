package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryPredicate;

import java.util.function.Function;

public interface ProductAllVariantsQueryModel<T> {
    QueryPredicate<T> where(Function<PartialProductVariantQueryModel, QueryPredicate<PartialProductVariantQueryModel>> embeddedPredicate);
}
