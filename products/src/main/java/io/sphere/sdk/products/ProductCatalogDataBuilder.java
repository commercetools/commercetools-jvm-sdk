package io.sphere.sdk.products;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

public class ProductCatalogDataBuilder extends Base implements Builder<ProductCatalogData> {
    private boolean isPublished;
    private ProductData current;
    private ProductData staged;

    private ProductCatalogDataBuilder(boolean isPublished, ProductData current, ProductData staged) {
        this.isPublished = isPublished;
        this.current = current;
        this.staged = staged;
    }

    public static ProductCatalogDataBuilder of(ProductData currentAndStaged) {
        return new ProductCatalogDataBuilder(true, currentAndStaged, currentAndStaged);
    }

    public ProductCatalogDataBuilder current(final ProductData current) {
        this.current = current;
        return this;
    }
    
    public ProductCatalogDataBuilder staged(final ProductData staged) {
        this.staged = staged;
        return this;
    }

    @Override
    public ProductCatalogData build() {
        return new ProductCatalogDataImpl(isPublished, current, staged);
    }
}
