package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public class ProductDataFacetSearchModel extends SearchModelImpl<ProductProjection> {

    public ProductDataFacetSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductVariantFacetSearchModel allVariants() {
        return new ProductVariantFacetSearchModel(this, "variants");
    }

    public TermFacetSearchModel<ProductProjection, String> id() {
        return new StringSearchModel<>(this, "id").faceted();
    }

    public LocalizedStringFacetSearchModel<ProductProjection> name() {
        return new LocalizedStringFacetSearchModel<>(this, "name");
    }

    public ReferenceFacetSearchModel<ProductProjection> categories() {
        return new ReferenceFacetSearchModel<>(this, "categories");
    }

    public ReferenceFacetSearchModel<ProductProjection> productType() {
        return new ReferenceFacetSearchModel<>(this, "productType");
    }

    public RangeFacetSearchModel<ProductProjection, ZonedDateTime> createdAt() {
        return new DateTimeSearchModel<>(this, "createdAt").faceted();
    }

    public RangeFacetSearchModel<ProductProjection, ZonedDateTime> lastModifiedAt() {
        return new DateTimeSearchModel<>(this, "lastModifiedAt").faceted();
    }
}
