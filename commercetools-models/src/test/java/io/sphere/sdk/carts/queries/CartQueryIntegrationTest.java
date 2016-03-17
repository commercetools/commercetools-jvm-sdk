package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddDiscountCode;
import io.sphere.sdk.carts.commands.updateactions.RemoveDiscountCode;
import io.sphere.sdk.carts.commands.updateactions.SetShippingMethod;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.orders.OrderFixtures;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import net.jcip.annotations.NotThreadSafe;
import org.javamoney.moneta.function.MonetaryUtil;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Locale;

import static io.sphere.sdk.carts.CartFixtures.*;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomerAndCart;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static io.sphere.sdk.test.SphereTestUtils.ENGLISH;
import static io.sphere.sdk.test.SphereTestUtils.EUR;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class CartQueryIntegrationTest extends IntegrationTest {
    @Test
    public void expandDiscountCodeReference() throws Exception {
        withCartAndDiscountCode(client(), (cart, discountCode) -> {
            //addDiscountCode
            final Cart cartWithCode = client().executeBlocking(CartUpdateCommand.of(cart, AddDiscountCode.of(discountCode)));

            final CartQuery query = CartQuery.of()
                    .withPredicates(m -> m.id().is(cart.getId()))
                    .withExpansionPaths(m -> m.discountCodes().discountCode());
            final Cart loadedCart = client().executeBlocking(query).head().get();


            final DiscountCodeInfo discountCodeInfo = loadedCart.getDiscountCodes().get(0);
            assertThat(discountCodeInfo.getDiscountCode()).isEqualTo(discountCode.toReference());
            assertThat(discountCodeInfo.getDiscountCode().getObj()).isNotNull();

            //query cart by discountCodes
            final CartQuery cartQuery = CartQuery.of()
                    .withPredicates(m -> m.discountCodes().discountCode().is(discountCode))
                    .plusPredicates(m -> m.is(cart));
            assertThat(client().executeBlocking(cartQuery).head()).contains(loadedCart);

            //clean up
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cartWithCode, RemoveDiscountCode.of(discountCode)));
            assertThat(updatedCart.getDiscountCodes()).isEmpty();

            return updatedCart;
        });
    }

    @Test
    public void byCustomerIdAndByCustomerEmail() throws Exception {
        withCustomerAndCart(client(), (customer, cart) -> {
            final CartQuery cartQuery = CartQuery.of()
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .withPredicates(
                            m -> m.customerId().is(customer.getId())
                                    .and(m.customerEmail().is(customer.getEmail())));
            final Cart loadedCart = client().executeBlocking(cartQuery).head().get();
            assertThat(loadedCart.getCustomerId()).contains(customer.getId());
        });
    }

    @Test
    public void byCustomerEmail() throws Exception {
        withCustomerAndCart(client(), (customer, cart) -> {
            final CartQuery cartQuery = CartQuery.of()
                    .withPredicates(m -> m.customerEmail().is(customer.getEmail()));
            final Cart loadedCart = client().executeBlocking(cartQuery).head().get();
            assertThat(loadedCart.getCustomerId()).contains(customer.getId());
        });
    }

    @Test
    public void queryTotalPrice() throws Exception {
        withFilledCart(client(), cart -> {
            final long centAmount = centAmountOf(cart.getTotalPrice());
            final CartQuery cartQuery = CartQuery.of()
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .withPredicates(
                            m -> m.totalPrice().centAmount().isGreaterThan(centAmount - 1)
                                    .and(m.totalPrice().centAmount().isLessThan(centAmount + 1)
                                                    .and(m.totalPrice().currencyCode().is(EUR))
                                    ));
            final Cart loadedCart = client().executeBlocking(cartQuery).head().get();
            assertThat(loadedCart.getId()).isEqualTo(cart.getId());
        });
    }

    private long centAmountOf(final MonetaryAmount totalPrice) {
        return totalPrice.multiply(100).getNumber().longValueExact();
    }

    @Test
    public void queryTaxedPrice() throws Exception {
        withFilledCart(client(), cart -> {
            final CartQuery cartQuery = CartQuery.of()
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .withPredicates(m -> m.taxedPrice().isPresent()
                                    .and(m.taxedPrice().totalNet().centAmount().is(centAmountOf(cart.getTaxedPrice().getTotalNet())))
                                    .and(m.taxedPrice().totalGross().centAmount().is(centAmountOf(cart.getTaxedPrice().getTotalGross()))
                                    .and(m.id().is(cart.getId())))
                    );
            final Cart loadedCart = client().executeBlocking(cartQuery).head().get();
            assertThat(loadedCart.getId()).isEqualTo(cart.getId());
        });
    }

    @Test
    public void lineItemModel() throws Exception {
        withFilledCart(client(), cart -> {
            final LineItem lineItem = cart.getLineItems().get(0);
            final String englishName = lineItem.getName().get(Locale.ENGLISH);
            final Price price = lineItem.getPrice();
            final Long centAmount = price.getValue().query(MonetaryUtil.minorUnits());
            final ItemState itemState = lineItem.getState().stream().findFirst().get();
            final CartQuery query = CartQuery.of()
                    .withLimit(1L)
                    .withPredicates(m ->
                            m.lineItems().id().is(lineItem.getId())
                                    .and(m.lineItems().quantity().is(lineItem.getQuantity()))
                                    .and(m.lineItems().productId().is(lineItem.getProductId()))
                                    .and(m.lineItems().name().locale(ENGLISH).is(englishName))
                                    .and(m.lineItems().variant().sku().is(lineItem.getVariant().getSku()))
                                    .and(m.lineItems().price().discounted().isNotPresent())
                                    .and(m.lineItems().price().id().is(price.getId()))
                                    .and(m.lineItems().price().value().centAmount().is(centAmount))
                                    .and(m.lineItems().price().country().is(price.getCountry()))
                                    .and(m.lineItems().state().quantity().is(itemState.getQuantity()))
                                    .and(m.lineItems().state().state().is(itemState.getState()))
                    );
            final CartQuery cartQuery = query.plusPredicates(m -> m.id().is(cart.getId()));
            final Cart loadedCart = client().executeBlocking(cartQuery).head().get();
            assertThat(loadedCart.getId()).isEqualTo(cart.getId());
        });
    }


    @Test
    public void shippingMethodQuery() throws Exception {
        withShippingMethodForGermany(client(), shippingMethod -> {
            withCart(client(), createCartWithShippingAddress(client()), cart -> {
                final CartUpdateCommand updateCommand =
                        CartUpdateCommand.of(cart, SetShippingMethod.of(shippingMethod))
                                .plusExpansionPaths(m -> m.shippingInfo().shippingMethod().taxCategory())
                                .plusExpansionPaths(m -> m.shippingInfo().taxCategory());
                final Cart cartWithShippingMethod = client().executeBlocking(updateCommand);

                final CartShippingInfo shippingInfo = cartWithShippingMethod.getShippingInfo();

                final CartQuery query = CartQuery.of()
                        .plusPredicates(m -> m.id().is(cart.getId()))
                        .plusPredicates(m -> m.shippingInfo().shippingMethodName().is(shippingMethod.getName()))
                        .plusPredicates(m -> m.shippingInfo().price().centAmount().is(MoneyImpl.centAmountOf(shippingInfo.getPrice())))
                        .plusPredicates(m -> m.shippingInfo().taxRate().name().is(shippingInfo.getTaxRate().getName()))
                        .plusPredicates(m -> m.shippingInfo().taxRate().includedInPrice().is(shippingInfo.getTaxRate().isIncludedInPrice()))
                        .plusPredicates(m -> m.shippingInfo().taxRate().country().is(shippingInfo.getTaxRate().getCountry()))
                        .plusPredicates(m -> m.shippingInfo().shippingMethod().is(shippingMethod))
                        .plusPredicates(m -> m.shippingInfo().discountedPrice().isNotPresent())
                        ;
                assertThat(client().executeBlocking(query).head().get()).isEqualTo(cartWithShippingMethod);


                return cartWithShippingMethod;
            });
        });
    }

    @Test
    public void cartState() {
        OrderFixtures.withOrder(client(), order -> {
            final CartQuery query = CartQuery.of()
                    .plusPredicates(m -> m.cartState().is(CartState.ORDERED))
                    .plusPredicates(m -> m.is(order.getCart()));
            final List<Cart> results = client().executeBlocking(query).getResults();
            assertThat(results).hasSize(1);
            assertThat(results.get(0).getId()).isEqualTo(order.getCart().getId());
            return order;
        });
    }
}