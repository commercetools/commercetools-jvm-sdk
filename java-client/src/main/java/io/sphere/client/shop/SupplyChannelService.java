package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.SupplyChannel;
import io.sphere.client.shop.model.SupplyChannelUpdate;

public interface SupplyChannelService {
    CommandRequest<SupplyChannel> create(String key);
    CommandRequest<SupplyChannel> updateSupplyChannel(VersionedId versionedId, SupplyChannelUpdate update);
}
