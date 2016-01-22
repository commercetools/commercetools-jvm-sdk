package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

import javax.annotation.Nullable;
import java.util.function.Function;

final class PartialProductDataQueryModelImpl extends ProductDataQueryModelImpl<PartialProductDataQueryModel> implements PartialProductDataQueryModel {
    PartialProductDataQueryModelImpl(@Nullable final QueryModel<PartialProductDataQueryModel> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ReferenceCollectionQueryModel<PartialProductDataQueryModel, Category> categories() {
        return super.categories();
    }

    @Override
    public LocalizedStringQueryModel<PartialProductDataQueryModel> description() {
        return super.description();
    }

    @Override
    public ProductVariantQueryModel<PartialProductDataQueryModel> masterVariant() {
        return super.masterVariant();
    }

    @Override
    public LocalizedStringQuerySortingModel<PartialProductDataQueryModel> metaDescription() {
        return super.metaDescription();
    }

    @Override
    public LocalizedStringQuerySortingModel<PartialProductDataQueryModel> metaKeywords() {
        return super.metaKeywords();
    }

    @Override
    public LocalizedStringQuerySortingModel<PartialProductDataQueryModel> metaTitle() {
        return super.metaTitle();
    }

    @Override
    public LocalizedStringQuerySortingModel<PartialProductDataQueryModel> name() {
        return super.name();
    }

    @Override
    public LocalizedStringQuerySortingModel<PartialProductDataQueryModel> slug() {
        return super.slug();
    }

    @Override
    public ProductVariantQueryModel<PartialProductDataQueryModel> variants() {
        return super.variants();
    }

    @Override
    public QueryPredicate<PartialProductDataQueryModel> where(final Function<PartialProductDataQueryModel, QueryPredicate<PartialProductDataQueryModel>> embeddedPredicate) {
        return super.where(embeddedPredicate);
    }

    @Override
    public QueryPredicate<PartialProductDataQueryModel> where(final QueryPredicate<PartialProductDataQueryModel> embeddedPredicate) {
        return super.where(embeddedPredicate);
    }
}
