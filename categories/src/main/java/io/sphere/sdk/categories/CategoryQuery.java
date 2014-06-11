package io.sphere.sdk.categories;

import io.sphere.sdk.queries.EntityQueryWithCopy;
import io.sphere.sdk.queries.EntityQueryWithCopyImpl;

import java.util.Locale;

public class CategoryQuery extends EntityQueryWithCopyImpl<Category, CategoryImpl, CategoryQueryModel<Category>> {

    public CategoryQuery() {
        super(CategoryRequestDefaults.ENDPOINT, CategoryRequestDefaults.PAGED_QUERY_RESULT_TYPE_REFERENCE);
    }

    public EntityQueryWithCopy<Category, CategoryImpl, CategoryQueryModel<Category>> bySlug(final Locale locale, final String slug) {
        return withPredicate(CategoryQueryModel.get().slug().lang(locale).is(slug));
    }

    public EntityQueryWithCopy<Category, CategoryImpl, CategoryQueryModel<Category>> byName(final Locale locale, final String name) {
        return withPredicate(CategoryQueryModel.get().name().lang(locale).is(name));
    }

    public EntityQueryWithCopy<Category, CategoryImpl, CategoryQueryModel<Category>> byId(final Locale locale, final String name) {
        return withPredicate(CategoryQueryModel.get().name().lang(locale).is(name));
    }
}
