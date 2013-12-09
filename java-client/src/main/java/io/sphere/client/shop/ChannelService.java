package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.Channel;
import io.sphere.client.shop.model.ChannelUpdate;

public interface ChannelService {
    CommandRequest<Channel> create(String key);
    CommandRequest<Channel> updateChannel(VersionedId versionedId, ChannelUpdate update);
}
