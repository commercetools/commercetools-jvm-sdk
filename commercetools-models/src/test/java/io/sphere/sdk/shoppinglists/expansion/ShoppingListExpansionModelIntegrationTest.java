package io.sphere.sdk.shoppinglists.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.shoppinglists.LineItem;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.ShoppingListDraftDsl;
import io.sphere.sdk.shoppinglists.queries.ShoppingListByIdGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListExpansionModelIntegrationTest extends IntegrationTest {

    @Test
    public void lineItemsProductSlug() {
        withTaxedProduct(client(), product -> {
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithLineItems(product);
            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final ExpansionPathContainer<ShoppingList> productSlugExpansion = ShoppingListExpansionModel.of().lineItems().productSlug();
                final ShoppingList fetchedShoppingList = client().executeBlocking(ShoppingListByIdGet.of(shoppingList.getId()).
                        withExpansionPaths(productSlugExpansion));

                final List<LineItem> lineItems = fetchedShoppingList.getLineItems();
                assertThat(lineItems.size()).isEqualTo(3);

                lineItems.forEach(lineItem -> assertThat(lineItem.getProductSlug()).isNotNull());

                return fetchedShoppingList;
            });
        });
    }

    @Test
    public void lineItemsProductType() {
        withTaxedProduct(client(), product -> {
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithLineItems(product);
            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final ExpansionPathContainer<ShoppingList> productTypeExpansion = ShoppingListExpansionModel.of().lineItems().productType();
                final ShoppingList fetchedShoppingList = client().executeBlocking(ShoppingListByIdGet.of(shoppingList.getId()).
                        withExpansionPaths(productTypeExpansion));

                assertThat(fetchedShoppingList.getLineItems().size()).isEqualTo(3);
                final LineItem lineItem = fetchedShoppingList.getLineItems().get(0);

                assertThat(lineItem.getProductType()).isNotNull();
                assertThat(lineItem.getProductType().getObj()).isNotNull();

                return fetchedShoppingList;
            });
        });
    }

    @Test
    public void lineItemsVariant() {
        withTaxedProduct(client(), product -> {
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftWithLineItems(product);
            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final ExpansionPathContainer<ShoppingList> productVariantExpansion = ShoppingListExpansionModel.of().lineItems().variant();
                final ShoppingList fetchedShoppingList = client().executeBlocking(ShoppingListByIdGet.of(shoppingList.getId()).
                        withExpansionPaths(productVariantExpansion));

                assertThat(fetchedShoppingList.getLineItems().size()).isEqualTo(3);
                final LineItem lineItem = fetchedShoppingList.getLineItems().get(0);

                assertThat(lineItem.getVariant()).isNotNull();

                return fetchedShoppingList;
            });
        });
    }

    @Test
    public void customer() {
        withCustomer(client(), customer -> {
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftBuilder().customer(customer).build();
            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final ExpansionPathContainer<ShoppingList> customerExpansion = ShoppingListExpansionModel.of().customer();
                final ShoppingList fetchedShoppingList = client().executeBlocking(ShoppingListByIdGet.of(shoppingList.getId()).
                        withExpansionPaths(customerExpansion));

                assertThat(fetchedShoppingList.getCustomer()).isNotNull();
                assertThat(fetchedShoppingList.getCustomer().getObj()).isNotNull();

                return fetchedShoppingList;
            });
        });
    }
}
