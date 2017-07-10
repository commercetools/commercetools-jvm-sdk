package io.sphere.sdk.client;

import com.fasterxml.jackson.annotation.JsonValue;
import io.sphere.sdk.models.Base;

public final class SphereProjectScope extends Base implements SphereScope {

    private final String scope;

    @Deprecated
    /**Grants full access to the APIs for the specified project.*/
    public final static SphereProjectScope MANAGE_PROJECT = SphereProjectScope.of("manage_project");

    @Deprecated
    /** Grants access to the APIs for creating, modifying and viewing anything related to products in a project. Implies view_products for the same project.*/
    public final static SphereProjectScope MANAGE_PRODUCTS = SphereProjectScope.of("manage_products");

    @Deprecated
    /** Grants access to the APIs for viewing anything related to products in a project.*/
    public final static SphereProjectScope VIEW_PRODUCTS = SphereProjectScope.of("view_products");

    @Deprecated
    /** Grants access to the APIs for creating, modifying and viewing anything related to orders in a project. Implies view_orders for the same project.*/
    public final static SphereProjectScope MANAGE_ORDERS = SphereProjectScope.of("manage_orders");

    @Deprecated
    /** Grants access to the APIs for viewing anything related to orders in a project.*/
    public final static SphereProjectScope VIEW_ORDERS = SphereProjectScope.of("view_orders");

    @Deprecated
    /** If used with the password flow, grants access to the APIs for creating, modifying and viewing orders and carts of the customer to whom the access token was issued.*/
    public final static SphereProjectScope MANAGE_MY_ORDERS = SphereProjectScope.of("manage_my_orders");

    @Deprecated
    /** Grants access to the APIs for creating, modifying and viewing anything related to customers in a project. Implies view_customers for the same project.*/
    public final static SphereProjectScope MANAGE_CUSTOMERS = SphereProjectScope.of("manage_customers");

    @Deprecated
    /** Grants access to the APIs for viewing anything related to customers in a project.*/
    public final static SphereProjectScope VIEW_CUSTOMERS = SphereProjectScope.of("view_customers");

    @Deprecated
    /** If used with the password flow, grants access to the APIs for creating, modifying and viewing the profile of the customer to whom the access token was issued.*/
    public final static SphereProjectScope MANAGE_MY_PROFILE = SphereProjectScope.of("manage_my_profile");

    @Deprecated
    /** Grants access to the APIs for creating, modifying and viewing anything related to types in a project.*/
    public final static SphereProjectScope MANAGE_TYPES = SphereProjectScope.of("manage_types");

    @Deprecated
    /** Grants access to the APIs for viewing anything related to types in a project.*/
    public final static SphereProjectScope VIEW_TYPES = SphereProjectScope.of("view_types");

    @Deprecated
    /** Grants access to the APIs for creating, modifying and viewing anything related to payments in a project.*/
    public final static SphereProjectScope MANAGE_PAYMENTS = SphereProjectScope.of("manage_payments");

    @Deprecated
    /** Grants access to the APIs for viewing anything related to payments in a project.*/
    public final static SphereProjectScope VIEW_PAYMENTS = SphereProjectScope.of("view_payments");

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
