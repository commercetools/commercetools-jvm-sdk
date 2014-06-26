package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.producttypes.attributes.AttributeDefinition;

import java.util.List;

@JsonDeserialize(as=ProductTypeImpl.class)
public interface ProductType extends DefaultModel {

    String getName();

    String getDescription();

    List<AttributeDefinition> getAttributes();
}
