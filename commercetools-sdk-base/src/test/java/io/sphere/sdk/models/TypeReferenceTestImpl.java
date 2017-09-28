package io.sphere.sdk.models;

import com.fasterxml.jackson.core.type.TypeReference;

public class TypeReferenceTestImpl extends TypeReference<Reference<TestEntity>> {

    private TypeReferenceTestImpl(){}

    public static TypeReferenceTestImpl of(){return new TypeReferenceTestImpl();}


}
