package io.sphere.sdk.products;

public enum ProductProjectionType {
    CURRENT, STAGED;

    public Boolean isStaged() {
        return this == STAGED;
    }
}
