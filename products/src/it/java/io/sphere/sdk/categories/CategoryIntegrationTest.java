package io.sphere.sdk.categories;

import java.util.Optional;

import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteByIdCommand;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.commands.updateactions.ChangeName;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.http.ClientRequest;
import org.junit.Test;

import static io.sphere.sdk.models.LocalizedString.ofEnglishLocale;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.DefaultModelAssert.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static io.sphere.sdk.test.ReferenceAssert.assertThat;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class CategoryIntegrationTest extends QueryIntegrationTest<Category> {

    public static final Locale LOCALE = Locale.ENGLISH;

    @Override
    protected ClientRequest<Category> deleteCommand(final Category item) {
        return new CategoryDeleteByIdCommand(item);
    }

    @Override
    protected ClientRequest<Category> newCreateCommandForName(final String name) {
        final LocalizedString localized = en(name);
        return createCreateCommand(localized, localized);
    }

    @Override
    protected String extractName(final Category instance) {
        return instance.getSlug().get(Locale.ENGLISH).get();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Category>> queryRequestForQueryAll() {
        return new CategoryQuery();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Category>> queryObjectForName(final String name) {
        return new CategoryQuery().bySlug(LOCALE, name);
    }

    @Override
    protected ClientRequest<PagedQueryResult<Category>> queryObjectForNames(final List<String> names) {
        return new CategoryQuery().withPredicate(CategoryQuery.model().slug().lang(Locale.ENGLISH).isOneOf(names));
    }

    @Test
    public void queryByName() throws Exception {
        final String name = "name xyz";
        final String slug = "slug-xyz";
        cleanUpByName(slug);
        final Category category = client().execute(createCreateCommand(en(name), en(slug)));
        final Query<Category> query = new CategoryQuery().byName(LOCALE, name);
        assertThat(client().execute(query).head().get().getId()).isEqualTo(category.getId());
        cleanUpByName(slug);
    }

    @Test
    public void queryByExternalId() throws Exception {
        final String slug = "queryByExternalId";
        final String externalId = "queryByExternalId-externalId";
        cleanUpByName(slug);
        final NewCategory newCategory = NewCategoryBuilder.of(en(slug), en(slug)).externalId(externalId).build();
        final CategoryCreateCommand createCommand = new CategoryCreateCommand(newCategory);
        final Category category = client().execute(createCommand);
        final Query<Category> query = new CategoryQuery().byExternalId(externalId);
        final Category createdCategory = client().execute(query).head().get();
        assertThat(createdCategory.getId()).isEqualTo(category.getId());
        assertThat(createdCategory.getExternalId()).isPresentAs(externalId);
        cleanUpByName(slug);
    }

    @Test
    public void queryByNotName() throws Exception {
        final String name = "name xyz";
        final String slug = "slug-xyz";
        cleanUpByName(slug);
        final Category category = client().execute(createCreateCommand(en(name), en(slug)));
        final Query<Category> query = new CategoryQuery().
                withPredicate(CategoryQuery.model().name().lang(Locale.ENGLISH).isNot(name));
        final long actual = client().execute(query).getResults().stream().filter(c -> c.getId().equals(name)).count();
        assertThat(actual).isEqualTo(0);
        cleanUpByName(slug);
    }

    @Test
    public void createSmallCategoryHierarchy() throws Exception {
        final String slug = "create-category-test";
        final String name = "create category test";
        final String desc = "desc create category test";
        final String hint = "0.5";
        final String parentName = name + "parent";
        final String parentSlug = slug + "parent";

        cleanUpByName(slug);
        cleanUpByName(parentSlug);
        final NewCategory newParentCategory = NewCategoryBuilder.of(en(parentName), en(parentSlug)).description(en(desc + "parent")).orderHint(hint + "3").build();
        final Category parentCategory = createCategory(newParentCategory);
        final Reference<Category> reference = new Reference<>(Category.typeId(), parentCategory.getId(), Optional.ofNullable(parentCategory));
        final NewCategory newCategory = NewCategoryBuilder.of(en(name), en(slug)).description(en(desc)).orderHint(hint).parent(reference).build();
        final Category category = createCategory(newCategory);
        assertThat(category.getName()).isEqualTo(en(name));
        assertThat(category.getDescription().get()).isEqualTo(en(desc));
        assertThat(category.getSlug()).isEqualTo(en(slug));
        assertThat(category.getOrderHint().get()).isEqualTo(hint);
        assertThat(category.getParent()).isEqualTo((Optional.of(reference.filled(Optional.empty()))));
        assertThat(parentCategory.getParent()).isEqualTo(Optional.empty());
        cleanUpByName(slug);
        cleanUpByName(parentSlug);
    }

    @Test
    public void ancestorsReferenceExpansion() throws Exception {
        CategoryFixtures.withCategory(client(), NewCategoryBuilder.of(en("1"), en("level1")), level1 -> {
            CategoryFixtures.withCategory(client(), NewCategoryBuilder.of(en("2"), en("level2")).parent(level1), level2 -> {
                CategoryFixtures.withCategory(client(), NewCategoryBuilder.of(en("3"), en("level3")).parent(level2), level3 -> {
                    CategoryFixtures.withCategory(client(), NewCategoryBuilder.of(en("4"), en("level4")).parent(level3), level4 -> {
                        final ExpansionPath<Category> expansionPath = CategoryQuery.expansionPath().ancestors().ancestors();
                        final Query<Category> query = new CategoryQuery().byId(level4.getId())
                                .withExpansionPaths(expansionPath)
                                .toQuery();
                        final PagedQueryResult<Category> queryResult = client().execute(query);
                        final Category loadedLevel4 = queryResult.head().get();
                        final List<Reference<Category>> ancestors = loadedLevel4.getAncestors();
                        final List<String> expectedAncestorIds = ancestors.stream().map(r -> r.getObj().get().getId()).collect(toList());
                        assertThat(expectedAncestorIds).containsExactly(level1.getId(), level2.getId(), level3.getId());

                        final Category level3ExpandedAncestor = ancestors.get(2).getObj().get();
                        assertThat(level3ExpandedAncestor).hasSameIdAs(level3);

                        assertThat(level3ExpandedAncestor.getAncestors().get(0).getObj().get()).hasSameIdAs(level1);
                    });
                });
            });
        });
    }

    @Test
    public void parentsReferenceExpansion() throws Exception {
        CategoryFixtures.withCategory(client(), NewCategoryBuilder.of(en("1"), en("level1")), level1 -> {
            CategoryFixtures.withCategory(client(), NewCategoryBuilder.of(en("2"), en("level2")).parent(level1), level2 -> {
                final ExpansionPath<Category> expansionPath = CategoryQuery.expansionPath().parent();
                final Query<Category> query = new CategoryQuery().byId(level2.getId())
                        .withExpansionPaths(expansionPath)
                        .toQuery();
                final PagedQueryResult<Category> queryResult = client().execute(query);
                final Category loadedLevel2 = queryResult.head().get();
                assertThat(loadedLevel2.getParent().get()).hasAnExpanded(level1);
            });
        });
    }

    @Test
    public void isGreaterThanComparisonPredicate() throws Exception {
        final Predicate<Category> predicate = CategoryQuery.model().name().lang(Locale.ENGLISH).isGreaterThan("1");
        final Consumer<List<Category>> assertions = categories -> {
            final List<String> names = categories.stream().map(c -> c.getName().get(Locale.ENGLISH).get()).collect(toList());
            assertThat(names).contains("2", "10");
            assertThat(names.contains("1")).isFalse();
        };
        predicateTestCase(predicate, assertions);
    }

    @Test
    public void isLessThanComparisonPredicate() throws Exception {
        final Predicate<Category> predicate = CategoryQuery.model().name().lang(Locale.ENGLISH).isLessThan("2");
        final Consumer<List<Category>> assertions = categories -> {
            final List<String> names = categories.stream().map(c -> c.getName().get(Locale.ENGLISH).get()).collect(toList());
            assertThat(names).contains("1", "10");
            assertThat(names.contains("2")).isFalse();
        };
        predicateTestCase(predicate, assertions);
    }

    @Test
    public void isLessThanOrEqualsComparisonPredicate() throws Exception {
        final Predicate<Category> predicate = CategoryQuery.model().name().lang(Locale.ENGLISH).isLessThanOrEquals("10");
        final Consumer<List<Category>> assertions = categories -> {
            final List<String> names = categories.stream().map(c -> c.getName().get(Locale.ENGLISH).get()).collect(toList());
            assertThat(names).contains("1", "10");
            assertThat(names.contains("2")).isFalse();
        };
        predicateTestCase(predicate, assertions);
    }

    @Test
    public void isGreaterThanOrEqualsComparisonPredicate() throws Exception {
        final Predicate<Category> predicate = CategoryQuery.model().name().lang(Locale.ENGLISH).isGreaterThanOrEquals("10");
        final Consumer<List<Category>> assertions = categories -> {
            final List<String> names = categories.stream().map(c -> c.getName().get(Locale.ENGLISH).get()).collect(toList());
            assertThat(names).contains("2", "10");
            assertThat(names.contains("1")).isFalse();
        };
        predicateTestCase(predicate, assertions);
    }

    @Test
    public void isNotInPredicates() throws Exception {
        final Predicate<Category> predicate = CategoryQuery.model().name().lang(Locale.ENGLISH).isNotIn("10", "2");
        final Consumer<List<Category>> assertions = categories -> {
            final List<String> names = categories.stream().map(c -> c.getName().get(Locale.ENGLISH).get()).collect(toList());
            assertThat(names).contains("1");
            assertThat(names.contains("2")).isFalse();
            assertThat(names.contains("10")).isFalse();
        };
        predicateTestCase(predicate, assertions);
    }

    @Test
    public void isDefinedPredicates() throws Exception {
        final Predicate<Category> predicate = CategoryQuery.model().name().lang(Locale.CHINESE).isPresent();
        final Consumer<List<Category>> assertions = categories -> {
            final List<String> names = categories.stream().map(c -> c.getName().get(Locale.ENGLISH).get()).collect(toList());
            assertThat(names.contains("1")).isFalse();
            assertThat(names).contains("2");
            assertThat(names.contains("10")).isFalse();
        };
        predicateTestCase(predicate, assertions);
    }

    @Test
    public void isNotDefinedPredicates() throws Exception {
        final Predicate<Category> predicate = CategoryQuery.model().name().lang(Locale.CHINESE).isNotPresent();
        final Consumer<List<Category>> assertions = categories -> {
            final List<String> names = categories.stream().map(c -> c.getName().get(Locale.ENGLISH).get()).collect(toList());
            assertThat(names).contains("1", "10");
            assertThat(names.contains("2")).isFalse();
        };
        predicateTestCase(predicate, assertions);
    }

    public void predicateTestCase(final Predicate<Category> predicate, final Consumer<List<Category>> assertions) {
        CategoryFixtures.withCategory(client(), NewCategoryBuilder.of(en("1"), en("1")).description(Optional.empty()), c1 -> {
            CategoryFixtures.withCategory(client(), NewCategoryBuilder.of(en("2").plus(Locale.CHINESE, "x"), en("2")).description(en("desc 2")), c2 -> {
                CategoryFixtures.withCategory(client(), NewCategoryBuilder.of(en("10"), en("10")), c10 -> {
                    final Query<Category> query = new CategoryQuery().withPredicate(predicate);
                    final List<Category> results = client().execute(query).getResults();
                    assertions.accept(results);
                });
            });
        });
    }

    private Category createCategory(final NewCategory upperTemplate) {
        return client().execute(new CategoryCreateCommand(upperTemplate));
    }

    private ClientRequest<Category> createCreateCommand(final LocalizedString localizedName, final LocalizedString slug) {
        final NewCategory newCategory = NewCategoryBuilder.of(localizedName, slug).description(localizedName).build();
        return new CategoryCreateCommand(newCategory);
    }

    @Test
    public void updateCommandDsl() throws Exception {
        withByName("update name", category -> {
            final LocalizedString newName = ofEnglishLocale("new name");
            final CategoryUpdateCommand command = new CategoryUpdateCommand(category, asList(ChangeName.of(newName)));
            final Category updatedCategory = client().execute(command);
            assertThat(updatedCategory.getName()).isEqualTo(newName);

            final LocalizedString newName2 = ofEnglishLocale("new name2");
            final CategoryUpdateCommand command2 = new CategoryUpdateCommand(category /** with old version */, asList(ChangeName.of(newName2)));
            final Category againUpdatedCategory = client().execute(command2.withVersion(updatedCategory));
            assertThat(againUpdatedCategory.getName()).isEqualTo(newName2);
            assertThat(againUpdatedCategory.getVersion()).isGreaterThan(updatedCategory.getVersion());
        });
    }
}
