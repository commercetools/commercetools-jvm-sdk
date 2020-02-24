package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductProjectionByIdGet;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.ReviewDraftBuilder;
import io.sphere.sdk.reviews.ReviewFixtures;
import io.sphere.sdk.reviews.messages.ReviewCreatedMessage;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateDraft;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.states.commands.StateDeleteCommand;
import io.sphere.sdk.states.queries.StateQuery;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.JsonNodeReferenceResolver;
import io.sphere.sdk.types.*;
import net.jcip.annotations.NotThreadSafe;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.states.StateFixtures.withState;
import static io.sphere.sdk.states.StateRole.REVIEW_INCLUDED_IN_STATISTICS;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static io.sphere.sdk.test.SphereTestUtils.draftFromJsonResource;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class ReviewCreateCommandIntegrationTest extends IntegrationTest {
    @Before
    public void clean() throws Exception {
        ReviewFixtures.deleteReviews(client());
        //since the state can have a remaining review, the review needs to be deleted first
        final State state = client().executeBlocking(StateQuery.of().withPredicates(m -> m.key().is("initial-review-state"))).head().orElse(null);
        if (state != null) {
            client().executeBlocking(StateDeleteCommand.of(state));
        }
    }

    @Test
    public void createByCode() {
        withType(client(), (Type type) -> {
            withCustomer(client(), (Customer customer) -> {
                withProduct(client(), (Product product) -> {
                    withState(client(), StateDraft.of("initial-review-state", StateType.REVIEW_STATE), (State state) -> {
                        final CustomFieldsDraft extraFields = CustomFieldsDraft.ofTypeKeyAndObjects(type.getKey(), singletonMap("screenshotUrls",
                                Collections.singleton("https://docs.commercetools.com/assets/img/CT-logo.svg")));
                        final ReviewDraft reviewDraft = ReviewDraftBuilder.ofTitle("Commercetools rocks")
                                .authorName("John Smith")
                                .text("It is great.")
                                .rating(100)
                                .custom(extraFields)
                                .customer(customer)
                                .key("review1")
                                //only allow one review for a product from one customer
                                .uniquenessValue(product.getId() + "+" + customer.getId())
                                .locale(Locale.ENGLISH)
                                .state(state)
                                .target(product)
                                .build();

                        final Review review = client().executeBlocking(ReviewCreateCommand.of(reviewDraft));

                        assertThat(review.getAuthorName()).isEqualTo("John Smith");
                        assertThat(review.getCustom().getFieldAsStringSet("screenshotUrls"))
                                .containsExactly("https://docs.commercetools.com/assets/img/CT-logo.svg");
                        assertThat(review.getCustomer()).isEqualTo(customer.toReference());
                        assertThat(review.getKey()).isEqualTo("review1");
                        assertThat(review.getUniquenessValue()).isEqualTo(product.getId() + "+" + customer.getId());
                        assertThat(review.getLocale()).isEqualTo(Locale.ENGLISH);
                        assertThat(review.getAuthorName()).isEqualTo("John Smith");
                        assertThat(review.getTitle()).isEqualTo("Commercetools rocks");
                        assertThat(review.getText()).isEqualTo("It is great.");
                        assertThat(review.getTarget()).isEqualTo(product.toReference());
                        assertThat(review.getRating()).isEqualTo(100);
                        assertThat(review.getState()).isEqualTo(state.toReference());
                        assertThat(review.getCustomer()).isEqualTo(customer.toReference());

                        final ProductProjection productProjection = client().executeBlocking(ProductProjectionByIdGet.ofStaged(product));
                        assertThat(productProjection.getReviewRatingStatistics()).
                                as("the state has not the role ReviewIncludedInStatistics, so it is not accounted yet")
                                .isNull();

                        //you can observe a message
                        assertEventually(() -> {
                            final PagedQueryResult<ReviewCreatedMessage> pagedQueryResult = client().executeBlocking(MessageQuery.of()
                                    .withPredicates(m -> m.resource().is(review))
                                    .forMessageType(ReviewCreatedMessage.MESSAGE_HINT));

                            final Optional<ReviewCreatedMessage> paymentCreatedMessage = pagedQueryResult.head();

                            assertThat(paymentCreatedMessage).isPresent();
                            assertThat(paymentCreatedMessage.get().getReview().getId()).isEqualTo(review.getId());
                            assertThat(paymentCreatedMessage.get().getResource().getId()).isEqualTo(review.getId());
                        });

                        client().executeBlocking(ReviewDeleteCommand.of(review));
                    });
                });
            });
        });
    }

    @Test
    public void createByJson() {
        final JsonNodeReferenceResolver referenceResolver = new JsonNodeReferenceResolver();
        withCustomer(client(), (Customer customer) -> {
            withProduct(client(), (Product product) -> {
                withState(client(), StateDraft.of("initial-review-state", StateType.REVIEW_STATE).withRoles(REVIEW_INCLUDED_IN_STATISTICS), (State state) -> {
                    referenceResolver.addResourceByKey("review-product", product);
                    referenceResolver.addResourceByKey("review-customer", customer);
                    final ReviewDraft reviewDraft = draftFromJsonResource("drafts-tests/review.json", ReviewDraft.class, referenceResolver);

                    final Review review = client().executeBlocking(ReviewCreateCommand.of(reviewDraft));

                    assertThat(review.getAuthorName()).isEqualTo("John Smith");
                    assertThat(review.getCustomer()).isEqualTo(customer.toReference());
                    assertThat(review.getKey()).isEqualTo("review1");
                    assertThat(review.getLocale()).isEqualTo(Locale.ENGLISH);
                    assertThat(review.getAuthorName()).isEqualTo("John Smith");
                    assertThat(review.getTitle()).isEqualTo("Commercetools rocks");
                    assertThat(review.getTarget()).isEqualTo(product.toReference());
                    assertThat(review.getRating()).isEqualTo(100);
                    assertThat(review.getCustomer()).isEqualTo(customer.toReference());
                    assertThat(review.getState()).isEqualTo(state.toReference());

                    assertEventually(() -> {
                        final ProductProjection productProjection = client().executeBlocking(ProductProjectionByIdGet.ofStaged(product));
                        assertThat(productProjection.getReviewRatingStatistics()).isNotNull();
                        assertThat(productProjection.getReviewRatingStatistics().getAverageRating()).isEqualTo(100D);
                        assertThat(productProjection.getReviewRatingStatistics().getHighestRating()).isEqualTo(100);
                        assertThat(productProjection.getReviewRatingStatistics().getLowestRating()).isEqualTo(100);
                        assertThat(productProjection.getReviewRatingStatistics().getCount()).isEqualTo(1);
                        assertThat(productProjection.getReviewRatingStatistics().getRatingsDistribution()).isEqualTo(singletonMap(100, 1));
                    });

                    client().executeBlocking(ReviewDeleteCommand.of(review));
                });
            });
        });
    }

    private static void withType(final BlockingSphereClient client, Consumer<Type> consumer) {
        TypeFixtures.withType(client, typeBuilder -> {
            final LocalizedString label = LocalizedString.ofEnglish("customer screenshots of the product");
            final FieldDefinition screenShotsFieldDefinition =
                    FieldDefinition.of(SetFieldType.of(StringFieldType.of()), "screenshotUrls", label, false);
            return typeBuilder.plusFieldDefinitions(screenShotsFieldDefinition);
        }, consumer);
    }
}
