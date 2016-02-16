package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.commands.updateactions.*;
import io.sphere.sdk.reviews.messages.ReviewRatingSetMessage;
import io.sphere.sdk.reviews.messages.ReviewStateTransitionMessage;
import io.sphere.sdk.states.StateRole;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Locale;
import java.util.Optional;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.reviews.ReviewFixtures.withUpdateableReview;
import static io.sphere.sdk.states.StateFixtures.withStateByBuilder;
import static io.sphere.sdk.states.StateRole.REVIEW_INCLUDED_IN_STATISTICS;
import static io.sphere.sdk.states.StateType.REVIEW_STATE;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class ReviewUpdateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void setAuthorName() {
        withUpdateableReview(client(), (Review review) -> {
            final String authorName = randomKey();
            final Review updatedReview =
                    client().executeBlocking(ReviewUpdateCommand.of(review, SetAuthorName.of(authorName)));

            assertThat(updatedReview.getAuthorName()).isEqualTo(authorName);

            return updatedReview;
        });
    }

    @Test
    public void setCustomer() {
        withCustomer(client(), (Customer customer) -> {
            withUpdateableReview(client(), (Review review) -> {
                final Review updatedReview =
                        client().executeBlocking(ReviewUpdateCommand.of(review, SetCustomer.of(customer)));

                assertThat(updatedReview.getCustomer()).isEqualTo(customer.toReference());

                return updatedReview;
            });
        });
    }

    @Test
    public void setRating() {
        withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            withUpdateableReview(client(),builder -> builder.target(channel), (Review review) -> {
                final int rating = 44;
                final Review updatedReview =
                        client().executeBlocking(ReviewUpdateCommand.of(review, SetRating.of(rating)));

                assertThat(updatedReview.getRating()).isEqualTo(rating);

                //you can observe a message
                assertEventually(() -> {
                    final Query<ReviewRatingSetMessage> messageQuery = MessageQuery.of()
                            .withPredicates(m -> m.resource().is(review))
                            .forMessageType(ReviewRatingSetMessage.MESSAGE_HINT);
                    final PagedQueryResult<ReviewRatingSetMessage> messages = client().executeBlocking(messageQuery);
                    assertThat(messages.head()).isPresent();
                    final ReviewRatingSetMessage reviewRatingSetMessage = messages.head().get();
                    assertThat(reviewRatingSetMessage.getNewRating()).isEqualTo(rating);
                    assertThat(reviewRatingSetMessage.getOldRating()).isEqualTo(100);
                    assertThat(reviewRatingSetMessage.isIncludedInStatistics()).isTrue();
                    assertThat(reviewRatingSetMessage.getTarget()).isEqualTo(channel.toReference());
                });

                return updatedReview;
            });
        });
    }

    @Test
    public void setTarget() {
        withProduct(client(), (Product product) -> {
            withUpdateableReview(client(), (Review review) -> {
                final Review updatedReview =
                        client().executeBlocking(ReviewUpdateCommand.of(review, SetTarget.of(product)));

                assertThat(updatedReview.getTarget()).isEqualTo(product.toReference());

                return updatedReview;
            });

        });
    }

    @Test
    public void setText() {
        withUpdateableReview(client(), (Review review) -> {
            final String text = randomKey();
            final Review updatedReview =
                    client().executeBlocking(ReviewUpdateCommand.of(review, SetText.of(text)));

            assertThat(updatedReview.getText()).isEqualTo(text);

            return updatedReview;
        });
    }

    @Test
    public void setTitle() {
        withUpdateableReview(client(), (Review review) -> {
            final String title = randomKey();
            final Review updatedReview =
                    client().executeBlocking(ReviewUpdateCommand.of(review, SetTitle.of(title)));

            assertThat(updatedReview.getTitle()).isEqualTo(title);

            return updatedReview;
        });
    }

    @Test
    public void setKey() {
        withUpdateableReview(client(), (Review review) -> {
            final String key = randomKey();
            final Review updatedReview =
                    client().executeBlocking(ReviewUpdateCommand.of(review, SetKey.of(key)));

            assertThat(updatedReview.getKey()).isEqualTo(key);

            return updatedReview;
        });
    }

    @Test
    public void transitionState() {
        withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            withStateByBuilder(client(), stateBuilder -> stateBuilder.initial(true).type(REVIEW_STATE).roles(REVIEW_INCLUDED_IN_STATISTICS), newState -> {
                withStateByBuilder(client(), stateBuilder -> stateBuilder.type(REVIEW_STATE).transitions(singleton(newState.toReference())).roles(REVIEW_INCLUDED_IN_STATISTICS), oldState -> {
                    withUpdateableReview(client(), reviewBuilder -> reviewBuilder.state(oldState).target(channel), (Review review) -> {
                        final Review updatedReview =
                                client().executeBlocking(ReviewUpdateCommand.of(review, TransitionState.of(newState)));

                        assertThat(updatedReview.getState()).isEqualTo(newState.toReference());

                        //you can observe a message
                        assertEventually(() -> {
                            final Query<ReviewStateTransitionMessage> messageQuery = MessageQuery.of()
                                    .withPredicates(m -> m.resource().is(review))
                                    .forMessageType(ReviewStateTransitionMessage.MESSAGE_HINT);
                            final Optional<ReviewStateTransitionMessage> messageOptional =
                                    client().executeBlocking(messageQuery).head();
                            assertThat(messageOptional).isPresent();
                            final ReviewStateTransitionMessage message = messageOptional.get();
                            assertThat(message.getOldState()).isEqualTo(oldState.toReference());
                            assertThat(message.getNewState()).isEqualTo(newState.toReference());
                            assertThat(message.getTarget()).isEqualTo(channel.toReference());
                            assertThat(message.getOldIncludedInStatistics()).isTrue();
                            assertThat(message.getNewIncludedInStatistics()).isTrue();
                        });

                        return updatedReview;
                    });
                });
            });
        });
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
            withUpdateableReview(client(), (Review review) -> {
                final SetCustomType updateAction = SetCustomType
                        .ofTypeIdAndObjects(type.getId(), singletonMap(STRING_FIELD_NAME, "foo"));
                final Review updatedReview = client().executeBlocking(ReviewUpdateCommand.of(review, updateAction));

                assertThat(updatedReview.getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo("foo");

                final Review updatedReview2 = client().executeBlocking(ReviewUpdateCommand.of(updatedReview,
                        SetCustomField.ofObject(STRING_FIELD_NAME, "bar")));

                assertThat(updatedReview2.getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo("bar");

                return updatedReview2;
            });
            return type;
        });

    }

    @Test
    public void setLocale() {
        withUpdateableReview(client(), builder -> builder.locale(Locale.GERMAN), (Review review) -> {
            final Review updatedReview =
                    client().executeBlocking(ReviewUpdateCommand.of(review, SetLocale.of(Locale.ENGLISH)));

            assertThat(updatedReview.getLocale()).isEqualTo(Locale.ENGLISH);

            return updatedReview;
        });
    }
}