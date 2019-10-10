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
import org.junit.Ignore;
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

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class ProductCategoriesIdTermFilterSearchModelIntegrationTest extends IntegrationTest {

    private static CategoryTree categoryTree;

    @BeforeClass
    public static void createCategories() {
        CategoryFixtures.deleteAll(client());
        setupCategories();
    }

    @Ignore
    @Test
    public void containsAll() {
        final List<String> categoryIds1 = getCategoryIds("A", "B-1", "C-2-2");
        final List<String> categoryIds2 = getCategoryIds("A", "B-1");
        final List<String> categoryIds3 = getCategoryIds("A", "B", "C");
        withProductInCategories(client(),  categoryIds1, (Product product1) -> {
            withProductInCategories(client(), categoryIds2, (Product product2) -> {
                withProductInCategories(client(), categoryIds3, (Product product3) -> {
                    assertEventually(() -> {
                        final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                                .withQueryFilters(m -> m.categories().id().containsAll(categoryIds1))
                                .plusQueryFilters(m -> m.id().isIn(asList(product1.getId(), product2.getId(), product3.getId())));
                        assertThat(client().executeBlocking(request)).has(onlyProducts(product1));
                    });
                });
            });
        });
    }

    @Ignore
    @Test
    public void containsAny() {
        final List<String> categoryIds1 = getCategoryIds("A", "B-1", "C-2-2");
        final List<String> categoryIds2 = getCategoryIds("A", "B-1");
        final List<String> categoryIds3 = getCategoryIds("A", "B", "C");
        withProductInCategories(client(),  categoryIds1, (Product product1) -> {
            withProductInCategories(client(), categoryIds2, (Product product2) -> {
                withProductInCategories(client(), categoryIds3, (Product product3) -> {
                    assertEventually(() -> {
                        final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                                .withQueryFilters(m -> m.categories().id().containsAny(categoryIds1))
                                .plusQueryFilters(m -> m.id().isIn(asList(product1.getId(), product2.getId(), product3.getId())));
                        assertThat(client().executeBlocking(request)).has(onlyProducts(product1, product2, product3));
                    });
                });
            });
        });
    }

    @Ignore
    @Test
    public void is() {
        final List<String> categoryIds1 = getCategoryIds("A", "B-1", "C-2-2");
        final List<String> categoryIds2 = getCategoryIds("A", "B-1");
        final List<String> categoryIds3 = getCategoryIds("A", "B", "C");
        withProductInCategories(client(),  categoryIds1, (Product product1) -> {
            withProductInCategories(client(), categoryIds2, (Product product2) -> {
                withProductInCategories(client(), categoryIds3, (Product product3) -> {
                    assertEventually(() -> {
                        final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                                .withQueryFilters(m -> m.categories().id().containsAny(getCategoryIds("B-1")))
                                .plusQueryFilters(m -> m.id().isIn(asList(product1.getId(), product2.getId(), product3.getId())));
                        assertThat(client().executeBlocking(request)).has(onlyProducts(product1, product2));
                    });
                });
            });
        });
    }

    @Ignore
     @Test
    public void containsAnyIncludingSubtrees() {
         final List<String> categoryIds1 = getCategoryIds("A", "B-1", "C-2-2");
         final List<String> categoryIds2 = getCategoryIds("A", "B-1");
         final List<String> categoryIds3 = getCategoryIds("A", "B", "C");
         withProductInCategories(client(),  categoryIds1, (Product product1) -> {
             withProductInCategories(client(), categoryIds2, (Product product2) -> {
                 withProductInCategories(client(), categoryIds3, (Product product3) -> {
                     assertEventually(() -> {
                         final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                                 .withQueryFilters(m -> m.categories().id().containsAnyIncludingSubtrees(getCategoryIds("C-2")))
                                 .plusQueryFilters(m -> m.id().isIn(asList(product1.getId(), product2.getId(), product3.getId())));
                         assertThat(client().executeBlocking(request)).has(onlyProducts(product1));
                     });
                 });
             });
         });
    }

    @Ignore
    @Test
    public void containsAllIncludingSubtrees() {
        final List<String> categoryIds1 = getCategoryIds("B-1", "C-2-2");
        final List<String> categoryIds2 = getCategoryIds("B-1-2", "C-1-3");
        final List<String> categoryIds3 = getCategoryIds("B-3", "C");
        withProductInCategories(client(),  categoryIds1, (Product product1) -> {
            withProductInCategories(client(), categoryIds2, (Product product2) -> {
                withProductInCategories(client(), categoryIds3, (Product product3) -> {
                    assertEventually(() -> {
                        final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                                .withQueryFilters(m -> m.categories().id().containsAllIncludingSubtrees(getCategoryIds("C", "B-1")))
                                .plusQueryFilters(m -> m.id().isIn(asList(product1.getId(), product2.getId(), product3.getId())));
                        assertThat(client().executeBlocking(request)).has(onlyProducts(product1, product2));
                    });
                });
            });
        });
    }

    @Ignore
    @Test
    public void isInSubtree() {
        final List<String> categoryIds1 = getCategoryIds("B-1", "C-2-2");
        final List<String> categoryIds2 = getCategoryIds("B-1-2", "C-1-3");
        final List<String> categoryIds3 = getCategoryIds("B-3", "C");
        withProductInCategories(client(),  categoryIds1, (Product product1) -> {
            withProductInCategories(client(), categoryIds2, (Product product2) -> {
                withProductInCategories(client(), categoryIds3, (Product product3) -> {
                    assertEventually(() -> {
                        final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                                .withQueryFilters(m -> {
                                    final String categoryId = getCategoryIds("B-1").get(0);
                                    return m.categories().id().isInSubtree(categoryId);
                                })
                                .plusQueryFilters(m -> m.id().isIn(asList(product1.getId(), product2.getId(), product3.getId())));
                        assertThat(client().executeBlocking(request)).has(onlyProducts(product1, product2));
                    });
                });
            });
        });
    }

    @Ignore
    @Test
    public void isInSubtreeOrInCategory() {
        final List<String> categoryIds1 = getCategoryIds("C-2-2");
        final List<String> categoryIds2 = getCategoryIds("C-1-3");
        final List<String> categoryIds3 = getCategoryIds("B-3");
        withProductInCategories(client(),  categoryIds1, (Product product1) -> {
            withProductInCategories(client(), categoryIds2, (Product product2) -> {
                withProductInCategories(client(), categoryIds3, (Product product3) -> {
                    assertEventually(() -> {
                        final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                                .withQueryFilters(m -> {
                                    final List<String> categoryIdsSubtree = getCategoryIds("B");//somehow in B
                                    final List<String> categoryIdsDirectly = getCategoryIds("C-1-3");//but directly in C-1-3
                                    return m.categories().id().isInSubtreeOrInCategory(categoryIdsSubtree, categoryIdsDirectly);
                                })
                                .plusQueryFilters(m -> m.id().isIn(asList(product1.getId(), product2.getId(), product3.getId())));
                        assertThat(client().executeBlocking(request)).has(onlyProducts(product2, product3));
                    });
                });
            });
        });
    }

    @Ignore
    @Test
    public void facet() {
        final List<String> categoryIds1 = getCategoryIds("A", "B-1", "C-2-2");
        final List<String> categoryIds2 = getCategoryIds("A", "B-2-3");
        final List<String> categoryIds3 = getCategoryIds("B", "C");
        withProductInCategories(client(),  categoryIds1, (Product product1) -> {
            withProductInCategories(client(), categoryIds2, (Product product2) -> {
                withProductInCategories(client(), categoryIds3, (Product product3) -> {
                    assertEventually(() -> {
                        final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                                .plusFacets(m -> m.categories().id().withAlias("productsInA").onlyTermSubtree(getCategoryIds("A")))
                                .plusFacets(m -> m.categories().id().withAlias("productsInB").onlyTermSubtree(getCategoryIds("B")))
                                .plusFacets(m -> m.categories().id().withAlias("productsInAorB").onlyTermSubtree(getCategoryIds("A", "B")))
                                .plusFacets(m -> m.categories().id().withAlias("productsInC2").onlyTermSubtree(getCategoryIds("C-2")))
                                .plusQueryFilters(m -> m.id().isIn(asList(product1.getId(), product2.getId(), product3.getId())));
                        final PagedSearchResult<ProductProjection> pagedSearchResult = client().executeBlocking(request);

                        assertThat(pagedSearchResult.getFilteredFacetResult("productsInA").getCount()).isEqualTo(2);
                        assertThat(pagedSearchResult.getFilteredFacetResult("productsInB").getCount()).isEqualTo(3);
                        assertThat(pagedSearchResult.getFilteredFacetResult("productsInAorB").getCount()).isEqualTo(3);
                        assertThat(pagedSearchResult.getFilteredFacetResult("productsInC2").getCount()).isEqualTo(1);
                    });
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
        }.describedAs(String.format("expected %s", expectedIds));
    }

    private List<String> getCategoryIds(final String ... names) {
        return Arrays.stream(names)
                .map(name -> categoryTree.findByExternalId(name).get().getId())
                .collect(Collectors.toList());
    }

    private void withProductInCategories(final BlockingSphereClient client, final List<String> ids, final Consumer<Product> consumer) {
        final List<Reference<Category>> categories = ids.stream()
                .map(id -> Category.referenceOfId(id))
                .collect(Collectors.toList());
        ProductFixtures.withProduct(client, builder -> builder.categories(categories), consumer);
    }

    private static void setupCategories() {
        final List<Category> rootCategories = Stream.of("A", "B", "C")
                .map(id -> CategoryDraftBuilder.of(en("name " + id), en("slug-" + id)).externalId(id).build())
                .map(createCategory())
                .collect(toList());
        final List<Category> secondLevelCategories = rootCategories.stream()
                .flatMap(parent -> IntStream.range(1, 4)
                        .mapToObj(i -> {
                            final String id = parent.getExternalId() + "-" + i;
                            return CategoryDraftBuilder.of(en("name " + id), en("slug-" + id))
                                    .parent(parent).externalId(id).build();
                        })
                        .map(createCategory()))
                .collect(Collectors.toList());
        final List<Category> thirdLevelCategories = secondLevelCategories.stream()
                .flatMap(parent -> IntStream.range(1, 4)
                        .mapToObj(i -> {
                            final String id = parent.getExternalId() + "-" + i;
                            return CategoryDraftBuilder.of(en("name " + id), en("slug-" + id)).parent(parent).externalId(id).build();
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

    private static Function<CategoryDraft, Category> createCategory() {
        return draft -> client().executeBlocking(CategoryCreateCommand.of(draft));
    }

}