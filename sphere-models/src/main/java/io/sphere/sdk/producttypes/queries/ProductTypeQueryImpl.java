package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.queries.*;


/** {@doc.gen summary product types}

  Query all product types:

  {@include.example example.QueryProductTypeExamples#queryAll()}

  Scenario to load a specific product type:

  {@include.example example.QueryByProductTypeNameExample}

  With {@link ProductTypeQueryModel} you can query for product types containing specific attributes:

  {@include.example example.QueryProductTypeExamples#queryByAttributeName()}

 */
final class ProductTypeQueryImpl extends UltraQueryDslImpl<ProductType, ProductTypeQuery, ProductTypeQueryModel<ProductType>, ProductTypeExpansionModel<ProductType>> implements ProductTypeQuery {
    ProductTypeQueryImpl(){
        super(ProductTypeEndpoint.ENDPOINT.endpoint(), ProductTypeQuery.resultTypeReference(), ProductTypeQueryModel.of(), ProductTypeExpansionModel.of(), ProductTypeQueryImpl::new);
    }

    private ProductTypeQueryImpl(final UltraQueryDslBuilder<ProductType, ProductTypeQuery, ProductTypeQueryModel<ProductType>, ProductTypeExpansionModel<ProductType>> builder) {
        super(builder);
    }
}