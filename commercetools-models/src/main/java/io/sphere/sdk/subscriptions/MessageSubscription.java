package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.messages.GenericMessage;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ResourceValue
public interface MessageSubscription<R> {
    String getResourceTypeId();

    @Nullable
    List<String> getTypes();

    /**
     * This builder method adds the given message type to a new copy of this subscription.
     *
     * @param messageType the message type
     *
     * @return a copy of this object with the added message type
     */
    default MessageSubscription<R> addType(final Class<? extends GenericMessage<R>> messageType) {
        final String type = messageType.getAnnotation(JsonTypeName.class).value();
        final List<String> types = Stream.concat(getTypes().stream(), Stream.of(type)).collect(Collectors.toList());
        return new MessageSubscriptionImpl(getResourceTypeId(), types);
    }

    /**
     * This builder method removes the given message type from a new copy of this subscription.
     *
     * @param messageType the message type
     *
     * @return a copy of this object with the removed message type
     */
    default MessageSubscription<R> removeType(final Class<? extends GenericMessage<R>> messageType) {
        final String type = messageType.getAnnotation(JsonTypeName.class).value();
        final List<String> removedTypes = getTypes().stream()
                .filter(t -> !t.equals(type))
                .collect(Collectors.toList());

        return new MessageSubscriptionImpl(getResourceTypeId(), removedTypes);
    }

    @SafeVarargs
    static <R> MessageSubscription<R> of(final Class<R> resoureClass, final Class<? extends GenericMessage<R>>... messageClasses) {
        final boolean allTypeInfosAvailable = Stream.of(messageClasses)
                .allMatch(m -> m.getAnnotation(JsonTypeName.class) != null);

        Validate.isTrue(allTypeInfosAvailable, "Not all provided message types are annotated with {}",
                JsonTypeName.class.getCanonicalName());

        final List<String> types  = Stream.of(messageClasses)
                .map(m -> m.getAnnotation(JsonTypeName.class))
                .map(JsonTypeName::value)
                .collect(Collectors.toList());

        final String resourceTypeId = resoureClass.getAnnotation(JsonTypeName.class).value();

        return new MessageSubscriptionImpl(resourceTypeId, types);
    }
}
