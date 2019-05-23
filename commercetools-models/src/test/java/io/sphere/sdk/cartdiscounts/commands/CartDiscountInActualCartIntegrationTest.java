package io.sphere.sdk.cartdiscounts.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.cartdiscounts.*;
import io.sphere.sdk.cartdiscounts.queries.CartDiscountByIdGet;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.carts.commands.CartDeleteCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.Recalculate;
import io.sphere.sdk.carts.commands.updateactions.SetCustomerId;
import io.sphere.sdk.carts.commands.updateactions.SetShippingAddress;
import io.sphere.sdk.carts.commands.updateactions.SetShippingMethod;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.carts.queries.CartQuery;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.javamoney.moneta.function.MonetaryQueries;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static io.sphere.sdk.carts.CartFixtures.*;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.*;

public class CartDiscountInActualCartIntegrationTest extends IntegrationTest {
    @Test
    public void createACartDiscountAndGetTheDiscountedValueFromACart() throws Exception {
        withCustomer(client(), customer -> {
                    withFilledCart(client(), cart -> {
                        final Cart cartWithCustomer = client().executeBlocking(CartUpdateCommand.of(cart, SetCustomerId.ofCustomer(customer)));

                        final LineItem undiscountedLineItem = cart.getLineItems().get(0);
                        assertThat(undiscountedLineItem.getDiscountedPricePerQuantity()).isEmpty();
                        final MonetaryAmount oldLineItemTotalPrice = MoneyImpl.ofCents(3702, EUR);
                        assertThat(undiscountedLineItem.getTotalPrice()).isEqualTo(oldLineItemTotalPrice);


                        final LocalizedString name = en("di");
                        final CartPredicate cartPredicate = CartPredicate.of(format("customer.id = \"%s\"", customer.getId()));
                        final MonetaryAmount discountAmount = EURO_1;
                        final AbsoluteCartDiscountValue value = CartDiscountValue.ofAbsolute(discountAmount);
                        final CartDiscountDraft discountDraft = CartDiscountDraftBuilder.of(name, cartPredicate, value, LineItemsTarget.ofAll(), randomSortOrder(), false)
                                .build();
                        final CartDiscount cartDiscount = client().executeBlocking(CartDiscountCreateCommand.of(discountDraft));
                        final Cart cartIncludingDiscount = client().executeBlocking(CartUpdateCommand.of(cartWithCustomer, Recalculate.of()));

                        assertThat(cartIncludingDiscount.getTotalPrice()).
                                isEqualTo(cart.getTotalPrice().subtract(discountAmount));


                        final LineItem lineItemWithDiscount = cartIncludingDiscount.getLineItems().get(0);
                        final List<DiscountedLineItemPriceForQuantity> discountedPricePerQuantity =
                                lineItemWithDiscount.getDiscountedPricePerQuantity()
                                        .stream()
                                        .sorted(Comparator.comparing(x -> x.getQuantity()))
                                        .collect(toList());
                        assertThat(discountedPricePerQuantity).hasSize(2);

                        assertThat(discountedPricePerQuantity.get(0).getQuantity()).isEqualTo(1);
                        final DiscountedLineItemPrice discountedLineItemPrice1 = discountedPricePerQuantity.get(0).getDiscountedPrice();
                        assertThat(discountedLineItemPrice1.getValue()).isEqualTo(MoneyImpl.ofCents(1200, EUR));
                        assertThat(discountedLineItemPrice1.getIncludedDiscounts()).hasSize(1);
                        assertThat(discountedLineItemPrice1.getIncludedDiscounts().get(0).getDiscount()).isEqualTo(cartDiscount.toReference());
                        assertThat(discountedLineItemPrice1.getIncludedDiscounts().get(0).getDiscountedAmount()).isEqualTo(MoneyImpl.ofCents(34, EUR));

                        assertThat(discountedPricePerQuantity.get(1).getQuantity()).isEqualTo(2);
                        final DiscountedLineItemPrice discountedLineItemPrice2 = discountedPricePerQuantity.get(1).getDiscountedPrice();
                        assertThat(discountedLineItemPrice2.getValue()).isEqualTo(MoneyImpl.ofCents(1201, EUR));
                        assertThat(discountedLineItemPrice2.getIncludedDiscounts()).hasSize(1);
                        assertThat(discountedLineItemPrice2.getIncludedDiscounts().get(0).getDiscount()).isEqualTo(cartDiscount.toReference());
                        assertThat(discountedLineItemPrice2.getIncludedDiscounts().get(0).getDiscountedAmount()).isEqualTo(MoneyImpl.ofCents(33, EUR));
                        assertThat(lineItemWithDiscount.getTotalPrice()).isEqualTo(oldLineItemTotalPrice.subtract(discountAmount));
                        assertThat(lineItemWithDiscount.getQuantity()).as("lineItem quantity").isEqualTo(3);


                        final DiscountedLineItemPrice discountedPrice = lineItemWithDiscount.getDiscountedPricePerQuantity().get(0).getDiscountedPrice();
                        final CartQuery cartQuery = CartQuery.of()
                                .plusPredicates(m -> m.lineItems().discountedPricePerQuantity().discountedPrice().includedDiscounts().discount().is(cartDiscount))
                                .plusPredicates(m -> m.lineItems().discountedPricePerQuantity().discountedPrice().includedDiscounts().discountedAmount().centAmount().is(discountedPrice.getIncludedDiscounts().get(0).getDiscountedAmount().query(MonetaryQueries.convertMinorPart())))
                                .plusPredicates(m -> m.lineItems().discountedPricePerQuantity().discountedPrice().value().centAmount().is(discountedPrice.getValue().query(MonetaryQueries.convertMinorPart())))
                                .plusPredicates(m -> m.lineItems().discountedPricePerQuantity().quantity().is(lineItemWithDiscount.getDiscountedPricePerQuantity().get(0).getQuantity()))
                                .plusPredicates(m -> m.id().is(cart.getId()));
                        assertThat(client().executeBlocking(cartQuery).head().get().getId()).as("line item queries").isEqualTo(cart.getId());


                        //Cart Discount knows cart
                        assertEventually(() -> {
                            final CartDiscountByIdGet discountByIdGet =
                                    CartDiscountByIdGet.of(cartDiscount).withExpansionPaths(m -> m.references());
                            final CartDiscount loadedCartDiscount = client().executeBlocking(discountByIdGet);
                            assertThat(loadedCartDiscount.getReferences()).hasSize(1);
                            assertThat(loadedCartDiscount.getReferences().get(0)).is(expanded());
                        });

                        //clean up
                        client().executeBlocking(CartDiscountDeleteCommand.of(cartDiscount));
                    });
                }
        );
    }

    @Test
    public void shippingDiscount() {
        withShippingMethodForGermany(client(), shippingMethod -> {
            withCustomerAndFilledCart(client(), (customer, cart) -> {
                client().executeBlocking(CartUpdateCommand.of(cart, SetShippingMethod.of(shippingMethod.toResourceIdentifier())));
                CartDiscountFixtures.withCartDiscount(client(), builder -> builder
                        .value(RelativeCartDiscountValue.of(10000))
                        .target(ShippingCostTarget.of())
                        .cartPredicate(CartPredicate.of("customer.id =\"" + customer.getId() + "\"")), cartDiscount -> {
                    assertEventually(() -> {
                        final Versioned<Cart> cartVersion = client().executeBlocking(CartByIdGet.of(cart));
                        final CartUpdateCommand cmd =
                                CartUpdateCommand.of(cartVersion, Recalculate.of().withUpdateProductData(true))
                                .withExpansionPaths(c -> c.shippingInfo().discountedPrice().includedDiscounts().discount());
                        final Cart loadedCart = client().executeBlocking(cmd);
                        final DiscountedLineItemPrice discountedPrice = loadedCart.getShippingInfo().getDiscountedPrice();
                        assertThat(discountedPrice).isNotNull();
                        final MonetaryAmount value = discountedPrice.getValue();
                        assertThat(value).isEqualTo(MoneyImpl.of(0, EUR));
                        final Reference<CartDiscount> discount = discountedPrice.getIncludedDiscounts().get(0).getDiscount();
                        assertThat(discount.getObj()).isNotNull().isEqualTo(cartDiscount);
                    });
                });
                final Cart cartToDelete = client().executeBlocking(CartByIdGet.of(cart));
                client().executeBlocking(CartDeleteCommand.of(cartToDelete));
            });
        });
    }

    @Test
    public void multiBuyDiscountDiscount() {
        final Long discountedQuantity = 2L;
        withShippingMethodForGermany(client(), shippingMethod -> {
            withCustomerAndFilledCart(client(), (customer, cart) -> {
                client().executeBlocking(CartUpdateCommand.of(cart, SetShippingMethod.of(shippingMethod.toResourceIdentifier())));
                CartDiscountFixtures.withCartDiscount(client(), builder -> builder
                        .value(RelativeCartDiscountValue.of(10000))
                        .target(MultiBuyLineItemsTarget.of("1 = 1", 3L, discountedQuantity, SelectionMode.CHEAPEST))
                        .cartPredicate(CartPredicate.of("customer.id =\"" + customer.getId() + "\"")), cartDiscount -> {
                    assertEventually(() -> {
                        final Versioned<Cart> cartVersion = client().executeBlocking(CartByIdGet.of(cart));
                        final CartUpdateCommand cmd =
                                CartUpdateCommand.of(cartVersion, Recalculate.of().withUpdateProductData(true))
                                        .withExpansionPaths(c -> c.shippingInfo().discountedPrice().includedDiscounts().discount());
                        final Cart loadedCart = client().executeBlocking(cmd);
                        final List<DiscountedLineItemPriceForQuantity> discountedPricePerQuantity =
                                loadedCart.getLineItems().get(0).getDiscountedPricePerQuantity();
                        assertThat(discountedPricePerQuantity).hasSize(2);

                        final DiscountedLineItemPriceForQuantity discountedLineItemPriceForQuantity = discountedPricePerQuantity.get(0);
                        assertThat(discountedLineItemPriceForQuantity.getQuantity()).isEqualTo(discountedQuantity);
                        assertThat(discountedLineItemPriceForQuantity.getDiscountedPrice().getValue()).isEqualTo(MoneyImpl.of(0, EUR));

                        final DiscountedLineItemPriceForQuantity remainingLineItemPriceForQuantity = discountedPricePerQuantity.get(1);
                        assertThat(remainingLineItemPriceForQuantity.getQuantity()).isEqualTo(1L);
                        final MonetaryAmount totalPrice = loadedCart.getLineItems().get(0).getTotalPrice();
                        assertThat(remainingLineItemPriceForQuantity.getDiscountedPrice().getValue()).isEqualTo(totalPrice);
                    });
                });
                final Cart cartToDelete = client().executeBlocking(CartByIdGet.of(cart));
                client().executeBlocking(CartDeleteCommand.of(cartToDelete));
            });
        });
    }

    @Test
    public void multiBuyDiscountCustomLineItem() {
        final Long discountedQuantity = 2L;
        final RelativeCartDiscountValue relativeCartDiscountValue = RelativeCartDiscountValue.of(1000);
        withShippingMethodForGermany(client(), shippingMethod -> {
            withCustomer(client(), customer -> {
                withCartHavingDiscountedCustomLineItem(client(),relativeCartDiscountValue, cart -> {
                    client().executeBlocking(CartUpdateCommand.of(cart, Arrays.asList(SetShippingAddress.of(Address.of(CountryCode.DE)),SetShippingMethod.of(shippingMethod.toResourceIdentifier()))));
                    withCartDiscount(client(), builder -> builder
                            .value(relativeCartDiscountValue)
                            .target(MultiBuyCustomLineItemsTarget.of("1 = 1", 3L, discountedQuantity, SelectionMode.CHEAPEST))
                            .cartPredicate(CartPredicate.of("customer.id =\"" + customer.getId() + "\"")), cartDiscount -> {

                        assertEventually(() -> {
                            final Versioned<Cart> cartVersion = client().executeBlocking(CartByIdGet.of(cart));
                            final CartUpdateCommand cmd =
                                    CartUpdateCommand.of(cartVersion, Recalculate.of().withUpdateProductData(true))
                                            .withExpansionPaths(c -> c.shippingInfo().discountedPrice().includedDiscounts().discount());
                            final Cart loadedCart = client().executeBlocking(cmd);
                            final List<DiscountedLineItemPriceForQuantity> discountedPricePerQuantity =
                                    loadedCart.getCustomLineItems().get(0).getDiscountedPricePerQuantity();
                            assertThat(discountedPricePerQuantity).hasSize(1);

                            final DiscountedLineItemPriceForQuantity discountedLineItemPriceForQuantity = discountedPricePerQuantity.get(0);
                            assertThat(discountedLineItemPriceForQuantity.getQuantity()).isEqualTo(5);//was 2
                            assertThat(discountedLineItemPriceForQuantity.getDiscountedPrice().getValue()).isEqualTo(MoneyImpl.ofCents(2115, EUR));
                        });

                    });
                    final Cart cartToDelete = client().executeBlocking(CartByIdGet.of(cart));
                    return cartToDelete;
                });
            });
        });
    }
}
