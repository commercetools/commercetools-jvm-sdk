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
    static TypeReference<PagedQueryResult<ProductType>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<ProductType>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ProductType>>";
            }
        };
    }

    default ProductTypeQuery byName(String name) {
        return withPredicate(ProductTypeQueryModel.of().name().is(name));
    }

    static ProductTypeQuery of() {
        return new ProductTypeQueryImpl();
    }
}
