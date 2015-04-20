package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

import java.util.Optional;
import java.util.function.Function;

class ProductDataQueryModelBase<T> extends DefaultModelQueryModelImpl<T> {

    public static PartialProductDataQueryModel getPartialProductDataQueryModel() {
        return new PartialProductDataQueryModel(Optional.empty(), Optional.empty());
    }

    public Predicate<T> where(final Predicate<PartialProductDataQueryModel> embeddedPredicate) {
        return new EmbeddedPredicate<>(this, embeddedPredicate);
    }

    public Predicate<T> where(final Function<PartialProductDataQueryModel, Predicate<PartialProductDataQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(ProductDataQueryModel.getPartialProductDataQueryModel()));
    }

    ProductDataQueryModelBase(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringsQuerySortingModel<T> name() {
        return LocalizedStringsQuerySortingModel.of(this, "name");
    }

    public LocalizedStringsQueryModel<T> description() {
        return LocalizedStringsQuerySortingModel.of(this, "description");
    }

    public LocalizedStringsQuerySortingModel<T> slug() {
        return LocalizedStringsQuerySortingModel.of(this, "slug");
    }

    public ProductAllVariantsQueryModel<T> allVariants() {
        return new ProductAllVariantsQueryModel<>(Optional.of(this));
    }

    public ProductVariantQueryModel<T> masterVariant() {
        return new ProductVariantQueryModel<>(Optional.of(this), "masterVariant");
    }

    public ProductVariantQueryModel<T> variants() {
        return new ProductVariantQueryModel<>(Optional.of(this), "variants");
    }

    public LocalizedStringsQuerySortingModel<T> metaTitle() {
        return LocalizedStringsQuerySortingModel.of(this, "metaTitle");
    }

    public LocalizedStringsQuerySortingModel<T> metaKeywords() {
        return LocalizedStringsQuerySortingModel.of(this, "metaKeywords");
    }

    public LocalizedStringsQuerySortingModel<T> metaDescription() {
        return LocalizedStringsQuerySortingModel.of(this, "metaDescription");
    }

    public ReferenceListQueryModel<T, Category> categories() {
        return new ReferenceListQueryModel<>(Optional.of(this), "categories");
    }
}

