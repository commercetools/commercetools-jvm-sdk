package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;

public class ReferenceAttributeType extends AttributeTypeBase {

    private final String referenceTypeId;

    protected ReferenceAttributeType(final String referenceTypeId) {
        this.referenceTypeId = referenceTypeId;
    }

    public String getReferenceTypeId() {
        return referenceTypeId;
    }

    @JsonIgnore
    public static ReferenceAttributeType of(final String referenceTypeId) {
        return new ReferenceAttributeType(referenceTypeId);
    }

    public static RichReferenceAttributeType<Product> ofProduct() {
        return of("product", new TypeReference<Reference<Product>>() {
        });
    }

    public static RichReferenceAttributeType<ProductType> ofProductType() {
        return of("product-type", new TypeReference<Reference<ProductType>>() {
        });
    }

    public static RichReferenceAttributeType<Channel> ofChannel() {
        return of("channel", new TypeReference<Reference<Channel>>() {
        });
    }

    public static RichReferenceAttributeType<Category> ofCategory() {
        return of("category", new TypeReference<Reference<Category>>() {
        });
    }

    @JsonIgnore
    private static <T> RichReferenceAttributeType<T> of(final String referenceTypeId, final TypeReference<Reference<T>> typeReference) {
        return RichReferenceAttributeType.of(referenceTypeId, typeReference);
    }
}
