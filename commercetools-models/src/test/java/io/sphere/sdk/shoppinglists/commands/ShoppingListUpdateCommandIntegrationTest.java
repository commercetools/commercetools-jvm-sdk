package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Locale;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
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

    @Test
    public void addLineItem() throws Exception {
        withTaxedProduct(client(), product -> {
            withUpdateableShoppingList(client(), shoppingList -> {
                final ShoppingListUpdateCommand cmd = ShoppingListUpdateCommand.of(shoppingList, AddLineItem.of(product, 1 , 2));
                final ShoppingList updatedShoppingList = client().executeBlocking(cmd);

                assertThat(updatedShoppingList.getLineItems().size()).isEqualTo(1);
                final LineItem lineItem = updatedShoppingList.getLineItems().get(0);
                assertThat(lineItem.getProductId()).isEqualTo(product.getId());
                assertThat(lineItem.getQuantity()).isEqualTo(2);

                return updatedShoppingList;
            });
        });
    }

    @Test
    public void removeLineItem() throws Exception {
        withTaxedProduct(client(), product -> {
            withUpdateableShoppingList(client(), shoppingList -> {
                final ShoppingListUpdateCommand addLineItemCmd = ShoppingListUpdateCommand.of(shoppingList, AddLineItem.of(product, 1 , 3));
                final ShoppingList updatedShoppingList1 = client().executeBlocking(addLineItemCmd);
                assertThat(updatedShoppingList1.getLineItems().size()).isEqualTo(1);
                final LineItem lineItem1 = updatedShoppingList1.getLineItems().get(0);

                final ShoppingListUpdateCommand removeLineItemCmd1 = ShoppingListUpdateCommand.of(updatedShoppingList1, RemoveLineItem.of(lineItem1, 2L));
                final ShoppingList updatedShoppingList2 = client().executeBlocking(removeLineItemCmd1);

                assertThat(updatedShoppingList2.getLineItems().size()).isEqualTo(1);
                final LineItem lineItem2 = updatedShoppingList2.getLineItems().get(0);
                assertThat(lineItem2.getQuantity()).isEqualTo(1L);

                final ShoppingListUpdateCommand removeLineItemCmd2 = ShoppingListUpdateCommand.of(updatedShoppingList2, RemoveLineItem.of(lineItem1, 1L));
                final ShoppingList updatedShoppingList3 = client().executeBlocking(removeLineItemCmd2);

                assertThat(updatedShoppingList3.getLineItems().size()).isEqualTo(0);

                return updatedShoppingList3;
            });
        });
    }
}

