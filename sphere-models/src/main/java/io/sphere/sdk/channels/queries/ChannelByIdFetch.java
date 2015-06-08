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
    ChannelByIdFetch plusExpansionPath(final Function<ChannelExpansionModel<Channel>, ExpansionPath<Channel>> m);

    @Override
    ChannelByIdFetch withExpansionPath(final Function<ChannelExpansionModel<Channel>, ExpansionPath<Channel>> m);

    @Override
    List<ExpansionPath<Channel>> expansionPaths();

    @Override
    ChannelByIdFetch plusExpansionPath(final ExpansionPath<Channel> expansionPath);

    @Override
    ChannelByIdFetch withExpansionPath(final ExpansionPath<Channel> expansionPath);

    @Override
    ChannelByIdFetch withExpansionPath(final List<ExpansionPath<Channel>> expansionPaths);
}

