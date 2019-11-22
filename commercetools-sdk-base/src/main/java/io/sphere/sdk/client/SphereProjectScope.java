package io.sphere.sdk.client;

import com.fasterxml.jackson.annotation.JsonValue;
import io.sphere.sdk.models.Base;

public final class SphereProjectScope extends Base implements SphereScope {

    private final String scope;

    /**Grants full access to the APIs for the specified project.*/
    public final static SphereProjectScope MANAGE_PROJECT = SphereProjectScope.of("manage_project");

    /** Grants access to the APIs for creating, modifying and viewing anything related to products in a project. Implies view_products for the same project.*/
    public final static SphereProjectScope MANAGE_PRODUCTS = SphereProjectScope.of("manage_products");

    /** Grants access to the APIs for viewing anything related to products in a project.*/
    public final static SphereProjectScope VIEW_PRODUCTS = SphereProjectScope.of("view_products");

    /** Grants access to the APIs for creating, modifying and viewing anything related to orders in a project. Implies view_orders for the same project.*/
    public final static SphereProjectScope MANAGE_ORDERS = SphereProjectScope.of("manage_orders");

    /** Grants access to the APIs for viewing anything related to orders in a project.*/
    public final static SphereProjectScope VIEW_ORDERS = SphereProjectScope.of("view_orders");

    /** If used with the password flow, grants access to the APIs for creating, modifying and viewing orders and carts of the customer to whom the access token was issued.*/
    public final static SphereProjectScope MANAGE_SHOPPING_LISTS = SphereProjectScope.of("manage_shopping_lists");

    /** Grants access to the APIs for viewing anything related to shopping lists in a project.*/
    public final static SphereProjectScope VIEW_SHOPPING_LISTS = SphereProjectScope.of("view_shopping_lists");

    /** Grants access to the APIs for creating, modifying and viewing anything related to shopping lists in a project. Implies view_shopping_lists for the same project.*/
    public final static SphereProjectScope MANAGE_MY_ORDERS = SphereProjectScope.of("manage_my_orders");

    /** Grants access to the APIs for creating, modifying and viewing anything related to customers in a project. Implies view_customers for the same project.*/
    public final static SphereProjectScope MANAGE_CUSTOMERS = SphereProjectScope.of("manage_customers");

    /** Grants access to the APIs for viewing anything related to customers in a project.*/
    public final static SphereProjectScope VIEW_CUSTOMERS = SphereProjectScope.of("view_customers");

    /** If used with the password flow, grants access to the APIs for creating, modifying and viewing the profile of the customer to whom the access token was issued.*/
    public final static SphereProjectScope MANAGE_MY_PROFILE = SphereProjectScope.of("manage_my_profile");

    /** Grants access to the APIs for creating, modifying and viewing anything related to types in a project.*/
    public final static SphereProjectScope MANAGE_TYPES = SphereProjectScope.of("manage_types");

    /** Grants access to the APIs for viewing anything related to types in a project.*/
    public final static SphereProjectScope VIEW_TYPES = SphereProjectScope.of("view_types");

    /** Grants access to the APIs for creating, modifying and viewing anything related to payments in a project.*/
    public final static SphereProjectScope MANAGE_PAYMENTS = SphereProjectScope.of("manage_payments");

    /** Grants access to the APIs for viewing anything related to payments in a project.*/
    public final static SphereProjectScope VIEW_PAYMENTS = SphereProjectScope.of("view_payments");

    /** Grants access to the access tokens for Anonymous Sessions.*/
    public final static SphereProjectScope CREATE_ANONYMOUS_TOKEN = SphereProjectScope.of("create_anonymous_token");

    /** Grants access to the APIs for creating, modifying and viewing anything related to subscriptions in a project.*/
    public final static SphereProjectScope MANAGE_SUBSCRIPTIONS = SphereProjectScope.of("manage_subscriptions");

    /** Grants access to the API for viewing the project settings.*/
    public final static SphereProjectScope VIEW_PROJECT_SETTINGS = SphereProjectScope.of("view_project_settings");

    /** Grants access to the APIs for creating, modifying and viewing anything related to Extensions in a project*/
    public final static SphereProjectScope MANAGE_EXTENSIONS = SphereProjectScope.of("manage_extensions");

    /** Grants access to the API for modifying and viewing the project settings*/
    public final static SphereProjectScope MANAGE_PROJECT_SETTINGS = SphereProjectScope.of("manage_project_settings");

    /** Grants access to the APIs for creating, modifying and viewing anything related to states in a project.*/
    public final static SphereProjectScope MANAGE_STATES = SphereProjectScope.of("manage_states");

    /** Grants access to the APIs for viewing anything related to states in a project.*/
    public final static SphereProjectScope VIEW_STATES = SphereProjectScope.of("view_states");

    /** Grants access to the APIs for viewing anything related to messages in a project.*/
    public final static SphereProjectScope VIEW_MESSAGES = SphereProjectScope.of("view_messages");

    /** Grants access to the APIs for creating, deleting and viewing api clients */
    public final static SphereProjectScope MANAGE_API_CLIENTS = SphereProjectScope.of("manage_api_clients");

    /** Grants access to the APIs for viewing api clients*/
    public final static SphereProjectScope VIEW_API_CLIENTS = SphereProjectScope.of("view_api_clients");

    /** Grants access to all the APIs for creating, deleting and viewing Discount Codes */
    public final static SphereProjectScope MANAGE_DISCOUNT_CODES = SphereProjectScope.of("manage_discount_codes");

    /** Grants access to the APIs for viewing Discount Codes */
    public final static SphereProjectScope VIEW_DISCOUNT_CODES = SphereProjectScope.of("view_discount_codes");
    
    private SphereProjectScope(final String scope) {
        this.scope = scope;
    }

    public static SphereProjectScope of(final String scope) {
        return new SphereProjectScope(scope);
    }

    @Override
    public String toScopeString() {
        return this.scope;
    }

    public static SphereProjectScope ofScopeString(final String scope) {
        return of(scope);
    }

    @JsonValue
    public String getScope() {
        return scope.toUpperCase();
    }
}
