package io.sphere.sdk.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.SphereClient;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.fest.assertions.Assertions.assertThat;

public class QueryDemo {
    private SphereClient client;
    private Query<Category> query;
    private CompletableFuture<PagedQueryResult<Category>> future;
    private final Locale locale = Locale.ENGLISH;



    private void formulatingAQuery() {
        Query<Category> query = CategoryQuery.of().byName(Locale.ENGLISH, "demo cat");
    }

    private void executeQuery() {
        CompletableFuture<PagedQueryResult<Category>> future = client.execute(query);
    }

    private void createQueryWithCompanionClass() {
        Query<Category> query = CategoryQuery.of().byName(Locale.ENGLISH, "demo cat");
    }

    private void queryFromCompanionHelper() {
        Query<Category> queryById = CategoryQuery.of().byId("the-id");
        Query<Category> queryBySlug = CategoryQuery.of().bySlug(Locale.ENGLISH, "category-slug");
        Query<Category> queryByName = CategoryQuery.of().byName(Locale.ENGLISH, "demo cat");
    }

    private void categoryQueryModel() {
        Predicate<Category> predicate = CategoryQuery.model().name().lang(locale).is("demo cat");
        Query<Category> query = CategoryQuery.of().withPredicate(predicate);
    }

    private void withPagination() {
        Predicate<Category> predicate = CategoryQuery.model().name().lang(locale).is("demo cat");

        Sort<Category> sortByName = CategoryQuery.model().name().lang(locale).sort(SortDirection.DESC);
        Sort<Category> sortById = CategoryQuery.model().id().sort(SortDirection.ASC);
        List<Sort<Category>> sort = Arrays.asList(sortByName, sortById);//sort by name desc and then by ID if name is the same

        int offset = 1;//skip first page
        int limit = 200;//collect at most 200 entities per request
        Query<Category> query = CategoryQuery.of().
          withPredicate(predicate).
          withSort(sort).
          withOffset(offset).
          withLimit(limit);
    }

    private void immutableQueryDsl() {
        CategoryQuery query = CategoryQuery.of();
        assertThat(query).isNotEqualTo(query.withLimit(30));
        assertThat(query.limit()).isEqualTo(Optional.empty());
        assertThat(query.withLimit(30).limit()).isEqualTo(Optional.of(30));
    }

    private void nextPage() {
        CategoryQuery query = CategoryQuery.of();
        Long previousOffset = query.offset().orElse(0L);//on the first page, the offset is unset for 0
        Query<Category> queryForNextPageVariant1 = query.withOffset(previousOffset + 1);
        //alternatively
        Query<Category> queryForNextPageVariant2 = Queries.nextPage(query);
    }
}
