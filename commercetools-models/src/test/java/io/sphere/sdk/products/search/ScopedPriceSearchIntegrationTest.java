package io.sphere.sdk.products.search;

import io.sphere.sdk.cartdiscounts.CartDiscountFixtures;
import io.sphere.sdk.models.ResourceView;
import io.sphere.sdk.productdiscounts.AbsoluteProductDiscountValue;
import io.sphere.sdk.productdiscounts.ProductDiscountDraft;
import io.sphere.sdk.productdiscounts.ProductDiscountPredicate;
import io.sphere.sdk.productdiscounts.ProductDiscountValue;
import io.sphere.sdk.products.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.SortExpression;
import io.sphere.sdk.search.TermFacetExpression;
import io.sphere.sdk.search.TermStats;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.productdiscounts.ProductDiscountFixtures.withProductDiscount;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.products.ProductsScenario1Fixtures.BOOL_FALSE;
import static io.sphere.sdk.products.ProductsScenario1Fixtures.BOOL_TRUE;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class ScopedPriceSearchIntegrationTest extends IntegrationTest {
    public static final String PRODUCT_TYPE_KEY = ScopedPriceSearchIntegrationTest.class.getSimpleName();
    private static ProductType productType;

    @AfterClass
    public static void delete() {
        CartDiscountFixtures.deleteDiscountCodesAndCartDiscounts(client());
        ProductFixtures.deleteProductsAndProductTypes(client());
        productType = null;
    }

    @BeforeClass
    public static void createData() {
        ProductFixtures.deleteProductsAndProductTypes(client());
        final ProductTypeDraft productTypeDraft =
                ProductTypeDraft.of(PRODUCT_TYPE_KEY, PRODUCT_TYPE_KEY, PRODUCT_TYPE_KEY, emptyList());
        productType = client().executeBlocking(ProductTypeCreateCommand.of(productTypeDraft));
    }

    @Ignore
    @Test
    public void filterByValueCentAmountAndCountry() {
        withProductOfPrices(asList(PriceDraft.of(EURO_20), PriceDraft.of(EURO_30).withCountry(DE)), product1 -> {
            withProductOfPrices(asList(PriceDraft.of(EURO_30), PriceDraft.of(EURO_40).withCountry(DE)), product2 -> {
                final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                        .withPriceSelection(PriceSelection.of(EUR).withPriceCountry(DE))
                        .plusQueryFilters(m -> m.id().isIn(asList(product1.getId(), product2.getId())))
                        .plusQueryFilters(m -> m.allVariants().scopedPrice().value()
                                .centAmount().isLessThanOrEqualTo(3000L));
                assertEventually(() -> {
                    final PagedSearchResult<ProductProjection> result = client().executeBlocking(search);
                    assertThat(result.getResults()).extracting(ResourceView::getId)
                            .as("product1 with small price included").contains(product1.getId())
                            .as("product2 is expensive in the scope, not included").doesNotContain(product2.getId());
                    final ProductVariant masterVariant = result.getResults().get(0).getMasterVariant();
                    final Price price = masterVariant.getPrice();
                    assertThat(price).as("price").isNotNull();
                    assertThat(price.getValue()).isEqualTo(EURO_30);
                    assertThat(masterVariant.getScopedPrice()).isNotNull();
                    assertThat(masterVariant.getScopedPrice().getValue()).as("scopedPrice").isEqualTo(EURO_30);
                });
            });
        });
    }

    @Ignore
    @Test
    public void sortByCurrentScopedPrice() {
        final List<PriceDraft> prices1 = asList(PriceDraft.of(EURO_20), PriceDraft.of(EURO_30).withCountry(DE));
        final List<PriceDraft> prices2 = asList(PriceDraft.of(EURO_30), PriceDraft.of(EURO_40).withCountry(DE));
        final SortExpression<ProductProjection> sortExpression = ProductProjectionSearchModel.of().sort().allVariants().scopedPrice().currentValue().centAmount().asc();
        sortCheck(prices1, prices2, sortExpression);
    }

    @Ignore
    @Test
    public void sortByValueScopedPrice() {
        final List<PriceDraft> prices1 = asList(PriceDraft.of(EURO_20), PriceDraft.of(EURO_30).withCountry(DE));
        final List<PriceDraft> prices2 = asList(PriceDraft.of(EURO_30), PriceDraft.of(EURO_40).withCountry(DE));
        final SortExpression<ProductProjection> sortExpression = ProductProjectionSearchModel.of().sort().allVariants().scopedPrice().value().centAmount().asc();
        sortCheck(prices1, prices2, sortExpression);
    }

    private void sortCheck(final List<PriceDraft> prices1, final List<PriceDraft> prices2, final SortExpression<ProductProjection> sortExpression) {
        withProductOfPrices(prices1, product1 -> {
            withProductOfPrices(prices2, product2 -> {
                final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                        .withPriceSelection(PriceSelection.of(EUR).withPriceCountry(DE))
                        .plusQueryFilters(m -> m.id().isIn(asList(product1.getId(), product2.getId())))
                        .plusSort(sortExpression);
                assertEventually(() -> {
                    final PagedSearchResult<ProductProjection> result = client().executeBlocking(search);
                    assertThat(result.getResults()).extracting(ResourceView::getId)
                            .as("product1 with small price included").containsExactly(product1.getId(), product2.getId());
                });
            });
        });
    }

    @Ignore
    @Test
    public void discounts() {
        withProductOfPrices(asList(PriceDraft.of(EURO_20), PriceDraft.of(EURO_30).withCountry(DE)), product -> {
            final ProductDiscountDraft productDiscountDraft = discountDraftOfAbsoluteValue(product, EURO_5);
            withProductDiscount(client(), productDiscountDraft, productDiscount -> {
                assertEventually(s -> {
                    final ProductProjectionSearch searchForCurrentValue = ProductProjectionSearch.ofStaged()
                            .withMarkingMatchingVariants(true)
                            .withPriceSelection(PriceSelection.of(EUR).withPriceCountry(DE))
                            .plusQueryFilters(m -> m.id().is(product.getId()))
                            .plusQueryFilters(m -> m.allVariants().scopedPrice().currentValue()
                                    .centAmount().isLessThanOrEqualTo(2500L))
                            .plusQueryFilters(m -> m.allVariants().scopedPriceDiscounted().is(true));
                    final PagedSearchResult<ProductProjection> resultForCurrentValue =
                            client().executeBlocking(searchForCurrentValue);
                    assertThat(resultForCurrentValue.getResults()).hasSize(1)
                            .as("currentValue search finds discounted price")
                            .extracting(ResourceView::getId).contains(product.getId());
                    final ProductVariant matchingVariant =
                            resultForCurrentValue.getResults().get(0).findFirstMatchingVariant().orElse(null);
                    s.assertThat(matchingVariant).isNotNull();
                    s.assertThat(matchingVariant.getScopedPrice().getValue()).as("value").isEqualTo(EURO_30);
                    s.assertThat(matchingVariant.getScopedPrice().getDiscounted()).isNotNull();
                    final MonetaryAmount discountedValue = matchingVariant.getScopedPrice().getDiscounted().getValue();
                    s.assertThat(discountedValue).as("discountedValue from the selected scope").isEqualTo(EURO_25)
                            .as("does not use the fallback price").isNotEqualTo(EURO_20)
                            .as("or the discounted fallback price").isNotEqualTo(EURO_15);
                    s.assertThat(matchingVariant.getScopedPrice().getDiscounted().getDiscount())
                            .isEqualTo(productDiscount.toReference());
                    s.assertThat(matchingVariant.isScopedPriceDiscounted()).isTrue();
                    s.assertThat(matchingVariant.getScopedPrice()).isNotNull();
                    s.assertThat(matchingVariant.getScopedPrice().getCurrentValue())
                            .as("currentValue").isEqualTo(discountedValue);

                    //it is also possible to filter for discounted values
                    final ProductProjectionSearch searchForDiscountedValue = ProductProjectionSearch.ofStaged()
                            .withMarkingMatchingVariants(true)
                            .withPriceSelection(PriceSelection.of(EUR).withPriceCountry(DE))
                            .plusQueryFilters(m -> m.id().is(product.getId()))
                            //filter by discounted value
                            .plusQueryFilters(m -> m.allVariants().scopedPrice().discounted().value()
                                    .centAmount().isLessThanOrEqualTo(2500L))
                            //also possible to filter for a specific discount
                            .plusQueryFilters(m -> m.allVariants().scopedPrice()
                                    .discounted().discount().id().is(productDiscount.getId()));
                    final PagedSearchResult<ProductProjection> discountedSearchResult =
                            client().executeBlocking(searchForDiscountedValue);
                    final List<ProductProjection> results = discountedSearchResult.getResults();
                    assertThat(results).hasSize(1);
                    final ProductVariant discountedVariant = results.get(0).findFirstMatchingVariant().orElse(null);
                    assertThat(discountedVariant).isNotNull();
                    s.assertThat(discountedVariant.getScopedPrice()).isEqualTo(matchingVariant.getScopedPrice());
                });
            });
        });
    }

    @Ignore
    @Test
    public void sortByScopedPriceDiscounted() {
        withProductOfPrices(asList(PriceDraft.of(EURO_20), PriceDraft.of(EURO_30).withCountry(DE)), product1 -> {
            withProductOfPrices(asList(PriceDraft.of(EURO_30), PriceDraft.of(EURO_40).withCountry(DE)), product2 -> {
                final ProductDiscountDraft productDiscountDraft1 = discountDraftOfAbsoluteValue(product1, EURO_5);
                withProductDiscount(client(), productDiscountDraft1, productDiscount1 -> {
                    assertEventually(s -> {
                        //Products ordered by scopedPriceDiscounted (ascending)
                        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                                .withPriceSelection(PriceSelection.of(EUR).withPriceCountry(DE))
                                .plusQueryFilters(m -> m.id().isIn(asList(product1.getId(), product2.getId())))
                                .plusSort(m -> m.allVariants().scopedPriceDiscounted().asc());
                        final PagedSearchResult<ProductProjection> results = client().executeBlocking(search);
                        assertThat(results.getResults()).hasSize(2);
                        s.assertThat(results.getResults()).extracting(ResourceView::getId).containsExactly(product2.getId(), product1.getId());

                        //Products ordered by scopedPriceDiscounted (descending)
                        final ProductProjectionSearch searchDescending = ProductProjectionSearch.ofStaged()
                                .withPriceSelection(PriceSelection.of(EUR).withPriceCountry(DE))
                                .plusQueryFilters(m -> m.id().isIn(asList(product1.getId(), product2.getId())))
                                .plusSort(m -> m.allVariants().scopedPriceDiscounted().desc());
                        final PagedSearchResult<ProductProjection> resultsDescending = client().executeBlocking(searchDescending);
                        assertThat(resultsDescending.getResults()).hasSize(2);
                        s.assertThat(resultsDescending.getResults()).extracting(ResourceView::getId).containsExactly(product1.getId(), product2.getId());
                        assertThat(resultsDescending.getResults().get(0).getMasterVariant().getPrice().getDiscounted()).isNotNull();
                    });
                });
            });
        });
    }

    @Ignore
    @Test
    public void facetSearchByScopedPriceDiscounted() {
        withProductOfPrices(asList(PriceDraft.of(EURO_20), PriceDraft.of(EURO_30).withCountry(DE)), product1 -> {
            withProductOfPrices(asList(PriceDraft.of(EURO_30), PriceDraft.of(EURO_40).withCountry(DE)), product2 -> {
                withProductOfPrices(asList(PriceDraft.of(EURO_40), PriceDraft.of(EURO_25).withCountry(DE)), product3 -> {
                    final ProductDiscountDraft productDiscountDraft1 = discountDraftOfAbsoluteValue(product1, EURO_5);
                    withProductDiscount(client(), productDiscountDraft1, productDiscount1 -> {
                        assertEventually(s -> {
                            final TermFacetExpression<ProductProjection> facetExpression = ProductProjectionSearchModel.of()
                                    .facet().allVariants().scopedPriceDiscounted().allTerms();
                            final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                                    .withPriceSelection(PriceSelection.of(EUR).withPriceCountry(DE))
                                    .plusQueryFilters(m -> m.id().isIn(asList(product1.getId(), product2.getId(), product3.getId())))
                                    .withFacets(facetExpression);
                            final PagedSearchResult<ProductProjection> results = client().executeBlocking(search);
                            assertThat(results.getResults()).hasSize(3);
                            final List<TermStats> termStats = results.getFacetResult(facetExpression).getTerms();
                            s.assertThat(termStats).containsOnly(
                                    TermStats.of(BOOL_TRUE, 1L),
                                    TermStats.of(BOOL_FALSE, 2L));
                        });
                    });
                });
            });
        });
    }

    private ProductDiscountDraft discountDraftOfAbsoluteValue(final Product product, final MonetaryAmount amount) {
        final AbsoluteProductDiscountValue value = ProductDiscountValue.ofAbsolute(amount);
        final ProductDiscountPredicate predicate =
                ProductDiscountPredicate.of("product.id = \"" + product.getId() + "\"");
        return ProductDiscountDraft.of(randomSlug(), randomSlug(), predicate, value, randomSortOrder(), true);
    }

    private void withProductOfPrices(final List<PriceDraft> priceDrafts, final Consumer<Product> productConsumer) {
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .prices(priceDrafts)
                .build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), masterVariant)
                .publish(true)
                .build();
        withProduct(client(), () -> productDraft, productConsumer);
    }
}
