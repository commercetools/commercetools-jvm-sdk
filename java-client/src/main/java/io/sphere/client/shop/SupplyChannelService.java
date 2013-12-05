package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.shop.model.SupplyChannel;

public interface SupplyChannelService {
    CommandRequest<SupplyChannel> create(String key);
}
