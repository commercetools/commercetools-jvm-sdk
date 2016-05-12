package io.sphere.sdk.products.search;

import io.sphere.sdk.categories.*;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Condition;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static io.sphere.sdk.test.SphereTestUtils.en;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductCategoriesIdTermFilterSearchModelIntegrationTest extends IntegrationTest {

    private static CategoryTree categoryTree;

    @BeforeClass
    public static void createCategories() {
        CategoryFixtures.deleteAll(client());
        setupCategories();
    }

    @Test
    public void containsAll() {
        final List<String> categoryIdsA = getCategoryIds("A", "B-1", "C-2-2");
        final List<String> categoryIdsB = getCategoryIds("A", "B-1");
        withProductInCategories(client(),  categoryIdsA, (Product productA) -> {
            withProductInCategories(client(), categoryIdsB, (Product productB) -> {
                assertEventually(() -> {
                    final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                            .withQueryFilters(m -> m.categories().id().containsAll(categoryIdsA));
                    assertThat(client().executeBlocking(request)).has(onlyProducts(productA));
                });
            });
        });
    }

    private Condition<PagedSearchResult<ProductProjection>> onlyProducts(final Product ... products) {
        final Set<String> expectedIds = Arrays.stream(products).map(Product::getId).collect(Collectors.toSet());
        return new Condition<PagedSearchResult<ProductProjection>>() {
            @Override
            public boolean matches(final PagedSearchResult<ProductProjection> value) {
                final Set<String> actualIds = value.getResults().stream().map(ProductProjection::getId).collect(Collectors.toSet());
                return expectedIds.equals(actualIds);
            }
        }.describedAs(String.format("exprected %s", expectedIds));
    }

    private List<String> getCategoryIds(final String ... names) {
        return Arrays.stream(names)
                .map(name -> categoryTree.findByExternalId(getExternalId(name)).get().getId())
                .collect(Collectors.toList());
    }

    private void withProductInCategories(final BlockingSphereClient client, final List<String> ids, final Consumer<Product> consumer) {
        final List<Reference<Category>> categories = ids.stream()
                .map(id -> Category.referenceOfId(id))
                .collect(Collectors.toList());
        ProductFixtures.withProduct(client, builder -> builder.categories(categories), consumer);
    }

    @Test
    public void containsAny() {

    }

    @Test
    public void is() {

    }

    @Test
    public void isIn() {

    }

    @Test
    public void containsAnyIncludingSubtrees() {

    }

    @Test
    public void containsAllIncludingSubtrees() {

    }

    @Test
    public void isInSubtree() {

    }

    @Test
    public void isInSubtreeOrInCategory() {

    }

    private static void setupCategories() {
        final List<Category> rootCategories = Stream.of("A", "B", "C")
                .map(id -> CategoryDraftBuilder.of(en("name " + id), en("slug-" + id)).externalId(getExternalId(id)).build())
                .map(createCategory())
                .collect(toList());
        final List<Category> secondLevelCategories = rootCategories.stream()
                .flatMap(parent -> IntStream.range(1, 4)
                        .mapToObj(i -> {
                            final String id = parent.getExternalId() + "-" + i;
                            return CategoryDraftBuilder.of(en("name " + id), en("slug-" + id))
                                    .parent(parent).externalId(getExternalId(id)).build();
                        })
                        .map(createCategory()))
                .collect(Collectors.toList());
        final List<Category> thirdLevelCategories = secondLevelCategories.stream()
                .flatMap(parent -> IntStream.range(1, 4)
                        .mapToObj(i -> {
                            final String id = parent.getExternalId() + "-" + i;
                            return CategoryDraftBuilder.of(en("name " + id), en("slug-" + id)).parent(parent).externalId(getExternalId(id)).build();
                        })
                        .map(createCategory()))
                .collect(Collectors.toList());
        final List<Category> all = new LinkedList<>();
        all.addAll(rootCategories);
        all.addAll(secondLevelCategories);
        all.addAll(thirdLevelCategories);
        assertThat(all).hasSize(39);
        categoryTree = CategoryTree.of(all);
    }

    private static String getExternalId(final String id) {
        return id;
    }

    private static Function<CategoryDraft, Category> createCategory() {
        return draft -> client().executeBlocking(CategoryCreateCommand.of(draft));
    }

}