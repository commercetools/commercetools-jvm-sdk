package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.producttypes.attributes.PlainEnumValue;

@JsonDeserialize(as = AttributeImpl.class)
public interface Attribute {
    String getName();

    //TODO implement generic accessor for value


    //TODO check if this method is necessary
    public static TypedAttribute<PlainEnumValue> ofEnum(final String name, final PlainEnumValue value) {
        return new AttributeImpl<>(name, value);
    }

    public static <T> Attribute of(final String name, final T value) {
        return new AttributeImpl<>(name, value);
    }
}
