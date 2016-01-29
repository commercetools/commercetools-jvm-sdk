package io.sphere.sdk.types.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.expansion.TypeExpansionModel;

/**
 * {@doc.gen summary types}
 */
public interface TypeQuery extends MetaModelQueryDsl<Type, TypeQuery, TypeQueryModel, TypeExpansionModel<Type>> {
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
    static TypeReference<PagedQueryResult<Type>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Type>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Type>>";
            }
        };
    }

    static TypeQuery of() {
        return new TypeQueryImpl();
    }
}
