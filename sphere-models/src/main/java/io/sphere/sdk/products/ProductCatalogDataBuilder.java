package io.sphere.sdk.products;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Nullable;

public class ProductCatalogDataBuilder extends Base implements Builder<ProductCatalogData> {
    private final boolean isPublished;
    private boolean hasStagedChanges;
    @Nullable
    private ProductData current;
    private ProductData staged;

    private ProductCatalogDataBuilder(final boolean isPublished, @Nullable final ProductData current, final ProductData staged,
                                      final boolean hasStagedChanges) {
        this.isPublished = isPublished;
        this.current = current;
        this.staged = staged;
        this.hasStagedChanges = hasStagedChanges;
    }

    public static ProductCatalogDataBuilder ofStaged(final ProductData staged) {
        return new ProductCatalogDataBuilder(true, null, staged, false);
    }

    public ProductCatalogDataBuilder current(@Nullable final ProductData current) {
        this.current = current;
        fixInvariants();
        return this;
    }

    public ProductCatalogDataBuilder staged(final ProductData staged) {
        this.staged = staged;
        fixInvariants();
        return this;
    }

    private void fixInvariants() {
        hasStagedChanges = current.equals(staged);
    }

    @Override
    public ProductCatalogData build() {
        return new ProductCatalogDataImpl(isPublished, current, staged, hasStagedChanges);
    }
}
