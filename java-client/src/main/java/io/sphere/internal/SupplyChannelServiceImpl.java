package io.sphere.internal;

import io.sphere.client.CommandRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.model.QueryResult;
import io.sphere.client.shop.SupplyChannelService;
import io.sphere.client.shop.model.SupplyChannel;
import io.sphere.internal.command.SupplyChannelCommands;
import io.sphere.internal.request.RequestFactory;
import org.codehaus.jackson.type.TypeReference;

public class SupplyChannelServiceImpl extends ProjectScopedAPI<SupplyChannel> implements SupplyChannelService {
    public SupplyChannelServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(requestFactory, endpoints, new TypeReference<SupplyChannel>() {}, new TypeReference<QueryResult<SupplyChannel>>(){});
    }

    @Override
    public CommandRequest<SupplyChannel> create(String key) {
        final SupplyChannelCommands.CreateSupplyChannel command = new SupplyChannelCommands.CreateSupplyChannel(key);
        return createCommandRequest(endpoints.supplyChannels.root(), command);
    }
}
