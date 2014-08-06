package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;

import java.util.Locale;

public class CategoryQuery extends DefaultModelQuery<Category> {

    public CategoryQuery() {
        super("/categories", resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<Category>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Category>>() {
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Category>>";
            }
        };
    }

    public QueryDsl<Category> bySlug(final Locale locale, final String slug) {
        return withPredicate(CategoryQueryModel.get().slug().lang(locale).is(slug));
    }

    public QueryDsl<Category> byName(final Locale locale, final String name) {
        return withPredicate(CategoryQueryModel.get().name().lang(locale).is(name));
    }

    public QueryDsl<Category> byId(final String id) {
        return withPredicate(CategoryQueryModel.get().id().is(id));
    }

    public static CategoryExpansionModel expansionPath() {
        return new CategoryExpansionModel();
    }

    public static CategoryQuery of() {
        return new CategoryQuery();
    }
}
