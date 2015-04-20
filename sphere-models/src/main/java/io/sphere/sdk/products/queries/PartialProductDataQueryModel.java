package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

import java.util.Optional;
import java.util.function.Function;

public class PartialProductDataQueryModel extends ProductDataQueryModel<PartialProductDataQueryModel> {
    public PartialProductDataQueryModel(final Optional<? extends QueryModel<PartialProductDataQueryModel>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ReferenceListQueryModel<PartialProductDataQueryModel, Category> categories() {
        return super.categories();
    }

    @Override
    public LocalizedStringsQueryModel<PartialProductDataQueryModel> description() {
        return super.description();
    }

    @Override
    public ProductVariantQueryModel<PartialProductDataQueryModel> masterVariant() {
        return super.masterVariant();
    }

    @Override
    public LocalizedStringsQuerySortingModel<PartialProductDataQueryModel> metaDescription() {
        return super.metaDescription();
    }

    @Override
    public LocalizedStringsQuerySortingModel<PartialProductDataQueryModel> metaKeywords() {
        return super.metaKeywords();
    }

    @Override
    public LocalizedStringsQuerySortingModel<PartialProductDataQueryModel> metaTitle() {
        return super.metaTitle();
    }

    @Override
    public LocalizedStringsQuerySortingModel<PartialProductDataQueryModel> name() {
        return super.name();
    }

    @Override
    public LocalizedStringsQuerySortingModel<PartialProductDataQueryModel> slug() {
        return super.slug();
    }

    @Override
    public ProductVariantQueryModel<PartialProductDataQueryModel> variants() {
        return super.variants();
    }

    @Override
    public Predicate<PartialProductDataQueryModel> where(final Function<PartialProductDataQueryModel, Predicate<PartialProductDataQueryModel>> embeddedPredicate) {
        return super.where(embeddedPredicate);
    }

    @Override
    public Predicate<PartialProductDataQueryModel> where(final Predicate<PartialProductDataQueryModel> embeddedPredicate) {
        return super.where(embeddedPredicate);
    }
}
