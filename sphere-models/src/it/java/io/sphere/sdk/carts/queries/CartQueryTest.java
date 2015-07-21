package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddDiscountCode;
import io.sphere.sdk.carts.commands.updateactions.RemoveDiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import javax.money.MonetaryAmount;

import static io.sphere.sdk.carts.CartFixtures.*;
import static io.sphere.sdk.customers.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class CartQueryTest extends IntegrationTest {
    @Test
    public void expandDiscountCodeReference() throws Exception {
        withCartAndDiscountCode(client(), (cart, discountCode) -> {
            //addDiscountCode
            final Cart cartWithCode = execute(CartUpdateCommand.of(cart, AddDiscountCode.of(discountCode)));

            final CartQuery query = CartQuery.of()
                    .withPredicate(m -> m.id().is(cart.getId()))
                    .withExpansionPaths(m -> m.discountCodes().discountCode());
            final Cart loadedCart = execute(query).head().get();


            final DiscountCodeInfo discountCodeInfo = loadedCart.getDiscountCodes().get(0);
            assertThat(discountCodeInfo.getDiscountCode()).isEqualTo(discountCode.toReference());
            assertThat(discountCodeInfo.getDiscountCode().getObj()).isNotNull();

            //clean up
            final Cart updatedCart = execute(CartUpdateCommand.of(cartWithCode, RemoveDiscountCode.of(discountCode)));
            assertThat(updatedCart.getDiscountCodes()).isEmpty();

            return updatedCart;
        });
    }

    @Test
    public void byCustomerIdAndByCustomerEmail() throws Exception {
        withCustomerAndCart(client(), (customer, cart) -> {
            final CartQuery cartQuery = CartQuery.of()
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1)
                    .withPredicate(
                            m -> m.customerId().is(customer.getId())
                                    .and(m.customerEmail().is(customer.getEmail())));
            final Cart loadedCart = execute(cartQuery
            ).head().get();
            assertThat(loadedCart.getCustomerId()).contains(customer.getId());
        });
    }

    @Test
    public void queryTotalPrice() throws Exception {
        withFilledCart(client(), cart -> {
            final long centAmount = centAmountOf(cart.getTotalPrice());
            final Cart loadedCart = execute(CartQuery.of()
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1)
                    .withPredicate(
                            m -> m.totalPrice().centAmount().isGreaterThan(centAmount - 1)
                                    .and(m.totalPrice().centAmount().isLessThan(centAmount + 1)
                                            .and(m.totalPrice().currencyCode().is(EUR))
                                    ))).head().get();
            assertThat(loadedCart.getId()).isEqualTo(cart.getId());
        });
    }

    private long centAmountOf(final MonetaryAmount totalPrice) {
        return totalPrice.multiply(100).getNumber().longValueExact();
    }

    @Test
    public void queryTaxedPrice() throws Exception {
        withFilledCart(client(), cart -> {
            final Cart loadedCart = execute(CartQuery.of()
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1)
                    .withPredicate(m -> m.taxedPrice().isPresent()
                            .and(m.taxedPrice().totalNet().centAmount().is(centAmountOf(cart.getTaxedPrice().get().getTotalNet())))
                            .and(m.taxedPrice().totalGross().centAmount().is(centAmountOf(cart.getTaxedPrice().get().getTotalGross())))
                    )).head().get();
            assertThat(loadedCart.getId()).isEqualTo(cart.getId());
        });
    }
}