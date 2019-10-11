package io.sphere.sdk.reviews;

import io.sphere.sdk.annotations.NotOSGiCompatible;
import io.sphere.sdk.cartdiscounts.CartDiscountFixtures;
import io.sphere.sdk.client.SphereClientUtils;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
import io.sphere.sdk.products.search.ProductProjectionSortSearchModel;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.reviews.commands.ReviewCreateCommand;
import io.sphere.sdk.reviews.queries.ReviewQuery;
import io.sphere.sdk.reviews.search.ReviewRatingStatisticsFacetedSearchSearchModel;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.SortExpression;
import io.sphere.sdk.search.TermFacetedSearchExpression;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.RangeStats;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
@NotOSGiCompatible
public class ReviewProductProjectionSearchIntegrationTest extends IntegrationTest {

    public static final String PRODUCT_TYPE_KEY = ReviewProductProjectionSearchIntegrationTest.class.getSimpleName().substring(0, 36);//currently type keys have a max length of 36
    public static final int REVIEWS_PER_PRODUCT = 20;
    public static final int HIGHEST_RATING = 5;
    public static final int LOWEST_RATING = 0;
    public static Product product;

    @AfterClass
    public static void cleanUp() {
        CartDiscountFixtures.deleteDiscountCodesAndCartDiscounts(client());
        ReviewFixtures.deleteReviews(client());
        ProductFixtures.deleteProductsAndProductTypes(client());
    }

    /**
     * The fixtures are created by using {@link Random} with a fixed seed so the results are the same for each test run.
     * The generated data contains reviews with the highest rating ({@value HIGHEST_RATING}) and the lowest rating ({@value LOWEST_RATING}).
     */
    @BeforeClass
    public static void prepareData() throws Exception {
        cleanUp();
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(PRODUCT_TYPE_KEY, PRODUCT_TYPE_KEY, randomKey(), emptyList());
        final ProductType productType = client().executeBlocking(ProductTypeCreateCommand.of(productTypeDraft));
        final List<CompletionStage<Product>> productStages = new LinkedList<>();

        final Random random = new Random(32);
        final int productCount = 3;
        final int reviewsPerProduct = REVIEWS_PER_PRODUCT;
        final List<CompletionStage<Review>> reviewStages = new ArrayList<>(reviewsPerProduct * productCount);
        for(int productIteration = 0; productIteration < productCount; productIteration++) {
            final ProductDraft draft = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), ProductVariantDraftBuilder.of().build()).build();
            final CompletionStage<Product> productCompletionStage = client().execute(ProductCreateCommand.of(draft));
            productStages.add(productCompletionStage);
        }
        final List<Product> products = productStages.stream()
                .map(productStage -> SphereClientUtils.blockingWait(productStage, 10, TimeUnit.SECONDS))
                .collect(toList());

        final ProductDraft draft = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), ProductVariantDraftBuilder.of().build()).build();
        final Product productWithoutReview = client().executeBlocking(ProductCreateCommand.of(draft));

        product = products.get(0);
        products.forEach(product -> {
            for(int reviewIteration = 0; reviewIteration < reviewsPerProduct; reviewIteration++) {
                final int rating = random.nextInt(6);
                final ReviewDraft reviewDraft = ReviewDraftBuilder.ofRating(rating).target(product).build();
                final CompletionStage<Review> reviewCompletionStage = client().execute(ReviewCreateCommand.of(reviewDraft));
                reviewStages.add(reviewCompletionStage);
            }
        });
        reviewStages.forEach(stage -> SphereClientUtils.blockingWait(stage, 10, TimeUnit.SECONDS));
    }

    @Ignore
    @Test
    public void searchForReviewsWithAverageRatingGreaterThan2() {
        final List<FacetRange<BigDecimal>> facetRanges = IntStream.range(LOWEST_RATING, HIGHEST_RATING)
                .mapToObj(i -> FacetRange.of(new BigDecimal(i), new BigDecimal(i + 1)))
                .collect(toList());
        assertThat(facetRanges.toString()).isEqualTo("[[0 to 1), [1 to 2), [2 to 3), [3 to 4), [4 to 5)]");

        final ProductProjectionSearch searchRequest = ProductProjectionSearch.ofStaged()//in prod it would be current
                .withResultFilters(m -> m.reviewRatingStatistics().averageRating().isGreaterThanOrEqualTo(new BigDecimal(2)))
                .withFacets(m -> m.reviewRatingStatistics().averageRating().onlyRange(facetRanges))
                .withSort(m -> m.reviewRatingStatistics().averageRating().desc());

        assertEventually(Duration.ofSeconds(60), Duration.ofMillis(100), () -> {
            final PagedSearchResult<ProductProjection> result = client().executeBlocking(searchRequest);
            assertThat(result.getResults()).hasSize(2);
            final ProductProjection productProjection = result.getResults().get(0);
            assertThat(productProjection.getReviewRatingStatistics().getCount()).isEqualTo(REVIEWS_PER_PRODUCT);
            final RangeFacetResult facetResult = (RangeFacetResult) result.getFacetResult("reviewRatingStatistics.averageRating");
            assertThat(facetResult.getRanges().get(2)).isEqualTo(RangeStats.of("2.0", "3.0", 2L, null, "2.2", "2.7", "4.9", 2.45));

        });
    }

    @Ignore
    @Test
    public void getReviewsForOneProduct() {
        final String productId = product.getId();
        final ReviewQuery reviewQuery = ReviewQuery.of()
                .withPredicates(m -> m.target().id().is(productId))
                .withSort(m -> m.createdAt().sort().desc())
                .withLimit(REVIEWS_PER_PRODUCT);
        final List<Review> reviews = client().executeBlocking(reviewQuery).getResults();
        assertThat(reviews).hasSize(REVIEWS_PER_PRODUCT);
        assertThat(reviews).extracting(r -> r.getTarget().getId()).containsOnlyElementsOf(singletonList(productId));
    }

    @Ignore
    @Test
    public void getApprovedReviewsForOneProduct() {
        final String productId = product.getId();
        final ReviewQuery reviewQuery = ReviewQuery.of()
                .withPredicates(review -> review.includedInStatistics().is(true).and(review.target().id().is(productId)))
                .withSort(m -> m.createdAt().sort().desc())
                .withLimit(REVIEWS_PER_PRODUCT);
        final List<Review> reviews = client().executeBlocking(reviewQuery).getResults();
        assertThat(reviews).hasSize(REVIEWS_PER_PRODUCT);
        assertThat(reviews).extracting(r -> r.getTarget().getId()).containsOnlyElementsOf(singletonList(productId));
        assertThat(reviews).extracting(r -> r.isIncludedInStatistics()).containsOnlyElementsOf(singletonList(true));
    }

    @Ignore
    @Test
    public void searchByCount() {
        final ProductProjectionSearch projectionSearch = ProductProjectionSearch.ofStaged()//in prod it would be current
                .withResultFilters(m -> m.reviewRatingStatistics().count().isGreaterThanOrEqualTo(new BigDecimal(2)))
                .plusResultFilters(m -> m.reviewRatingStatistics().highestRating().isGreaterThanOrEqualTo(new BigDecimal(2)))
                .plusResultFilters(m -> m.reviewRatingStatistics().lowestRating().isGreaterThanOrEqualTo(new BigDecimal(0)))
                .plusFacets(m -> m.reviewRatingStatistics().count().allRanges())
                .plusFacets(m -> m.reviewRatingStatistics().highestRating().allRanges())
                .plusFacets(m -> m.reviewRatingStatistics().lowestRating().allRanges())
                ;
        assertEventually(Duration.ofSeconds(60), Duration.ofMillis(100), () -> {
            softAssert(soft -> {
                final PagedSearchResult<ProductProjection> res = client().executeBlocking(projectionSearch);
                soft.assertThat(res.getCount()).isGreaterThanOrEqualTo(3);
                final RangeFacetResult countFacets = (RangeFacetResult) res.getFacetResult("reviewRatingStatistics.count");
                soft.assertThat(countFacets.getRanges().get(1).getSum()).as("count facets").isEqualTo("60.0");
                final RangeFacetResult lowestRatingFacets = (RangeFacetResult) res.getFacetResult("reviewRatingStatistics.lowestRating");
                soft.assertThat(lowestRatingFacets.getRanges().get(1).getSum()).as("lowestRating facets").isEqualTo("0.0");
                final RangeFacetResult highestRatingFacets = (RangeFacetResult) res.getFacetResult("reviewRatingStatistics.highestRating");
                soft.assertThat(highestRatingFacets.getRanges().get(1).getSum()).as("highestRating facets").isEqualTo("14.0");
            });
        });
    }

    @Ignore
    @Test
    public void sortByAverageRating() {
        checkSorting(
                m -> m.reviewRatingStatistics().averageRating().desc(),
                p -> assertThat(p.getReviewRatingStatistics().getAverageRating()).isBetween(2.70D, 2.74D)
        );
    }

    @Ignore
    @Test
    public void sortByCount() {
        checkSorting(
                m -> m.reviewRatingStatistics().count().desc(),
                p -> assertThat(p.getReviewRatingStatistics().getCount()).isEqualTo(REVIEWS_PER_PRODUCT)
        );
    }

    @Ignore
    @Test
    public void sortByLowestRating() {
        checkSorting(
                m -> m.reviewRatingStatistics().lowestRating().asc(),
                p -> assertThat(p.getReviewRatingStatistics().getLowestRating()).isEqualTo(LOWEST_RATING)
        );
    }

    @Ignore
    @Test
    public void sortByHighestRating() {
        checkSorting(
                m -> m.reviewRatingStatistics().highestRating().desc(),
                p -> assertThat(p.getReviewRatingStatistics().getHighestRating()).isEqualTo(HIGHEST_RATING)
        );
    }

    @Ignore
    @Test
    public void facetAndFilter() {
        final ReviewRatingStatisticsFacetedSearchSearchModel<ProductProjection> reviewRatingsModel
                = ProductProjectionSearchModel.of().facetedSearch().reviewRatingStatistics();
        final TermFacetedSearchExpression<ProductProjection> count = reviewRatingsModel.count().is("20");
        final TermFacetedSearchExpression<ProductProjection> averageRating = reviewRatingsModel.averageRating().is("2.7");
        final TermFacetedSearchExpression<ProductProjection> highestRating = reviewRatingsModel.highestRating().is(String.valueOf(HIGHEST_RATING));
        final TermFacetedSearchExpression<ProductProjection> lowestRating = reviewRatingsModel.lowestRating().is(String.valueOf(LOWEST_RATING));

        final ProductProjectionSearch productProjectionSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(m -> m.id().is(product.getId()))
                .plusFacetedSearch(asList(count, averageRating, highestRating, lowestRating));
        assertEventually(() -> {
            final PagedSearchResult<ProductProjection> result = client().executeBlocking(productProjectionSearch);
            final List<ProductProjection> productProjectionList = result.getResults();
            assertThat(productProjectionList).isNotEmpty();
            assertThat(productProjectionList.get(0).getId()).isEqualTo(product.getId());

            assertThat(result.getFacetResult(count).getTotal()).as("count facet").isEqualTo(1L);
            assertThat(result.getFacetResult(averageRating).getTotal()).as("averageRating facet").isEqualTo(1L);
            assertThat(result.getFacetResult(highestRating).getTotal()).as("highestRating facet").isEqualTo(1L);
            assertThat(result.getFacetResult(lowestRating).getTotal()).as("lowestRating facet").isEqualTo(1L);
        });
    }

    private void checkSorting(final Function<ProductProjectionSortSearchModel, SortExpression<ProductProjection>> sortExpressionFunction, final Consumer<ProductProjection> asserter) {
        final ProductProjectionSearch productProjectionSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(m -> m.reviewRatingStatistics().count().isGreaterThanOrEqualTo(BigDecimal.ZERO))
                .withSort(sortExpressionFunction);
        assertEventually(Duration.ofSeconds(15), Duration.ofMillis(200), () -> {
            final List<ProductProjection> results = client().executeBlocking(productProjectionSearch).getResults();
            assertThat(results.size()).isGreaterThanOrEqualTo(3);
            final ProductProjection first = results.get(0);
            asserter.accept(first);
        });
    }
}
