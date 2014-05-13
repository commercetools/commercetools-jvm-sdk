package io.sphere.client.exceptions;

public class DuplicateSkuException  extends SphereException {
    public DuplicateSkuException(String sku) {
        super("SKU already in use: " + sku);
    }
}
