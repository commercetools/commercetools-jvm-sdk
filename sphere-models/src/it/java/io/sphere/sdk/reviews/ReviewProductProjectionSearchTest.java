package io.sphere.sdk.reviews;

import io.sphere.sdk.client.SphereClientUtils;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.reviews.commands.ReviewCreateCommand;
import io.sphere.sdk.reviews.queries.ReviewQuery;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.RangeStats;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ReviewProductProjectionSearchTest extends IntegrationTest {

    public static final String PRODUCT_TYPE_KEY = ReviewProductProjectionSearchTest.class.getSimpleName();
    public static Product product;

    @BeforeClass
    @AfterClass
    public static void cleanUp() {
        ReviewFixtures.deleteReviews(client());
        ProductFixtures.deleteProductsAndProductTypes(client());
    }

    @BeforeClass
    public static void prepareData() throws InterruptedException {

        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(PRODUCT_TYPE_KEY, PRODUCT_TYPE_KEY, randomKey(), emptyList());
        final ProductType productType = client().executeBlocking(ProductTypeCreateCommand.of(productTypeDraft));
        final List<CompletionStage<Product>> productStages = new LinkedList<>();

        final Random random = new Random(32);
        final int productCount = 3;
        final int reviewsPerProduct = 20;
        final List<CompletionStage<Review>> reviewStages = new ArrayList<>(reviewsPerProduct * productCount);
        for(int productIteration = 0; productIteration < productCount; productIteration++) {
            final ProductDraft draft = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), ProductVariantDraftBuilder.of().build()).build();
            final CompletionStage<Product> productCompletionStage = client().execute(ProductCreateCommand.of(draft));
            productStages.add(productCompletionStage);
        }
        final List<Product> products = productStages.stream()
                .map(productStage -> SphereClientUtils.blockingWait(productStage, 10, TimeUnit.SECONDS))
                .collect(toList());
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

    @Test
    public void searchForReviewsWithAverageRatingGreaterThan2() {
        final List<FacetRange<BigDecimal>> facetRanges = IntStream.range(0, 5)
                .mapToObj(i -> FacetRange.of(new BigDecimal(i), new BigDecimal(i + 1)))
                .collect(toList());
        assertThat(facetRanges.toString()).isEqualTo("[[0 to 1), [1 to 2), [2 to 3), [3 to 4), [4 to 5)]");

        final ProductProjectionSearch searchRequest = ProductProjectionSearch.ofStaged()//in prod it would be current
                .withResultFilters(m -> m.reviewRatingStatistics().averageRating().byGreaterThanOrEqualTo(new BigDecimal(2)))
                .withFacets(m -> m.reviewRatingStatistics().averageRating().onlyRange(facetRanges))
                .withSort(m -> m.reviewRatingStatistics().averageRating().byDesc());

        assertEventually(() -> {
            final PagedSearchResult<ProductProjection> result = client().executeBlocking(searchRequest);
            assertThat(result.getResults()).hasSize(2);
            final ProductProjection productProjection = result.getResults().get(0);
            assertThat(productProjection.getReviewRatingStatistics().getCount()).isEqualTo(20);
            final RangeFacetResult facetResult = (RangeFacetResult) result.getFacetResult("reviewRatingStatistics.averageRating");
            assertThat(facetResult.getRanges().get(2)).isEqualTo(RangeStats.of("2.0", "3.0", 2L, "2.2", "2.7", "4.9", 2.45));

        });
    }

    @Test
    public void getReviewsForOneProduct() {
        final String productId = product.getId();
        final ReviewQuery reviewQuery = ReviewQuery.of()
                .withPredicates(m -> m.target().id().is(productId))
                .withSort(m -> m.createdAt().sort().desc())
                .withLimit(20);
        final List<Review> reviews = client().executeBlocking(reviewQuery).getResults();
        assertThat(reviews).hasSize(20);
        assertThat(reviews).extracting(r -> r.getTarget().getId()).containsOnlyElementsOf(singletonList(productId));
    }

    @Test
    public void getApprovedReviewsForOneProduct() {
        final String productId = product.getId();
        final ReviewQuery reviewQuery = ReviewQuery.of()
                .withPredicates(review -> review.includedInStatistics().is(true).and(review.target().id().is(productId)))
                .withSort(m -> m.createdAt().sort().desc())
                .withLimit(20);
        final List<Review> reviews = client().executeBlocking(reviewQuery).getResults();
        assertThat(reviews).hasSize(20);
        assertThat(reviews).extracting(r -> r.getTarget().getId()).containsOnlyElementsOf(singletonList(productId));
        assertThat(reviews).extracting(r -> r.isIncludedInStatistics()).containsOnlyElementsOf(singletonList(true));
    }
}
