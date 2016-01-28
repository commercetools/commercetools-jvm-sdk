package io.sphere.sdk.inventory.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary }

 */
public class InventoryEntryQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<InventoryEntryQueryBuilder, InventoryEntry, InventoryEntryQuery, InventoryEntryQueryModel, InventoryEntryExpansionModel<InventoryEntry>> {

    private InventoryEntryQueryBuilder(final InventoryEntryQuery template) {
        super(template);
    }

    public static InventoryEntryQueryBuilder of() {
        return new InventoryEntryQueryBuilder(InventoryEntryQuery.of());
    }

    @Override
    protected InventoryEntryQueryBuilder getThis() {
        return this;
    }

    @Override
    public InventoryEntryQuery build() {
        return super.build();
    }

    @Override
    public InventoryEntryQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public InventoryEntryQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public InventoryEntryQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public InventoryEntryQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public InventoryEntryQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public InventoryEntryQueryBuilder plusExpansionPaths(final Function<InventoryEntryExpansionModel<InventoryEntry>, ExpansionPathContainer<InventoryEntry>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public InventoryEntryQueryBuilder plusPredicates(final Function<InventoryEntryQueryModel, QueryPredicate<InventoryEntry>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public InventoryEntryQueryBuilder plusPredicates(final QueryPredicate<InventoryEntry> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public InventoryEntryQueryBuilder plusPredicates(final List<QueryPredicate<InventoryEntry>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public InventoryEntryQueryBuilder plusSort(final Function<InventoryEntryQueryModel, QuerySort<InventoryEntry>> m) {
        return super.plusSort(m);
    }

    @Override
    public InventoryEntryQueryBuilder plusSort(final List<QuerySort<InventoryEntry>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public InventoryEntryQueryBuilder plusSort(final QuerySort<InventoryEntry> sort) {
        return super.plusSort(sort);
    }

    @Override
    public InventoryEntryQueryBuilder predicates(final Function<InventoryEntryQueryModel, QueryPredicate<InventoryEntry>> m) {
        return super.predicates(m);
    }

    @Override
    public InventoryEntryQueryBuilder predicates(final QueryPredicate<InventoryEntry> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public InventoryEntryQueryBuilder predicates(final List<QueryPredicate<InventoryEntry>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public InventoryEntryQueryBuilder sort(final Function<InventoryEntryQueryModel, QuerySort<InventoryEntry>> m) {
        return super.sort(m);
    }

    @Override
    public InventoryEntryQueryBuilder sort(final List<QuerySort<InventoryEntry>> sort) {
        return super.sort(sort);
    }

    @Override
    public InventoryEntryQueryBuilder sort(final QuerySort<InventoryEntry> sort) {
        return super.sort(sort);
    }

    @Override
    public InventoryEntryQueryBuilder sortMulti(final Function<InventoryEntryQueryModel, List<QuerySort<InventoryEntry>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public InventoryEntryQueryBuilder expansionPaths(final Function<InventoryEntryExpansionModel<InventoryEntry>, ExpansionPathContainer<InventoryEntry>> m) {
        return super.expansionPaths(m);
    }
}
