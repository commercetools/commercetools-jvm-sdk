package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;

public class ReferenceType extends AttributeTypeBase {

    private final String referenceTypeId;

    protected ReferenceType(final String referenceTypeId) {
        this.referenceTypeId = referenceTypeId;
    }

    public String getReferenceTypeId() {
        return referenceTypeId;
    }

    @JsonIgnore
    public static ReferenceType of(final String referenceTypeId) {
        return new ReferenceType(referenceTypeId);
    }

    public static RichReferenceType<Product> ofProduct() {
        return of("product", new TypeReference<Reference<Product>>() {
        });
    }

    public static RichReferenceType<ProductType> ofProductType() {
        return of("product-type", new TypeReference<Reference<ProductType>>() {
        });
    }

    public static RichReferenceType<Channel> ofChannel() {
        return of("channel", new TypeReference<Reference<Channel>>() {
        });
    }

    public static RichReferenceType<Category> ofCategory() {
        return of("category", new TypeReference<Reference<Category>>() {
        });
    }

    @JsonIgnore
    private static <T> RichReferenceType<T> of(final String referenceTypeId, final TypeReference<Reference<T>> typeReference) {
        return RichReferenceType.of(referenceTypeId, typeReference);
    }
}
