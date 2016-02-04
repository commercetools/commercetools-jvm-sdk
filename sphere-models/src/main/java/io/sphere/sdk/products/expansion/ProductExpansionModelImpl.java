package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

final class ProductExpansionModelImpl<T> extends ExpansionModel<T> implements ProductExpansionModel<T> {
    ProductExpansionModelImpl() {
    }

    @Override
    public ProductTypeExpansionModel<T> productType() {
        return ProductTypeExpansionModel.of(buildPathExpression(), "productType");
    }

    @Override
    public TaxCategoryExpansionModel<T> taxCategory() {
        return TaxCategoryExpansionModel.of(buildPathExpression(), "taxCategory");
    }

    @Override
    public ProductCatalogExpansionModel<T> masterData() {
        return new ProductCatalogExpansionModelImpl<>(null, "masterData");
    }
}
