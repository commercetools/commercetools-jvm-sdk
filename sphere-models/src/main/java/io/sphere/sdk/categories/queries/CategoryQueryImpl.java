package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.queries.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * {@doc.gen summary categories}
 *
 */
final class CategoryQueryImpl extends UltraQueryDslImpl<Category, CategoryQuery, CategoryQueryModel<Category>, CategoryExpansionModel<Category>> implements CategoryQuery {
    CategoryQueryImpl(){
        super(CategoryEndpoint.ENDPOINT.endpoint(), CategoryQuery.resultTypeReference(), CategoryQueryModel.of(), CategoryExpansionModel.of());
    }

    private CategoryQueryImpl(final Optional<QueryPredicate<Category>> predicate, final List<QuerySort<Category>> sort, final Optional<Long> limit, final Optional<Long> offset, final String endpoint, final Function<HttpResponse, PagedQueryResult<Category>> resultMapper, final List<ExpansionPath<Category>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters, final CategoryQueryModel<Category> queryModel, final CategoryExpansionModel<Category> expansionModel) {
        super(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
    }

    @Override
    protected UltraQueryDslBuilder<Category, CategoryQuery, CategoryQueryModel<Category>, CategoryExpansionModel<Category>> copyBuilder() {
        return new CategoryQueryQueryDslBuilder(this);
    }

    private static class CategoryQueryQueryDslBuilder extends UltraQueryDslBuilder<Category, CategoryQuery, CategoryQueryModel<Category>, CategoryExpansionModel<Category>> {
        public CategoryQueryQueryDslBuilder(final UltraQueryDslImpl<Category, CategoryQuery, CategoryQueryModel<Category>, CategoryExpansionModel<Category>> template) {
            super(template);
        }

        @Override
        public CategoryQueryImpl build() {
            return new CategoryQueryImpl(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
        }
    }
}