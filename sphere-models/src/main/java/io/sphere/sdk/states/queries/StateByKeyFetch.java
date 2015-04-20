package io.sphere.sdk.states.queries;

import io.sphere.sdk.queries.QueryToFetchAdapter;
import io.sphere.sdk.states.State;

/**
 * Fetches a State by key.
 *
 * {@include.example io.sphere.sdk.states.queries.StateByKeyFetchTest#execution()}
 */
public class StateByKeyFetch extends QueryToFetchAdapter<State> {

    private StateByKeyFetch(final String key) {
        super(StateQuery.resultTypeReference(), StateQuery.of().byKey(key));
    }

    public static StateByKeyFetch of(final String key) {
        return new StateByKeyFetch(key);
    }
}



