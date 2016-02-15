package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import io.sphere.sdk.carts.commands.updateactions.SetLineItemCustomField;
import io.sphere.sdk.carts.commands.updateactions.SetLineItemCustomType;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import static io.sphere.sdk.carts.CartFixtures.withCartAndTaxedProduct;
import static io.sphere.sdk.carts.CartFixtures.withLineItemAndCustomLineItemFilledCart;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class LineItemCustomFieldsIntegrationTest extends IntegrationTest {
    @Test
    public void creation() {
        withUpdateableType(client(), type -> {
            withCartAndTaxedProduct(client(), (cart, product) -> {
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).addObject(STRING_FIELD_NAME, "a value").build();
                final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, AddLineItem.of(product, 1, 1L).withCustom(customFieldsDraft)));
                assertThat(updatedCart.getLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a value");
                return updatedCart;
            });
            return type;
        });
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
            withLineItemAndCustomLineItemFilledCart(client(), cart -> {
                final String lineItemId = cart.getLineItems().get(0).getId();
                final SetLineItemCustomType updateAction = SetLineItemCustomType.
                        ofTypeIdAndObjects(type.getId(), STRING_FIELD_NAME, "a value", lineItemId);
                final CartUpdateCommand cartUpdateCommand = CartUpdateCommand.of(cart, updateAction);
                final Cart updatedCart = client().executeBlocking(cartUpdateCommand);

                final LineItem lineItem = updatedCart.getLineItems().get(0);
                assertThat(lineItem.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo("a value");

                final Cart updated2 = client().executeBlocking(CartUpdateCommand.of(updatedCart, SetLineItemCustomField.ofObject(STRING_FIELD_NAME, "a new value", lineItem.getId())));
                assertThat(updated2.getLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo("a new value");
                return updated2;
            });
            return type;
        });
    }
}