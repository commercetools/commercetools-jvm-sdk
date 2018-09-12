package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.extensions.Extension;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.states.State;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.types.Type;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link ChangeSubscription}.
 */
public class ChangeSubscriptionTest {

    @Test
    public void of() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Category.resourceTypeId());
        assertThat(changeSubscription.getResourceTypeId()).isEqualTo(Category.resourceTypeId());
    }

    @Test
    public void serializeWithCart() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Cart.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Cart.resourceTypeId());
    }

    @Test
    public void serializeWithCategory() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Category.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Category.resourceTypeId());
    }

    @Test
    public void serializeWithCustomer() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Customer.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Customer.resourceTypeId());
    }

    @Test
    public void serializeWithInventoryEntry() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(InventoryEntry.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(InventoryEntry.resourceTypeId());
    }

    @Test
    public void serializeWithOrder() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Order.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Order.resourceTypeId());
    }

    @Test
    public void serializeWithPayment() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Payment.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Payment.resourceTypeId());
    }

    @Test
    public void serializeWithProduct() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Product.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Product.resourceTypeId());
    }

    @Test
    public void serializeWithProductType() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(ProductType.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(ProductType.resourceTypeId());
    }

    @Test
    public void serializeWithReview() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Review.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Review.resourceTypeId());
    }


    @Test
    public void serializeWithCartDiscount() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(CartDiscount.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(CartDiscount.resourceTypeId());
    }


    @Test
    public void serializeWithChannel() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Channel.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Channel.resourceTypeId());
    }


    @Test
    public void serializeWithDiscountCode() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(DiscountCode.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(DiscountCode.resourceTypeId());
    }


    @Test
    public void serializeWithExtension() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Extension.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Extension.resourceTypeId());
    }


    @Test
    public void serializeWithProductDiscount() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(ProductDiscount.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(ProductDiscount.resourceTypeId());
    }


    @Test
    public void serializeWithShoppingList() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(ShoppingList.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(ShoppingList.resourceTypeId());
    }


    @Test
    public void serializeWithSubscription() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Subscription.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Subscription.resourceTypeId());
    }


    @Test
    public void serializeWithState() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(State.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(State.resourceTypeId());
    }


    @Test
    public void serializeWithTaxCategory() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(TaxCategory.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(TaxCategory.resourceTypeId());
    }


    @Test
    public void serializeWithType() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Type.resourceTypeId());
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Type.resourceTypeId());
    }

    @Test
    public void deserialize() throws Exception {
        ChangeSubscription changeSubscription = SphereJsonUtils.readObject("{\"resourceTypeId\":\"category\"}", ChangeSubscription.class);
        assertThat(changeSubscription.getResourceTypeId()).isEqualTo(Category.resourceTypeId());
    }
}
