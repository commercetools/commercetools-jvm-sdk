package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.shoppinglists.LineItem;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.commands.updateactions.AddLineItem;
import io.sphere.sdk.shoppinglists.commands.updateactions.SetLineItemCustomField;
import io.sphere.sdk.shoppinglists.commands.updateactions.SetLineItemCustomType;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.withShoppingListAndTaxedProduct;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class LineItemCustomFieldsIntegrationTest extends IntegrationTest {
    @Test
    public void creation() {
        withUpdateableType(client(), type -> {
            withShoppingListAndTaxedProduct(client(), (shoppingList, product) -> {
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).addObject(STRING_FIELD_NAME, "a value").build();
                final ShoppingList updatedShoppingList = client().executeBlocking(ShoppingListUpdateCommand.of(shoppingList,
                        AddLineItem.of(product, 1, 1L).withCustom(customFieldsDraft)));

                assertThat(updatedShoppingList.getLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a value");

                return updatedShoppingList;
            });
            return type;
        });
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
            withShoppingListAndTaxedProduct(client(), (shoppingList, product) -> {
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).addObject(STRING_FIELD_NAME, "a value").build();
                final ShoppingList updatedShoppingList1 = client().executeBlocking(ShoppingListUpdateCommand.of(shoppingList,
                        AddLineItem.of(product, 1, 1L).withCustom(customFieldsDraft)));
                final String lineItemId = updatedShoppingList1.getLineItems().get(0).getId();
                final ShoppingListUpdateCommand shoppingListUpdateCommand = ShoppingListUpdateCommand.of(updatedShoppingList1,
                        SetLineItemCustomType.ofTypeIdAndObjects(type.getId(), STRING_FIELD_NAME, "a value", lineItemId));
                final ShoppingList updatedShoppingList2 = client().executeBlocking(shoppingListUpdateCommand);

                final LineItem lineItem = updatedShoppingList2.getLineItems().get(0);
                assertThat(lineItem.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo("a value");

                final ShoppingList updatedShoppingList3 = client().executeBlocking(ShoppingListUpdateCommand.of(updatedShoppingList2,
                        SetLineItemCustomField.ofObject(STRING_FIELD_NAME, "a new value", lineItem.getId())));
                assertThat(updatedShoppingList3.getLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo("a new value");
                return updatedShoppingList3;
            });
            return type;
        });
    }
}