package io.sphere.sdk.products;

import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.categories.CategoryOrderHints;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.producttypes.ProductTypeFixtures;
import io.sphere.sdk.search.SearchSort;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.products.ProductProjectionComparators.comparingCategoryOrderHints;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.MapUtils.mapOf;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductCategoryOrderHintTest extends IntegrationTest {

    private static List<Product> products;
    private static List<ProductProjection> productProjections;
    private static List<String> sortedIdsFromBackendForCategory1;
    private static List<String> sortedIdsFromBackendForCategory2;
    private static String category1Id;
    private static String category2Id;
    private static String id1;
    private static String id2;
    private static String id3;
    private static String id4;
    private static String id5;

    @Test
    public void sortProductsByCategory1() {
        final Comparator<Product> comparator = comparatorOfStagedForCategory(category1Id);
        assertThat(productsSortedBy(comparator)).isEqualTo(asList(id1, id3, id2, id5, id4));
    }

    @Test
    public void sortProductsByCategory2() {
        final Comparator<Product> comparator = comparatorOfStagedForCategory(category2Id);
        assertThat(productsSortedBy(comparator)).startsWith(id5, id2, id1);
    }

    @Test
    public void sortProductProjectionsByCategory1() {
        final Comparator<ProductProjection> comparator = comparingCategoryOrderHints(category1Id);
        assertThat(productProjectionsSortedBy(comparator)).isEqualTo(asList(id1, id3, id2, id5, id4));
        assertThat(sortedIdsFromBackendForCategory1).isEqualTo(asList(id1, id3, id2, id5, id4));

    }

    @Test
    public void sortProductProjectionsByCategory2() {
        final Comparator<ProductProjection> comparator = comparingCategoryOrderHints(category2Id);
        assertThat(productProjectionsSortedBy(comparator)).startsWith(id5, id2, id1);
        assertThat(sortedIdsFromBackendForCategory2).startsWith(id5, id2, id1);

    }

    private static Comparator<Product> comparatorOfStagedForCategory(final String categoryId) {
        final Function<Product, String> orderHintExtractor =
                p -> Optional.ofNullable(p.getMasterData().getStaged().getCategoryOrderHints())
                        .map(categoryOrderHints -> categoryOrderHints.get(categoryId))
                        .orElse(null);
        return comparing(orderHintExtractor, Comparator.nullsLast(Comparator.<String>naturalOrder()));
    }

    private List<String> productProjectionsSortedBy(final Comparator<ProductProjection> comparator) {
        return productProjections.stream()
                .sorted(comparator)
                .map(ProductIdentifiable::getId)
                .collect(toList());
    }

    private List<String> productsSortedBy(final Comparator<Product> comparator) {
        return products.stream()
                .sorted(comparator)
                .map(product -> product.getId())
                .collect(toList());
    }

    @BeforeClass
    public static void setupProducts() {
        CategoryFixtures.withCategory(client(), category1 ->
            CategoryFixtures.withCategory(client(), category2 ->
                ProductTypeFixtures.withEmptyProductType(client(), randomKey(), productType -> {
                    category1Id = category1.getId();
                    category2Id = category2.getId();

                    final List<Map<String, String>> orderHintsList = asList(
                            mapOf(category1.getId(), "0.1", category2.getId(), "0.89"),
                            mapOf(category1.getId(), "0.3", category2.getId(), "0.88"),
                            mapOf(category1.getId(), "0.2"),//missing category
                            null,//missing orderHints
                            mapOf(category1.getId(), "0.4", category2.getId(), "0.86")
                    );


                    products = orderHintsList.stream()
                            .map(orderHints -> {
                                final ProductVariantDraft masterVariantDraft = ProductVariantDraftBuilder.of().build();
                                final CategoryOrderHints categoryOrderHints = orderHints != null ? CategoryOrderHints.of(orderHints) : null;
                                final ProductDraft productDraft = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), masterVariantDraft)
                                        .categoryOrderHints(categoryOrderHints)
                                        .categories(asList(category1.toReference(), category2.toReference()))
                                        .build();
                                final Product product = execute(ProductCreateCommand.of(productDraft));
                                return product;
                            })
                            .collect(toList());

                    final List<String> ids = products.stream().map(p -> p.getId()).collect(toList());

                    //eventual consistency
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    sortedIdsFromBackendForCategory1 = searchAndSortByCategoryId(category1Id);
                    sortedIdsFromBackendForCategory2 = searchAndSortByCategoryId(category2Id);


                    productProjections = execute(ProductProjectionQuery.ofStaged()
                            .withPredicates(product -> product.id().isIn(ids))).getResults();

                    id1 = products.get(0).getId();
                    id2 = products.get(1).getId();
                    id3 = products.get(2).getId();
                    id4 = products.get(3).getId();
                    id5 = products.get(4).getId();
                })
            )
        );
    }

    private static List<String> searchAndSortByCategoryId(final String categoryId) {
        final ProductProjectionSearch searchRequest = ProductProjectionSearch.ofStaged()
                .withQueryFilters(product -> product.categories().id().filtered().by(categoryId))
                .withSort(product -> SearchSort.of("categoryOrderHints." + categoryId + " asc"));
        return execute(searchRequest).getResults().stream()
                .map(x -> x.getId())
                .collect(toList());
    }

    @AfterClass
    public static void cleanUp() {
        products = null;
        productProjections = null;
        category1Id = null;
        category2Id = null;
        id1 = null;
        id2 = null;
        id3 = null;
        id4 = null;
        id5 = null;
    }
}
