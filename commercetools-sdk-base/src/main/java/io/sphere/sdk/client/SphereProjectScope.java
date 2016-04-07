package io.sphere.sdk.client;

public enum SphereProjectScope implements SphereScope {

    /**Grants full access to the APIs for the specified project.*/
    MANAGE_PROJECT,

    /** Grants access to the APIs for creating, modifying and viewing anything related to products in a project. Implies view_products for the same project.*/
    MANAGE_PRODUCTS,

    /** Grants access to the APIs for viewing anything related to products in a project.*/
    VIEW_PRODUCTS,

    /** Grants access to the APIs for creating, modifying and viewing anything related to orders in a project. Implies view_orders for the same project.*/
    MANAGE_ORDERS,

    /** Grants access to the APIs for viewing anything related to orders in a project.*/
    VIEW_ORDERS,

    /** If used with the password flow, grants access to the APIs for creating, modifying and viewing orders and carts of the customer to whom the access token was issued.*/
    MANAGE_MY_ORDERS,

    /** Grants access to the APIs for creating, modifying and viewing anything related to customers in a project. Implies view_customers for the same project.*/
    MANAGE_CUSTOMERS,

    /** Grants access to the APIs for viewing anything related to customers in a project.*/
    VIEW_CUSTOMERS,

    /** If used with the password flow, grants access to the APIs for creating, modifying and viewing the profile of the customer to whom the access token was issued.*/
    MANAGE_MY_PROFILE,

    /** Grants access to the APIs for creating, modifying and viewing anything related to types in a project.*/
    MANAGE_TYPES,

    /** Grants access to the APIs for viewing anything related to types in a project.*/
    VIEW_TYPES,

    /** Grants access to the APIs for creating, modifying and viewing anything related to payments in a project.*/
    MANAGE_PAYMENTS,

    /** Grants access to the APIs for viewing anything related to payments in a project.*/
    VIEW_PAYMENTS;

    @Override
    public String toScopeString() {
        return name().toLowerCase();
    }

    public static SphereProjectScope ofScopeString(final String scope) {
        return valueOf(scope.toUpperCase());
    }
}
