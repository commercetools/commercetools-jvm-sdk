package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.channels.ChannelFixtures.withUpdatableChannelOfRole;
import static org.assertj.core.api.Assertions.assertThat;

public class ChannelQueryTest extends IntegrationTest {
    @Test
    public void execution() {
        withUpdatableChannelOfRole(client(), ChannelRole.PRIMARY, channel -> {
            final String key = channel.getKey();
            final PagedQueryResult<Channel> pagedQueryResult = client().executeBlocking(ChannelQuery.of().byKey(key));

            assertThat(pagedQueryResult).has(onlyTheResult(channel));
            return channel;
        });
    }
}