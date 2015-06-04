package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;

import java.util.Optional;

public final class ProductProjectionQueryModel<T> extends ProductDataQueryModelBase<T> {

    private ProductProjectionQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static ProductProjectionQueryModel<ProductProjection> of() {
        return new ProductProjectionQueryModel<>(Optional.<QueryModel<ProductProjection>>empty(), Optional.<String>empty());
    }

    public ReferenceQueryModel<T, ProductType> productType() {
        return new ReferenceQueryModel<>(Optional.of(this), "productType");
    }

    public BooleanQueryModel<T> hasStagedChanges() {
        return new BooleanQueryModel<>(Optional.of(this), "hasStagedChanges");
    }

    @Override
    public ReferenceListQueryModel<T, Category> categories() {
        return super.categories();
    }

    @Override
    public LocalizedStringsQueryModel<T> description() {
        return super.description();
    }

    @Override
    public ProductAllVariantsQueryModel<T> allVariants() {
        return super.allVariants();
    }

    @Override
    public ProductVariantQueryModel<T> masterVariant() {
        return super.masterVariant();
    }

    @Override
    public LocalizedStringsQuerySortingModel<T> metaDescription() {
        return super.metaDescription();
    }

    @Override
    public LocalizedStringsQuerySortingModel<T> metaKeywords() {
        return super.metaKeywords();
    }

    @Override
    public LocalizedStringsQuerySortingModel<T> metaTitle() {
        return super.metaTitle();
    }

    @Override
    public LocalizedStringsQuerySortingModel<T> name() {
        return super.name();
    }

    @Override
    public LocalizedStringsQuerySortingModel<T> slug() {
        return super.slug();
    }

    @Override
    public ProductVariantQueryModel<T> variants() {
        return super.variants();
    }
}