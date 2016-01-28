package io.sphere.sdk.states.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.expansion.StateExpansionModel;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary}

 */
public class StateQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<StateQueryBuilder, State, StateQuery, StateQueryModel, StateExpansionModel<State>> {

    private StateQueryBuilder(final StateQuery template) {
        super(template);
    }

    public static StateQueryBuilder of() {
        return new StateQueryBuilder(StateQuery.of());
    }

    @Override
    protected StateQueryBuilder getThis() {
        return this;
    }

    @Override
    public StateQuery build() {
        return super.build();
    }

    @Override
    public StateQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public StateQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public StateQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public StateQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public StateQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public StateQueryBuilder plusExpansionPaths(final Function<StateExpansionModel<State>, ExpansionPathContainer<State>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public StateQueryBuilder plusPredicates(final Function<StateQueryModel, QueryPredicate<State>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public StateQueryBuilder plusPredicates(final QueryPredicate<State> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public StateQueryBuilder plusPredicates(final List<QueryPredicate<State>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public StateQueryBuilder plusSort(final Function<StateQueryModel, QuerySort<State>> m) {
        return super.plusSort(m);
    }

    @Override
    public StateQueryBuilder plusSort(final List<QuerySort<State>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public StateQueryBuilder plusSort(final QuerySort<State> sort) {
        return super.plusSort(sort);
    }

    @Override
    public StateQueryBuilder predicates(final Function<StateQueryModel, QueryPredicate<State>> m) {
        return super.predicates(m);
    }

    @Override
    public StateQueryBuilder predicates(final QueryPredicate<State> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public StateQueryBuilder predicates(final List<QueryPredicate<State>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public StateQueryBuilder sort(final Function<StateQueryModel, QuerySort<State>> m) {
        return super.sort(m);
    }

    @Override
    public StateQueryBuilder sort(final List<QuerySort<State>> sort) {
        return super.sort(sort);
    }

    @Override
    public StateQueryBuilder sort(final QuerySort<State> sort) {
        return super.sort(sort);
    }

    @Override
    public StateQueryBuilder sortMulti(final Function<StateQueryModel, List<QuerySort<State>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public StateQueryBuilder expansionPaths(final Function<StateExpansionModel<State>, ExpansionPathContainer<State>> m) {
        return super.expansionPaths(m);
    }
}
