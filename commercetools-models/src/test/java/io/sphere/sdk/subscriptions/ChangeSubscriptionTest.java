package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.reviews.Review;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link ChangeSubscription}.
 */
public class ChangeSubscriptionTest {

    @Test
    public void of() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Category.class);
        assertThat(changeSubscription.getResourceTypeId()).isEqualTo(Category.referenceTypeId());
    }

    @Test
    public void serializeWithCart() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Cart.class);
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Cart.referenceTypeId());
    }

    @Test
    public void serializeWithCategory() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Category.class);
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Category.referenceTypeId());
    }

    @Test
    public void serializeWithCustomer() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Customer.class);
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Customer.referenceTypeId());
    }

    @Test
    public void serializeWithInventoryEntry() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(InventoryEntry.class);
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(InventoryEntry.referenceTypeId());
    }

    @Test
    public void serializeWithOrder() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Order.class);
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Order.referenceTypeId());
    }

    @Test
    public void serializeWithPayment() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Payment.class);
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Payment.referenceTypeId());
    }

    @Test
    public void serializeWithProduct() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Product.class);
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Product.referenceTypeId());
    }

    @Test
    public void serializeWithProductType() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(ProductType.class);
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(ProductType.referenceTypeId());
    }

    @Test
    public void serializeWithReview() throws Exception {
        final ChangeSubscription changeSubscription = ChangeSubscription.of(Review.class);
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(changeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Review.referenceTypeId());
    }

    @Test
    public void deserialize() throws Exception {
        ChangeSubscription changeSubscription = SphereJsonUtils.readObject("{\"resourceTypeId\":\"category\"}", ChangeSubscription.class);
        assertThat(changeSubscription.getResourceTypeId()).isEqualTo(Category.resourceTypeId());
        assertThat(changeSubscription.getResourceType()).isEqualTo(Category.class);
    }
}
