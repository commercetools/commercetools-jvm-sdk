package io.sphere.sdk.categories;

import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.queries.QueryDslImpl;

import java.util.Locale;

public class CategoryQuery extends QueryDslImpl<Category, CategoryImpl, CategoryQueryModel<Category>> {

    public CategoryQuery() {
        super(CategoryRequestDefaults.ENDPOINT, QueryDslImpl.<Category, CategoryImpl>resultMapperOf(CategoryRequestDefaults.PAGED_QUERY_RESULT_TYPE_REFERENCE));
    }

    public QueryDsl<Category, CategoryQueryModel<Category>> bySlug(final Locale locale, final String slug) {
        return withPredicate(CategoryQueryModel.get().slug().lang(locale).is(slug));
    }

    public QueryDsl<Category, CategoryQueryModel<Category>> byName(final Locale locale, final String name) {
        return withPredicate(CategoryQueryModel.get().name().lang(locale).is(name));
    }

    public QueryDsl<Category, CategoryQueryModel<Category>> byId(final String id) {
        return withPredicate(CategoryQueryModel.get().id().is(id));
    }
}
