package io.sphere.sdk.reviews;


import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.commands.ReviewCreateCommand;
import io.sphere.sdk.reviews.commands.ReviewUpdateCommand;
import io.sphere.sdk.reviews.commands.updateCommands.SetAuthorName;
import io.sphere.sdk.reviews.commands.updateCommands.SetScore;
import io.sphere.sdk.reviews.commands.updateCommands.SetText;
import io.sphere.sdk.reviews.commands.updateCommands.SetTitle;
import io.sphere.sdk.reviews.queries.ReviewQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.reviews.ReviewsFixtures.withReview;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by njacinto on 17/04/15.
 */

public class ReviewIntegrationTest extends IntegrationTest {
    private static final long SERVER_TIME_TOLERANCE_SEC = 120;
    // { authorName, title, text, score }
    private static final Object[][] REVIEWS = {
            {"First author","First review","This is my first comment",0.3f},
            {"Second author","Second review","This is my second comment",0.4f},
            {"Third author","Third review","This is my third comment",0.5f},
            {"Fourth author","Fourth review","This is my fourth comment",0.6f},
            {"Fifth author","Fifth review","This is my fifth comment",0.7f}
    };

    @Test
    public void testCreate() throws Exception {
        withProduct(client(), product ->
            withCustomer(client(), customer -> {
                Instant start = Instant.now().minusSeconds(SERVER_TIME_TOLERANCE_SEC); // time tolerance
                Review review = client().execute(ReviewCreateCommand.of(ReviewDraft.of(product.getId(), customer.getId())
                        .withAuthorName((String) REVIEWS[0][0]).withTitle((String) REVIEWS[0][1])
                        .withText((String) REVIEWS[0][2]).withScore((Float) REVIEWS[0][3])));
                Instant afterCreation = Instant.now().plusSeconds(SERVER_TIME_TOLERANCE_SEC); // time tolerance
                assertEquals(product.getId(), review.getProductId());
                assertEquals(customer.getId(), review.getCustomerId());
                assertEquals((String) REVIEWS[0][0], review.getAuthorName().get());
                assertEquals((String) REVIEWS[0][1], review.getTitle().get());
                assertEquals((String) REVIEWS[0][2], review.getText().get());
                assertEquals((Float) REVIEWS[0][3], review.getScore().get());
                assertNotNull(review.getId());
                assertEquals(1, review.getVersion());
                Instant timestamp = review.getCreatedAt();
                if (!timestamp.isAfter(start) || !timestamp.isBefore(afterCreation)) {
                    fail(String.format("Invalid creation time %s when expected between %s and %s",
                            DateTimeFormatter.ISO_INSTANT.format(timestamp),
                            DateTimeFormatter.ISO_INSTANT.format(start),
                            DateTimeFormatter.ISO_INSTANT.format(afterCreation)));
                }
                timestamp = review.getLastModifiedAt();
                if (!timestamp.isAfter(start) || !timestamp.isBefore(afterCreation)) {
                    fail(String.format("Invalid last modified time %s when expected between %s and %s",
                            DateTimeFormatter.ISO_INSTANT.format(timestamp),
                            DateTimeFormatter.ISO_INSTANT.format(start),
                            DateTimeFormatter.ISO_INSTANT.format(afterCreation)));
                }
            })
        );

    }

    @Test(expected = io.sphere.sdk.client.ErrorResponseException.class)
    public void testCreate_OneReviewPerCustomerProductConstraint() throws Exception {
        withProduct(client(), product ->
                withCustomer(client(), customer -> {
                    for (int i = 0; i < 2; i++) {
                        Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of(product.getId(), customer.getId())
                                .withAuthorName((String) REVIEWS[i][0]).withTitle((String) REVIEWS[i][1])
                                .withText((String) REVIEWS[i][2]).withScore((Float) REVIEWS[i][3])));
                    }
                }));

    }

    @Test(expected = io.sphere.sdk.client.ErrorResponseException.class)
    public void testCreate_NullProduct() throws Exception {
        withCustomer(client(), customer -> {
            for (int i = 0; i < REVIEWS.length; i++) {
                Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of(null, customer.getId())
                        .withAuthorName((String) REVIEWS[i][0]).withTitle((String) REVIEWS[i][1])
                        .withText((String) REVIEWS[i][2]).withScore((Float) REVIEWS[i][3])));
            }
        });
    }

    @Test(expected = io.sphere.sdk.client.ErrorResponseException.class)
    public void testCreate_InvalidProductId() throws Exception {
        withCustomer(client(), customer -> {
            for (int i = 0; i < REVIEWS.length; i++) {
                Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of("invalid", customer.getId())
                        .withAuthorName((String) REVIEWS[i][0]).withTitle((String) REVIEWS[i][1])
                        .withText((String) REVIEWS[i][2]).withScore((Float) REVIEWS[i][3])));
            }
        });
    }

    @Test(expected = io.sphere.sdk.client.ErrorResponseException.class)
    public void testCreate_NullCustomer() throws Exception {
        withProduct(client(), product -> {
            for (int i = 0; i < REVIEWS.length; i++) {
                Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of(product.getId(), null)
                        .withAuthorName((String) REVIEWS[i][0]).withTitle((String) REVIEWS[i][1])
                        .withText((String) REVIEWS[i][2]).withScore((Float) REVIEWS[i][3])));
            }
        });
    }

    @Test(expected = io.sphere.sdk.client.ErrorResponseException.class)
    public void testCreate_InvalidCustomerId() throws Exception {
        withProduct(client(), product -> {
            for (int i = 0; i < REVIEWS.length; i++) {
                Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of(product.getId(), "invalid")
                        .withAuthorName((String) REVIEWS[i][0]).withTitle((String) REVIEWS[i][1])
                        .withText((String) REVIEWS[i][2]).withScore((Float) REVIEWS[i][3])));
            }
        });
    }

    @Test(expected = io.sphere.sdk.client.ErrorResponseException.class)
    public void testCreate_ConstraintNoTitleTextScore() throws Exception {
        withProduct(client(), product ->
            withCustomer(client(), customer -> {
                Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of(product.getId(), customer.getId())
                        .withAuthorName((String) REVIEWS[0][0])));
            }));
    }

    @Test(expected = io.sphere.sdk.client.ErrorResponseException.class)
    public void testUpdateNoTitleTextScore() throws Exception {
        withProduct(client(), product ->
                withCustomer(client(), customer ->
                        withReview(client(), product, customer, review -> {
                            client().execute(ReviewUpdateCommand.of(
                                    Versioned.of(review.getId(), review.getVersion()), Arrays.asList(
                                            SetTitle.of(Optional.empty()),
                                            SetText.of(Optional.empty()),
                                            SetScore.of(Optional.empty()))));
                        })));
    }

    @Test
    public void testCreate_ConstraintTitleTextScore() throws Exception {
        withProduct(client(), product -> {
            withCustomer(client(), customer -> {
                Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of(product.getId(), customer.getId())
                        .withAuthorName((String) REVIEWS[0][0]).withTitle((String) REVIEWS[0][1])));
                assertNotNull(review.getId());
            });
            withCustomer(client(), customer -> {
                Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of(product.getId(), customer.getId())
                        .withAuthorName((String) REVIEWS[0][0]).withText((String) REVIEWS[0][2])));
                assertNotNull(review.getId());
            });
            withCustomer(client(), customer -> {
                Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of(product.getId(), customer.getId())
                        .withAuthorName((String) REVIEWS[0][0]).withScore((Float) REVIEWS[0][3])));
                assertNotNull(review.getId());
            });
            withCustomer(client(), customer -> {
                Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of(product.getId(), customer.getId())
                        .withAuthorName((String) REVIEWS[0][0]).withTitle((String) REVIEWS[0][1])
                        .withText((String) REVIEWS[0][2])));
                assertNotNull(review.getId());
            });
            withCustomer(client(), customer -> {
                Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of(product.getId(), customer.getId())
                        .withAuthorName((String) REVIEWS[0][0]).withTitle((String) REVIEWS[0][1])
                        .withScore((Float) REVIEWS[0][3])));
                assertNotNull(review.getId());
            });
            withCustomer(client(), customer -> {
                Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of(product.getId(), customer.getId())
                        .withAuthorName((String) REVIEWS[0][0]).withText((String) REVIEWS[0][2]).withScore((Float) REVIEWS[0][3])));
                assertNotNull(review.getId());
            });
            withCustomer(client(), customer -> {
                Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of(product.getId(), customer.getId())
                        .withAuthorName((String) REVIEWS[0][0]).withTitle((String) REVIEWS[0][1])
                        .withText((String) REVIEWS[0][2]).withScore((Float) REVIEWS[0][3])));
                assertNotNull(review.getId());
            });
        });

    }

    @Test
    public void testCreate_NoAuthorName() throws Exception {
        withProduct(client(), product ->
                withCustomer(client(), customer -> {
                    Review review = client().execute(new ReviewCreateCommand(ReviewDraft.of(product.getId(), customer.getId())
                            .withTitle((String) REVIEWS[0][1])));
                }));
    }

    @Test
    public void testGetAll() throws Exception {
        withProduct(client(), product -> {
            for(int i=0; i<2; i++) {
                withCustomer(client(), customer ->
                    withReview(client(), product, customer, newReview -> {
                        assertNotNull(newReview.getId());
                        PagedQueryResult<Review> result = null;
                        boolean notFound = true;
                        int offset = 0;
                        while((result==null || !result.isLast()) && notFound) {
                            result = client().execute(ReviewQuery.of().withOffset(offset));
                            Optional<Review> optReview =
                                    result.getResults().stream().filter(review1 -> review1.getId().equals(newReview.getId())).findFirst();
                            notFound = !optReview.isPresent();
                            offset = result.getOffset()+result.getResults().size();
                        }
                        assertFalse(notFound);
                    }));
            }
        });
    }

    @Test
    public void testGetOne() throws Exception {
        withProduct(client(), product ->
                withCustomer(client(), customer ->
                        withReview(client(), product, customer, review -> {
                            PagedQueryResult<Review> result = client().execute(ReviewQuery.of()
                                    .withPredicate(Predicate.of("id = \"" + review.getId() + "\"")));
                            Optional<Review> optReview =
                                    result.getResults().stream().filter(review1 -> review1.getId().equals(review.getId())).findFirst();
                            assertTrue(optReview.isPresent());
                            assertEquals(optReview.get(), review);
                        })));
    }

    @Test
    public void testGetById() throws Exception {
        withProduct(client(), product ->
                withCustomer(client(), customer ->
                        withReview(client(), product, customer, review -> {
                            PagedQueryResult<Review> result = client().execute(ReviewQuery.of().byId(review.getId()));
                            Optional<Review> optReview =
                                    result.getResults().stream().filter(review1 -> review1.getId().equals(review.getId())).findFirst();
                            assertTrue(optReview.isPresent());
                            assertEquals(optReview.get(), review);
                        })));
    }

    @Test
    public void testGetByProductId() throws Exception {
        withProduct(client(), product ->
                withCustomer(client(), customer ->
                        withReview(client(), product, customer, review -> {
                            PagedQueryResult<Review> result = client().execute(ReviewQuery.of().byProductId(review.getProductId()));
                            Optional<Review> optReview =
                                    result.getResults().stream().filter(review1 -> review1.getId().equals(review.getId())).findFirst();
                            assertTrue(optReview.isPresent());
                            assertEquals(optReview.get(), review);
                        })));
    }

    @Test
    public void testFilterProductIdAndCustomerId() throws Exception {
        withProduct(client(), product ->
                withCustomer(client(), customer ->
                        withReview(client(), product, customer, review -> {
                            PagedQueryResult<Review> result = client().execute(ReviewQuery.of()
                                    .withPredicate(Predicate.of("customerId = \"" + review.getCustomerId() + "\"")));
                            Optional<Review> optReview =
                                    result.getResults().stream().filter(review1 -> review1.getId().equals(review.getId())).findFirst();
                            assertTrue(optReview.isPresent());
                            assertEquals(optReview.get(), review);

                            result = client().execute(ReviewQuery.of()
                                    .withPredicate(Predicate.of("productId = \"" + review.getProductId() + "\"")));
                            optReview = result.getResults().stream().filter(review1 -> review1.getId().equals(review.getId())).findFirst();
                            assertTrue(optReview.isPresent());
                            assertEquals(optReview.get(), review);

                            result = client().execute(ReviewQuery.of()
                                    .withPredicate(Predicate.of("productId = \"" + review.getProductId() + "\" and customerId = \"" + review.getCustomerId() + "\"")));
                            optReview = result.getResults().stream().filter(review1 -> review1.getId().equals(review.getId())).findFirst();
                            assertTrue(optReview.isPresent());
                            assertEquals(optReview.get(), review);
                        })));
    }

    @Test
    public void testUpdateAuthorName() throws Exception {
        withProduct(client(), product ->
                withCustomer(client(), customer ->
                        withReview(client(), product, customer, newReview -> {
                            Instant startUpdate = Instant.now().minusSeconds(SERVER_TIME_TOLERANCE_SEC); // time tolerance
                            Review review = client().execute(ReviewUpdateCommand.of(
                                    Versioned.of(newReview.getId(), newReview.getVersion()), SetAuthorName.of(Optional.of((String) REVIEWS[1][0]))));
                            Instant afterUpdate = Instant.now().plusSeconds(SERVER_TIME_TOLERANCE_SEC); // seconds tolerance
                            assertEquals((String) REVIEWS[1][0], review.getAuthorName().get());
                            assertNotEquals(newReview.getVersion(), review.getVersion());
                            checkTime(review.getLastModifiedAt(), review.getLastModifiedAt(),
                                    startUpdate, afterUpdate);
                            review = client().execute(ReviewUpdateCommand.of(
                                    Versioned.of(review.getId(), review.getVersion()), SetAuthorName.of(Optional.empty())));
                            assertFalse(review.getAuthorName().isPresent());
                        })));
    }

    @Test
    public void testUpdateTitle() throws Exception {
        withProduct(client(), product ->
                withCustomer(client(), customer ->
                        withReview(client(), product, customer, newReview -> {
                            Instant startUpdate = Instant.now().minusSeconds(SERVER_TIME_TOLERANCE_SEC); // time tolerance
                            Review review = client().execute(ReviewUpdateCommand.of(
                                    Versioned.of(newReview.getId(), newReview.getVersion()), SetTitle.of(Optional.of((String) REVIEWS[1][1]))));
                            Instant afterUpdate = Instant.now().plusSeconds(SERVER_TIME_TOLERANCE_SEC); // seconds tolerance
                            assertEquals((String) REVIEWS[1][1], review.getTitle().get());
                            assertNotEquals(newReview.getVersion(), review.getVersion());
                            checkTime(review.getLastModifiedAt(), review.getLastModifiedAt(),
                                    startUpdate, afterUpdate);
                            review = client().execute(ReviewUpdateCommand.of(
                                    Versioned.of(review.getId(), review.getVersion()), SetTitle.of(Optional.empty())));
                            assertFalse(review.getTitle().isPresent());
                        })));
    }

    @Test
    public void testUpdateText() throws Exception {
        withProduct(client(), product ->
                withCustomer(client(), customer ->
                    withReview(client(), product, customer, newReview -> {
                        Instant startUpdate = Instant.now().minusSeconds(SERVER_TIME_TOLERANCE_SEC); // time tolerance
                        Review review = client().execute(ReviewUpdateCommand.of(
                                Versioned.of(newReview.getId(), newReview.getVersion()), SetText.of(Optional.of((String) REVIEWS[1][2]))));
                        Instant afterUpdate = Instant.now().plusSeconds(SERVER_TIME_TOLERANCE_SEC); // seconds tolerance
                        assertEquals((String) REVIEWS[1][2], review.getText().get());
                        assertNotEquals(newReview.getVersion(), review.getVersion());
                        checkTime(review.getLastModifiedAt(), review.getLastModifiedAt(),
                                startUpdate, afterUpdate);
                        review = client().execute(ReviewUpdateCommand.of(
                                Versioned.of(review.getId(), review.getVersion()), SetText.of(Optional.empty())));
                        assertFalse(review.getText().isPresent());
                    })));
    }

    @Test
    public void testUpdateScore() throws Exception {
        withProduct(client(), product ->
                withCustomer(client(), customer ->
                        withReview(client(), product, customer, newReview -> {
                            float score = newReview.getScore().orElse(0.3f);
                            score += score>0.5 ? -0.2f : 0.2f;
                            Instant startUpdate = Instant.now().minusSeconds(SERVER_TIME_TOLERANCE_SEC); // time tolerance
                            Review review = client().execute(ReviewUpdateCommand.of(
                                    Versioned.of(newReview.getId(), newReview.getVersion()), SetScore.of(Optional.of(score))));
                            Instant afterUpdate = Instant.now().plusSeconds(SERVER_TIME_TOLERANCE_SEC); // time tolerance
                            float resultScore = review.getScore().get();
                            assertTrue(resultScore > (score - 0.1) && resultScore < (score + 0.1));
                            assertNotEquals(newReview.getVersion(), review.getVersion());
                            checkTime(review.getLastModifiedAt(), review.getLastModifiedAt(),
                                    startUpdate, afterUpdate);
                            review = client().execute(ReviewUpdateCommand.of(
                                    Versioned.of(review.getId(), review.getVersion()), SetScore.of(Optional.empty())));
                            assertFalse(review.getScore().isPresent());
                        })));
    }

    private static void checkTime(Instant oldTimestamp, Instant newTimestamp, Instant start, Instant end){

        if (newTimestamp.getEpochSecond()<oldTimestamp.getEpochSecond() || !newTimestamp.isAfter(start) || !newTimestamp.isBefore(end)) {
            fail(String.format("Invalid last modified time %s when expected bigger than %s between %s and %s",
                    DateTimeFormatter.ISO_INSTANT.format(newTimestamp),
                    DateTimeFormatter.ISO_INSTANT.format(oldTimestamp),
                    DateTimeFormatter.ISO_INSTANT.format(start),
                    DateTimeFormatter.ISO_INSTANT.format(end)));
        }
    }
}