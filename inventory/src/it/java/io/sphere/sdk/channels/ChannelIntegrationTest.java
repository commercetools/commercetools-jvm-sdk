package io.sphere.sdk.channels;

import io.sphere.sdk.channels.commands.ChannelCreateCommand;
import io.sphere.sdk.channels.commands.ChannelDeleteByIdCommand;
import io.sphere.sdk.channels.queries.ChannelQuery;
import io.sphere.sdk.channels.queries.FetchChannelByKey;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryIntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static java.util.Locale.ENGLISH;

public class ChannelIntegrationTest extends QueryIntegrationTest<Channel> {
    @Override
    protected ClientRequest<Channel> deleteCommand(final Channel item) {
        return new ChannelDeleteByIdCommand(item);
    }

    @Override
    protected ClientRequest<Channel> newCreateCommandForName(final String name) {
        return new ChannelCreateCommand(NewChannel.of(name));
    }

    @Override
    protected String extractName(final Channel instance) {
        return instance.getKey();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Channel>> queryRequestForQueryAll() {
        return new ChannelQuery();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Channel>> queryObjectForName(final String name) {
        return new ChannelQuery().byKey(name);
    }

    @Override
    protected ClientRequest<PagedQueryResult<Channel>> queryObjectForNames(final List<String> names) {
        return new ChannelQuery().withPredicate(ChannelQuery.model().key().isOneOf(names));
    }

    @Test
    public void FetchChannelByKey() throws Exception {
        withChannel(client(), NewChannelBuilder.of("foo"), channel -> {
                    final Optional<Channel> channelOption = client().execute(new FetchChannelByKey(channel.getKey()));
                    assertThat(channelOption).isPresentAs(channel);
                }
        );
    }

    private void withChannel(final TestClient client, final Supplier<NewChannel> creator, final Consumer<Channel> user) {
        final NewChannel newChannel = creator.get();
        cleanUpByName(newChannel.getKey());
        final Channel channel = client.execute(new ChannelCreateCommand(newChannel));
        user.accept(channel);
        cleanUpByName(newChannel.getKey());
    }

    @Test
    public void deleteChannelById() throws Exception {
        final Channel channel = createChannel();
        final Channel deletedChannel = client().execute(new ChannelDeleteByIdCommand(channel));
    }

    private Channel createChannel() {
        final NewChannel newChannel = NewChannel.of("my-store")
                .withDescription(LocalizedString.of(ENGLISH, "description"));
        final Channel channel = client().execute(new ChannelCreateCommand(newChannel));
        return channel;
    }
}
