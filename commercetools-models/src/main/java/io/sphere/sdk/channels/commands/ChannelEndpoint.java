package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.client.JsonEndpoint;

final class ChannelEndpoint {
    static final JsonEndpoint<Channel> ENDPOINT = JsonEndpoint.of(Channel.typeReference(), "/channels");
}
