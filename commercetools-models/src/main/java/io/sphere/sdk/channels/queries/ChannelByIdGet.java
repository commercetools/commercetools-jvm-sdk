package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

/**
 * Fetches a channel by a known ID.
 *
 * {@include.example io.sphere.sdk.channels.queries.ChannelByIdGetTest#execution()}
 */
public interface ChannelByIdGet extends MetaModelGetDsl<Channel, Channel, ChannelByIdGet, ChannelExpansionModel<Channel>> {
    static ChannelByIdGet of(final Identifiable<Channel> channel) {
        return of(channel.getId());
    }

    static ChannelByIdGet of(final String id) {
        return new ChannelByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<Channel>> expansionPaths();

    @Override
    ChannelByIdGet plusExpansionPaths(final ExpansionPath<Channel> expansionPath);

    @Override
    ChannelByIdGet withExpansionPaths(final ExpansionPath<Channel> expansionPath);

    @Override
    ChannelByIdGet withExpansionPaths(final List<ExpansionPath<Channel>> expansionPaths);
}

