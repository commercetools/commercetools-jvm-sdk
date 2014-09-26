package io.sphere.sdk.attributes;

public class ReferenceType extends AttributeTypeBase {

    private final String referenceTypeId;

    private ReferenceType(final String referenceTypeId) {
        this.referenceTypeId = referenceTypeId;
    }

    public String getReferenceTypeId() {
        return referenceTypeId;
    }

    public static ReferenceType of(final String referenceTypeId) {
        return new ReferenceType(referenceTypeId);
    }

    public static ReferenceType ofProduct() {
        return of("product");
    }

    public static ReferenceType ofProductType() {
        return of("product-type");
    }

    public static ReferenceType ofChannel() {
        return of("channel");
    }

    public static ReferenceType ofCategory() {
        return of("category");
    }
}
