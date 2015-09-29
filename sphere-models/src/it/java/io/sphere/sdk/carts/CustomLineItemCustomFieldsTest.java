package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.*;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import static io.sphere.sdk.carts.CartFixtures.withCartAndTaxedProduct;
import static io.sphere.sdk.carts.CartFixtures.withLineItemAndCustomLineItemFilledCart;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.StrictAssertions.assertThat;

public class CustomLineItemCustomFieldsTest extends IntegrationTest {
    @Test
    public void creation() {
        withUpdateableType(client(), type -> {
            withCartAndTaxedProduct(client(), (cart, product) -> {
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).addObject(STRING_FIELD_NAME, "a value").build();
                final Cart updatedCart = execute(CartUpdateCommand.of(cart, AddCustomLineItem.of(en("custom line item"), "foo", EURO_30, product.getTaxCategory(), 3).withCustom(customFieldsDraft)));
                assertThat(updatedCart.getCustomLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a value");
                return updatedCart;
            });
            return type;
        });
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
            withLineItemAndCustomLineItemFilledCart(client(), cart -> {
                final String customLineItemId = cart.getCustomLineItems().get(0).getId();
                final SetCustomLineItemCustomType updateAction = SetCustomLineItemCustomType.
                        ofTypeIdAndObjects(type.getId(), STRING_FIELD_NAME, "a value", customLineItemId);
                final CartUpdateCommand cartUpdateCommand = CartUpdateCommand.of(cart, updateAction);
                final Cart updatedCart = execute(cartUpdateCommand);

                final CustomLineItem lineItem = updatedCart.getCustomLineItems().get(0);
                assertThat(lineItem.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo("a value");

                final Cart updated2 = execute(CartUpdateCommand.of(updatedCart, SetCustomLineItemCustomField.ofObject(STRING_FIELD_NAME, "a new value", lineItem.getId())));
                assertThat(updated2.getCustomLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo("a new value");
                return updated2;
            });
            return type;
        });
    }
}