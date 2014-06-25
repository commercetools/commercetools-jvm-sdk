package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.DefaultModel;

import java.util.List;

public interface ProductType extends DefaultModel {

    String getName();

    String getDescription();

    List<AttributeDefinition> getAttributes();
}
