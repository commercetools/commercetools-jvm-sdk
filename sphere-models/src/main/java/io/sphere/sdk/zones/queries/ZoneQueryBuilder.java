package io.sphere.sdk.zones.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary zones}

 */
public final class ZoneQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<ZoneQueryBuilder, Zone, ZoneQuery, ZoneQueryModel, ZoneExpansionModel<Zone>> {

    private ZoneQueryBuilder(final ZoneQuery template) {
        super(template);
    }

    public static ZoneQueryBuilder of() {
        return new ZoneQueryBuilder(ZoneQuery.of());
    }

    @Override
    protected ZoneQueryBuilder getThis() {
        return this;
    }

    @Override
    public ZoneQuery build() {
        return super.build();
    }

    @Override
    public ZoneQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public ZoneQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public ZoneQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public ZoneQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public ZoneQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public ZoneQueryBuilder plusExpansionPaths(final Function<ZoneExpansionModel<Zone>, ExpansionPathContainer<Zone>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ZoneQueryBuilder plusPredicates(final Function<ZoneQueryModel, QueryPredicate<Zone>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public ZoneQueryBuilder plusPredicates(final QueryPredicate<Zone> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public ZoneQueryBuilder plusPredicates(final List<QueryPredicate<Zone>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public ZoneQueryBuilder plusSort(final Function<ZoneQueryModel, QuerySort<Zone>> m) {
        return super.plusSort(m);
    }

    @Override
    public ZoneQueryBuilder plusSort(final List<QuerySort<Zone>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ZoneQueryBuilder plusSort(final QuerySort<Zone> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ZoneQueryBuilder predicates(final Function<ZoneQueryModel, QueryPredicate<Zone>> m) {
        return super.predicates(m);
    }

    @Override
    public ZoneQueryBuilder predicates(final QueryPredicate<Zone> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public ZoneQueryBuilder predicates(final List<QueryPredicate<Zone>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public ZoneQueryBuilder sort(final Function<ZoneQueryModel, QuerySort<Zone>> m) {
        return super.sort(m);
    }

    @Override
    public ZoneQueryBuilder sort(final List<QuerySort<Zone>> sort) {
        return super.sort(sort);
    }

    @Override
    public ZoneQueryBuilder sort(final QuerySort<Zone> sort) {
        return super.sort(sort);
    }

    @Override
    public ZoneQueryBuilder sortMulti(final Function<ZoneQueryModel, List<QuerySort<Zone>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public ZoneQueryBuilder expansionPaths(final Function<ZoneExpansionModel<Zone>, ExpansionPathContainer<Zone>> m) {
        return super.expansionPaths(m);
    }
}
