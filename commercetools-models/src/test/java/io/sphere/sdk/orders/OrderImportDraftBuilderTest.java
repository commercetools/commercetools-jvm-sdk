package io.sphere.sdk.orders;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.attributes.AttributeImportDraft;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderImportDraftBuilderTest {

    @Test
    public void constructWithAllValuesSet() {
        final LocalizedEnumValue yellow = LocalizedEnumValue.of("yellow", LocalizedString.of(ENGLISH, "yellow").plus(GERMAN, "gelb"));
        final ProductVariantImportDraft productVariantImportDraft = ProductVariantImportDraftBuilder.of("product-id", 1)
                .attributes(
                        AttributeImportDraft.of("attributecolor", yellow),
                        AttributeImportDraft.of("attributerrp", EURO_30)
                ).build();
        final LineItemImportDraft lineItemImportDraft = LineItemImportDraftBuilder.of(productVariantImportDraft, 1, Price.of(EURO_1), en("product name")).build();
        final Reference<TaxCategory> taxCategoryReference = TaxCategory.referenceOfId("reference-tax-category-id");
        final CustomLineItemImportDraft customLineItemImportDraft = CustomLineItemImportDraftBuilder.of(en("custom line"), 2, EURO_5, taxCategoryReference).build();
        final Reference<CustomerGroup> customerGroupReference = CustomerGroup.referenceOfId("customer-group-id");
        final CustomFieldsDraft custom = CustomFieldsDraftBuilder.ofTypeId("type-id").build();
        final MonetaryAmount lineItemsTotalPrice = EURO_20;
        final OrderState lineItemsOrderState = OrderState.COMPLETE;
        final List<LineItemImportDraft> lineItems = asList(lineItemImportDraft);
        final String orderNumber = "order-123";
        final String customerId = "customer-id";
        final String customerEmail = "customer@email.com";
        final List<CustomLineItemImportDraft> customLineItems = asList(customLineItemImportDraft);
        final MonetaryAmount totalPrice = EURO_10;
        final TaxedPrice taxedPrice = TaxedPrice.of(EURO_15, EURO_20, asList(TaxPortion.of(1.5, EURO_1)));
        final Address shippingAddress = Address.of(CountryCode.DE);
        final Address billingAddress = Address.of(CountryCode.DE);
        final CountryCode country = CountryCode.DE;
        final OrderState orderState = OrderState.COMPLETE;
        final ShipmentState shipmentState = ShipmentState.SHIPPED;
        final PaymentState paymentState = PaymentState.PAID;
        final ZonedDateTime completedAt = ZonedDateTime.now();
        final RoundingMode taxRoundingMode = RoundingMode.HALF_UP;
        final InventoryMode inventoryMode = InventoryMode.NONE;
        final TaxCalculationMode taxCalculationMode = TaxCalculationMode.LINE_ITEM_LEVEL;
        final CartOrigin cartOrigin = CartOrigin.MERCHANT;
        final OrderImportDraftBuilder orderImportDraftBuilder = OrderImportDraftBuilder
                .ofLineItems(lineItemsTotalPrice, lineItemsOrderState, lineItems)
                .orderNumber(orderNumber)
                .customerId(customerId)
                .customerEmail(customerEmail)
                .customLineItems(customLineItems)
                .totalPrice(totalPrice)
                .taxedPrice(taxedPrice)
                .shippingAddress(shippingAddress)
                .billingAddress(billingAddress)
                .customerGroup(customerGroupReference)
                .country(country)
                .orderState(orderState)
                .shipmentState(shipmentState)
                .paymentState(paymentState)
                .completedAt(completedAt)
                .custom(custom)
                .taxRoundingMode(taxRoundingMode)
                .inventoryMode(inventoryMode)
                .taxCalculationMode(taxCalculationMode)
                .origin(cartOrigin)
                ;
        final OrderImportDraft orderImportDraft = orderImportDraftBuilder.build();
        assertThat(orderImportDraft.getLineItems()).isEqualTo(lineItems);
        assertThat(orderImportDraft.getOrderNumber()).isEqualTo(orderNumber);
        assertThat(orderImportDraft.getCustomerId()).isEqualTo(customerId);
        assertThat(orderImportDraft.getCustomerEmail()).isEqualTo(customerEmail);
        assertThat(orderImportDraft.getCustomLineItems()).isEqualTo(customLineItems);
        assertThat(orderImportDraft.getTotalPrice()).isEqualTo(totalPrice);
        assertThat(orderImportDraft.getTaxedPrice()).isEqualTo(taxedPrice);
        assertThat(orderImportDraft.getShippingAddress()).isEqualTo(shippingAddress);
        assertThat(orderImportDraft.getBillingAddress()).isEqualTo(billingAddress);
        assertThat(orderImportDraft.getCustomerGroup()).isEqualTo(customerGroupReference);
        assertThat(orderImportDraft.getCountry()).isEqualTo(country);
        assertThat(orderImportDraft.getOrderState()).isEqualTo(orderState);
        assertThat(orderImportDraft.getShipmentState()).isEqualTo(shipmentState);
        assertThat(orderImportDraft.getPaymentState()).isEqualTo(paymentState);
        assertThat(orderImportDraft.getCompletedAt()).isEqualTo(completedAt);
        assertThat(orderImportDraft.getCustom()).isEqualTo(custom);
        assertThat(orderImportDraft.getTaxRoundingMode()).isEqualTo(taxRoundingMode);
        assertThat(orderImportDraft.getInventoryMode()).isEqualTo(inventoryMode);
        assertThat(orderImportDraft.getTaxCalculationMode()).isEqualTo(taxCalculationMode);
        assertThat(orderImportDraft.getOrigin()).isEqualTo(cartOrigin);
    }
}