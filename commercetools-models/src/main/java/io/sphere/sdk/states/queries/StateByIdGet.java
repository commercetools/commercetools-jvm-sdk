package io.sphere.sdk.states.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.expansion.StateExpansionModel;

import java.util.List;
import java.util.function.Function;

public interface StateByIdGet extends MetaModelGetDsl<State, State, StateByIdGet, StateExpansionModel<State>> {
    static StateByIdGet of(final Identifiable<State> state) {
        return of(state.getId());
    }

    static StateByIdGet of(final String id) {
        return new StateByIdGetImpl(id);
    }

    @Override
    StateByIdGet plusExpansionPaths(final ExpansionPath<State> expansionPath);

    @Override
    List<ExpansionPath<State>> expansionPaths();

    @Override
    StateByIdGet withExpansionPaths(final ExpansionPath<State> expansionPath);

    @Override
    StateByIdGet withExpansionPaths(final List<ExpansionPath<State>> expansionPaths);
}

