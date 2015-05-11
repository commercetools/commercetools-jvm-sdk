package io.sphere.sdk.channels;

import io.sphere.sdk.channels.commands.ChannelCreateCommand;
import io.sphere.sdk.channels.commands.ChannelDeleteCommand;
import io.sphere.sdk.channels.queries.ChannelByKeyFetch;
import io.sphere.sdk.channels.queries.ChannelQuery;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryIntegrationTest;
import org.junit.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

public class ChannelIntegrationTest extends QueryIntegrationTest<Channel> {
    @Override
    protected SphereRequest<Channel> deleteCommand(final Channel item) {
        return ChannelDeleteCommand.of(item);
    }

    @Override
    protected SphereRequest<Channel> newCreateCommandForName(final String name) {
        return ChannelCreateCommand.of(ChannelDraft.of(name));
    }

    @Override
    protected String extractName(final Channel instance) {
        return instance.getKey();
    }

    @Override
    protected SphereRequest<PagedQueryResult<Channel>> queryRequestForQueryAll() {
        return ChannelQuery.of();
    }

    @Override
    protected SphereRequest<PagedQueryResult<Channel>> queryObjectForName(final String name) {
        return ChannelQuery.of().byKey(name);
    }

    @Override
    protected SphereRequest<PagedQueryResult<Channel>> queryObjectForNames(final List<String> names) {
        return ChannelQuery.of().withPredicate(ChannelQuery.model().key().isOneOf(names));
    }

    @Test
    public void FetchChannelByKey() throws Exception {
        withChannel(client(), ChannelDraftBuilder.of("foo"), channel -> {
                    final Optional<Channel> channelOption = execute(ChannelByKeyFetch.of(channel.getKey()));
                    assertThat(channelOption).contains(channel);
                }
        );
    }

    private void withChannel(final TestClient client, final Supplier<ChannelDraft> creator, final Consumer<Channel> user) {
        final ChannelDraft channelDraft = creator.get();
        cleanUpByName(channelDraft.getKey());
        final Channel channel = client.execute(ChannelCreateCommand.of(channelDraft));
        user.accept(channel);
        cleanUpByName(channelDraft.getKey());
    }

    @Test
    public void deleteChannelById() throws Exception {
        final Channel channel = createChannel();
        final Channel deletedChannel = execute(ChannelDeleteCommand.of(channel));
    }

    private Channel createChannel() {
        final ChannelDraft channelDraft = ChannelDraft.of("my-store")
                .withDescription(LocalizedStrings.of(ENGLISH, "description"));
        return execute(ChannelCreateCommand.of(channelDraft));
    }

    @BeforeClass
    @AfterClass
    public static void classCleanPp() throws Exception {
        execute(ChannelQuery.of().byKey("my-store")).getResults()
                .forEach(channel -> execute(ChannelDeleteCommand.of(channel)));
    }
}
