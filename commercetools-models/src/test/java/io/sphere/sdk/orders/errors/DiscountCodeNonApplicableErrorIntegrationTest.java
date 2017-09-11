package io.sphere.sdk.orders.errors;

import io.sphere.sdk.cartdiscounts.CartDiscountFixtures;
import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddDiscountCode;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeDraft;
import io.sphere.sdk.discountcodes.DiscountCodeFixtures;
import io.sphere.sdk.orders.commands.OrderFromCartCreateCommand;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import java.util.function.Consumer;

import static io.sphere.sdk.carts.CartFixtures.withFilledCart;
import static io.sphere.sdk.discountcodes.DiscountCodeState.DOES_NOT_MATCH_CART;
import static io.sphere.sdk.test.SphereTestUtils.EUR;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DiscountCodeNonApplicableErrorIntegrationTest extends IntegrationTest {
    @Test
    public void order() {
        withDiscountCodeOfPredicate("totalPrice > \"800.00 EUR\"", (DiscountCode discountCode) ->
                withFilledCart(client(), cart -> {
                    final Cart cartWithDiscountCode =
                            client().executeBlocking(CartUpdateCommand.of(cart, AddDiscountCode.of(discountCode)));
                    assertThat(cartWithDiscountCode.getTotalPrice()).isEqualTo(MoneyImpl.of("37.02", EUR));
                    assertThat(cartWithDiscountCode.getDiscountCodes()).hasSize(1);
                    assertThat(cartWithDiscountCode.getDiscountCodes().get(0).getState())
                            .as("the discount (code) does not match the cart")
                            .isEqualTo(DOES_NOT_MATCH_CART);

                    final Throwable throwable = catchThrowable(() ->
                            client().executeBlocking(OrderFromCartCreateCommand.of(cartWithDiscountCode)));
                    assertThat(throwable).isInstanceOf(ErrorResponseException.class);
                    final ErrorResponseException e = (ErrorResponseException) throwable;
                    assertThat(e.hasErrorCode(DiscountCodeNonApplicableError.CODE)).isTrue();
                }));
    }

    private void withDiscountCodeOfPredicate(final String predicate, final Consumer<DiscountCode> consumer) {
        CartDiscountFixtures.withCartDiscount(client(),
                builder -> builder.cartPredicate(CartPredicate.of(predicate))
                        .requiresDiscountCode(true),
                cartDiscount -> {
                    final DiscountCodeDraft draft = DiscountCodeDraft.of(randomKey(), cartDiscount);
                    DiscountCodeFixtures.withDiscountCode(client(), draft, discountCode -> {
                        consumer.accept(discountCode);
                    });
                });
    }

}