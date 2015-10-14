package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public class ProductProjectionFilterSearchModel extends ProductDataFilterSearchModel {

    ProductProjectionFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ProductVariantFilterSearchModel allVariants() {
        return super.allVariants();
    }

    @Override
    public TermFilterSearchModel<ProductProjection, String> id() {
        return super.id();
    }

    @Override
    public LocalizedStringFilterSearchModel<ProductProjection> name() {
        return super.name();
    }

    @Override
    public ReferenceFilterSearchModel<ProductProjection> categories() {
        return super.categories();
    }

    @Override
    public ReferenceFilterSearchModel<ProductProjection> productType() {
        return super.productType();
    }

    @Override
    public RangeFilterSearchModel<ProductProjection, ZonedDateTime> createdAt() {
        return super.createdAt();
    }

    @Override
    public RangeFilterSearchModel<ProductProjection, ZonedDateTime> lastModifiedAt() {
        return super.lastModifiedAt();
    }
}
