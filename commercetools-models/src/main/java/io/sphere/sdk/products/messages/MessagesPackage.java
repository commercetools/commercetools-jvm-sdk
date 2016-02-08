package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;

final class MessagesPackage {
    public static final TypeReference<Reference<Product>> PRODUCT_REFERENCE_TYPE_REFERENCE = new TypeReference<Reference<Product>>() {
        @Override
        public String toString() {
            return "TypeReference<Reference<Product>>";
        }
    };

    private MessagesPackage() {
    }
}
