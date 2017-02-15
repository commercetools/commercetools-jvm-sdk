package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.ShoppingListDraftDsl;
import io.sphere.sdk.shoppinglists.commands.updateactions.AddLineItem;
import io.sphere.sdk.shoppinglists.commands.updateactions.SetLineItemCustomField;
import io.sphere.sdk.shoppinglists.commands.updateactions.SetLineItemCustomType;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class LineItemCustomFieldsIntegrationTest extends IntegrationTest {
    @Test
    public void creation() {
        withUpdateableType(client(), type -> {
            withShoppingListAndTaxedProduct(client(), (shoppingList, product) -> {
                final String value = randomString();
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).addObject(STRING_FIELD_NAME, value).build();
                final ShoppingList updatedShoppingList = client().executeBlocking(ShoppingListUpdateCommand.of(shoppingList,
                        AddLineItem.of(product).withCustom(customFieldsDraft)));

                final CustomFields custom = updatedShoppingList.getLineItems().get(0).getCustom();
                assertThat(custom.getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo(value);

                return updatedShoppingList;
            });
            return type;
        });
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
            withTaxedProduct(client(), product -> {
                final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithLineItem(product, 1L);

                withShoppingList(client(), shoppingListDraft, shoppingList -> {
                    final String lineItemId = shoppingList.getLineItems().get(0).getId();
                    final String value = randomString();
                    final ShoppingList shoppingListWithCustomType = client().executeBlocking(ShoppingListUpdateCommand.of(shoppingList,
                            SetLineItemCustomType.ofTypeIdAndObjects(type.getId(), STRING_FIELD_NAME, value, lineItemId)));

                    assertThat(shoppingListWithCustomType.getLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                            .isEqualTo(value);

                    return shoppingListWithCustomType;
                });
            });
            return type;
        });
    }

    @Test
    public void setCustomField() {
        withUpdateableType(client(), type -> {
            withTaxedProduct(client(), product -> {
                final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithLineItem(product, 1L);

                withShoppingList(client(), shoppingListDraft, shoppingList -> {
                    final String lineItemId = shoppingList.getLineItems().get(0).getId();
                    final String value = randomString();
                    final ShoppingList shoppingListWithCustomType = client().executeBlocking(ShoppingListUpdateCommand.of(shoppingList,
                            SetLineItemCustomType.ofTypeIdAndObjects(type.getId(), STRING_FIELD_NAME, value, lineItemId)));

                    final String newValue = randomString();
                    final ShoppingList shoppingListWithLineCustomField = client().executeBlocking(ShoppingListUpdateCommand.of(shoppingListWithCustomType,
                            SetLineItemCustomField.ofObject(STRING_FIELD_NAME, newValue, lineItemId)));

                    final CustomFields custom = shoppingListWithLineCustomField.getLineItems().get(0).getCustom();
                    assertThat(custom.getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                            .isEqualTo(newValue);

                    return shoppingListWithLineCustomField;
                });
            });
            return type;
        });
    }
}