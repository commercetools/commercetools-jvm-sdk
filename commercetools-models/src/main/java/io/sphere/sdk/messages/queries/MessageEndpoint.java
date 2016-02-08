package io.sphere.sdk.messages.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.messages.Message;

final class MessageEndpoint {
    public static final JsonEndpoint<Message> ENDPOINT = JsonEndpoint.of(Message.typeReference(), "/messages");
}
