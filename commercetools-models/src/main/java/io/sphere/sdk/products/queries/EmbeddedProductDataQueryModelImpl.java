package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

import javax.annotation.Nullable;
import java.util.function.Function;

final class EmbeddedProductDataQueryModelImpl extends ProductDataQueryModelImpl<EmbeddedProductDataQueryModel> implements EmbeddedProductDataQueryModel {
    EmbeddedProductDataQueryModelImpl(@Nullable final QueryModel<EmbeddedProductDataQueryModel> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ReferenceCollectionQueryModel<EmbeddedProductDataQueryModel, Category> categories() {
        return super.categories();
    }

    @Override
    public LocalizedStringQueryModel<EmbeddedProductDataQueryModel> description() {
        return super.description();
    }

    @Override
    public ProductVariantQueryModel<EmbeddedProductDataQueryModel> masterVariant() {
        return super.masterVariant();
    }

    @Override
    public LocalizedStringQuerySortingModel<EmbeddedProductDataQueryModel> metaDescription() {
        return super.metaDescription();
    }

    @Override
    public LocalizedStringQuerySortingModel<EmbeddedProductDataQueryModel> metaKeywords() {
        return super.metaKeywords();
    }

    @Override
    public LocalizedStringQuerySortingModel<EmbeddedProductDataQueryModel> metaTitle() {
        return super.metaTitle();
    }

    @Override
    public LocalizedStringQuerySortingModel<EmbeddedProductDataQueryModel> name() {
        return super.name();
    }

    @Override
    public LocalizedStringQuerySortingModel<EmbeddedProductDataQueryModel> slug() {
        return super.slug();
    }

    @Override
    public ProductVariantQueryModel<EmbeddedProductDataQueryModel> variants() {
        return super.variants();
    }

    @Override
    public QueryPredicate<EmbeddedProductDataQueryModel> where(final Function<EmbeddedProductDataQueryModel, QueryPredicate<EmbeddedProductDataQueryModel>> embeddedPredicate) {
        return super.where(embeddedPredicate);
    }

    @Override
    public QueryPredicate<EmbeddedProductDataQueryModel> where(final QueryPredicate<EmbeddedProductDataQueryModel> embeddedPredicate) {
        return super.where(embeddedPredicate);
    }
}
