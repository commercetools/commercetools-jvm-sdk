package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.messages.GenericMessage;
import io.sphere.sdk.models.TypeRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonDeserialize(as = MessageSubscriptionImpl.class)
@ResourceValue
public interface MessageSubscription {
    @JsonProperty("resourceTypeId")
    String getResourceTypeId();

    @JsonIgnore
    default <R> Class<R> getResourceType() {
        return TypeRegistry.of().toClass(getResourceTypeId());
    }

    @JsonProperty("types")
    @Nullable
    List<String> getTypeNames();

    @JsonIgnore
    default List<Class<?>> getTypes() {
        return TypeRegistry.of().toClasses(getTypeNames());
    }

    /**
     * This builder method adds the given message type to a new copy of this subscription.
     *
     * @param messageType the message type
     *
     * @return a copy of this object with the added message type
     */
    default <R> MessageSubscription addType(final Class<? extends GenericMessage<R>> messageType) {
        final String type = TypeRegistry.of().toType(messageType);
        final List<String> types = Stream.concat(getTypeNames().stream(), Stream.of(type)).collect(Collectors.toList());

        return new MessageSubscriptionImpl(getResourceTypeId(), types);
    }

    /**
     * This builder method removes the given message type from a new copy of this subscription.
     *
     * @param messageType the message type
     *
     * @return a copy of this object with the removed message type
     */
    default <R> MessageSubscription removeType(final Class<? extends GenericMessage<R>> messageType) {
        final String type = TypeRegistry.of().toType(messageType);
        final List<String> removedTypes = getTypeNames().stream()
                .filter(t -> !t.equals(type))
                .collect(Collectors.toList());

        return new MessageSubscriptionImpl(getResourceTypeId(), removedTypes);
    }

    @SafeVarargs
    static <R> MessageSubscription of(final Class<R> resoureClass, final Class<? extends GenericMessage<R>>... messageClasses) {
        final List<String> types  = TypeRegistry.of().toTypes(messageClasses);

        final String resourceTypeId = TypeRegistry.of().toType(resoureClass);

        return new MessageSubscriptionImpl(resourceTypeId, types);
    }
}
