package io.sphere.sdk.states.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.expansion.StateExpansionModel;

import java.util.List;
import java.util.function.Function;

public interface StateByIdFetch extends MetaModelFetchDsl<State, State, StateByIdFetch, StateExpansionModel<State>> {
    static StateByIdFetch of(final Identifiable<State> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static StateByIdFetch of(final String id) {
        return new StateByIdFetchImpl(id);
    }

    @Override
    StateByIdFetch plusExpansionPaths(final Function<StateExpansionModel<State>, ExpansionPath<State>> m);

    @Override
    StateByIdFetch withExpansionPaths(final Function<StateExpansionModel<State>, ExpansionPath<State>> m);

    @Override
    StateByIdFetch plusExpansionPaths(final ExpansionPath<State> expansionPath);

    @Override
    List<ExpansionPath<State>> expansionPaths();

    @Override
    StateByIdFetch withExpansionPaths(final ExpansionPath<State> expansionPath);

    @Override
    StateByIdFetch withExpansionPaths(final List<ExpansionPath<State>> expansionPaths);
}

