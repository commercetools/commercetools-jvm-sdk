package io.sphere.sdk.products.search;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;

public interface IsOnStockInChannelsSearchModel<T> {
    List<FilterExpression<T>> channels(Iterable<String> channels);
}
