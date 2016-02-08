package io.sphere.sdk.messages;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.messages.queries.MessageQueryModel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.queries.QueryPredicate;

import java.util.function.Supplier;

/**
 * TypeReference container used by query to get only messages of a certain type.
 * @param <T> the type of the message the type hint is about
 * @see MessageQuery#forMessageType(io.sphere.sdk.messages.MessageDerivateHint)
 */
public final class MessageDerivateHint<T> extends Base {
    private final JavaType javaType;
    private final Supplier<QueryPredicate<Message>> predicateSupplier;

    private MessageDerivateHint(final JavaType javaType, final Supplier<QueryPredicate<Message>> predicateSupplier) {
        this.javaType = javaType;
        this.predicateSupplier = predicateSupplier;
    }

    public QueryPredicate<Message> predicate() {
        return predicateSupplier.get();
    }

    private static <T> MessageDerivateHint<T> ofSingleMessageType(final String type, final JavaType javaType) {
        return new MessageDerivateHint<>(javaType, () -> MessageQueryModel.of().type().is(type));
    }

    public static <T> MessageDerivateHint<T> ofSingleMessageType(final String type, final Class<T> clazz) {
        return ofSingleMessageType(type, SphereJsonUtils.convertToJavaType(clazz));
    }

    public static <T> MessageDerivateHint<T> ofResourceType(final String resourceId, final Class<T> clazz) {
        return new MessageDerivateHint<>(SphereJsonUtils.convertToJavaType(clazz), () -> MessageQueryModel.of().resource().typeId().is(resourceId));
    }

    public JavaType javaType() {
        return javaType;
    }
}
