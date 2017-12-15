package io.sphere.sdk.messages.queries;

import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.client.SphereRequestDecorator;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

final class MultiTypedMessageQuery extends SphereRequestDecorator<PagedQueryResult<Message>> implements Query<Message> {
    private final List<MessageDerivateHint<? extends Message>> messageHints;

    protected MultiTypedMessageQuery(final SphereRequest<PagedQueryResult<Message>> delegate, final List<MessageDerivateHint<? extends Message>> messageHints) {
        super(delegate);
        this.messageHints = messageHints;
    }

    @Override
    public PagedQueryResult<Message> deserialize(final HttpResponse httpResponse) {
        final PagedQueryResult<Message> pagedQueryResult = super.deserialize(httpResponse);
        final List<Message> results = pagedQueryResult.getResults().stream()
                .map((Message genericMessage) -> {
                    final String type = genericMessage.getType();
                    final Optional<? extends Class<?>> directMatchingClassOptional = messageHints.stream()
                            .filter(hint -> hint.typeString() != null)
                            .filter(hint -> hint.typeString().equals(type))
                            .map(hint -> hint.clazz())
                            .filter(clazz -> Message.class.isAssignableFrom(clazz))
                            .findFirst();
                    if (directMatchingClassOptional.isPresent()) {
                        return forceCast(genericMessage, directMatchingClassOptional);
                    } else {
                        //look for generic types
                        final Optional<? extends Class<? extends Message>> classOptional = messageHints.stream()
                                .filter(hint -> hint.typeString() == null)
                                .filter(hint -> hint.resourceReferenceTypeId() != null)
                                .filter(hint -> hint.resourceReferenceTypeId().equals(genericMessage.getResource().getTypeId()))
                                .map(hint -> hint.clazz())
                                .filter(clazz -> Message.class.isAssignableFrom(clazz))
                                .findFirst();
                        if (classOptional.isPresent()) {
                            return forceCast(genericMessage, classOptional);
                        } else {
                            return genericMessage;
                        }
                    }
                })
                .collect(Collectors.toList());
        return PagedQueryResult.of(pagedQueryResult.getOffset(), pagedQueryResult.getLimit(), pagedQueryResult.getTotal(), results);
    }

    @SuppressWarnings("unchecked")
    private Message forceCast(final Message genericMessage, final Optional<? extends Class<?>> directMatchingClassOptional) {
        final Class<Message> messageClass = (Class<Message>) directMatchingClassOptional.get();
        final Message message = genericMessage.<Message>as(messageClass);
        return message;
    }
}