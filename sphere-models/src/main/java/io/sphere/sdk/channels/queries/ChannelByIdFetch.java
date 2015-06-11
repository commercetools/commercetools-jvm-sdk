package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

public interface ChannelByIdFetch extends MetaModelFetchDsl<Channel, ChannelByIdFetch, ChannelExpansionModel<Channel>> {
    static ChannelByIdFetch of(final Identifiable<Channel> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static ChannelByIdFetch of(final String id) {
        return new ChannelByIdFetchImpl(id);
    }

    @Override
    ChannelByIdFetch plusExpansionPaths(final Function<ChannelExpansionModel<Channel>, ExpansionPath<Channel>> m);

    @Override
    ChannelByIdFetch withExpansionPaths(final Function<ChannelExpansionModel<Channel>, ExpansionPath<Channel>> m);

    @Override
    List<ExpansionPath<Channel>> expansionPaths();

    @Override
    ChannelByIdFetch plusExpansionPaths(final ExpansionPath<Channel> expansionPath);

    @Override
    ChannelByIdFetch withExpansionPaths(final ExpansionPath<Channel> expansionPath);

    @Override
    ChannelByIdFetch withExpansionPaths(final List<ExpansionPath<Channel>> expansionPaths);
}

