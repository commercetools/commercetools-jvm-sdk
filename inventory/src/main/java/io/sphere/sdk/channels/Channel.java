package io.sphere.sdk.channels;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Channels represent a source or destination of different entities.
 */
@JsonDeserialize(as = ChannelImpl.class)
public interface Channel {
}
