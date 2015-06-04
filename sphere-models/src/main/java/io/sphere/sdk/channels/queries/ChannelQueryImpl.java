package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.queries.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 {@doc.gen summary channels}
 */
final class ChannelQueryImpl extends UltraQueryDslImpl<Channel, ChannelQuery, ChannelQueryModel<Channel>, ChannelExpansionModel<Channel>> implements ChannelQuery {
    ChannelQueryImpl(){
        super(ChannelEndpoint.ENDPOINT.endpoint(), ChannelQuery.resultTypeReference(), ChannelQueryModel.of(), ChannelExpansionModel.of());
    }

    private ChannelQueryImpl(final Optional<QueryPredicate<Channel>> predicate, final List<QuerySort<Channel>> sort, final Optional<Long> limit, final Optional<Long> offset, final String endpoint, final Function<HttpResponse, PagedQueryResult<Channel>> resultMapper, final List<ExpansionPath<Channel>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters, final ChannelQueryModel<Channel> queryModel, final ChannelExpansionModel<Channel> expansionModel) {
        super(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
    }

    @Override
    protected UltraQueryDslBuilder<Channel, ChannelQuery, ChannelQueryModel<Channel>, ChannelExpansionModel<Channel>> copyBuilder() {
        return new ChannelQueryQueryDslBuilder(this);
    }

    private static class ChannelQueryQueryDslBuilder extends UltraQueryDslBuilder<Channel, ChannelQuery, ChannelQueryModel<Channel>, ChannelExpansionModel<Channel>> {
        public ChannelQueryQueryDslBuilder(final UltraQueryDslImpl<Channel, ChannelQuery, ChannelQueryModel<Channel>, ChannelExpansionModel<Channel>> template) {
            super(template);
        }

        @Override
        public ChannelQueryImpl build() {
            return new ChannelQueryImpl(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
        }
    }
}