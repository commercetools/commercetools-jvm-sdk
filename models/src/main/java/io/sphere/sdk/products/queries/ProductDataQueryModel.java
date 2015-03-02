package io.sphere.sdk.products.queries;

import java.util.Optional;
import java.util.function.Function;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

public class ProductDataQueryModel<T> extends ProductDataQueryModelBase<T> {

    public static PartialProductDataQueryModel get() {
        return new PartialProductDataQueryModel(Optional.empty(), Optional.empty());
    }
   
    ProductDataQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public Predicate<T> where(final Predicate<PartialProductDataQueryModel> embeddedPredicate) {
        return new EmbeddedPredicate<>(this, embeddedPredicate);
    }

    public Predicate<T> where(final Function<PartialProductDataQueryModel, Predicate<PartialProductDataQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(ProductDataQueryModel.get()));
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

