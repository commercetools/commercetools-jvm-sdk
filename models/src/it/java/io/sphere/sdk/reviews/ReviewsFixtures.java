package io.sphere.sdk.reviews;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.reviews.commands.ReviewCreateCommand;
import io.sphere.sdk.test.SphereTestUtils;
import java.util.Random;

import java.util.function.Consumer;

/**
 * Created by njacinto on 20/04/15.
 */
public class ReviewsFixtures {
    public static final Random RANDOM_GENERATOR = new Random(System.currentTimeMillis());


    public static void withReview(final TestClient client, final Product product, final Customer customer,
                                  final Consumer<Review> c) {
        withReview(client, product.getId(), customer.getId(), c);
    }

    public static void withReview(final TestClient client, final String productId, final String customerId,
                                             final Consumer<Review> c) {
        Review review = client.execute(new ReviewCreateCommand(ReviewDraft.of(productId, customerId)
                .withAuthorName(SphereTestUtils.randomString()).withTitle(SphereTestUtils.randomString())
                .withText(SphereTestUtils.randomString()).withScore(RANDOM_GENERATOR.nextFloat())));
        c.accept(review);
    }

}
