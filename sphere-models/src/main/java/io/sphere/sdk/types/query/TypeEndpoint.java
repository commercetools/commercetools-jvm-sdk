package io.sphere.sdk.types.query;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.types.Type;

final class TypeEndpoint {
    static final JsonEndpoint<Type> ENDPOINT = JsonEndpoint.of(Type.typeReference(), "/types");
}
