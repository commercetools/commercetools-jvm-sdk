package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Locale;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.withPersistentCartDiscount;
import static org.assertj.core.api.Assertions.*;

public class CartDiscountQueryIntegrationTest extends IntegrationTest {
    @Test
    public void queryById() {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final QueryPredicate<CartDiscount> knownId = CartDiscountQueryModel.of().id().is(cartDiscount.getId());
            final PagedQueryResult<CartDiscount> result = client().executeBlocking(CartDiscountQuery.of().withPredicates(knownId));
            assertThat(result.getResults()).hasSize(1);
            assertThat(result.getResults().get(0).getId()).isEqualTo(cartDiscount.getId());
        });
    }

    @Test
    public void queryByEngName() {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final QueryPredicate<CartDiscount> nameQuery =
                    CartDiscountQueryModel.of().name().lang(Locale.ENGLISH).is(cartDiscount.getName().get(Locale.ENGLISH));
            final PagedQueryResult<CartDiscount> result = client().executeBlocking(CartDiscountQuery.of().withPredicates(nameQuery));
            assertThat(result.getResults()).hasSize(1);
            assertThat(result.getResults().get(0).getName()).isEqualTo(cartDiscount.getName());
        });
    }

    @Test
    public void queryByKey() {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final QueryPredicate<CartDiscount> keyQuery = CartDiscountQueryModel.of().key().is(cartDiscount.getKey());
            final PagedQueryResult<CartDiscount> result = client().executeBlocking(CartDiscountQuery.of().withPredicates(keyQuery));
            assertThat(result.getResults()).hasSize(1);
            assertThat(result.getResults().get(0).getKey()).isEqualTo(cartDiscount.getKey());
        });
    }
}
