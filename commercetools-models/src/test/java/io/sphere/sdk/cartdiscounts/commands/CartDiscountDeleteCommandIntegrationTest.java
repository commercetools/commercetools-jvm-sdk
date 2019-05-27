package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountDraft;
import io.sphere.sdk.cartdiscounts.CartDiscountDraftBuilder;
import io.sphere.sdk.cartdiscounts.CartDiscountValue;
import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.cartdiscounts.LineItemsTarget;
import io.sphere.sdk.cartdiscounts.queries.CartDiscountQuery;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.EUR;
import static io.sphere.sdk.test.SphereTestUtils.en;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class CartDiscountDeleteCommandIntegrationTest extends IntegrationTest {

    @Test
    public void deleteCartDiscountById() {
        final CartDiscountDraft cartDiscountDraft =
                CartDiscountDraftBuilder.of(en("name"),
                        CartPredicate.of("1 = 1"),
                        CartDiscountValue.ofAbsolute(MoneyImpl.of(10, EUR)),
                        LineItemsTarget.of("1 = 1"), "0.001", false)
                        .build();

        final CartDiscount newCartDiscount = client().executeBlocking(CartDiscountCreateCommand.of(cartDiscountDraft));
        client().executeBlocking(CartDiscountDeleteCommand.of(newCartDiscount));
        assertThat(client().executeBlocking(CartDiscountQuery.of().byId(newCartDiscount.getId())).head()).isEmpty();
    }

    @Test
    public void deleteCartDiscountByKey() {
        final String key = randomKey();
        final CartDiscountDraft cartDiscountDraft =
                CartDiscountDraftBuilder.of(en("name"),
                        CartPredicate.of("1 = 1"),
                        CartDiscountValue.ofAbsolute(MoneyImpl.of(10, EUR)),
                        LineItemsTarget.of("1 = 1"), "0.001", false)
                        .key(key)
                        .build();

        final CartDiscount newCartDiscount = client().executeBlocking(CartDiscountCreateCommand.of(cartDiscountDraft));
        client().executeBlocking(CartDiscountDeleteCommand.ofKey(key, newCartDiscount.getVersion()));
        assertThat(client().executeBlocking(CartDiscountQuery.of().byKey(newCartDiscount.getKey())).head()).isEmpty();
    }
}
