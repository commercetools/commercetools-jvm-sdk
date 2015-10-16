package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.*;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.Recalculate;
import io.sphere.sdk.carts.commands.updateactions.SetCustomerId;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.assertj.core.data.Offset;
import org.junit.Test;

import javax.money.MonetaryAmount;

import java.util.Comparator;
import java.util.List;

import static io.sphere.sdk.carts.CartFixtures.withFilledCart;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class CartDiscountInActualCartTest extends IntegrationTest {
    @Test
    public void createACartDiscountAndGetTheDiscountedValueFromACart() throws Exception {
        withCustomer(client(), customer ->
            withFilledCart(client(), cart -> {
                final Cart cartWithCustomer = execute(CartUpdateCommand.of(cart, SetCustomerId.ofCustomer(customer)));

                final LineItem undiscountedLineItem = cart.getLineItems().get(0);
                assertThat(undiscountedLineItem.getDiscountedPricePerQuantity()).isEmpty();
                final MonetaryAmount oldLineItemTotalPrice = MoneyImpl.ofCents(3702, EUR);
                assertThat(undiscountedLineItem.getTotalPrice()).isEqualTo(oldLineItemTotalPrice);


                final LocalizedString name = en("di");
                final CartDiscountPredicate cartPredicate = CartDiscountPredicate.of(format("customer.id = \"%s\"", customer.getId()));
                final MonetaryAmount discountAmount = EURO_1;
                final AbsoluteCartDiscountValue value = CartDiscountValue.ofAbsolute(discountAmount);
                final CartDiscountDraft discountDraft = CartDiscountDraftBuilder.of(name, cartPredicate, value, LineItemsTarget.ofAll(), randomSortOrder(), false)
                        .build();
                final CartDiscount cartDiscount = execute(CartDiscountCreateCommand.of(discountDraft));
                final Cart cartIncludingDiscount = execute(CartUpdateCommand.of(cartWithCustomer, Recalculate.of()));

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

                //clean up
                execute(CartDiscountDeleteCommand.of(cartDiscount));
            })
        );
    }
}
