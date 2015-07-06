package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddDiscountCode;
import io.sphere.sdk.carts.commands.updateactions.RemoveDiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeReference;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.carts.CartFixtures.withCartAndDiscountCode;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomerAndCart;
import static org.assertj.core.api.Assertions.*;

public class CartQueryTest extends IntegrationTest {
    @Test
    public void expandDiscountCodeReference() throws Exception {
        withCartAndDiscountCode(client(), (cart, discountCode) -> {
            //addDiscountCode
            final Cart cartWithCode = execute(CartUpdateCommand.of(cart, AddDiscountCode.of(discountCode)));

            final CartQuery query = CartQuery.of()
                    .withPredicate(m -> m.id().is(cart.getId()))
                    .withExpansionPaths(m -> m.discountCodes());
            final Cart loadedCart = execute(query).head().get();


            final DiscountCodeReference discountCodeReference = loadedCart.getDiscountCodes().get(0);
            assertThat(discountCodeReference.getDiscountCode()).isEqualTo(discountCode.toReference());
            assertThat(discountCodeReference.getDiscountCode().getObj()).isPresent();

            //clean up
            final Cart updatedCart = execute(CartUpdateCommand.of(cartWithCode, RemoveDiscountCode.of(discountCode)));
            assertThat(updatedCart.getDiscountCodes()).isEmpty();

            return updatedCart;
        });
    }

    @Test
    public void byCustomerIdAndByCustomerEmail() throws Exception {
        withCustomerAndCart(client(), (customer, cart) -> {
            final Cart loadedCart = execute(CartQuery.of()
                            .withPredicate(
                                    m -> m.customerId().is(customer.getId())
                                            .and(m.customerEmail().is(customer.getEmail())))
            ).head().get();
            assertThat(loadedCart.getCustomerId()).contains(customer.getId());
        });
    }
}