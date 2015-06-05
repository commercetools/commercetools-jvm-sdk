package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.queries.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


final class InventoryEntryQueryImpl extends UltraQueryDslImpl<InventoryEntry, InventoryEntryQuery, InventoryEntryQueryModel<InventoryEntry>, InventoryEntryExpansionModel<InventoryEntry>> implements InventoryEntryQuery {
    InventoryEntryQueryImpl(){
        super(InventoryEntryEndpoint.ENDPOINT.endpoint(), InventoryEntryQuery.resultTypeReference(), InventoryEntryQueryModel.of(), InventoryEntryExpansionModel.of());
    }

    private InventoryEntryQueryImpl(final Optional<QueryPredicate<InventoryEntry>> predicate, final List<QuerySort<InventoryEntry>> sort, final Optional<Long> limit, final Optional<Long> offset, final String endpoint, final Function<HttpResponse, PagedQueryResult<InventoryEntry>> resultMapper, final List<ExpansionPath<InventoryEntry>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters, final InventoryEntryQueryModel<InventoryEntry> queryModel, final InventoryEntryExpansionModel<InventoryEntry> expansionModel) {
        super(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
    }

    @Override
    protected UltraQueryDslBuilder<InventoryEntry, InventoryEntryQuery, InventoryEntryQueryModel<InventoryEntry>, InventoryEntryExpansionModel<InventoryEntry>> copyBuilder() {
        return new InventoryEntryQueryQueryDslBuilder(this);
    }

    private static class InventoryEntryQueryQueryDslBuilder extends UltraQueryDslBuilder<InventoryEntry, InventoryEntryQuery, InventoryEntryQueryModel<InventoryEntry>, InventoryEntryExpansionModel<InventoryEntry>> {
        public InventoryEntryQueryQueryDslBuilder(final UltraQueryDslImpl<InventoryEntry, InventoryEntryQuery, InventoryEntryQueryModel<InventoryEntry>, InventoryEntryExpansionModel<InventoryEntry>> template) {
            super(template);
        }

        @Override
        public InventoryEntryQueryImpl build() {
            return new InventoryEntryQueryImpl(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
        }
    }
}