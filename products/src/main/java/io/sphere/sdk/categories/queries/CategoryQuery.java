package io.sphere.sdk.categories.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.QueryDsl;

import java.util.Locale;

/**
 * {@doc.gen summary categories}
 *
 * <p>Example:</p>
 *
 * {@include.example example.CategoryLifecycleExample#query()}
 */
public class CategoryQuery extends DefaultModelQuery<Category> {

    public CategoryQuery() {
        super(CategoriesEndpoint.ENDPOINT.endpoint(), resultTypeReference());
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
        return withPredicate(model().slug().lang(locale).is(slug));
    }

    public QueryDsl<Category> byName(final Locale locale, final String name) {
        return withPredicate(model().name().lang(locale).is(name));
    }

    public QueryDsl<Category> byId(final String id) {
        return withPredicate(model().id().is(id));
    }

    public static CategoryExpansionModel<Category> expansionPath() {
        return new CategoryExpansionModel<>();
    }

    public static CategoryQuery of() {
        return new CategoryQuery();
    }

    public static CategoryQueryModel model() {
        return CategoryQueryModel.get();
    }

    public Query<Category> byExternalId(final String externalId) {
        return withPredicate(model().externalId().is(externalId));
    }
}
