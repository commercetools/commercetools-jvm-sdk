package io.sphere.sdk.messages;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.messages.queries.MessageQueryModel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.queries.QueryPredicate;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * TypeReference container used by query to get only messages of a certain type.
 * @param <T> the type of the message the type hint is about
 * @see MessageQuery#forMessageType(io.sphere.sdk.messages.MessageDerivateHint)
 */
public final class MessageDerivateHint<T extends Message> extends Base {
    private final JavaType javaType;
    private final Class<? extends Message> clazz;
    private final Supplier<QueryPredicate<Message>> predicateSupplier;
    @Nullable
    private final String typeString;
    @Nullable
    private final String referenceTypeId;

    private MessageDerivateHint(final JavaType javaType, final Supplier<QueryPredicate<Message>> predicateSupplier, final Class<? extends Message> clazz, final String typeString, final String referenceTypeId) {
        this.javaType = javaType;
        this.predicateSupplier = predicateSupplier;
        this.clazz = clazz;
        this.typeString = typeString;
        this.referenceTypeId = referenceTypeId;
    }

    public QueryPredicate<Message> predicate() {
        return predicateSupplier.get();
    }

    private static <T extends Message> MessageDerivateHint<T> ofSingleMessageType(final String type, final JavaType javaType, final Class<T> clazz, final String referenceTypeId) {
        return new MessageDerivateHint<>(javaType, () -> MessageQueryModel.of().type().is(type), clazz, type, referenceTypeId);
    }

    public static <T extends Message> MessageDerivateHint<T> ofSingleMessageType(final String type, final Class<T> clazz, final String referenceTypeId) {
        return ofSingleMessageType(type, SphereJsonUtils.convertToJavaType(clazz), clazz, referenceTypeId);
    }

    public static <T extends Message> MessageDerivateHint<T> ofResourceType(final String resourceId, final Class<T> clazz, final String referenceTypeId) {
        return new MessageDerivateHint<>(SphereJsonUtils.convertToJavaType(clazz), () -> MessageQueryModel.of().resource().typeId().is(resourceId), clazz, null, referenceTypeId);
    }

    public JavaType javaType() {
        return javaType;
    }

    public Class<? extends Message> clazz() {
        return clazz;
    }

    @Nullable
    public String typeString() {
        return typeString;
    }

    @Nullable
    public String resourceReferenceTypeId() {
        return referenceTypeId;
    }
}
