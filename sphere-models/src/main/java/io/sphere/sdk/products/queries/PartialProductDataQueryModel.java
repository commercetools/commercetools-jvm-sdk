package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

import javax.annotation.Nullable;
import java.util.function.Function;

public final class PartialProductDataQueryModel extends ProductDataQueryModel<PartialProductDataQueryModel> {
    private PartialProductDataQueryModel(@Nullable final QueryModel<PartialProductDataQueryModel> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public static PartialProductDataQueryModel of() {
        return new PartialProductDataQueryModel(null, null);
    }

    @Override
    public ReferenceCollectionQueryModel<PartialProductDataQueryModel, Category> categories() {
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
    public QueryPredicate<PartialProductDataQueryModel> where(final Function<PartialProductDataQueryModel, QueryPredicate<PartialProductDataQueryModel>> embeddedPredicate) {
        return super.where(embeddedPredicate);
    }

    @Override
    public QueryPredicate<PartialProductDataQueryModel> where(final QueryPredicate<PartialProductDataQueryModel> embeddedPredicate) {
        return super.where(embeddedPredicate);
    }
}
