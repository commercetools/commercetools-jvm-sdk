package io.sphere.sdk.categories;

import io.sphere.sdk.queries.EntityQueryWithCopy;
import io.sphere.sdk.queries.EntityQueryWithCopyWrapper;

import java.util.Locale;

public class BySlugCategoryQuery extends EntityQueryWithCopyWrapper<Category, CategoryImpl, CategoryQueryModel> {
    private final Locale locale;
    private final String slug;

    public BySlugCategoryQuery(final Locale locale, final String slug) {
        this.locale = locale;
        this.slug = slug;
    }

    @Override
    protected EntityQueryWithCopy<Category, CategoryImpl, CategoryQueryModel> delegate() {
        return Categories.query().withPredicate(CategoryQueryModel.get().slug().lang(locale).is(slug));
    }
}
