package io.sphere.sdk.meta;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.queries.ExperimentalReactiveStreamUtils;
import io.sphere.sdk.test.IntegrationTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static java.util.Locale.*;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryDocumentationTest extends IntegrationTest {

    private static final Comparator<Category> EXTERNALID_COMPARATOR = Comparator.comparing(c -> Integer.parseInt(c.getExternalId().get()));

    @BeforeClass
    public static void beforeClass() throws IOException {
        deleteAllCategories();
        setUpData();
    }

    @Test
    public void fetchAll() throws Exception {
        final Publisher<Category> categoryPublisher =
                ExperimentalReactiveStreamUtils.publisherOf(CategoryQuery.of(), sphereClient());
        final CompletionStage<List<Category>> categoriesStage =
                ExperimentalReactiveStreamUtils.collectAll(categoryPublisher);
        final List<Category> categories = categoriesStage.toCompletableFuture().join();
        assertThat(categories).matches(cats -> cats.parallelStream().anyMatch(cat -> cat.getSlug().get(ENGLISH).get().equals("boots-women")));
    }

    @Test
    public void fetchRoots() throws Exception {
        final CategoryQuery seedQuery = CategoryQuery.of().withPredicate(m -> m.parent().isNotPresent());
        final Publisher<Category> categoryPublisher =
                ExperimentalReactiveStreamUtils.publisherOf(seedQuery, sphereClient());
        final CompletionStage<List<Category>> categoriesStage =
                ExperimentalReactiveStreamUtils.collectAll(categoryPublisher);
        final List<Category> rootCategories = categoriesStage.toCompletableFuture().join();
        assertThat(rootCategories.stream().allMatch(cat -> !cat.getParent().isPresent()))
                .overridingErrorMessage("fetched categories have no parent")
                .isTrue();
    }

    @Test
    public void simpleCategoryTreeOperations() throws Exception {
        final CategoryTree categoryTree = createCategoryTree();

        //find by slug
        final Optional<Category> jeansWomenOptional = categoryTree.findBySlug(ENGLISH, "jeans-women");
        assertThat(jeansWomenOptional).isPresent();
        assertThat(categoryTree.findBySlug(ENGLISH, "not-existing-slug")).isEmpty();

        //find by ID
        final Reference<Category> clothingWomenReference = jeansWomenOptional.get().getParent().get();
        final Optional<Category> clothingWomenOptional = categoryTree.findById(clothingWomenReference.getId());
        assertThat(clothingWomenOptional).isPresent();
        assertThat(clothingWomenOptional.get().getSlug().get(ENGLISH)).contains("womens-clothing");

        //find all direct children of one category
        final Category clothingWomen = clothingWomenOptional.get();
        final List<Category> clothingWomenSubcategories = categoryTree.findChildren(clothingWomen);
        final List<String> names = clothingWomenSubcategories.stream()
                .map(cat -> cat.getName().get(ENGLISH).get())
                .sorted()
                .collect(toList());
        assertThat(names).contains("jeans", "t-shirts");
    }

    @Test
    public void createAViewForACategoryTree() throws Exception {
        final CategoryTree categoryTree = createCategoryTree();
        final StringBuilder stringBuilder = new StringBuilder();
        categoryTree.getRoots()
                .forEach(category -> appendToBuilderFullCategoryTree(category, stringBuilder, categoryTree, 0));
        final String actual = stringBuilder.toString();
        assertThat(actual).isEqualTo(
                "0 top\n" +
                        "    1 men\n" +
                        "        3 clothing\n" +
                        "            7 t-shirts\n" +
                        "            8 jeans\n" +
                        "        4 shoes\n" +
                        "            9 sandals\n" +
                        "            10 boots\n" +
                        "    2 women\n" +
                        "        5 clothing\n" +
                        "            11 t-shirts\n" +
                        "            12 jeans\n" +
                        "        6 shoes\n" +
                        "            13 sandals\n" +
                        "            14 boots\n");
    }
//
//    @Test
//    public void createAViewForACategoryTreePart() throws Exception {
//        final CategoryTree categoryTree = createCategoryTree();
//        final Category currentCategory = categoryTree.findBySlug(ENGLISH, "tshirts-men").get();
//
//
//        assertThat(actual).isEqualTo(
//                        "1 men\n" +
//                        "    3 clothing\n" +
//                        "        ***7 t-shirts***\n" +
//                        "        8 jeans\n" +
//                        "    4 shoes\n");
//    }
//
//
//    private void appendToBuilderCategoryTreePart(final Category category, final StringBuilder stringBuilder, final CategoryTree categoryTree, final int level) {
//
//
//
//
//        final String name = category.getName().get(ENGLISH).get();
//        final String externalId = category.getExternalId().get();
//        final String offset = StringUtils.repeat(' ', level * 4);
//        stringBuilder.append(offset).append(externalId).append(" ").append(name).append("\n");
//        final List<Category> children = categoryTree.findChildren(category);
//        children.stream()
//                .sorted(EXTERNALID_COMPARATOR)
//                .forEach(child -> appendToBuilderFullCategoryTree(child, stringBuilder, categoryTree, level + 1));
//    }

    @Test
    public void createAViewForACategoryBreadCrumb() throws Exception {
        final CategoryTree categoryTree = createCategoryTree();
        final Category currentCategory = categoryTree.findBySlug(ENGLISH, "tshirts-men").get();
        final List<Reference<Category>> ancestorReferences = currentCategory.getAncestors().stream()
                .skip(1)//remove top level category
                .collect(toList());

        final Function<Category, String> formatCategory = cat -> cat.getExternalId().orElse("")
                + " " + cat.getName().get(ENGLISH).orElse("");
        final String ancestorCategoriesString = ancestorReferences.stream()
                .map(ref -> categoryTree.findById(ref.getId()).get())
                .map(formatCategory)
                .collect(joining(" > "));
        final String actual = ancestorCategoriesString + " > " + formatCategory.apply(currentCategory);
        assertThat(actual).isEqualTo("1 men > 3 clothing > 7 t-shirts");
    }

    private void appendToBuilderFullCategoryTree(final Category category, final StringBuilder stringBuilder, final CategoryTree categoryTree, final int level) {
        final String name = category.getName().get(ENGLISH).get();
        final String externalId = category.getExternalId().get();
        final String offset = StringUtils.repeat(' ', level * 4);
        stringBuilder.append(offset).append(externalId).append(" ").append(name).append("\n");
        final List<Category> children = categoryTree.findChildren(category);
        children.stream()
                .sorted(EXTERNALID_COMPARATOR)
                .forEach(child -> appendToBuilderFullCategoryTree(child, stringBuilder, categoryTree, level + 1));
    }

    private CategoryTree createCategoryTree() {
        final Publisher<Category> categoryPublisher =
                ExperimentalReactiveStreamUtils.publisherOf(CategoryQuery.of(), sphereClient());
        final CompletionStage<List<Category>> categoriesStage =
                ExperimentalReactiveStreamUtils.collectAll(categoryPublisher);
        final List<Category> categories = categoriesStage.toCompletableFuture().join();
        final CategoryTree categoryTree = CategoryTree.of(categories);
        return categoryTree;
    }

    private static void deleteAllCategories() {
        final Publisher<Category> publisher = ExperimentalReactiveStreamUtils.publisherOf(CategoryQuery.of().byIsRoot(), sphereClient());
        final CategoryDeleteSubscriber subscriber = new CategoryDeleteSubscriber();
        publisher.subscribe(subscriber);
        subscriber.getFuture().join();
    }

    private static void setUpData() throws IOException {
        final Map<String, Category> externalIdToCategoryMap = new HashMap<>();//contains all the created categories
        try (final InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("category-import-1.csv")) {
            try (final InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream)) {
                try (final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    bufferedReader.lines()
                            .skip(1)//first line is headers
                            .map(line -> line.trim())
                            .filter(line -> !"".equals(line))//remove empty lines
                            .map(line -> {
                                final String[] split = line.split(",");
                                return split;
                            })
                            .map(columns -> {
                                final LocalizedStrings name = LocalizedStrings.of(GERMAN, columns[2]).plus(ENGLISH, columns[4]);
                                final LocalizedStrings slug = LocalizedStrings.of(GERMAN, columns[3]).plus(ENGLISH, columns[5]);
                                final String externalId = columns[0];
                                final CategoryDraftBuilder categoryDraftBuilder = CategoryDraftBuilder.of(name, slug).externalId(externalId);
                                final String externalIdParent = columns[1];
                                if (!"".equals(externalIdParent)) {
                                    final Category parent = externalIdToCategoryMap.get(externalIdParent);
                                    requireNonNull(parent, externalIdParent + " ");
                                    categoryDraftBuilder.parent(parent);
                                }
                                final CategoryDraft draft = categoryDraftBuilder.build();

                                //here is the call to SPHERE.IO
                                final Category category = client().execute(CategoryCreateCommand.of(draft));

                                externalIdToCategoryMap.put(externalId, category);
                                return category;
                            })
                    .collect(toList());
                }
            }
        }
    }

    private static class CategoryDeleteSubscriber implements Subscriber<Category> {
        private final CompletableFuture<Void> future = new CompletableFuture<>();
        private Subscription subscription;

        @Override
        public void onSubscribe(final Subscription subscription) {
            this.subscription = subscription;
            requestElements();
        }

        private void requestElements() {
            subscription.request(1);
        }

        @Override
        public void onNext(final Category category) {
            client().getUnderlying().execute(CategoryDeleteCommand.of(category)).whenComplete((c, t) -> {
                requestElements();
            });
        }

        @Override
        public void onError(final Throwable throwable) {
            future.completeExceptionally(throwable);
        }

        @Override
        public void onComplete() {
            future.complete(null);
        }

        public CompletableFuture<Void> getFuture() {
            return future;
        }
    }
}