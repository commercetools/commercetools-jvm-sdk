package io.sphere.sdk.client;

import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import static io.sphere.sdk.client.SphereProjectScope.*;
import static org.assertj.core.api.Assertions.assertThat;

public class SphereProjectScopeTest {

    @Test
    public void toSphereScope() {
        assertThat(MANAGE_PROJECT.toScopeString()).isEqualTo("manage_project");
        assertThat(MANAGE_PRODUCTS.toScopeString()).isEqualTo("manage_products");
        assertThat(VIEW_PRODUCTS.toScopeString()).isEqualTo("view_products");
        assertThat(MANAGE_ORDERS.toScopeString()).isEqualTo("manage_orders");
        assertThat(VIEW_ORDERS.toScopeString()).isEqualTo("view_orders");
        assertThat(MANAGE_MY_ORDERS.toScopeString()).isEqualTo("manage_my_orders");
        assertThat(MANAGE_SHOPPING_LISTS.toScopeString()).isEqualTo("manage_shopping_lists");
        assertThat(VIEW_SHOPPING_LISTS.toScopeString()).isEqualTo("view_shopping_lists");
        assertThat(MANAGE_CUSTOMERS.toScopeString()).isEqualTo("manage_customers");
        assertThat(VIEW_CUSTOMERS.toScopeString()).isEqualTo("view_customers");
        assertThat(MANAGE_MY_PROFILE.toScopeString()).isEqualTo("manage_my_profile");
        assertThat(MANAGE_TYPES.toScopeString()).isEqualTo("manage_types");
        assertThat(VIEW_TYPES.toScopeString()).isEqualTo("view_types");
        assertThat(MANAGE_PAYMENTS.toScopeString()).isEqualTo("manage_payments");
        assertThat(VIEW_PAYMENTS.toScopeString()).isEqualTo("view_payments");
        assertThat(CREATE_ANONYMOUS_TOKEN.toScopeString()).isEqualTo("create_anonymous_token");
        assertThat(MANAGE_SUBSCRIPTIONS.toScopeString()).isEqualTo("manage_subscriptions");
        assertThat(VIEW_PROJECT_SETTINGS.toScopeString()).isEqualTo("view_project_settings");
        assertThat(MANAGE_STATES.toScopeString()).isEqualTo("manage_states");
        assertThat(VIEW_STATES.toScopeString()).isEqualTo("view_states");
        assertThat(VIEW_MESSAGES.toScopeString()).isEqualTo("view_messages");
        assertThat(MANAGE_PROJECT_SETTINGS.toScopeString()).isEqualTo("manage_project_settings");
        assertThat(MANAGE_EXTENSIONS.toScopeString()).isEqualTo("manage_extensions");
        assertThat(MANAGE_DISCOUNT_CODES.toScopeString()).isEqualTo("manage_discount_codes");
        assertThat(VIEW_DISCOUNT_CODES.toScopeString()).isEqualTo("view_discount_codes");
    }

    @Test
    public void ofScopeString() {
        assertThat(SphereProjectScope.ofScopeString("manage_project")).isEqualTo(MANAGE_PROJECT);
    }

    @Test
    public void scopeSerialization() {
        final String serializedScope = SphereJsonUtils.toPrettyJsonString(SphereProjectScope.ofScopeString("manage_project"));
        assertThat(serializedScope).isEqualTo("\"MANAGE_PROJECT\"");
    }

}