package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link ChangeSubscription}.
 */
public class ChangeSubscriptionTest {

    @Test
    public void of() throws Exception {
        final ChangeSubscription categoryChangeSubscription = ChangeSubscription.of(Category.class);
        assertThat(categoryChangeSubscription.getResourceTypeId()).isEqualTo(Category.referenceTypeId());
    }

    @Test
    public void serialize() throws Exception {
        final ChangeSubscription categoryChangeSubscription = ChangeSubscription.of(Category.class);
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(categoryChangeSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Category.referenceTypeId());
    }

    @Test
    public void deserialize() throws Exception {
        ChangeSubscription changeSubscription = SphereJsonUtils.readObject("{\"resourceTypeId\":\"category\"}", ChangeSubscription.class);
        assertThat(changeSubscription.getResourceTypeId()).isEqualTo(Category.resourceTypeId());
    }
}
