package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.ShoppingListDraftDsl;
import io.sphere.sdk.shoppinglists.TextLineItem;
import io.sphere.sdk.shoppinglists.commands.updateactions.AddTextLineItem;
import io.sphere.sdk.shoppinglists.commands.updateactions.SetTextLineItemCustomField;
import io.sphere.sdk.shoppinglists.commands.updateactions.SetTextLineItemCustomType;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.*;
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
                final String value = randomString();
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).addObject(STRING_FIELD_NAME, value).build();
                final ShoppingList updatedShoppingList = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, AddTextLineItem.of(en(randomString())).withQuantity(1L).withCustom(customFieldsDraft)));

                final CustomFields custom = updatedShoppingList.getTextLineItems().get(0).getCustom();
                assertThat(custom.getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo(value);

                return updatedShoppingList;
            });
            return type;
        });
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
                final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithTextLineItem(1L);

                withShoppingList(client(), shoppingListDraft, shoppingList -> {
                    final String textLineItemId = shoppingList.getTextLineItems().get(0).getId();
                    final String value = randomString();
                    final ShoppingList updatedShoppingList = client().executeBlocking(ShoppingListUpdateCommand.of(shoppingList,
                            SetTextLineItemCustomType.ofTypeIdAndObjects(type.getId(), STRING_FIELD_NAME, value, textLineItemId)));

                    final TextLineItem textLineItem = updatedShoppingList.getTextLineItems().get(0);
                    assertThat(textLineItem.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                            .isEqualTo(value);

                    return updatedShoppingList;
                });
            return type;
        });
    }

    @Test
    public void setCustomField() {
        withUpdateableType(client(), type -> {
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithTextLineItem(1L);

            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final String textLineItemId = shoppingList.getTextLineItems().get(0).getId();
                final String value = randomString();
                final ShoppingList shoppingListWithCustomType = client().executeBlocking(ShoppingListUpdateCommand.of(shoppingList,
                        SetTextLineItemCustomType.ofTypeIdAndObjects(type.getId(), STRING_FIELD_NAME, value, textLineItemId)));

                final String newValue = randomString();
                final ShoppingList shoppingListWithCustomField = client().executeBlocking(ShoppingListUpdateCommand.of(shoppingListWithCustomType,
                        SetTextLineItemCustomField.ofObject(STRING_FIELD_NAME, newValue, textLineItemId)));

                final TextLineItem updatedTextLineItem = shoppingListWithCustomField.getTextLineItems().get(0);
                assertThat(updatedTextLineItem.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo(newValue);

                return shoppingListWithCustomField;
            });
            return type;
        });
    }
}