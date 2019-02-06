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
        @JsonSubTypes.Type(value = SetShippingMethodTaxRate.class, name = "setShippingMethodTaxRate")
})
public interface OrderEditStagedUpdateAction extends StagedUpdateAction<OrderEdit> {
}
