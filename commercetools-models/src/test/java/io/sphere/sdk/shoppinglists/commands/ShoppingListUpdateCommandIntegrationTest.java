package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.*;
import io.sphere.sdk.shoppinglists.commands.updateactions.*;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListUpdateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void changeName() throws Exception {
        withUpdateableShoppingList(client(), shoppingList -> {
            final String newName = randomString();
            final ShoppingList updatedShoppingList = client().executeBlocking(
                    ShoppingListUpdateCommand.of(shoppingList, ChangeName.of(en(newName))));

            assertThat(updatedShoppingList.getName().get(Locale.ENGLISH)).isEqualTo(newName);

            return updatedShoppingList;
        });
    }

    @Test
    public void setSlug() throws Exception {
        withUpdateableShoppingList(client(), shoppingList -> {
            final LocalizedString newSlug = randomSlug();
            final ShoppingList updatedShoppingList = client().executeBlocking(
                    ShoppingListUpdateCommand.of(shoppingList, SetSlug.of(newSlug)));

            assertThat(updatedShoppingList.getSlug()).isEqualTo(newSlug);

            return updatedShoppingList;
        });
    }

    @Test
    public void setDescription() throws Exception {
        withUpdateableShoppingList(client(), shoppingList -> {
            final LocalizedString newDescription = en(randomString());
            final ShoppingList updatedShoppingList = client().executeBlocking(
                    ShoppingListUpdateCommand.of(shoppingList, SetDescription.of(newDescription)));

            assertThat(updatedShoppingList.getDescription()).isEqualTo(newDescription);

            return updatedShoppingList;
        });
    }

    @Test
    public void setKey() throws Exception {
        withUpdateableShoppingList(client(), shoppingList -> {
            final String newKey = randomKey();
            final ShoppingList updatedShoppingList = client().executeBlocking(
                    ShoppingListUpdateCommand.of(shoppingList, SetKey.of(newKey)));

            assertThat(updatedShoppingList.getKey()).isEqualTo(newKey);

            return updatedShoppingList;
        });
    }

    @Test
    public void setCustomer() throws Exception {
        withCustomer(client(), customer -> {
            withUpdateableShoppingList(client(), shoppingList -> {
                final ShoppingList updatedShoppingList = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, SetCustomer.of(customer)));

                assertThat(updatedShoppingList.getCustomer()).isEqualTo(customer.toReference());

                return updatedShoppingList;
            });
        });
    }

    @Test
    public void setDeleteDaysAfterLastModification() throws Exception {
        withCustomer(client(), customer -> {
            withUpdateableShoppingList(client(), shoppingList -> {
                final int deleteDaysAfterLastModification = 11;
                final ShoppingList updatedShoppingList = client().executeBlocking(
                    ShoppingListUpdateCommand.of(shoppingList, SetDeleteDaysAfterLastModification.of(deleteDaysAfterLastModification)));

                assertThat(updatedShoppingList.getDeleteDaysAfterLastModification()).isEqualTo(deleteDaysAfterLastModification);

                return updatedShoppingList;
            });
        });
    }

    @Test
    public void setStore() throws Exception {
        StoreFixtures.withStore(client(), newStore -> {
            withUpdateableShoppingList(client(), shoppingList -> {
                final ShoppingList updatedShoppingList = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, SetStore.of(newStore.toResourceIdentifier())));
                assertThat(updatedShoppingList.getStore().getKey()).isEqualTo(newStore.getKey());

                return updatedShoppingList;
            });
        });
    }

    @Test
    public void addLineItem() throws Exception {
        withTaxedProduct(client(), product -> {
            withUpdateableShoppingList(client(), shoppingList -> {
                final ShoppingList updatedShoppingList = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, AddLineItem.of(product).withVariantId(1).withQuantity(2L)));

                assertThat(updatedShoppingList.getLineItems()).hasSize(1);

                final LineItem lineItem = updatedShoppingList.getLineItems().get(0);

                assertThat(lineItem.getProductId()).isEqualTo(product.getId());
                assertThat(lineItem.getVariantId()).isEqualTo(1);
                assertThat(lineItem.getQuantity()).isEqualTo(2);
                assertThat(lineItem.getAddedAt()).isNotNull();

                return updatedShoppingList;
            });
        });
    }

    @Test
    public void addLineItemBySku() throws Exception {
        withTaxedProduct(client(), product -> {
            withUpdateableShoppingList(client(), shoppingList -> {
                final ShoppingList updatedShoppingList = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, AddLineItem.ofSku(product.getMasterData().getCurrent().getMasterVariant().getSku()).withQuantity(2L)));

                assertThat(updatedShoppingList.getLineItems()).hasSize(1);

                final LineItem lineItem = updatedShoppingList.getLineItems().get(0);

                assertThat(lineItem.getProductId()).isEqualTo(product.getId());
                assertThat(lineItem.getVariantId()).isEqualTo(1);
                assertThat(lineItem.getQuantity()).isEqualTo(2);
                assertThat(lineItem.getAddedAt()).isNotNull();

                return updatedShoppingList;
            });
        });
    }

    @Test
    public void removeLineItem() throws Exception {
        withTaxedProduct(client(), product -> {
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithLineItem(product, 3L);

            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final LineItem lineItem = shoppingList.getLineItems().get(0);

                final ShoppingList shoppingListWithRemovedLineItem = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, RemoveLineItem.of(lineItem).withQuantity(2L)));

                assertThat(shoppingListWithRemovedLineItem.getLineItems()).hasSize(1);
                final LineItem updatedLineItem = shoppingListWithRemovedLineItem.getLineItems().get(0);
                assertThat(updatedLineItem.getQuantity()).isEqualTo(1L);

                final ShoppingList shoppingListWithoutLineItem = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingListWithRemovedLineItem, RemoveLineItem.of(lineItem).withQuantity(1L)));

                assertThat(shoppingListWithoutLineItem.getLineItems()).isEmpty();

                return shoppingListWithoutLineItem;
            });
        });
    }

    @Test
    public void changeLineItemQuantity() throws Exception {
        withTaxedProduct(client(), product -> {
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithLineItem(product, 3L);

            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final LineItem lineItem = shoppingList.getLineItems().get(0);

                final ShoppingList updatedShoppingList = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, ChangeLineItemQuantity.of(lineItem, 2L)));

                assertThat(updatedShoppingList.getLineItems()).hasSize(1);
                final LineItem updatedTextLineItem = updatedShoppingList.getLineItems().get(0);
                assertThat(updatedTextLineItem.getQuantity()).isEqualTo(2L);

                return updatedShoppingList;
            });
        });
    }

    @Test
    public void addTextLineItem() throws Exception {
        withTaxedProduct(client(), product -> {
            withUpdateableShoppingList(client(), shoppingList -> {
                final LocalizedString name = en(randomString());
                final ShoppingList updatedShoppingList = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, AddTextLineItem.of(name).withQuantity(2L)));

                assertThat(updatedShoppingList.getTextLineItems()).hasSize(1);

                final TextLineItem textLineItem = updatedShoppingList.getTextLineItems().get(0);

                assertThat(textLineItem.getName()).isEqualTo(name);
                assertThat(textLineItem.getQuantity()).isEqualTo(2);
                assertThat(textLineItem.getAddedAt()).isNotNull();

                return updatedShoppingList;
            });
        });
    }

    @Test
    public void removeTextLineItem() throws Exception {
        withTaxedProduct(client(), product -> {
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithTextLineItem(3L);

            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final TextLineItem textLineItemToRemove = shoppingList.getTextLineItems().get(0);

                final ShoppingList shappingListWithRemovedLineItem = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, RemoveTextLineItem.of(textLineItemToRemove).withQuantity(2L)));

                assertThat(shappingListWithRemovedLineItem.getTextLineItems()).hasSize(1);
                final TextLineItem textLineItem = shappingListWithRemovedLineItem.getTextLineItems().get(0);
                assertThat(textLineItem.getQuantity()).isEqualTo(1L);

                final ShoppingList shoppingListWithoutTextLine = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shappingListWithRemovedLineItem, RemoveTextLineItem.of(textLineItem).withQuantity(1L)));

                assertThat(shoppingListWithoutTextLine.getTextLineItems()).isEmpty();

                return shoppingListWithoutTextLine;
            });
        });
    }

    @Test
    public void changeTextLineItemQuantity() throws Exception {
        withTaxedProduct(client(), product -> {
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithTextLineItem(3L);

            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final TextLineItem textLineItem = shoppingList.getTextLineItems().get(0);

                final ShoppingList updatedShoppingList = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, ChangeTextLineItemQuantity.of(textLineItem, 2L)));

                assertThat(updatedShoppingList.getTextLineItems()).hasSize(1);
                final TextLineItem updatedTextLineItem = updatedShoppingList.getTextLineItems().get(0);
                assertThat(updatedTextLineItem.getQuantity()).isEqualTo(2L);

                return updatedShoppingList;
            });
        });
    }

    @Test
    public void changeTextLineItemName() throws Exception {
        withTaxedProduct(client(), product -> {
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithTextLineItem(3L);

            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final TextLineItem textLineItem = shoppingList.getTextLineItems().get(0);

                final LocalizedString newName = en(randomString());
                final ShoppingList updatedShoppingList = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, ChangeTextLineItemName.of(textLineItem, newName)));

                assertThat(updatedShoppingList.getTextLineItems()).hasSize(1);
                final TextLineItem updatedTextLineItem = updatedShoppingList.getTextLineItems().get(0);
                assertThat(updatedTextLineItem.getName()).isEqualTo(newName);

                return updatedShoppingList;
            });
        });
    }

    @Test
    public void setTextLineItemDescription() throws Exception {
        withTaxedProduct(client(), product -> {
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithTextLineItem(3L);

            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final TextLineItem textLineItem = shoppingList.getTextLineItems().get(0);

                final LocalizedString newDescription = en(randomString());
                final ShoppingList updatedShoppingList = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, SetTextLineItemDescription.of(textLineItem).withDescription(newDescription)));

                assertThat(updatedShoppingList.getTextLineItems()).hasSize(1);
                final TextLineItem updatedTextLineItem = updatedShoppingList.getTextLineItems().get(0);
                assertThat(updatedTextLineItem.getDescription()).isEqualTo(newDescription);

                return updatedShoppingList;
            });
        });
    }

    @Test
    public void changeTextLineItemsOrder() throws Exception {
        final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithTextLineItems();

        withShoppingList(client(), shoppingListDraft, shoppingList -> {
            final List<String> newTextLineItemOrder = shoppingList.getTextLineItems()
                    .stream().map(TextLineItem::getId).collect(Collectors.toList());
            Collections.reverse(newTextLineItemOrder);

            final ShoppingList updatedShoppingList = client().executeBlocking(
                    ShoppingListUpdateCommand.of(shoppingList, ChangeTextLineItemsOrder.of(newTextLineItemOrder)));

            final List<String> updatedTextLineItemOrder = updatedShoppingList.getTextLineItems()
                    .stream().map(TextLineItem::getId).collect(Collectors.toList());
            assertThat(updatedTextLineItemOrder).isEqualTo(newTextLineItemOrder);

            return updatedShoppingList;
        });
    }

    @Test
    public void changeLineItemsOrder() throws Exception {
        withTaxedProduct(client(), product -> {
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithLineItems(product);

            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final List<String> newListItemOrder = shoppingList.getLineItems().
                        stream().map(LineItem::getId).collect(Collectors.toList());
                Collections.reverse(newListItemOrder);

                final ShoppingList updatedShoppingList = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, ChangeLineItemsOrder.of(newListItemOrder)));

                final List<String> updateLineItemOrder = updatedShoppingList.getLineItems()
                        .stream().map(LineItem::getId).collect(Collectors.toList());
                assertThat(updateLineItemOrder).isEqualTo(newListItemOrder);

                return updatedShoppingList;
            });
        });
    }
}

