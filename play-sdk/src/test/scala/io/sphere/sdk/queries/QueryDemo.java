package io.sphere.sdk.queries;

import java.util.Optional;
import io.sphere.sdk.categories.*;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.PlayJavaClient;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.fest.assertions.Assertions.assertThat;

public class QueryDemo extends Controller {
    private PlayJavaClient client;
    private Query<Category> query;
    private F.Promise<PagedQueryResult<Category>> promise;
    private Locale locale = Locale.ENGLISH;



    private void formulatingAQuery() {
        Query<Category> query = new CategoryQuery().byName(Locale.ENGLISH, "demo cat");
    }

    private void executeQuery() {
        F.Promise<PagedQueryResult<Category>> promise = client.execute(query);
    }

    private void mapResult() {
         promise.map(pagedQueryResult -> {
            List<Category> categories = pagedQueryResult.getResults();
            return ok(categoriesTemplate.render(categories));
        });
    }

    private void clientShowAsyncProcessing() {
        Query<Category> query = new CategoryQuery().byName(Locale.ENGLISH, "demo cat");
        F.Promise<PagedQueryResult<Category>> promise = client.execute(query);
        F.Promise<Result> result = promise.map(pagedQueryResult -> {
            List<Category> categories = pagedQueryResult.getResults();
            return ok(categoriesTemplate.render(categories));
        });
    }


    private void createQueryWithCompanionClass() {
        Query<Category> query = new CategoryQuery().byName(Locale.ENGLISH, "demo cat");
    }


    private F.Promise<Result> introduction2() {
        final Query<Category> query = new CategoryQuery().byName(Locale.ENGLISH, "demo category");
        return client.execute(query).map(
                pagedQueryResult -> ok(categoriesTemplate.render(pagedQueryResult.getResults()))
        );
    }

    private void queryFromCompanionHelper() {
        Query<Category> queryById = new CategoryQuery().byId("the-id");
        Query<Category> queryBySlug = new CategoryQuery().bySlug(Locale.ENGLISH, "category-slug");
        Query<Category> queryByName = new CategoryQuery().byName(Locale.ENGLISH, "demo cat");
    }

    private void categoryQueryModel() {
        Predicate<Category> predicate = CategoryQuery.model().name().lang(locale).is("demo cat");
        Query<Category> query = new CategoryQuery().withPredicate(predicate);
    }

    private void withPagination() {
        Predicate<Category> predicate = CategoryQuery.model().name().lang(locale).is("demo cat");

        Sort<Category> sortByName = CategoryQuery.model().name().lang(locale).sort(SortDirection.DESC);
        Sort<Category> sortById = CategoryQuery.model().id().sort(SortDirection.ASC);
        List<Sort<Category>> sort = Arrays.asList(sortByName, sortById);//sort by name desc and then by ID if name is the same

        int offset = 1;//skip first page
        int limit = 200;//collect at most 200 entities per request
        Query<Category> query = new CategoryQuery().
          withPredicate(predicate).
          withSort(sort).
          withOffset(offset).
          withLimit(limit);
    }

    private void immutableQueryDsl() {
        CategoryQuery query = new CategoryQuery();
        assertThat(query).isNotEqualTo(query.withLimit(30));
        assertThat(query.limit()).isEqualTo(Optional.empty());
        assertThat(query.withLimit(30).limit()).isEqualTo(Optional.of(30));
    }

    private void nextPage() {
        CategoryQuery query = new CategoryQuery();
        Long previousOffset = query.offset().orElse(0L);//on the first page, the offset is unset for 0
        Query<Category> queryForNextPageVariant1 = query.withOffset(previousOffset + 1);
        //alternatively
        Query<Category> queryForNextPageVariant2 = Queries.nextPage(query);
    }
}
