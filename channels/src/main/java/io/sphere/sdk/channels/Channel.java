package io.sphere.sdk.channels;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ChannelImpl.class)
public interface Channel {
}
