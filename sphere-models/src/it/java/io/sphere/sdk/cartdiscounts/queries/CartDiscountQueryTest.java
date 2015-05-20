package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.withPersistentCartDiscount;
import static org.assertj.core.api.Assertions.*;

public class CartDiscountQueryTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final QueryPredicate<CartDiscount> knownId = CartDiscountQuery.model().id().is(cartDiscount.getId());
            final PagedQueryResult<CartDiscount> result = execute(CartDiscountQuery.of().withPredicate(knownId));
            assertThat(result.getResults()).hasSize(1);
            assertThat(result.getResults().get(0).getId()).isEqualTo(cartDiscount.getId());
        });
    }
}