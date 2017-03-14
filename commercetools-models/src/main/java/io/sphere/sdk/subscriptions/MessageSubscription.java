package io.sphere.sdk.subscriptions;

import io.sphere.sdk.annotations.MessageTypeInfo;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.messages.GenericMessage;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ResourceValue
public interface MessageSubscription {
    String getResourceTypeId();

    @Nullable
    List<String> getTypes();

    @SafeVarargs
    static <T> MessageSubscription of(final Class<? extends GenericMessage<T>> messageClass,
                                      final Class<? extends GenericMessage<T>>... messageClasses) {
        final boolean allTypeInfosAvailable = Stream.concat(Stream.of(messageClass), Stream.of(messageClasses))
                .allMatch(m -> m.getAnnotation(MessageTypeInfo.class) != null);

        Validate.isTrue(allTypeInfosAvailable, "Not all provided message types are annotated with {}",
                MessageTypeInfo.class.getCanonicalName());

        final List<MessageTypeInfo> typeInfos = Stream.concat(Stream.of(messageClass), Stream.of(messageClasses))
                .map(m -> m.getAnnotation(MessageTypeInfo.class))
                .collect(Collectors.toList());

        final String referenceType = typeInfos.stream()
                .map(MessageTypeInfo::resourceType)
                .findFirst()
                .orElse(null);

        final List<String> types = typeInfos.stream()
                .map(MessageTypeInfo::type)
                .collect(Collectors.toList());

        return new MessageSubscriptionImpl(Objects.requireNonNull(referenceType), types);
    }
}
