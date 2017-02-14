package io.sphere.sdk.products.search;

import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

import javax.annotation.Nullable;
import java.util.List;

final class IsOnStockInChannelsSearchModelImpl<T> extends SearchModelImpl<T> implements IsOnStockInChannelsSearchModel<T> {

    public IsOnStockInChannelsSearchModelImpl(@Nullable final SearchModel<T> parent) {
        super(parent, "isOnStockInChannels");
    }

    @Override
    public List<FilterExpression<T>> channels(final Iterable<String> channels) {
        return stringSearchModel(null).filtered().containsAnyAsString(channels);
    }
}
