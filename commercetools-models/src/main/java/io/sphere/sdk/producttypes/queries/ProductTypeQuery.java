package io.sphere.sdk.producttypes.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;


/** {@doc.gen summary product types}

  Query all product types:

  {@include.example example.QueryProductTypeExamples#queryAll()}

  Scenario to load a specific product type:

  {@include.example example.QueryByProductTypeNameExample}

  With {@link io.sphere.sdk.producttypes.queries.ProductTypeQueryModel} you can query for product types containing specific attributes:

  {@include.example example.QueryProductTypeExamples#queryByAttributeName()}

 */
public interface ProductTypeQuery extends MetaModelQueryDsl<ProductType, ProductTypeQuery, ProductTypeQueryModel, ProductTypeExpansionModel<ProductType>> {
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
    static TypeReference<PagedQueryResult<ProductType>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<ProductType>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ProductType>>";
            }
        };
    }

    default ProductTypeQuery byName(String name) {
        return withPredicates(m -> m.name().is(name));
    }

    default ProductTypeQuery byKey(String key) {
        return withPredicates(m -> m.key().is(key));
    }

    static ProductTypeQuery of() {
        return new ProductTypeQueryImpl();
    }
}
