package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public class ProductDataFilterSearchModel extends SearchModelImpl<ProductProjection> {

    ProductDataFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductVariantFilterSearchModel allVariants() {
        return new ProductVariantFilterSearchModel(this, "variants");
    }

    public TermFilterSearchModel<ProductProjection, String> id() {
        return new StringSearchModel<>(this, "id").filtered();
    }

    public LocalizedStringFilterSearchModel<ProductProjection> name() {
        return new LocalizedStringFilterSearchModel<>(this, "name");
    }

    public ReferenceFilterSearchModel<ProductProjection> categories() {
        return new ReferenceFilterSearchModel<>(this, "categories");
    }

    public ReferenceFilterSearchModel<ProductProjection> productType() {
        return new ReferenceFilterSearchModel<>(this, "productType");
    }

    public RangeFilterSearchModel<ProductProjection, ZonedDateTime> createdAt() {
        return new DateTimeSearchModel<>(this, "createdAt").filtered();
    }

    public RangeFilterSearchModel<ProductProjection, ZonedDateTime> lastModifiedAt() {
        return new DateTimeSearchModel<>(this, "lastModifiedAt").filtered();
    }
}
