package io.sphere.sdk.types.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.types.Type;

final class TypeEndpoint {
    public static final JsonEndpoint<Type> ENDPOINT = JsonEndpoint.of(Type.typeReference(), "/types");
}
