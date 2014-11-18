package io.sphere.sdk.products;

public enum ProductUpdateScope {
    STAGED_AND_CURRENT, ONLY_STAGED;

    public Boolean isOnlyStaged() {
        return this == ONLY_STAGED;
    }
}
