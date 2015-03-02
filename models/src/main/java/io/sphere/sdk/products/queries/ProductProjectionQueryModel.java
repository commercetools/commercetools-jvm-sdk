package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;

import java.util.Optional;

public class ProductProjectionQueryModel extends ProductDataQueryModelBase<ProductProjection> {

    static ProductProjectionQueryModel get() {
        return new ProductProjectionQueryModel(Optional.<QueryModel<ProductProjection>>empty(), Optional.<String>empty());
    }

    private ProductProjectionQueryModel(final Optional<? extends QueryModel<ProductProjection>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public ReferenceQueryModel<ProductProjection, ProductType> productType() {
        return new ReferenceQueryModel<>(Optional.of(this), "productType");
    }

    public BooleanQueryModel<ProductProjection> hasStagedChanges() {
        return new BooleanQueryModel<>(Optional.of(this), "hasStagedChanges");
    }

    @Override
    public ReferenceListQueryModel<ProductProjection, Category> categories() {
        return super.categories();
    }

    @Override
    public LocalizedStringsQueryModel<ProductProjection> description() {
        return super.description();
    }

    @Override
    public ProductVariantQueryModel<ProductProjection> masterVariant() {
        return super.masterVariant();
    }

    @Override
    public LocalizedStringsQuerySortingModel<ProductProjection> metaDescription() {
        return super.metaDescription();
    }

    @Override
    public LocalizedStringsQuerySortingModel<ProductProjection> metaKeywords() {
        return super.metaKeywords();
    }

    @Override
    public LocalizedStringsQuerySortingModel<ProductProjection> metaTitle() {
        return super.metaTitle();
    }

    @Override
    public LocalizedStringsQuerySortingModel<ProductProjection> name() {
        return super.name();
    }

    @Override
    public LocalizedStringsQuerySortingModel<ProductProjection> slug() {
        return super.slug();
    }

    @Override
    public ProductVariantQueryModel<ProductProjection> variants() {
        return super.variants();
    }
}