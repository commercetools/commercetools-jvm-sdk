package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.ShoppingListDraftDsl;
import io.sphere.sdk.shoppinglists.commands.updateactions.SetCustomField;
import io.sphere.sdk.shoppinglists.commands.updateactions.SetCustomType;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.newShoppingListDraftBuilder;
import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.withShoppingList;
import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListCustomFieldsIntegrationTest extends IntegrationTest {
    @Test
    public void creation() {
        withUpdateableType(client(), type -> {
            final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).addObject(STRING_FIELD_NAME, "a value").build();
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftBuilder().custom(customFieldsDraft).build();
            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                assertThat(shoppingList.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a value");
                return shoppingList;
            });
            return type;
        });
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
            final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).addObject(STRING_FIELD_NAME, "a value").build();
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftBuilder().custom(customFieldsDraft).build();
            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final ShoppingListUpdateCommand shoppingListUpdateCommand = ShoppingListUpdateCommand.of(shoppingList,
                        SetCustomType.ofTypeIdAndObjects(type.getId(), STRING_FIELD_NAME, "a value"));
                final ShoppingList updatedShoppingList = client().executeBlocking(shoppingListUpdateCommand);

                assertThat(updatedShoppingList.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo("a value");

                return updatedShoppingList;
            });
            return type;
        });
    }

    @Test
    public void setCustomField() {
        withUpdateableType(client(), type -> {
            final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).addObject(STRING_FIELD_NAME, "a value").build();
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftBuilder().custom(customFieldsDraft).build();
            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final ShoppingList shoppingListWithCustomType = client().executeBlocking(ShoppingListUpdateCommand.of(shoppingList,
                        SetCustomType.ofTypeIdAndObjects(type.getId(), STRING_FIELD_NAME, "a value")));

                assertThat(shoppingListWithCustomType.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo("a value");

                final String newValue = randomString();
                final ShoppingList shoppingListWithCustomField = client().executeBlocking(ShoppingListUpdateCommand.of(shoppingListWithCustomType,
                        SetCustomField.ofObject(STRING_FIELD_NAME, newValue)));

                assertThat(shoppingListWithCustomField.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo(newValue);

                return shoppingListWithCustomField;
            });
            return type;
        });
    }
}