package io.sphere.sdk.models;

import com.fasterxml.jackson.core.type.TypeReference;

public class TypeReferenceImpl extends TypeReference<Reference<TestEntity>> {

    private TypeReferenceImpl(){}

    public static TypeReferenceImpl of(){return new TypeReferenceImpl();}


}
