package io.sphere.sdk.states.queries;


import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.expansion.StateExpansionModel;

/**
 * {@doc.gen summary states}
 */
public interface StateQuery extends MetaModelQueryDsl<State, StateQuery, StateQueryModel, StateExpansionModel<State>> {
    static StateQuery of() {
        return new StateQueryImpl();
    }

    default StateQuery byKey(final String key) {
        return withPredicates(StateQueryModel.of().key().is(key));
    }

    /**
     * Creates a container which contains the full Java type information to deserialize the query result (NOT this class) from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<PagedQueryResult<State>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<State>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<State>>";
            }
        };
    }
}

