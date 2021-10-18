package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.queries.EmbeddedProductDataQueryModel;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.products.queries.ProductQueryModel;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.test.IntegrationTest;
import net.jcip.annotations.NotThreadSafe;
import org.junit.Test;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.test.SphereTestUtils.en;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class CategoryPredicatesIntegrationTest extends IntegrationTest {
    @Test
    public void isLessThanComparisonPredicate() throws Exception {
        final QueryPredicate<Category> predicate = CategoryQueryModel.of().name().lang(Locale.ENGLISH).isLessThan("x2");
        final Consumer<List<Category>> assertions = categories -> {
            final List<String> names = categories.stream().map(c -> c.getName().get(Locale.ENGLISH)).collect(toList());
            assertThat(names).contains("x1", "x10");
            assertThat(names.contains("x2")).isFalse();
        };
        predicateTestCase(predicate, assertions);
    }

    @Test
    public void isLessThanOrEqualToComparisonPredicate() throws Exception {
        final QueryPredicate<Category> predicate = CategoryQueryModel.of().name().lang(Locale.ENGLISH).isLessThanOrEqualTo("x10");
        final Consumer<List<Category>> assertions = categories -> {
            final List<String> names = categories.stream().map(c -> c.getName().get(Locale.ENGLISH)).collect(toList());
            assertThat(names).contains("x1", "x10");
            assertThat(names.contains("x2")).isFalse();
        };
        predicateTestCase(predicate, assertions);
    }

    @Test
    public void isGreaterThanOrEqualToComparisonPredicate() throws Exception {
        final QueryPredicate<Category> predicate = CategoryQueryModel.of().name().lang(Locale.ENGLISH).isGreaterThanOrEqualTo("x10");
        final Consumer<List<Category>> assertions = categories -> {
            final List<String> names = categories.stream().map(c -> c.getName().get(Locale.ENGLISH)).collect(toList());
            assertThat(names).contains("x2", "x10");
            assertThat(names.contains("x1")).isFalse();
        };
        predicateTestCase(predicate, assertions);
    }

    @Test
    public void isNotInPredicates() throws Exception {
        final QueryPredicate<Category> predicate = CategoryQueryModel.of().name().lang(Locale.ENGLISH).isNotIn(asList("x10", "x2"));
        final Consumer<List<Category>> assertions = categories -> {
            final List<String> names = categories.stream().map(c -> c.getName().get(Locale.ENGLISH)).collect(toList());
            assertThat(names).contains("x1");
            assertThat(names.contains("x2")).isFalse();
            assertThat(names.contains("x10")).isFalse();
        };
        predicateTestCase(predicate, assertions);
    }

    @Test
    public void isDefinedPredicates() throws Exception {
        final QueryPredicate<Category> predicate = CategoryQueryModel.of().name().lang(Locale.CHINESE).isPresent();
        final Consumer<List<Category>> assertions = categories -> {
            final List<String> names = categories.stream().map(c -> c.getName().get(Locale.ENGLISH)).collect(toList());
            assertThat(names.contains("x1")).isFalse();
            assertThat(names).contains("x2");
            assertThat(names.contains("x10")).isFalse();
        };
        predicateTestCase(predicate, assertions);
    }

    @Test
    public void isNotDefinedPredicates() throws Exception {
        final QueryPredicate<Category> predicate = CategoryQueryModel.of().name().lang(Locale.CHINESE).isNotPresent();
        final Consumer<List<Category>> assertions = categories -> {
            final List<String> names = categories.stream().map(c -> c.getName().get(Locale.ENGLISH)).collect(toList());
            assertThat(names).contains("x1", "x10");
            assertThat(names.contains("x2")).isFalse();
        };
        predicateTestCase(predicate, assertions);
    }

    public void predicateTestCase(final QueryPredicate<Category> predicate, final Consumer<List<Category>> assertions) {
        withCategory(client(), CategoryDraftBuilder.of(en("x1"), en("x1")).description(null), c1 -> {
            withCategory(client(), CategoryDraftBuilder.of(en("x2").plus(Locale.CHINESE, "xx"), en("x2")).description(en("desc 2")), c2 -> {
                withCategory(client(), CategoryDraftBuilder.of(en("x10"), en("x10")), c10 -> {
                    final Query<Category> query = CategoryQuery.of().withPredicates(predicate).withSort(CategoryQueryModel.of().createdAt().sort().desc());
                    final List<Category> results = client().executeBlocking(query).getResults();
                    assertions.accept(results);
                });
            });
        });
    }

    @Test
    public void fetchAmountProductPerCategory() {
        final LocalizedString name = LocalizedString.of(Locale.ENGLISH, "winter clothing");
        final LocalizedString slug = name.slugifiedUnique();
        final LocalizedString metaDescription = LocalizedString.of(Locale.ENGLISH, "winter clothing to keep you warm");
        final LocalizedString metaTitle = LocalizedString.of(Locale.ENGLISH, "winter warm clothing");
        final LocalizedString metaKeywords = LocalizedString.of(Locale.ENGLISH, "winter,clothes");
        final CategoryDraft categoryDraft = CategoryDraftBuilder.of(name, slug)
                .metaDescription(metaDescription)
                .metaTitle(metaTitle)
                .metaKeywords(metaKeywords)
                .build();
        final Category category = client().executeBlocking(CategoryCreateCommand.of(categoryDraft));
        final QueryPredicate<EmbeddedProductDataQueryModel> predicate =
                EmbeddedProductDataQueryModel.of().categories().id().is(category.getId());
        final QueryPredicate<Product> productQueryPredicate = ProductQueryModel.of().masterData().where(m -> m.current().where(predicate).and(m.staged().where(predicate)));
        assertThat(productQueryPredicate.toSphereQuery()).isEqualTo("masterData(current(categories(id=\"" + category.getId() + "\")) and staged(categories(id=\"" + category.getId() + "\")))");
        final Query<Product> query = ProductQuery.of().withPredicates(productQueryPredicate).withFetchTotal(true);
        final List<Product> results = client().executeBlocking(query).getResults();
        assertThat(results).isNotNull();
    }
}
