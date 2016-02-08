package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;
import io.sphere.sdk.types.queries.CustomResourceQueryModelImpl;

final class CategoryQueryModelImpl extends CustomResourceQueryModelImpl<Category> implements CategoryQueryModel {

    CategoryQueryModelImpl(QueryModel<Category> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public LocalizedStringQuerySortingModel<Category> slug() {
        return localizedStringQuerySortingModel("slug");
    }

    @Override
    public LocalizedStringQuerySortingModel<Category> name() {
        return localizedStringQuerySortingModel("name");
    }

    @Override
    public StringQuerySortingModel<Category> externalId() {
        return stringModel("externalId");
    }

    @Override
    public ReferenceOptionalQueryModel<Category, Category> parent() {
        return referenceOptionalModel("parent");
    }

    @Override
    public CustomQueryModel<Category> custom() {
        return super.custom();
    }

    @Override
    public QueryPredicate<Category> is(final Identifiable<Category> identifiable) {
        return super.is(identifiable);
    }
}
