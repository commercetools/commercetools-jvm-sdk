package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.ReviewDraftBuilder;
import io.sphere.sdk.reviews.queries.ReviewByKeyGet;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateDraft;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.JsonNodeReferenceResolver;
import io.sphere.sdk.types.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Locale;
import java.util.function.Consumer;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.states.StateFixtures.withState;
import static io.sphere.sdk.test.SphereTestUtils.draftFromJsonResource;
import static org.assertj.core.api.Assertions.assertThat;

public class ReviewCreateCommandTest extends IntegrationTest {
    @Before
    public void clean() throws Exception {
        final Review review = client().executeBlocking(ReviewByKeyGet.of("review1"));
        if (review != null) {
            client().executeBlocking(ReviewDeleteCommand.of(review));
        }
    }

    @Test
    public void createByCode() {
        withType(client(), (Type type) -> {
            withCustomer(client(), (Customer customer) -> {
                withProduct(client(), (Product product) -> {
                    withState(client(), StateDraft.of("initial-review-state", StateType.REVIEW_STATE), (State state) -> {
                        final CustomFieldsDraft extraFields = CustomFieldsDraft.ofTypeKeyAndObjects(type.getKey(),
                                Collections.singletonMap("screenshotUrls",
                                        Collections.singleton("http://www.commercetools.com/assets/img/CT-logo.svg")));
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
                                .containsExactly("http://www.commercetools.com/assets/img/CT-logo.svg");
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
                withState(client(), StateDraft.of("initial-review-state", StateType.REVIEW_STATE), (State state) -> {
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