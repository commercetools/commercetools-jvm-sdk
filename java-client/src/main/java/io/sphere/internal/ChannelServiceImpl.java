package io.sphere.internal;

import io.sphere.client.CommandRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.ChannelService;
import io.sphere.client.shop.model.Channel;
import io.sphere.client.shop.model.ChannelRoles;
import io.sphere.client.shop.model.ChannelUpdate;
import io.sphere.internal.command.ChannelCommands;
import io.sphere.internal.command.UpdateCommand;
import io.sphere.internal.request.RequestFactory;
import org.codehaus.jackson.type.TypeReference;

import java.util.Set;

public class ChannelServiceImpl extends ProjectScopedAPI<Channel> implements ChannelService {
    public ChannelServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(requestFactory, endpoints, new TypeReference<Channel>() {}, new TypeReference<QueryResult<Channel>>(){});
    }

    @Override
    public CommandRequest<Channel> create(String key) {
        return createCommandRequest(new ChannelCommands.CreateChannel(key));
    }

    @Override
    public CommandRequest<Channel> create(String key, Set<ChannelRoles> roles) {
        return createCommandRequest(new ChannelCommands.CreateChannel(key, roles));
    }

    @Override
    public CommandRequest<Channel> updateChannel(VersionedId versionedId, ChannelUpdate update) {
        return createCommandRequest(
                endpoints.channels.byId(versionedId.getId()),
                new UpdateCommand<ChannelCommands.ChannelUpdateAction>(versionedId.getVersion(), update));
    }

    private CommandRequest<Channel> createCommandRequest(ChannelCommands.CreateChannel command) {
        return createCommandRequest(endpoints.channels.root(), command);
    }
}
