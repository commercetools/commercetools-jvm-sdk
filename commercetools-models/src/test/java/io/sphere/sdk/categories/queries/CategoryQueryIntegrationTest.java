package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.LocalizedStringEntry;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.categories.CategoryFixtures.withCategories;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryQueryIntegrationTest extends IntegrationTest {
    @Test
    public void queryByName() throws Exception {
        withCategory(client(), category -> {
            final LocalizedStringEntry name = category.getName().stream().findAny().get();

            final Query<Category> query = CategoryQuery.of().byName(name.getLocale(), name.getValue());
            assertThat(client().executeBlocking(query).head().get().getId()).isEqualTo(category.getId());
        });
    }

    @Test
    public void queryByExternalId() throws Exception {
        withCategory(client(), category -> {
            final String externalId = category.getExternalId();

            final Query<Category> query = CategoryQuery.of().byExternalId(externalId);

            final Category actual = client().executeBlocking(query).head().get();
            assertThat(actual).isEqualTo(category);
        });
    }

    @Test
    public void queryByNotName() throws Exception {
        withCategory(client(), category1 ->
            withCategory(client(), category2 -> {
                final Query<Category> query = CategoryQuery.of().
                        withPredicates(m -> m.name().lang(Locale.ENGLISH).isNot(category1.getName().get(Locale.ENGLISH)))
                        .withSort(m -> m.createdAt().sort().desc());
                final boolean category1IsPresent = client().executeBlocking(query).getResults().stream().anyMatch(cat -> cat.getId().equals(category1.getId()));
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
                        .withSort(m -> m.createdAt().sort().desc());
                final boolean category1IsPresent = client().executeBlocking(query).getResults().stream().anyMatch(cat -> cat.getId().equals(category1.getId()));
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
                        .withSort(m -> m.createdAt().sort().desc());
                final boolean category1IsPresent = client().executeBlocking(query).getResults().stream().anyMatch(cat -> cat.getId().equals(category1.getId()));
                assertThat(category1IsPresent).isFalse();
            })
        );
    }

    @Test
    public void withFetchTotalFalseRemovesTotalFromOutput() throws Exception {
        withCategory(client(), category -> {
            final CategoryQuery query = CategoryQuery.of().byId(category.getId());
            final PagedQueryResult<Category> resultWithTotal = client().executeBlocking(query);
            assertThat(resultWithTotal.getTotal())
                    .as("total is by default present")
                    .isNotNull().isEqualTo(1);

            final CategoryQuery queryWithoutTotal = query.withFetchTotal(false);
            final PagedQueryResult<Category> resultWithoutTotal = client().executeBlocking(queryWithoutTotal);
            assertThat(resultWithoutTotal.getTotal())
                    .as("total is not present")
                    .isNull();
        });
    }

    @Test
    public void thereIsNoDefaultSortInQueries() throws Exception {
        assertThat(CategoryQuery.of().sort()).isEmpty();
    }

    @Test
    public void queryByFullLocale() {
        final String englishSlug = randomKey();
        final String usSlug = randomKey();
        final LocalizedString slug = LocalizedString.of(Locale.ENGLISH, englishSlug, Locale.US, usSlug);
        final CategoryQueryModel m = CategoryQueryModel.of();
        withCategory(client(), CategoryDraftBuilder.of(randomSlug(), slug), category -> {
            assertThat(query(m.slug().lang(Locale.ENGLISH).is(englishSlug))).isPresent();
            assertThat(query(m.slug().lang(Locale.US).is(englishSlug))).isEmpty();
            assertThat(query(m.slug().lang(Locale.ENGLISH).is(usSlug))).isEmpty();
            assertThat(query(m.slug().lang(Locale.US).is(usSlug))).isPresent();
        });
    }

    @Test
    public void plusSort() {
        final List<QuerySort<Category>> sort = CategoryQuery.of()
                .withSort(m -> m.externalId().sort().asc())
                .plusSort(m -> m.createdAt().sort().desc()).sort();
        assertThat(sort).hasSize(2);
        assertThat(sort).extractingResultOf("toSphereSort").containsExactly("externalId asc", "createdAt desc");
    }

    @Test
    public void sortByOrderHint() {

        final List<String> hints = Arrays.asList("0.03539970150419025", "0.9124532896017283", "0.4677879627352788", "0.9306522049331096", "0.8442774484430258", "0.3002734797783949",
                "0.9053018615787292", "0.5897829295233494", "0.9736635784904704", "0.393321565584442", "0.605741386570728", "0.6644442788442779", "0.8541807248539874", "0.9945130750802729", "0.12979277595041627");

        List<String> sortedHints = new ArrayList<>(hints);
        Collections.sort(sortedHints, Comparator.comparingDouble(Double::valueOf));

        withCategories(client(),
                hints.stream().map(CategoryQueryIntegrationTest::categoryWithHint).collect(Collectors.toList()),

                categories -> {
                    PagedQueryResult<Category> res = client().executeBlocking(CategoryQuery.of().plusSort(m -> m.orderHint().sort().asc()).withLimit(500));
                    List<String> queryResultHints = res.getResults().stream().filter(cat -> categories.contains(cat)).map(cat -> cat.getOrderHint()).collect(Collectors.toList());
                    assertThat(queryResultHints).containsExactlyElementsOf(sortedHints);
                });
    }

    @Test
    public void queryForCreatedAtIs() {
        createdAtTest((m, date) -> m.is(date));
    }

    @Test
    public void queryForCreatedAtIsNot() {
        createdAtTest((m, date) -> m.isNot(date.plusMinutes(1)));
    }

    @Test
    public void queryForCreatedAtIsGreaterThan() {
        createdAtTest((m, date) -> m.isGreaterThan(date.minusSeconds(1)));
    }

    @Test
    public void queryForCreatedAtIsGreaterThanOrEqualTo() {
        createdAtTest((m, date) -> m.isGreaterThanOrEqualTo(date.minusSeconds(1)));
    }

    @Test
    public void queryForCreatedAtIsLessThan() {
        createdAtTest((m, date) -> m.isLessThan(date.plusSeconds(1)));
    }

    @Test
    public void queryForCreatedAtIsLessThanOrEqualTo() {
        createdAtTest((m, date) -> m.isLessThanOrEqualTo(date.plusSeconds(1)));
    }

    @Test
    public void queryForCreatedAtIsIn() {
        createdAtTest((m, date) -> m.isIn(singletonList(date)));
    }

    @Test
    public void queryForCreatedAtIsNotIn() {
        createdAtTest((m, date) -> m.isNotIn(singletonList(date.plusMinutes(2))));
    }

    private void createdAtTest(final BiFunction<TimestampSortingModel<Category>, ZonedDateTime, QueryPredicate<Category>> predicateFunction) {
        withCategory(client(), category -> {
            final QueryPredicate<Category> predicate = predicateFunction.apply(CategoryQueryModel.of().createdAt(), category.getCreatedAt());

            final CategoryQuery baseQuery = CategoryQuery.of()
                    .withPredicates(predicate)
                    .plusPredicates(m -> m.id().is(category.getId()));

            final Optional<Category> fetched = client().executeBlocking(baseQuery).head();
            assertThat(fetched).contains(category);
        });
    }

    private static Optional<Category> query(final QueryPredicate<Category> predicate) {
        return client().executeBlocking(CategoryQuery.of().withPredicates(predicate)).head();
    }

    private static CategoryDraftBuilder categoryWithHint(String orderHint){

        final String englishSlug = randomKey();
        final String usSlug = randomKey();
        final LocalizedString slug = LocalizedString.of(Locale.ENGLISH, englishSlug, Locale.US, usSlug);
        return CategoryDraftBuilder.of(randomSlug(), slug).orderHint(orderHint);

    }

}