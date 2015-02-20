package io.sphere.sdk.states.queries;


import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.states.State;

public class StateQuery extends DefaultModelQuery<State> {

    private StateQuery() {
        super(StateEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static StateQuery of() {
        return new StateQuery();
    }

    public QueryDsl<State> byKey(final String key) {
        return withPredicate(model().key().is(key));
    }

    public static TypeReference<PagedQueryResult<State>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<State>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<State>>";
            }
        };
    }

    public static StateQueryModel model() {
        return StateQueryModel.get();
    }

}

