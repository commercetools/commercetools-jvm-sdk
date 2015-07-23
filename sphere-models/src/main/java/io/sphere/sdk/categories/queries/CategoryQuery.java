package io.sphere.sdk.categories.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;

import java.util.Locale;

/**
 * {@doc.gen summary categories}
 *
 */
public interface CategoryQuery extends MetaModelQueryDsl<Category, CategoryQuery, CategoryQueryModel, CategoryExpansionModel<Category>> {

    static TypeReference<PagedQueryResult<Category>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Category>>() {
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Category>>";
            }
        };
    }

    default CategoryQuery bySlug(final Locale locale, final String slug) {
        return withPredicates(m -> m.slug().lang(locale).is(slug));
    }

    default CategoryQuery byName(final Locale locale, final String name) {
        return withPredicates(m -> m.name().lang(locale).is(name));
    }

    default CategoryQuery byId(final String id) {
        return withPredicates(m -> m.id().is(id));
    }

    default CategoryQuery byIsRoot() {
        return withPredicates(m -> m.parent().isNotPresent());
    }

    static CategoryQuery of() {
        return new CategoryQueryImpl();
    }

    default CategoryQuery byExternalId(final String externalId) {
        return withPredicates(m -> m.externalId().is(externalId));
    }
}
