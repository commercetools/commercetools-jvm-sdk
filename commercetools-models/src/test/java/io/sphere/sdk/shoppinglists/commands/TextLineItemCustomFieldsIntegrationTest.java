package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.TextLineItem;
import io.sphere.sdk.shoppinglists.commands.updateactions.AddTextLineItem;
import io.sphere.sdk.shoppinglists.commands.updateactions.SetTextLineItemCustomField;
import io.sphere.sdk.shoppinglists.commands.updateactions.SetTextLineItemCustomType;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.withShoppingListAndTaxedProduct;
import static io.sphere.sdk.test.SphereTestUtils.en;
import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class TextLineItemCustomFieldsIntegrationTest extends IntegrationTest {
    @Test
    public void creation() {
        withUpdateableType(client(), type -> {
            withShoppingListAndTaxedProduct(client(), (shoppingList, product) -> {
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).addObject(STRING_FIELD_NAME, "a value").build();
                final ShoppingList updatedShoppingList = client().executeBlocking(ShoppingListUpdateCommand.of(shoppingList,
                        AddTextLineItem.of(en(randomString()), 1L).withCustom(customFieldsDraft)));

                assertThat(updatedShoppingList.getTextLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a value");

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
                        AddTextLineItem.of(en(randomString()), 1L).withCustom(customFieldsDraft)));
                final String textLineItemId = updatedShoppingList1.getTextLineItems().get(0).getId();
                final ShoppingListUpdateCommand shoppingListUpdateCommand = ShoppingListUpdateCommand.of(updatedShoppingList1,
                        SetTextLineItemCustomType.ofTypeIdAndObjects(type.getId(), STRING_FIELD_NAME, "a value", textLineItemId));
                final ShoppingList updatedShoppingList2 = client().executeBlocking(shoppingListUpdateCommand);

                final TextLineItem textLineItem = updatedShoppingList2.getTextLineItems().get(0);
                assertThat(textLineItem.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo("a value");

                final ShoppingList updatedShoppingList3 = client().executeBlocking(ShoppingListUpdateCommand.of(updatedShoppingList2,
                        SetTextLineItemCustomField.ofObject(STRING_FIELD_NAME, "a new value", textLineItem.getId())));
                assertThat(updatedShoppingList3.getTextLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo("a new value");
                return updatedShoppingList3;
            });
            return type;
        });
    }
}