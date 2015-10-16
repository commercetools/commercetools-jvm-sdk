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
        return stringSearchModel("id").filtered();
    }

    public LocalizedStringFilterSearchModel<ProductProjection> name() {
        return localizedStringFilterSearchModel("name");
    }

    public ReferenceFilterSearchModel<ProductProjection> categories() {
        return referenceFilterSearchModel("categories");
    }

    public ReferenceFilterSearchModel<ProductProjection> productType() {
        return referenceFilterSearchModel("productType");
    }

    public RangeFilterSearchModel<ProductProjection, ZonedDateTime> createdAt() {
        return datetimeSearchModel("createdAt").filtered();
    }

    public RangeFilterSearchModel<ProductProjection, ZonedDateTime> lastModifiedAt() {
        return datetimeSearchModel("lastModifiedAt").filtered();
    }
}
