package io.sphere.sdk.states.queries;

import io.sphere.sdk.queries.QueryToFetchAdapter;
import io.sphere.sdk.states.State;

public class StateFetchByKey extends QueryToFetchAdapter<State> {

    private StateFetchByKey(final String key) {
        super(StateQuery.resultTypeReference(), StateQuery.of().byKey(key));
    }

    public static StateFetchByKey of(final String key) {
        return new StateFetchByKey(key);
    }
}



