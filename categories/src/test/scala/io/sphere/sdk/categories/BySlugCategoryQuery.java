package io.sphere.sdk.categories;

import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.queries.QueryDslWrapper;

import java.util.Locale;

public class BySlugCategoryQuery extends QueryDslWrapper<Category, CategoryImpl, CategoryQueryModel<Category>> {
    private final Locale locale;
    private final String slug;

    public BySlugCategoryQuery(final Locale locale, final String slug) {
        this.locale = locale;
        this.slug = slug;
    }

    @Override
    protected QueryDsl<Category, CategoryImpl, CategoryQueryModel<Category>> delegate() {
        return Categories.query().withPredicate(CategoryQueryModel.get().slug().lang(locale).is(slug));
    }
}
