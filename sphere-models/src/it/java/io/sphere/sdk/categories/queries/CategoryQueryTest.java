package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedStringsEntry;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.AbstractIntegerAssert;
import org.junit.Test;

import java.util.Locale;
import java.util.function.Consumer;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.queries.QuerySortDirection.DESC;
import static org.assertj.core.api.Assertions.*;

public class CategoryQueryTest extends IntegrationTest {
    @Test
    public void queryByName() throws Exception {
        withCategory(client(), category -> {
            final LocalizedStringsEntry name = category.getName().stream().findAny().get();

            final Query<Category> query = CategoryQuery.of().byName(name.getLocale(), name.getValue());
            assertThat(execute(query).head().get().getId()).isEqualTo(category.getId());
        });
    }

    @Test
    public void queryByExternalId() throws Exception {
        withCategory(client(), category -> {
            final String externalId = category.getExternalId().get();

            final Query<Category> query = CategoryQuery.of().byExternalId(externalId);
            assertThat(execute(query).head().get().getId()).isEqualTo(category.getId());
        });
    }

    @Test
    public void queryByNotName() throws Exception {
        withCategory(client(), category1 ->
            withCategory(client(), category2 -> {
                final Query<Category> query = CategoryQuery.of().
                        withPredicates(m -> m.name().lang(Locale.ENGLISH).isNot(category1.getName().get(Locale.ENGLISH)))
                        .withSort(m -> m.createdAt().sort(DESC));
                final boolean category1IsPresent = execute(query).getResults().stream().anyMatch(cat -> cat.getId().equals(category1.getId()));
                assertThat(category1IsPresent).isFalse();
            })
        );
    }

    @Test
    public void queryByNegatedPredicateName() throws Exception {
        withCategory(client(), category1 ->
            withCategory(client(), category2 -> {
                final Query<Category> query = CategoryQuery.of().
                        withPredicates(m -> {
                            final QueryPredicate<Category> predicate =
                                    m.name().lang(Locale.ENGLISH).is(category1.getName().get(Locale.ENGLISH)).negate();
                            return predicate;
                        })
                        .withSort(m -> m.createdAt().sort(DESC));
                final boolean category1IsPresent = execute(query).getResults().stream().anyMatch(cat -> cat.getId().equals(category1.getId()));
                assertThat(category1IsPresent).isFalse();
            })
        );
    }

    @Test
    public void queryByNegatedPredicateNameValidWithAnd() throws Exception {
        withCategory(client(), category1 ->
            withCategory(client(), category2 -> {
                final Query<Category> query = CategoryQuery.of().
                        withPredicates(m -> {
                            final QueryPredicate<Category> predicate =
                                    m.name().lang(Locale.ENGLISH).is(category1.getName().get(Locale.ENGLISH)).negate()
                                            .and(m.id().is(category1.getId()));
                            return predicate;
                        })
                        .withSort(m -> m.createdAt().sort(DESC));
                final boolean category1IsPresent = execute(query).getResults().stream().anyMatch(cat -> cat.getId().equals(category1.getId()));
                assertThat(category1IsPresent).isFalse();
            })
        );
    }

    @Test
    public void withFetchTotalFalseRemovesTotalFromOutput() throws Exception {
        withCategory(client(), category -> {
            final CategoryQuery baseQuery = CategoryQuery.of().byId(category.getId()).withLimit(1);
            checkTotalInQueryResultOf(baseQuery, total -> total.isNotNull().isEqualTo(1));
            checkTotalInQueryResultOf(baseQuery.withFetchTotal(true), total -> total.isNotNull().isEqualTo(1));
            checkTotalInQueryResultOf(baseQuery.withFetchTotal(false), total -> total.isNull());
        });

    }

    @Test
    public void thereIsNoDefaultSortInQueries() throws Exception {
        assertThat(CategoryQuery.of().sort()).isEmpty();
    }

    private static void checkTotalInQueryResultOf(final Query<Category> query, final Consumer<AbstractIntegerAssert<?>> check) {
        check.accept(assertThat(execute(query).getTotal()));
    }
}