package io.sphere.sdk.meta;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.commands.updateactions.ChangeParent;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereClientUtils;
import io.sphere.sdk.jsonnodes.queries.JsonNodeQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductDraftBuilder;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductVariantDraftBuilder;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryExecutionUtils;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.test.IntegrationTest;
import net.jcip.annotations.NotThreadSafe;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class CategoryDocumentationIntegrationTest extends IntegrationTest {

    private static List<Category> categories;
    private static CategoryTree tree;

    @BeforeClass
    public static void beforeClass() throws IOException {
        deleteAllCategories();
        categories = setUpData();
        tree = CategoryTree.of(categories);
    }

    @Test
    public void fetchAll() throws Exception {
        final CompletionStage<List<Category>> categoriesStage = QueryExecutionUtils.queryAll(client(), CategoryQuery.of(), 500);
        final List<Category> categories = SphereClientUtils.blockingWait(categoriesStage, Duration.ofMinutes(1));
        assertThat(categories)
                .hasSize(15)
                .matches(cats -> cats.parallelStream().anyMatch(cat -> cat.getSlug().get(ENGLISH).equals("boots-women")));
    }

    @Test
    public void fetchAll_withMapper() throws Exception {
        final CompletionStage<List<Category>> categoriesStage = QueryExecutionUtils
            .queryAll(client(), CategoryQuery.of(), category -> category, 500);
        assertEventually(() -> {
            final List<Category> categories = SphereClientUtils
                    .blockingWait(categoriesStage, Duration.ofMinutes(1));

            assertThat(categories)
                    .hasSize(15)
                    .matches(cats -> cats.parallelStream().anyMatch(cat -> cat.getSlug().get(ENGLISH).equals("boots-women")));
        });
    }

    @Test
    public void fetchAllExternalIdsWithUniformPageSizes() throws Exception {

        final CompletionStage<List<String>> categoriesStage = QueryExecutionUtils
            .queryAll(client(), CategoryQuery.of(), Category::getExternalId, 3);

        assertEventually(Duration.ofSeconds(120), Duration.ofMillis(100), () -> {
            final List<String> externalIds = SphereClientUtils
                    .blockingWait(categoriesStage, Duration.ofMinutes(1));

            assertThat(externalIds).hasSize(15);
            IntStream.range(0, externalIds.size()).forEach(index -> assertThat(externalIds).contains(index + ""));
        });
    }

    @Test
    public void fetchAllExternalIdsWithNonUniformPageSizes() throws Exception {
        final CompletionStage<List<String>> categoriesStage = QueryExecutionUtils
            .queryAll(client(), CategoryQuery.of(), Category::getExternalId, 4);
        assertEventually(Duration.ofSeconds(120), Duration.ofMillis(100), () -> {
            final List<String> externalIds = SphereClientUtils
                    .blockingWait(categoriesStage, Duration.ofMinutes(1));

            assertThat(externalIds).hasSize(15);
            IntStream.range(0, externalIds.size()).forEach(index -> assertThat(externalIds).contains(index + ""));
        });
    }

    @Test
    public void collectAllExternalIds() throws Exception {
        final List<String> externalIds = new ArrayList<>();

        final Consumer<Category> categoryConsumer = category -> externalIds.add(category.getExternalId());

        final CompletionStage<Void> categoriesStage = QueryExecutionUtils
            .queryAll(client(), CategoryQuery.of(), categoryConsumer, 500);
        SphereClientUtils.blockingWait(categoriesStage, Duration.ofMinutes(1));

        assertThat(externalIds).hasSize(15);
        IntStream.range(0, externalIds.size()).forEach(index -> assertThat(externalIds).contains(index + ""));
    }

    @Test
    public void fetchAllAsJson() throws Exception {
        final CompletionStage<List<JsonNode>> categoriesStage = QueryExecutionUtils.queryAll(client(), JsonNodeQuery.of("/categories"), 500);
        final List<JsonNode> categories = SphereClientUtils.blockingWait(categoriesStage, Duration.ofMinutes(1));
        assertThat(categories)
                .hasSize(15)
                .matches(cats -> cats.parallelStream().anyMatch(cat -> cat.get("slug").get("en").asText().equals("boots-women")));
    }

    @Test
    public void fetchAllAsJson_withMapper() throws Exception {
        final CompletionStage<List<JsonNode>> categoryPagesStage = QueryExecutionUtils
            .queryAll(client(), JsonNodeQuery.of("/categories"), category -> category, 500);
        final List<JsonNode> categoryNodes = SphereClientUtils
            .blockingWait(categoryPagesStage, Duration.ofMinutes(1));
        assertThat(categoryNodes)
            .hasSize(15)
            .matches(cats -> cats.parallelStream()
                                 .anyMatch(cat -> cat.get("slug").get("en").asText().equals("boots-women")));
    }

    @Test
    public void fetchRoots() throws Exception {
        final CategoryQuery seedQuery = CategoryQuery.of().withPredicates(m -> m.parent().isNotPresent());
        final CompletionStage<List<Category>> categoriesStage = QueryExecutionUtils.queryAll(client(), seedQuery);
        final List<Category> rootCategories = SphereClientUtils.blockingWait(categoriesStage, Duration.ofMinutes(1));
        assertThat(rootCategories.stream().allMatch(cat -> cat.getParent() == null))
                .overridingErrorMessage("fetched only root categories")
                .isTrue();
    }

    @Test
    public void fetchRoots_withMapper() throws Exception {
        final CategoryQuery seedQuery = CategoryQuery.of().withPredicates(m -> m.parent().isNotPresent());
        final CompletionStage<List<Category>> rootCategoryStage = QueryExecutionUtils
            .queryAll(client(), seedQuery, category -> category);
        final List<Category> rootCategories = SphereClientUtils
            .blockingWait(rootCategoryStage, Duration.ofMinutes(1));
        assertThat(rootCategories.stream().allMatch(cat -> cat.getParent() == null))
            .overridingErrorMessage("fetched only root categories")
            .isTrue();
    }

    @Test
    public void simpleCategoryTreeOperations() throws Exception {
        final CategoryTree categoryTree = createCategoryTree();

        //find by slug
        final Optional<Category> jeansWomenOptional = categoryTree.findBySlug(ENGLISH, "jeans-women");
        assertThat(jeansWomenOptional).isPresent();
        assertThat(categoryTree.findBySlug(ENGLISH, "not-existing-slug")).isEmpty();

        //find by Key
        final Optional<Category> jeansWomenOptionalByKey = categoryTree.findByKey(jeansWomenOptional.get().getKey());
        assertThat(jeansWomenOptionalByKey).isPresent();
        assertThat(jeansWomenOptionalByKey.get().getSlug().get(ENGLISH)).contains("jeans-women");

        //find by ID
        final Reference<Category> clothingWomenReference = jeansWomenOptional.get().getParent();
        final Optional<Category> clothingWomenOptional = categoryTree.findById(clothingWomenReference.getId());
        assertThat(clothingWomenOptional).isPresent();
        assertThat(clothingWomenOptional.get().getSlug().get(ENGLISH)).contains("womens-clothing");

        //find all direct children of one category
        final Category clothingWomen = clothingWomenOptional.get();
        final List<Category> clothingWomenSubcategories = categoryTree.findChildren(clothingWomen);
        final List<String> names = clothingWomenSubcategories.stream()
                .map(cat -> cat.getName().get(ENGLISH))
                .sorted()
                .collect(toList());
        assertThat(names).contains("jeans", "t-shirts");
    }

    @Test
    public void createAViewForACategoryTree() throws Exception {
        CategoryTreeTextRepresentation.demoForRendering(tree);
    }

    @Test
    public void createAViewForACategoryTreePart() throws Exception {
        RenderAPartialTree.demoForRendering(fetchCurrentTree());
    }

    @Test
    public void createAViewForACategoryBreadCrumb() throws Exception {
        final CategoryTree categoryTree = createCategoryTree();
        final Category currentCategory = categoryTree.findBySlug(ENGLISH, "tshirts-men").get();
        final List<Reference<Category>> ancestorReferences = currentCategory.getAncestors().stream()
                .skip(1)//remove top level category
                .collect(toList());

        final Function<Category, String> formatCategory = cat -> defaultString(cat.getExternalId())
                + " " + cat.getName().find(ENGLISH).orElse("");
        final String ancestorCategoriesString = ancestorReferences.stream()
                .map(ref -> categoryTree.findById(ref.getId()).get())
                .map(formatCategory)
                .collect(joining(" > "));
        final String actual = ancestorCategoriesString + " > " + formatCategory.apply(currentCategory);
        assertThat(actual).isEqualTo("1 men > 3 clothing > 7 t-shirts");
    }

    @Test
    public void categoryDeletionIsRecursive() throws Exception {
        final CategoryTree categoryTree = fetchCurrentTree();
        final String startingSituation = CategoryTreeTextRepresentation.visualizeTree(categoryTree);
        assertThat(startingSituation).isEqualTo(
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

        final Category men = categoryTree.findByExternalId("1").get();
        client().executeBlocking(CategoryDeleteCommand.of(men));
        final CategoryTree categoryTreeAfterDeletion = fetchCurrentTree();
        final String actual = CategoryTreeTextRepresentation.visualizeTree(categoryTreeAfterDeletion);
        assertThat(actual).isEqualTo(
                        "0 top\n" +
                        "    2 women\n" +
                        "        5 clothing\n" +
                        "            11 t-shirts\n" +
                        "            12 jeans\n" +
                        "        6 shoes\n" +
                        "            13 sandals\n" +
                        "            14 boots\n");
        //end example parsing here
        beforeClass();
    }

    @Test
    public void moveCategoryTree() throws Exception {
        final CategoryTree categoryTree = fetchCurrentTree();
        final String startingSituation = CategoryTreeTextRepresentation.visualizeTree(categoryTree);
        assertThat(startingSituation).isEqualTo(
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

        final Category mensClothing = categoryTree.findByExternalId("3").get();
        final Category top = categoryTree.findByExternalId("0").get();

        //make mensClothing a child of top
        client().executeBlocking(CategoryUpdateCommand.of(mensClothing, ChangeParent.of(top)));

        final CategoryTree categoryTreeAfterMovement = fetchCurrentTree();
        final String actual = CategoryTreeTextRepresentation.visualizeTree(categoryTreeAfterMovement);
        assertThat(actual).isEqualTo(
                        "0 top\n" +
                        "    1 men\n" +
                        "        4 shoes\n" +
                        "            9 sandals\n" +
                        "            10 boots\n" +
                        "    2 women\n" +
                        "        5 clothing\n" +
                        "            11 t-shirts\n" +
                        "            12 jeans\n" +
                        "        6 shoes\n" +
                        "            13 sandals\n" +
                        "            14 boots\n" +
                        "    3 clothing\n" +
                        "        7 t-shirts\n" +
                        "        8 jeans\n");
        //end example parsing here
        beforeClass();
    }

    @Test
    public void demoForFindProducts() throws Exception {
        final CategoryTree categoryTree = fetchCurrentTree();
        final String startingSituation = CategoryTreeTextRepresentation.visualizeTree(categoryTree);
        assertThat(startingSituation).isEqualTo(
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
        final Category mensClothingCategory = categoryTree.findByExternalId("3").get();
        final Category tshirtCategory = categoryTree.findByExternalId("7").get();
        final Category jeansCategory = categoryTree.findByExternalId("8").get();
        withProductInCategory(client(), jeansCategory, jeansProduct -> {
            withProductInCategory(client(), tshirtCategory, tshirtProduct -> {
                final ProductProjectionQuery sphereRequest = ProductProjectionQuery.ofStaged().withPredicates(m -> m.categories().isIn(asList(mensClothingCategory)));
                final PagedQueryResult<ProductProjection> resultForParentCategory =
                        //query for the parent category
                        client().executeBlocking(sphereRequest);
                assertThat(resultForParentCategory.getResults())
                        .overridingErrorMessage(
                                "if a product is in a category," +
                                        "but not in the parent category of this category" +
                                        "the query will not find the product")
                        .isEmpty();

                final ProductProjectionQuery query = ProductProjectionQuery.ofStaged().withPredicates(m -> m.categories().isIn(asList(tshirtCategory, jeansCategory)));
                assertThat(query.predicates().get(0))
                        .isEqualTo(QueryPredicate.of(format("categories(id in (\"%s\", \"%s\"))", tshirtCategory.getId(), jeansCategory.getId())));
                final PagedQueryResult<ProductProjection> resultForDirectCategories =
                        client().executeBlocking(query);
                assertThat(resultForDirectCategories.getResults())
                        .hasSize(2)
                        .overridingErrorMessage("if a product is in a category, you can directy query for it")
                        .matches(elements -> elements.stream()
                                .anyMatch(product -> product.getCategories().contains(tshirtCategory.toReference())));
            });
        });
    }


    private static void withProductInCategory(final BlockingSphereClient client, final Referenceable<Category> category, final Consumer<Product> user) {
        final ProductType productType = client.executeBlocking(ProductTypeCreateCommand.of(ProductTypeDraft.of(randomKey(), CategoryDocumentationIntegrationTest.class.getSimpleName(), "", asList())));
        final LocalizedString name = LocalizedString.of(ENGLISH, "foo");
        final Product product = client.executeBlocking(ProductCreateCommand.of(ProductDraftBuilder.of(productType, name, name.slugifiedUnique(), ProductVariantDraftBuilder.of().build()).categories(asSet(category.toReference())).build()));
        user.accept(product);
        client.executeBlocking(ProductDeleteCommand.of(product));
        client.executeBlocking(ProductTypeDeleteCommand.of(productType));
    }

    private static CategoryTree fetchCurrentTree() {
        final CompletionStage<List<Category>> categoriesStage = QueryExecutionUtils.queryAll(client(), CategoryQuery.of());
        final List<Category> categories = SphereClientUtils.blockingWait(categoriesStage, Duration.ofMinutes(1));
        return CategoryTree.of(categories);
    }

    private CategoryTree createCategoryTree() {
        //stuff from previous example
        final CompletionStage<List<Category>> categoriesStage = QueryExecutionUtils.queryAll(client(), CategoryQuery.of());
        final List<Category> categories = SphereClientUtils.blockingWait(categoriesStage, Duration.ofMinutes(1));

        return CategoryTree.of(categories);
    }

    private static void deleteAllCategories() {
        final CategoryQuery categoryQuery = CategoryQuery.of().byIsRoot().withLimit(500);
        final PagedQueryResult<Category> categoryPagedQueryResult = client().executeBlocking(categoryQuery);
        categoryPagedQueryResult.getResults()
                .forEach(cat -> client().executeBlocking(CategoryDeleteCommand.of(cat)));
        //delete also corrupt category nodes
        client().executeBlocking(CategoryQuery.of().withLimit(500))
                .getResults()
                .forEach(cat -> client().executeBlocking(CategoryDeleteCommand.of(cat)));
    }

    private static List<Category> setUpData() throws IOException {
        final Map<String, Category> externalIdToCategoryMap = new HashMap<>();//contains all the created categories
        try (final InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("category-import-1.csv")) {
            try (final InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream)) {
                try (final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    final List<Category> categories = bufferedReader.lines()
                            .skip(1)//first line is headers
                            .map(line -> line.trim())
                            .filter(line -> !"".equals(line))//remove empty lines
                            .map(line -> line.split(","))
                            .map(columns -> {
                                final LocalizedString name = LocalizedString.of(GERMAN, columns[2]).plus(ENGLISH, columns[4]);
                                final LocalizedString slug = LocalizedString.of(GERMAN, columns[3]).plus(ENGLISH, columns[5]);
                                final String externalId = columns[0];
                                final CategoryDraftBuilder categoryDraftBuilder = CategoryDraftBuilder.of(name, slug).externalId(externalId).key(randomKey());
                                final String externalIdParent = columns[1];
                                if (!"".equals(externalIdParent)) {
                                    final Category parent = externalIdToCategoryMap.get(externalIdParent);
                                    requireNonNull(parent, externalIdParent + " ");
                                    categoryDraftBuilder.parent(parent);
                                }
                                final CategoryDraft draft = categoryDraftBuilder.build();

                                //here is the call to the platform
                                final Category category = client().executeBlocking(CategoryCreateCommand.of(draft));

                                externalIdToCategoryMap.put(externalId, category);
                                return category;
                            })
                            .collect(toList());
                    return categories;
                }
            }
        }
    }
}
