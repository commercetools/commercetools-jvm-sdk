package io.sphere.sdk.client;

import com.fasterxml.jackson.annotation.JsonValue;

public class SphereProjectScope implements SphereScope {

    private final String scope;

    public final static SphereProjectScope MANAGE_PROJECT = SphereProjectScope.of("manage_project");
    public final static SphereProjectScope MANAGE_PRODUCTS = SphereProjectScope.of("manage_products");
    public final static SphereProjectScope VIEW_PRODUCTS = SphereProjectScope.of("view_products");
    public final static SphereProjectScope MANAGE_ORDERS = SphereProjectScope.of("manage_orders");
    public final static SphereProjectScope VIEW_ORDERS = SphereProjectScope.of("view_orders");
    public final static SphereProjectScope MANAGE_MY_ORDERS = SphereProjectScope.of("manage_my_orders");
    //manage_shopping_lists
    //view_shopping_lists
    public final static SphereProjectScope MANAGE_CUSTOMERS = SphereProjectScope.of("manage_customers");
    public final static SphereProjectScope VIEW_CUSTOMERS = SphereProjectScope.of("view_customers");
    public final static SphereProjectScope MANAGE_MY_PROFILE = SphereProjectScope.of("manage_my_profile");
    public final static SphereProjectScope MANAGE_TYPES = SphereProjectScope.of("manage_types");
    public final static SphereProjectScope VIEW_TYPES = SphereProjectScope.of("view_types");
    public final static SphereProjectScope MANAGE_PAYMENTS = SphereProjectScope.of("manage_payments");
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
