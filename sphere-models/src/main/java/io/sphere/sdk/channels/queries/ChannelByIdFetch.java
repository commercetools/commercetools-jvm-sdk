package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelFetchDsl;

public interface ChannelByIdFetch extends MetaModelFetchDsl<Channel, ChannelByIdFetch, ChannelExpansionModel<Channel>> {
    static ChannelByIdFetch of(final Identifiable<Channel> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static ChannelByIdFetch of(final String id) {
        return new ChannelByIdFetchImpl(id);
    }
}

