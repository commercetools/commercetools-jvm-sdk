package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.updateactions.*;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.carts.queries.CartQuery;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelFixtures;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.models.*;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.ChangePrice;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.products.commands.updateactions.SetAttribute;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static io.sphere.sdk.carts.CartFixtures.*;
import static io.sphere.sdk.carts.CustomLineItemFixtures.createCustomLineItemDraft;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.payments.PaymentFixtures.withPayment;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.*;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class CartUpdateCommandIntegrationTest extends IntegrationTest {
    public static final int MASTER_VARIANT_ID = 1;

    @Test
    public void addLineItem() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).isEmpty();
            final long quantity = 3;
            final String productId = product.getId();
            final AddLineItem action = AddLineItem.of(productId, MASTER_VARIANT_ID, quantity);

            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
            assertThat(lineItem.getProductSlug()).isEqualTo(product.getMasterData().getStaged().getSlug());
            assertThat(lineItem.getVariant().getIdentifier()).isEqualTo(ByIdVariantIdentifier.of(lineItem.getProductId(), lineItem.getVariant().getId()));
            assertThat(lineItem.getDiscountedPricePerQuantity()).isNotNull().isEmpty();
        });
    }

    @Test
    public void addLineItemWithChannels() throws Exception {
        final Channel inventorySupplyChannel = ChannelFixtures.persistentChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY);
        final Channel distributionChannel = ChannelFixtures.persistentChannelOfRole(client(), ChannelRole.PRODUCT_DISTRIBUTION);

        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final long quantity = 3;
            final String productId = product.getId();
            final AddLineItem action = AddLineItem.of(productId, MASTER_VARIANT_ID, quantity)
                    .withSupplyChannel(inventorySupplyChannel)
                    .withDistributionChannel(distributionChannel);

            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getDistributionChannel()).isEqualTo(distributionChannel.toReference());
            assertThat(lineItem.getSupplyChannel()).isEqualTo(inventorySupplyChannel.toReference());

            //check expansion and query
            final SphereRequest<PagedQueryResult<Cart>> sphereRequest = CartQuery.of()
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .withPredicates(
                            m -> m.lineItems().supplyChannel().is(inventorySupplyChannel)
                                    .and(m.lineItems().distributionChannel().is(distributionChannel)))
                    .plusExpansionPaths(m -> m.lineItems(0).supplyChannel())
                    .plusExpansionPaths(m -> m.lineItems(0).distributionChannel());
            final Cart loadedCart = client().executeBlocking(sphereRequest).head().get();
            final LineItem loadedLineItem = loadedCart.getLineItems().get(0);
            assertThat(loadedLineItem.getDistributionChannel().getObj()).isNotNull();
            assertThat(loadedLineItem.getSupplyChannel().getObj()).isNotNull();
        });
    }

    @Test
    public void removeLineItem() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 3L);

            final Cart cartWith3 = client().executeBlocking(CartUpdateCommand.of(cart, action));
            final LineItem lineItem = cartWith3.getLineItems().get(0);
            assertThat(lineItem.getQuantity()).isEqualTo(3);

            final Cart cartWith2 = client().executeBlocking(CartUpdateCommand.of(cartWith3, RemoveLineItem.of(lineItem, 1L)));
            assertThat(cartWith2.getLineItems().get(0).getQuantity()).isEqualTo(2);

            final Cart cartWith0 = client().executeBlocking(CartUpdateCommand.of(cartWith2, RemoveLineItem.of(lineItem)));
            assertThat(cartWith0.getLineItems()).hasSize(0);
        });
    }

    @Test
    public void changeLineItemQuantity() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 3L);

            final Cart cartWith3 = client().executeBlocking(CartUpdateCommand.of(cart, action));
            final LineItem lineItem = cartWith3.getLineItems().get(0);
            assertThat(lineItem.getQuantity()).isEqualTo(3);

            final Cart cartWith2 = client().executeBlocking(CartUpdateCommand.of(cartWith3, ChangeLineItemQuantity.of(lineItem, 2L)));
            assertThat(cartWith2.getLineItems().get(0).getQuantity()).isEqualTo(2);

            final Cart cartWith0 = client().executeBlocking(CartUpdateCommand.of(cartWith2, ChangeLineItemQuantity.of(lineItem, 0L)));
            assertThat(cartWith0.getLineItems()).hasSize(0);
        });
    }

    @Test
    public void addCustomLineItem() throws Exception {
        withTaxCategory(client(), taxCategory -> {
            final Cart cart = createCartWithCountry(client());
            assertThat(cart.getCustomLineItems()).isEmpty();
            final MonetaryAmount money = MoneyImpl.of("23.50", EUR);
            final String slug = "thing-slug";//you handle to identify the custom line item
            final LocalizedString name = en("thing");
            final long quantity = 5;
            final CustomLineItemDraft item = CustomLineItemDraft.of(name, slug, money, taxCategory, quantity, null);

            final Cart cartWith5 = client().executeBlocking(CartUpdateCommand.of(cart, AddCustomLineItem.of(item)));
            assertThat(cartWith5.getCustomLineItems()).hasSize(1);
            final CustomLineItem customLineItem = cartWith5.getCustomLineItems().get(0);
            assertThat(customLineItem.getMoney()).isEqualTo(money);
            assertThat(customLineItem.getName()).isEqualTo(name);
            assertThat(customLineItem.getQuantity()).isEqualTo(quantity);
            assertThat(customLineItem.getSlug()).isEqualTo(slug);
            final Set<ItemState> state = customLineItem.getState();
            assertThat(state).hasSize(1);
            assertThat(state).extracting("quantity").containsOnly(quantity);
            assertThat(customLineItem.getTaxCategory()).isEqualTo(taxCategory.toReference());

            final CartQuery cartQuery = CartQuery.of()
                    .withPredicates(m -> m.customLineItems().slug().is(customLineItem.getSlug())
                            .and(m.id().is(cart.getId())));
            assertThat(client().executeBlocking(cartQuery).head().get().getId()).isEqualTo(cart.getId());
        });
    }

    @Test
    public void changeCustomLineItemQuantity() throws Exception {
        withTaxCategory(client(), taxCategory -> {
            final Cart cart = createCartWithCountry(client());
            assertThat(cart.getCustomLineItems()).hasSize(0);
            final CustomLineItemDraft draftItem = createCustomLineItemDraft(taxCategory);

            final Cart cartWithCustomLineItem = client().executeBlocking(CartUpdateCommand.of(cart, AddCustomLineItem.of(draftItem)));
            assertThat(cartWithCustomLineItem.getCustomLineItems()).hasSize(1);
            final CustomLineItem customLineItem = cartWithCustomLineItem.getCustomLineItems().get(0);
            assertThat(customLineItem.getQuantity()).isEqualTo(5L);

            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cartWithCustomLineItem, ChangeCustomLineItemQuantity.of(customLineItem, 12L)));
            assertThat(updatedCart.getCustomLineItems().get(0).getQuantity()).isEqualTo(12L);
        });
    }

    @Test
    public void removeCustomLineItem() throws Exception {
        withTaxCategory(client(), taxCategory -> {
            final Cart cart = createCartWithCountry(client());
            assertThat(cart.getCustomLineItems()).hasSize(0);
            final CustomLineItemDraft draftItem = createCustomLineItemDraft(taxCategory);

            final Cart cartWithCustomLineItem = client().executeBlocking(CartUpdateCommand.of(cart, AddCustomLineItem.of(draftItem)));
            assertThat(cartWithCustomLineItem.getCustomLineItems()).hasSize(1);
            final CustomLineItem customLineItem = cartWithCustomLineItem.getCustomLineItems().get(0);

            final Cart emptyCart = client().executeBlocking(CartUpdateCommand.of(cartWithCustomLineItem, RemoveCustomLineItem.of(customLineItem)));
            assertThat(emptyCart.getCustomLineItems()).hasSize(0);
        });
    }

    @Test
    public void setCustomerEmail() throws Exception {
        final Cart cart = createCartWithCountry(client());
        assertThat(cart.getCustomerEmail()).isNull();
        final String email = "info@commercetools.de";
        final Cart cartWithEmail = client().executeBlocking(CartUpdateCommand.of(cart, SetCustomerEmail.of(email)));
        assertThat(cartWithEmail.getCustomerEmail()).contains(email);
    }

    @Test
    public void setShippingAddress() throws Exception {
        final Cart cart = createCartWithCountry(client());
        assertThat(cart.getShippingAddress()).isNull();
        final Address address = AddressBuilder.of(DE).build();
        final Cart cartWithAddress = client().executeBlocking(CartUpdateCommand.of(cart, SetShippingAddress.of(address)));
        assertThat(cartWithAddress.getShippingAddress()).isEqualTo(address);

        //you can query by shippingAddress fields
        final CartQuery query = CartQuery.of()
                .withPredicates(m -> m.shippingAddress().country().is(DE).and(m.id().is(cart.getId())));
        assertThat(client().executeBlocking(query).head()).contains(cartWithAddress);

        final Cart cartWithoutAddress = client().executeBlocking(CartUpdateCommand.of(cartWithAddress, SetShippingAddress.of(null)));
        assertThat(cartWithoutAddress.getShippingAddress()).isNull();
    }

    @Test
    public void setBillingAddress() throws Exception {
        final Cart cart = createCartWithCountry(client());
        assertThat(cart.getBillingAddress()).isNull();
        final Address address = AddressBuilder.of(DE).build();
        final Cart cartWithAddress = client().executeBlocking(CartUpdateCommand.of(cart, SetBillingAddress.of(address)));
        assertThat(cartWithAddress.getBillingAddress()).isEqualTo(address);

        //you can query by billingAddress fields
        final CartQuery query = CartQuery.of()
                .withPredicates(m -> m.billingAddress().country().is(DE).and(m.id().is(cart.getId())));
        assertThat(client().executeBlocking(query).head()).contains(cartWithAddress);

        final Cart cartWithoutAddress = client().executeBlocking(CartUpdateCommand.of(cartWithAddress, SetBillingAddress.of(null)));
        assertThat(cartWithoutAddress.getBillingAddress()).isNull();
    }

    @Test
    public void setCountry() throws Exception {
        final Cart cart = createCartWithoutCountry(client());
        assertThat(cart.getCountry()).isNull();
        final Cart cartWithCountry = client().executeBlocking(CartUpdateCommand.of(cart, SetCountry.of(DE)));
        assertThat(cartWithCountry.getCountry()).isEqualTo(DE);
        final Cart cartWithoutCountry = client().executeBlocking(CartUpdateCommand.of(cartWithCountry, SetCountry.of(null)));
        assertThat(cartWithoutCountry.getCountry()).isNull();
    }

    @Test
    public void setCustomShippingMethod() throws Exception {
        withTaxCategory(client(), taxCategory -> {
            final Cart cart = createCartWithShippingAddress(client());
            assertThat(cart.getShippingInfo()).isNull();
            final MonetaryAmount price = MoneyImpl.of(new BigDecimal("23.50"), EUR);
            final ShippingRate shippingRate = ShippingRate.of(price);
            final String shippingMethodName = "custom-shipping";
            final SetCustomShippingMethod action = SetCustomShippingMethod.of(shippingMethodName, shippingRate, taxCategory);
            final Cart cartWithShippingMethod = client().executeBlocking(CartUpdateCommand.of(cart, action));
            final CartShippingInfo shippingInfo = cartWithShippingMethod.getShippingInfo();
            assertThat(shippingInfo.getPrice()).isEqualTo(price);
            assertThat(shippingInfo.getShippingMethod()).isNull();
            assertThat(shippingInfo.getShippingMethodName()).isEqualTo(shippingMethodName);
            assertThat(shippingInfo.getShippingRate()).isEqualTo(shippingRate);
            assertThat(shippingInfo.getTaxCategory()).isEqualTo(taxCategory.toReference());
            assertThat(shippingInfo.getTaxRate()).isNotNull();
        });
    }

    @Test
    public void setShippingMethod() throws Exception {
        withShippingMethodForGermany(client(), shippingMethod -> {
            withCart(client(), createCartWithShippingAddress(client()), cart -> {
                //add shipping method
                assertThat(cart.getShippingInfo()).isNull();
                final CartUpdateCommand updateCommand =
                        CartUpdateCommand.of(cart, SetShippingMethod.of(shippingMethod))
                                .plusExpansionPaths(m -> m.shippingInfo().shippingMethod().taxCategory())
                                .plusExpansionPaths(m -> m.shippingInfo().taxCategory());
                final Cart cartWithShippingMethod = client().executeBlocking(updateCommand);
                assertThat(cartWithShippingMethod.getShippingInfo().getShippingMethod()).isEqualTo(shippingMethod.toReference());
                assertThat(cartWithShippingMethod.getShippingInfo().getShippingMethod().getObj())
                        .as("reference expansion shippingMethod")
                        .isEqualTo(shippingMethod);
                assertThat(cartWithShippingMethod.getShippingInfo().getShippingMethod().getObj().getTaxCategory().getObj())
                        .as("reference expansion taxCategory")
                        .isEqualTo(cartWithShippingMethod.getShippingInfo().getTaxCategory().getObj())
                        .isNotNull();

                //remove shipping method
                final Cart cartWithoutShippingMethod = client().executeBlocking(CartUpdateCommand.of(cartWithShippingMethod, SetShippingMethod.ofRemove()));
                assertThat(cartWithoutShippingMethod.getShippingInfo()).isNull();

                return cartWithoutShippingMethod;
            });
        });
    }

    @Test
    public void setShippingMethodById() throws Exception {
        withShippingMethodForGermany(client(), shippingMethod -> {
            withCart(client(), createCartWithShippingAddress(client()), cart -> {
                //add shipping method
                assertThat(cart.getShippingInfo()).isNull();
                final CartUpdateCommand updateCommand =
                        CartUpdateCommand.of(cart, SetShippingMethod.ofId(shippingMethod.getId()))
                                .plusExpansionPaths(m -> m.shippingInfo().shippingMethod().taxCategory())
                                .plusExpansionPaths(m -> m.shippingInfo().taxCategory());
                final Cart cartWithShippingMethod = client().executeBlocking(updateCommand);
                assertThat(cartWithShippingMethod.getShippingInfo().getShippingMethod()).isEqualTo(shippingMethod.toReference());
                assertThat(cartWithShippingMethod.getShippingInfo().getShippingMethod().getObj())
                        .as("reference expansion shippingMethod")
                        .isEqualTo(shippingMethod);

                //remove shipping method
                final Cart cartWithoutShippingMethod = client().executeBlocking(CartUpdateCommand.of(cartWithShippingMethod, SetShippingMethod.ofRemove()));
                assertThat(cartWithoutShippingMethod.getShippingInfo()).isNull();

                return cartWithoutShippingMethod;
            });
        });
    }

    @Test
    public void setCustomerId() throws Exception {
        withCustomer(client(), customer -> {
            final Cart cart = createCartWithCountry(client());
            assertThat(cart.getCustomerId()).isNull();
            final Cart cartWithCustomerId = client().executeBlocking(CartUpdateCommand.of(cart, SetCustomerId.ofCustomer(customer)));
            assertThat(cartWithCustomerId.getCustomerId()).contains(customer.getId());
            final Cart cartWithoutCustomerId = client().executeBlocking(CartUpdateCommand.of(cartWithCustomerId, SetCustomerId.empty()));
            assertThat(cartWithoutCustomerId.getCustomerId()).isNull();
        });
    }

    @Test
    public void recalculate() throws Exception {
        withEmptyCartAndProduct(client(), (emptyCart, product) -> {
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 1L);

            final Cart cartWithLineItem = client().executeBlocking(CartUpdateCommand.of(emptyCart, action));
            final Price oldPrice = cartWithLineItem.getLineItems().get(0).getPrice();
            final PriceDraft priceDraft = PriceDraft.of(oldPrice).withValue(oldPrice.getValue().multiply(2));
            final Product productWithChangedPrice =
                    client().executeBlocking(ProductUpdateCommand.of(product, asList(ChangePrice.of(oldPrice, priceDraft), Publish.of())));

            final List<Price> prices = productWithChangedPrice.getMasterData().getCurrent().getMasterVariant().getPrices();
            assertThat(prices.stream().map(price -> PriceDraft.of(price)).collect(Collectors.toList()))
                    .as("we updated the price of the product")
                    .isEqualTo(asList(priceDraft));

            final LineItem lineItemOfTheChangedProduct =
                    client().executeBlocking(CartByIdGet.of(cartWithLineItem)).getLineItems().get(0);
            assertThat(lineItemOfTheChangedProduct.getPrice())
                    .as("the new product price is not automatically propagated to the line item in the cart")
                    .isEqualTo(oldPrice).isNotEqualTo(priceDraft);

            final Cart recalculatedCart = client().executeBlocking(CartUpdateCommand.of(cartWithLineItem, Recalculate.of()));

            assertThat(PriceDraft.of(recalculatedCart.getLineItems().get(0).getPrice()))
                    .as("recalculate updated the price of the line item in the cart")
                    .isEqualTo(priceDraft);
            assertThat(recalculatedCart.getTotalPrice())
                    .as("recalculate also updated the total price of the cart")
                    .isEqualTo(priceDraft.getValue()).isNotEqualTo(cartWithLineItem.getTotalPrice());
        });
    }

    @Test
    public void recalculateAndUpdateProductData() throws Exception {
        withEmptyCartAndProduct(client(), (emptyCart, product) -> {
            //create cart with line item
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 1L);
            final Cart cartWithLineItem = client().executeBlocking(CartUpdateCommand.of(emptyCart, action));
            final NamedAttributeAccess<LocalizedEnumValue> colorAttribute = Colors.ATTRIBUTE;
            final LocalizedEnumValue oldColor = cartWithLineItem.getLineItems().get(0).getVariant().findAttribute(colorAttribute).get();

            //update the product
            final LocalizedEnumValue newValueForColor = Colors.RED;
            final SetAttribute localizedEnumUpdate = SetAttribute.of(MASTER_VARIANT_ID, colorAttribute, newValueForColor);
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, asList(localizedEnumUpdate, Publish.of())));
            assertThat(updatedProduct.getMasterData().getCurrent().getMasterVariant().findAttribute(colorAttribute)).contains(newValueForColor);

            //check the line item in the cart, the product data will not be updated
            final LineItem lineItemOfTheChangedProduct =
                    client().executeBlocking(CartByIdGet.of(cartWithLineItem)).getLineItems().get(0);
            assertThat(lineItemOfTheChangedProduct.getVariant().findAttribute(colorAttribute))
                    .as("the new product attribute value is not automatically propagated to the line item in the cart")
                    .contains(oldColor);

            //use recalculate with updateProductData flag, the product data will be updated
            final Cart recalculatedCart = client().executeBlocking(CartUpdateCommand.of(cartWithLineItem, Recalculate.of().withUpdateProductData(Boolean.TRUE)));
            assertThat(recalculatedCart.getLineItems().get(0).getVariant().findAttribute(colorAttribute))
                    .contains(newValueForColor);
        });
    }

    @Test
    public void moneyPortionIsPresent() throws Exception {
        withFilledCart(client(), cart -> {
            final MonetaryAmount money = cart.getTaxedPrice().getTaxPortions().get(0).getAmount();
            assertThat(money).isNotNull();
        });
    }

    @Test
    public void removeDiscountCode() throws Exception {
        withCartAndDiscountCode(client(), (cart, discountCode) -> {
            //addDiscountCode
            final Cart cartWithCode = client().executeBlocking(CartUpdateCommand.of(cart, AddDiscountCode.of(discountCode)));
            final DiscountCodeInfo discountCodeInfo = cartWithCode.getDiscountCodes().get(0);
            assertThat(discountCodeInfo.getDiscountCode()).isEqualTo(discountCode.toReference());

            //removeDiscountCode
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cartWithCode, RemoveDiscountCode.of(discountCode)));
            assertThat(updatedCart.getDiscountCodes()).isEmpty();

            return updatedCart;
        });
    }

    @Test
    public void addPayment() {
        withPayment(client(), payment -> {
            withCart(client(), cart -> {
                //add payment
                final CartUpdateCommand command = CartUpdateCommand.of(cart, AddPayment.of(payment))
                        .withExpansionPaths(m -> m.paymentInfo().payments());
                final Cart cartWithPayment = client().executeBlocking(command);

                final Reference<Payment> paymentReference = cartWithPayment.getPaymentInfo().getPayments().get(0);
                assertThat(paymentReference).isEqualTo(payment.toReference());
                assertThat(paymentReference).is(expanded(payment));

                //query cart by payment
                final CartQuery cartQuery = CartQuery.of()
                        .withPredicates(m -> m.paymentInfo().payments().isIn(singletonList(payment)));
                assertThat(client().executeBlocking(cartQuery).head()).contains(cartWithPayment);

                //remove payment
                final Cart cartWithoutPayment = client().executeBlocking(CartUpdateCommand.of(cartWithPayment, RemovePayment.of(payment)));

                assertThat(cartWithoutPayment.getPaymentInfo()).isNull();

                return cartWithoutPayment;
            });
            return payment;
        });
    }

    @Test
    public void locale() {
        withCart(client(), cart -> {
            assertThat(cart.getLocale()).isNull();
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, SetLocale.of(Locale.GERMAN)));
            assertThat(updatedCart.getLocale()).isEqualTo(GERMAN);
            return updatedCart;
        });
    }
}
