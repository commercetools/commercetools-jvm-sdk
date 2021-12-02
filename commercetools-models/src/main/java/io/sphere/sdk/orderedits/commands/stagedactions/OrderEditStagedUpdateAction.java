package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.sphere.sdk.commands.StagedUpdateAction;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.commands.stagedactions.SetCustomerEmail;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "action")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SetCustomerEmail.class, name = "setCustomerEmail"),
        @JsonSubTypes.Type(value = SetShippingAddress.class, name = "setShippingAddress"),
        @JsonSubTypes.Type(value = SetBillingAddress.class, name = "setBillingAddress"),
        @JsonSubTypes.Type(value = SetLocale.class, name = "setLocale"),
        @JsonSubTypes.Type(value = SetCountry.class, name = "setCountry"),
        @JsonSubTypes.Type(value = SetShippingMethod.class, name = "setShippingMethod"),
        @JsonSubTypes.Type(value = SetCustomShippingMethod.class, name = "setCustomShippingMethod"),
        @JsonSubTypes.Type(value = AddDiscountCode.class, name = "addDiscountCode"),
        @JsonSubTypes.Type(value = RemoveDiscountCode.class, name = "removeDiscountCode"),
        @JsonSubTypes.Type(value = SetCustomerId.class, name = "setCustomerId"),
        @JsonSubTypes.Type(value = SetCustomerGroup.class, name = "setCustomerGroup"),
        @JsonSubTypes.Type(value = SetCustomType.class, name = "setCustomType"),
        @JsonSubTypes.Type(value = SetCustomField.class, name = "setCustomField"),
        @JsonSubTypes.Type(value = AddPayment.class, name = "addPayment"),
        @JsonSubTypes.Type(value = RemovePayment.class, name = "removePayment"),
        @JsonSubTypes.Type(value = SetShippingMethodTaxRate.class, name = "setShippingMethodTaxRate"),
        @JsonSubTypes.Type(value = SetShippingMethodTaxAmount.class, name = "setShippingMethodTaxAmount"),
        @JsonSubTypes.Type(value = SetOrderTotalTax.class, name = "setOrderTotalTax"),
        @JsonSubTypes.Type(value = ChangeTaxMode.class, name = "changeTaxMode"),
        @JsonSubTypes.Type(value = ChangeTaxRoundingMode.class, name = "changeTaxRoundingMode"),
        @JsonSubTypes.Type(value = SetShippingRateInput.class, name = "setShippingRateInput"),
        @JsonSubTypes.Type(value = ChangeTaxCalculationMode.class, name = "changeTaxCalculationMode"),
        @JsonSubTypes.Type(value = AddShoppingList.class, name = "addShoppingList"),
        @JsonSubTypes.Type(value = AddItemShippingAddress.class, name = "addItemShippingAddress"),
        @JsonSubTypes.Type(value = RemoveItemShippingAddress.class, name = "removeItemShippingAddress"),
        @JsonSubTypes.Type(value = UpdateItemShippingAddress.class, name = "updateItemShippingAddress"),
        @JsonSubTypes.Type(value = SetShippingAddressAndShippingMethod.class, name = "setShippingAddressAndShippingMethod"),
        @JsonSubTypes.Type(value = SetLineItemShippingDetails.class, name = "setLineItemShippingDetails"),
        @JsonSubTypes.Type(value = AddLineItem.class, name = "addLineItem"),
        @JsonSubTypes.Type(value = RemoveLineItem.class, name = "removeLineItem"),
        @JsonSubTypes.Type(value = ChangeLineItemQuantity.class, name = "changeLineItemQuantity"),
        @JsonSubTypes.Type(value = SetLineItemCustomType.class, name = "setLineItemCustomType"),
        @JsonSubTypes.Type(value = SetLineItemCustomField.class, name = "setLineItemCustomField"),
        @JsonSubTypes.Type(value = SetLineItemTaxRate.class, name = "setLineItemTaxRate"),
        @JsonSubTypes.Type(value = SetLineItemTaxAmount.class, name = "setLineItemTaxAmount"),
        @JsonSubTypes.Type(value = SetLineItemTotalPrice.class, name = "setLineItemTotalPrice"),
        @JsonSubTypes.Type(value = SetLineItemPrice.class, name = "setLineItemPrice"),
        @JsonSubTypes.Type(value = AddCustomLineItem.class, name = "addCustomLineItem"),
        @JsonSubTypes.Type(value = RemoveCustomLineItem.class, name = "removeCustomLineItem"),
        @JsonSubTypes.Type(value = SetCustomLineItemCustomType.class, name = "setCustomLineItemCustomType"),
        @JsonSubTypes.Type(value = SetCustomLineItemCustomField.class, name = "setCustomLineItemCustomField"),
        @JsonSubTypes.Type(value = SetCustomLineItemTaxAmount.class, name = "setCustomLineItemTaxAmount"),
        @JsonSubTypes.Type(value = SetCustomLineItemTaxRate.class, name = "setCustomLineItemTaxRate"),
        @JsonSubTypes.Type(value = ChangeCustomLineItemQuantity.class, name = "changeCustomLineItemQuantity"),
        @JsonSubTypes.Type(value = ChangeCustomLineItemMoney.class, name = "changeCustomLineItemMoney"),
        @JsonSubTypes.Type(value = SetShippingAddressAndCustomShippingMethod.class, name = "setShippingAddressAndCustomShippingMethod"),
        @JsonSubTypes.Type(value = SetLineItemDistributionChannel.class, name = "setLineItemDistributionChannel")
})
public interface OrderEditStagedUpdateAction extends StagedUpdateAction<OrderEdit> {
}
