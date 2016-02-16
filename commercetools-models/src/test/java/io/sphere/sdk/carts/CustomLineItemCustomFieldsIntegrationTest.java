package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.*;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import static io.sphere.sdk.carts.CartFixtures.withCartAndTaxedProduct;
import static io.sphere.sdk.carts.CartFixtures.withLineItemAndCustomLineItemFilledCart;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomLineItemCustomFieldsIntegrationTest extends IntegrationTest {
    @Test
    public void creation() {
        withUpdateableType(client(), type -> {
            withCartAndTaxedProduct(client(), (cart, product) -> {
                final CustomFieldsDraft customFieldsDraft =
                        CustomFieldsDraftBuilder
                                .ofType(type)
                                .addObject(STRING_FIELD_NAME, "a value")
                                .build();
                final AddCustomLineItem updateAction = AddCustomLineItem.of(en("custom line item"), "foo", EURO_30, product.getTaxCategory(), 3L).withCustom(customFieldsDraft);

                final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, updateAction));

                final CustomFields customFields = updatedCart.getCustomLineItems().get(0).getCustom();
                assertThat(customFields.getFieldAsString(STRING_FIELD_NAME)).isEqualTo("a value");
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
                final Cart updatedCart = client().executeBlocking(cartUpdateCommand);

                final CustomLineItem lineItem = updatedCart.getCustomLineItems().get(0);
                assertThat(lineItem.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo("a value");

                final Cart updated2 = client().executeBlocking(CartUpdateCommand.of(updatedCart, SetCustomLineItemCustomField.ofObject(STRING_FIELD_NAME, "a new value", lineItem.getId())));
                assertThat(updated2.getCustomLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo("a new value");
                return updated2;
            });
            return type;
        });
    }
}