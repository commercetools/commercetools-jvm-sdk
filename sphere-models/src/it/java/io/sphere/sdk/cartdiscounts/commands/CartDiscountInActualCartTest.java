package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.*;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.Recalculate;
import io.sphere.sdk.carts.commands.updateactions.SetCustomerId;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.data.Offset;
import org.junit.Test;

import static io.sphere.sdk.carts.CartFixtures.withFilledCart;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class CartDiscountInActualCartTest extends IntegrationTest {
    @Test
    public void createACartDiscountAndGetTheDiscountedValueFromACart() throws Exception {
        withCustomer(client(), customer ->
            withFilledCart(client(), cart -> {
                final Cart cartWithCustomer = execute(CartUpdateCommand.of(cart, SetCustomerId.ofCustomer(customer)));

                final LocalizedString name = en("di");
                final CartDiscountPredicate cartPredicate = CartDiscountPredicate.of(format("customer.id = \"%s\"", customer.getId()));
                final AbsoluteCartDiscountValue value = CartDiscountValue.ofAbsolute(EURO_1);
                final CartDiscountDraft discountDraft = CartDiscountDraftBuilder.of(name, cartPredicate, value, LineItemsTarget.ofAll(), randomSortOrder(), false)
                        .build();
                final CartDiscount cartDiscount = execute(CartDiscountCreateCommand.of(discountDraft));
                final Cart cartIncludingDiscount = execute(CartUpdateCommand.of(cartWithCustomer, Recalculate.of()));

                assertThat(cartIncludingDiscount.getTotalPrice().getNumber().doubleValueExact()).
                        isEqualTo(cart.getTotalPrice().subtract(EURO_1).getNumber().doubleValueExact(), Offset.offset(0.05));
                final Reference<CartDiscount> discount = cartIncludingDiscount.getLineItems().get(0).getDiscountedPrice().getIncludedDiscounts().get(0).getDiscount();
                assertThat(discount.referencesSameResource(cartDiscount)).isTrue();

                //clean up
                execute(CartDiscountDeleteCommand.of(cartDiscount));
            })
        );
    }
}
