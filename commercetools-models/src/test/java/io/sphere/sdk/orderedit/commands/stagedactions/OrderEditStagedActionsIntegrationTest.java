package io.sphere.sdk.orderedit.commands.stagedactions;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.orderedit.OrderEditFixtures;
import io.sphere.sdk.orderedit.commands.OrderEditUpdateCommandIntegrationTest;
import io.sphere.sdk.orderedits.OrderEdit;

import io.sphere.sdk.orderedits.commands.OrderEditUpdateCommand;
import io.sphere.sdk.orderedits.commands.stagedactions.*;
import io.sphere.sdk.orderedits.commands.updateactions.AddStagedAction;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.commands.OrderFromCartCreateCommand;
import io.sphere.sdk.shippingmethods.CartValueBuilder;
import io.sphere.sdk.shippingmethods.ShippingMethodFixtures;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.shoppinglists.ShoppingListFixtures;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraftBuilder;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.utils.MoneyImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.util.*;

import static io.sphere.sdk.carts.CartFixtures.withFilledCart;
import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.channels.ChannelRole.PRODUCT_DISTRIBUTION;
import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withB2cCustomerGroup;
import static io.sphere.sdk.payments.PaymentFixtures.withPayment;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;

public class OrderEditStagedActionsIntegrationTest extends IntegrationTest {

    @Test
    public void setCustomerEmail() {
        final String newCustomerEmail = SphereTestUtils.randomEmail(OrderEditUpdateCommandIntegrationTest.class);
        final SetCustomerEmail setCustomerEmail = SetCustomerEmail.of(newCustomerEmail);
        testOrderEditStagedUpdateAction(setCustomerEmail);
    }

    @Test
    public void setShippingAddress() {
        final Address address = Address.of(CountryCode.DE);
        final SetShippingAddress setShippingAddress = SetShippingAddress.of(address);
        testOrderEditStagedUpdateAction(setShippingAddress);
    }

    @Test
    public void setBillingAddress(){
        final Address address = Address.of(CountryCode.DE);
        final SetBillingAddress setBillingAddress = SetBillingAddress.of(address);
        testOrderEditStagedUpdateAction(setBillingAddress);
    }

    @Test
    public void setLocale() {
        final SetLocale setLocale = SetLocale.of(Locale.GERMAN);
        testOrderEditStagedUpdateAction(setLocale);
    }

    @Test
    public void setCountry() {
        final SetCountry setCountry = SetCountry.of(SphereTestUtils.DE);
        testOrderEditStagedUpdateAction(setCountry);
    }

    @Test
    public void setShippingMethod() {
        ShippingMethodFixtures.withShippingMethodForGermany(client(), shippingMethod -> {
            final SetShippingMethod setShippingMethod = SetShippingMethod.of(shippingMethod.toResourceIdentifier(), null);
            testOrderEditStagedUpdateAction(setShippingMethod);
        });
    }

    @Test
    public void setCustomShippingMethod() {
        final ShippingRate shippingRate = ShippingRate.of(EURO_10, null,
                Arrays.asList(
                        CartValueBuilder.of(0L, EURO_30).build(),
                        CartValueBuilder.of(1L, EURO_20).build()
                ));
        final SetCustomShippingMethod setCustomShippingMethod = SetCustomShippingMethod.of("name", shippingRate, null, null);
        testOrderEditStagedUpdateAction(setCustomShippingMethod);
    }

    @Test
    public void addDiscountCode() {
        CartFixtures.withCartAndDiscountCode(client(), (cart, discountCode) -> {
            final AddDiscountCode addDiscountCode = AddDiscountCode.of(discountCode);
            testOrderEditStagedUpdateAction(addDiscountCode);
            return cart;
        });
    }

    @Test
    public void removeDiscountCode() {
        CartFixtures.withCartAndDiscountCode(client(), (cart, discountCode) -> {
            final RemoveDiscountCode removeDiscountCode = RemoveDiscountCode.of(discountCode);
            testOrderEditStagedUpdateAction(removeDiscountCode);
            return cart;
        });
    }

    @Test
    public void setCustomerId() {
        final SetCustomerId setCustomerId = SetCustomerId.of(UUID.randomUUID().toString());
        testOrderEditStagedUpdateAction(setCustomerId);
    }

    @Test
    public void setCustomerGroup() {
        withB2cCustomerGroup(client(), customerGroup -> {
            final SetCustomerGroup setCustomerGroup = SetCustomerGroup.of(customerGroup);
            testOrderEditStagedUpdateAction(setCustomerGroup);
        });
    }

    @Test
    public void setCustomType() {
        final SetCustomType setCustomType = SetCustomType.ofRemoveType();
        testOrderEditStagedUpdateAction(setCustomType);
    }

    @Test
    public void setCustomField() {
        final SetCustomField setCustomField = SetCustomField.ofObject(STRING_FIELD_NAME, "other");
        testOrderEditStagedUpdateAction(setCustomField);
    }

    @Test
    public void addPayment() {
        withPayment(client(), payment -> {
            final AddPayment addPayment = AddPayment.of(payment);
            testOrderEditStagedUpdateAction(addPayment);
            return payment;
        });
    }

    @Test
    public void removePayment() {
        withPayment(client(), payment -> {
            final RemovePayment removePayment = RemovePayment.of(payment);
            testOrderEditStagedUpdateAction(removePayment);
            return payment;
        });
    }

    @Test
    public void setShippingMethodTaxRate() {
        final String taxRateName = "special tax";
        final double taxRate = 0.20;
        final ExternalTaxRateDraft externalTaxRate =
                ExternalTaxRateDraftBuilder.ofAmount(taxRate, taxRateName, DE).build();
        final SetShippingMethodTaxRate setShippingMethodTaxRate = SetShippingMethodTaxRate.of(externalTaxRate);
        testOrderEditStagedUpdateAction(setShippingMethodTaxRate);
    }

    @Test
    public void setShippingMethodTaxAmount() {
        final ExternalTaxRateDraft taxRate = ExternalTaxRateDraftBuilder
                .ofAmount(1.0, "Test Tax", CountryCode.DE)
                .build();
        final MonetaryAmount totalGross = MoneyImpl.ofCents(100000, EUR);
        final ExternalTaxAmountDraftDsl taxAmountDraft = ExternalTaxAmountDraftBuilder
                .of(totalGross, taxRate)
                .build();
        final SetShippingMethodTaxAmount setShippingMethodTaxAmount = SetShippingMethodTaxAmount.of(taxAmountDraft);
        testOrderEditStagedUpdateAction(setShippingMethodTaxAmount);
    }

    @Test
    public void setOrderTotalTax() {
        final MonetaryAmount monetaryAmount = MoneyImpl.of(25, EUR);
        final SetOrderTotalTax setOrderTotalTax = SetOrderTotalTax.of(monetaryAmount);
        testOrderEditStagedUpdateAction(setOrderTotalTax);
    }

    @Test
    public void changeTaxMode() {
        final ChangeTaxMode changeTaxMode = ChangeTaxMode.of(TaxMode.PLATFORM);
        testOrderEditStagedUpdateAction(changeTaxMode);
    }

    @Test
    public void changeTaxRoundingMode() {
        final ChangeTaxRoundingMode changeTaxRoundingMode = ChangeTaxRoundingMode.of(RoundingMode.HALF_UP);
        testOrderEditStagedUpdateAction(changeTaxRoundingMode);
    }

    @Test
    public void setShippingRateInput() {
        final SetShippingRateInput setShippingRateInput = SetShippingRateInput.of(ClassificationShippingRateInputDraftBuilder.of("Small").build());
        testOrderEditStagedUpdateAction(setShippingRateInput);
    }

    @Test
    public void changeTaxCalculationMode() {
        final ChangeTaxCalculationMode changeTaxCalculationMode = ChangeTaxCalculationMode.of(TaxCalculationMode.UNIT_PRICE_LEVEL);
        testOrderEditStagedUpdateAction(changeTaxCalculationMode);
    }

    @Test
    public void addShoppingList() {
        ShoppingListFixtures.withShoppingList(client(), shoppingList -> {
            final AddShoppingList addShoppingList = AddShoppingList.of(shoppingList.toReference());
            testOrderEditStagedUpdateAction(addShoppingList);
            return shoppingList;
        });
    }

    @Test
    public void addItemShippingAddress() {
        final AddItemShippingAddress addItemShippingAddress = AddItemShippingAddress.of(Address.of(CountryCode.DE));
        testOrderEditStagedUpdateAction(addItemShippingAddress);
    }

    @Test
    public void removeItemShippingAddress() {
        final RemoveItemShippingAddress removeItemShippingAddress = RemoveItemShippingAddress.of(UUID.randomUUID().toString());
        testOrderEditStagedUpdateAction(removeItemShippingAddress);
    }

    @Test
    public void updateItemShippingAddress() {
        final UpdateItemShippingAddress updateItemShippingAddress = UpdateItemShippingAddress.of(Address.of(CountryCode.DE));
        testOrderEditStagedUpdateAction(updateItemShippingAddress);
    }

    @Test
    public void SetShippingAddressAndShippingMethod() {
        final SetShippingAddressAndShippingMethod setShippingAddressAndShippingMethod = SetShippingAddressAndShippingMethod.of(Address.of(CountryCode.DE));
        testOrderEditStagedUpdateAction(setShippingAddressAndShippingMethod);
    }

    @Test
    public void setLineItemShippingDetails() {
        final SetLineItemShippingDetails setLineItemShippingDetails = SetLineItemShippingDetails.of(UUID.randomUUID().toString());
        testOrderEditStagedUpdateAction(setLineItemShippingDetails);
    }

    @Test
    public void addLineItem() {
        withTaxedProduct(client(), product -> {
            final LineItemDraft lineItemDraft = LineItemDraft.of(product, 1, 5);
            final AddLineItem addLineItem = AddLineItem.of(lineItemDraft);
            testOrderEditStagedUpdateAction(addLineItem);
        });
    }

    @Test
    public void removeLineItem() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final RemoveLineItem removeLineItem = RemoveLineItem.of(firstLineItem.getId());
            testOrderEditStagedUpdateAction(removeLineItem);
        });
    }

    @Test
    public void changeLineItemQuantity() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final ChangeLineItemQuantity changeLineItemQuantity = ChangeLineItemQuantity.of(firstLineItem.getId(), 2L);
            testOrderEditStagedUpdateAction(changeLineItemQuantity);
        });
    }

    @Test
    public void setLineItemCustomType() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final SetLineItemCustomType setLineItemCustomType = SetLineItemCustomType.of(firstLineItem.getId());
            testOrderEditStagedUpdateAction(setLineItemCustomType);
        });
    }

    @Test
    public void setLineItemCustomField() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final SetLineItemCustomField setLineItemCustomField = SetLineItemCustomField.of("name", null, firstLineItem.getId());
            testOrderEditStagedUpdateAction(setLineItemCustomField);
        });
    }

    @Test
    public void setLineItemTaxRate() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final String taxRateName = "special tax";
            final double taxRate = 0.20;
            final ExternalTaxRateDraft externalTaxRate =
                    ExternalTaxRateDraftBuilder.ofAmount(taxRate, taxRateName, DE).build();

            final SetLineItemTaxRate setLineItemTaxRate = SetLineItemTaxRate.of(firstLineItem.getId(), externalTaxRate);
            testOrderEditStagedUpdateAction(setLineItemTaxRate);
        });
    }

    @Test
    public void setLineItemTaxAmount() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final SetLineItemTaxAmount setLineItemTaxAmount = SetLineItemTaxAmount.of(firstLineItem.getId(), null);
            testOrderEditStagedUpdateAction(setLineItemTaxAmount);
        });
    }

    @Test
    public void setLineItemTotalPrice() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final SetLineItemTotalPrice setLineItemTotalPrice = SetLineItemTotalPrice.of(firstLineItem.getId(), null);
            testOrderEditStagedUpdateAction(setLineItemTotalPrice);
        });
    }

    @Test
    public void setLineItemPrice() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final SetLineItemPrice setLineItemPrice = SetLineItemPrice.of(firstLineItem.getId(), null);
            testOrderEditStagedUpdateAction(setLineItemPrice);
        });
    }

    @Test
    public void addCustomLineItem() {
        final AddCustomLineItem addCustomLineItem = AddCustomLineItem.of(LocalizedString.ofEnglish("customItem"), "slug", MoneyImpl.of("23.50", EUR), null, 2L, null, null);
        testOrderEditStagedUpdateAction(addCustomLineItem);
    }

    @Test
    public void setLineItemDistributionChannel() {
        withChannelOfRole(client(), PRODUCT_DISTRIBUTION, channel -> {
            withFilledCart(client(), cart -> {
                final Order order = client().executeBlocking(OrderFromCartCreateCommand.of(cart));
                OrderEditFixtures.withUpdateableOrderEdit(client(), order.toReference(), orderEdit -> {
                    final LineItem firstLineItem = order.getLineItems().get(0);
                    final SetLineItemDistributionChannel setLineItemDistributionChannel = SetLineItemDistributionChannel.of(firstLineItem.getId())
                            .withDistributionChannel(Channel.referenceOfId(channel.getId()));

                    final OrderEditUpdateCommand orderEditUpdateCommand = OrderEditUpdateCommand.of(orderEdit, AddStagedAction.of(setLineItemDistributionChannel));
                    final OrderEdit updatedOrderEdit = client().executeBlocking(orderEditUpdateCommand);
                    Assertions.assertThat(updatedOrderEdit.getVersion()).isNotEqualTo(orderEdit.getVersion());
                    Assertions.assertThat(updatedOrderEdit.getStagedActions().size()).isEqualTo(1);
                    Assertions.assertThat(updatedOrderEdit.getStagedActions().get(0).getAction()).isEqualTo(setLineItemDistributionChannel.getAction());
                    Assertions.assertThat(updatedOrderEdit.getStagedActions().get(0)).isInstanceOf(setLineItemDistributionChannel.getClass());

                    return updatedOrderEdit;
                });
            });
        });
    }

    @Test
    public void removeCustomLineItem() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final RemoveCustomLineItem removeCustomLineItem = RemoveCustomLineItem.of(firstLineItem.getId());
            testOrderEditStagedUpdateAction(removeCustomLineItem);
        });
    }

    @Test
    public void setCustomLineItemCustomType() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final SetCustomLineItemCustomType setCustomLineItemCustomType = SetCustomLineItemCustomType.of(firstLineItem.getId(), null, null);
            testOrderEditStagedUpdateAction(setCustomLineItemCustomType);
        });
    }

    @Test
    public void setCustomLineItemCustomField() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final SetCustomLineItemCustomField setCustomLineItemCustomField = SetCustomLineItemCustomField.of(firstLineItem.getId(), "name", null);
            testOrderEditStagedUpdateAction(setCustomLineItemCustomField);
        });
    }

    @Test
    public void setCustomLineItemTaxAmount() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final SetCustomLineItemTaxAmount setCustomLineItemTaxAmount = SetCustomLineItemTaxAmount.of(firstLineItem.getId(), null);
            testOrderEditStagedUpdateAction(setCustomLineItemTaxAmount);
        });
    }

    @Test
    public void SetCustomLineItemTaxRate() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final SetCustomLineItemTaxRate setCustomLineItemTaxRate = SetCustomLineItemTaxRate.of(firstLineItem.getId(), null);
            testOrderEditStagedUpdateAction(setCustomLineItemTaxRate);
        });
    }

    @Test
    public void changeCustomLineItemQuantity() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final ChangeCustomLineItemQuantity changeCustomLineItemQuantity = ChangeCustomLineItemQuantity.of(firstLineItem.getId(), 2L);
            testOrderEditStagedUpdateAction(changeCustomLineItemQuantity);
        });
    }

    @Test
    public void changeCustomLineItemMoney() {
        CartFixtures.withFilledCart(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final ChangeCustomLineItemMoney changeCustomLineItemMoney = ChangeCustomLineItemMoney.of(firstLineItem.getId(), MoneyImpl.of(25, EUR));
            testOrderEditStagedUpdateAction(changeCustomLineItemMoney);
        });
    }

    @Test
    public void setShippingAddressAndCustomShippingMethod() {
        final ShippingRate shippingRate = ShippingRate.of(EURO_10, null,
                Arrays.asList(
                        io.sphere.sdk.shippingmethods.CartValueBuilder.of(0L, EURO_30).build(),
                        io.sphere.sdk.shippingmethods.CartValueBuilder.of(1L, EURO_20).build()
                ));
        final SetShippingAddressAndCustomShippingMethod setShippingAddressAndCustomShippingMethod =
                SetShippingAddressAndCustomShippingMethod.of(Address.of(CountryCode.DE),"custom-shipping", shippingRate);
        testOrderEditStagedUpdateAction(setShippingAddressAndCustomShippingMethod);
    }

    private void testOrderEditStagedUpdateAction(final OrderEditStagedUpdateAction orderEditStagedUpdateAction) {
        OrderEditFixtures.withUpdateableOrderEdit(client(), orderEdit -> {

            final OrderEditUpdateCommand orderEditUpdateCommand = OrderEditUpdateCommand.of(orderEdit, AddStagedAction.of(orderEditStagedUpdateAction));
            final OrderEdit updatedOrderEdit = client().executeBlocking(orderEditUpdateCommand);
            Assertions.assertThat(updatedOrderEdit).isNotNull();
            Assertions.assertThat(updatedOrderEdit.getStagedActions().size()).isEqualTo(1);
            Assertions.assertThat(updatedOrderEdit.getStagedActions().get(0).getAction()).isEqualTo(orderEditStagedUpdateAction.getAction());

            Assertions.assertThat(updatedOrderEdit.getStagedActions().get(0)).isInstanceOf(orderEditStagedUpdateAction.getClass());

            return updatedOrderEdit;
        });
    }
}