package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Locale;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.withUpdateableShoppingList;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListUpdateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void changeName() throws Exception {
        withUpdateableShoppingList(client(), shoppingList -> {
            final String newName = randomString();
            assertThat(shoppingList.getName().get(Locale.ENGLISH)).isNotEqualTo(newName);
            final ShoppingListUpdateCommand cmd = ShoppingListUpdateCommand.of(shoppingList, ChangeName.of(en(newName)));
            final ShoppingList updatedShoppingList = client().executeBlocking(cmd);
            assertThat(updatedShoppingList.getName().get(Locale.ENGLISH)).isEqualTo(newName);
            return updatedShoppingList;
        });
    }

    @Test
    public void setSlug() throws Exception {
        withUpdateableShoppingList(client(), shoppingList -> {
            final LocalizedString newSlug = randomSlug();
            assertThat(shoppingList.getSlug()).isNotEqualTo(newSlug);
            final ShoppingListUpdateCommand cmd = ShoppingListUpdateCommand.of(shoppingList, SetSlug.of(newSlug));
            final ShoppingList updatedShoppingList = client().executeBlocking(cmd);
            assertThat(updatedShoppingList.getSlug()).isEqualTo(newSlug);
            return updatedShoppingList;
        });
    }

    @Test
    public void setDescription() throws Exception {
        withUpdateableShoppingList(client(), shoppingList -> {
            final LocalizedString newDescription = en(randomString());
            assertThat(shoppingList.getDescription()).isNotEqualTo(newDescription);
            final ShoppingListUpdateCommand cmd = ShoppingListUpdateCommand.of(shoppingList, SetDescription.of(newDescription));
            final ShoppingList updatedShoppingList = client().executeBlocking(cmd);
            assertThat(updatedShoppingList.getDescription()).isEqualTo(newDescription);
            return updatedShoppingList;
        });
    }

    @Test
    public void setKey() throws Exception {
        withUpdateableShoppingList(client(), shoppingList -> {
            final String newKey = randomKey();
            assertThat(shoppingList.getKey()).isNotEqualTo(newKey);
            final ShoppingListUpdateCommand cmd = ShoppingListUpdateCommand.of(shoppingList, SetKey.of(newKey));
            final ShoppingList updatedShoppingList = client().executeBlocking(cmd);
            assertThat(updatedShoppingList.getKey()).isEqualTo(newKey);
            return updatedShoppingList;
        });
    }

    @Test
    public void setCustomer() throws Exception {
        withCustomer(client(), customer -> {
            withUpdateableShoppingList(client(), shoppingList -> {
                assertThat(shoppingList.getCustomer()).isNotEqualTo(customer);
                final ShoppingListUpdateCommand cmd = ShoppingListUpdateCommand.of(shoppingList, SetCustomer.of(customer));
                final ShoppingList updatedShoppingList = client().executeBlocking(cmd);
                assertThat(updatedShoppingList.getCustomer()).isEqualTo(customer.toReference());
                return updatedShoppingList;
            });
        });
    }
}

