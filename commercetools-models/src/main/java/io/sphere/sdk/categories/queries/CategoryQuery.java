package io.sphere.sdk.categories.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/**
 * {@doc.gen summary categories}
 *
 * {@include.example io.sphere.sdk.categories.queries.CategoryQueryTest#queryByExternalId()}
 *
 */
public interface CategoryQuery extends MetaModelQueryDsl<Category, CategoryQuery, CategoryQueryModel, CategoryExpansionModel<Category>> {

    /**
     * Creates a container which contains the full Java type information to deserialize the query result (NOT this class) from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
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

    @Override
    CategoryQuery plusPredicates(final Function<CategoryQueryModel, QueryPredicate<Category>> m);

    @Override
    CategoryQuery plusPredicates(final QueryPredicate<Category> queryPredicate);

    @Override
    CategoryQuery plusPredicates(final List<QueryPredicate<Category>> queryPredicates);

    @Override
    CategoryQuery plusSort(final Function<CategoryQueryModel, QuerySort<Category>> m);

    @Override
    CategoryQuery plusSort(final List<QuerySort<Category>> sort);

    @Override
    CategoryQuery plusSort(final QuerySort<Category> sort);

    @Override
    CategoryQuery withPredicates(final Function<CategoryQueryModel, QueryPredicate<Category>> predicateFunction);

    @Override
    CategoryQuery withPredicates(final QueryPredicate<Category> queryPredicate);

    @Override
    CategoryQuery withPredicates(final List<QueryPredicate<Category>> queryPredicates);

    @Override
    CategoryQuery withSort(final Function<CategoryQueryModel, QuerySort<Category>> m);

    @Override
    CategoryQuery withSort(final List<QuerySort<Category>> sort);

    @Override
    CategoryQuery withSort(final QuerySort<Category> sort);

    @Override
    CategoryQuery withSortMulti(final Function<CategoryQueryModel, List<QuerySort<Category>>> m);

    @Override
    CategoryQuery plusExpansionPaths(final Function<CategoryExpansionModel<Category>, ExpansionPathContainer<Category>> m);

    @Override
    CategoryQuery withExpansionPaths(final Function<CategoryExpansionModel<Category>, ExpansionPathContainer<Category>> m);

    @Override
    CategoryQuery withFetchTotal(final boolean fetchTotal);

    @Override
    CategoryQuery withLimit(final Long limit);

    @Override
    CategoryQuery withOffset(final Long offset);
}
