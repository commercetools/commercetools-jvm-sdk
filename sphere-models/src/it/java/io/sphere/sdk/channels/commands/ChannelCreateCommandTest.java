package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.channels.queries.ChannelQuery;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.sphere.sdk.utils.SetUtils.asSet;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

public class ChannelCreateCommandTest extends IntegrationTest {

    @Before
    @After
    public void setUp() throws Exception {
        final PagedQueryResult<Channel> queryResult = execute(ChannelQuery.of().byKey(channelKey()));
        queryResult.head().ifPresent(c -> execute(ChannelDeleteCommand.of(c)));
    }

    @Test
    public void execution() throws Exception {
        final String key = channelKey();
        final ChannelDraft channelDraft = ChannelDraft.of(key)
                .withName(LocalizedStrings.of(ENGLISH, "name"))
                .withDescription(LocalizedStrings.of(ENGLISH, "description"))
                .withRoles(ChannelRole.INVENTORY_SUPPLY);
        final Channel channel = execute(ChannelCreateCommand.of(channelDraft));
        assertThat(channel.getKey()).isEqualTo(key);
        assertThat(channel.getName()).isEqualTo(LocalizedStrings.of(ENGLISH, "name"));
        assertThat(channel.getDescription()).isEqualTo(LocalizedStrings.of(ENGLISH, "description"));
        assertThat(channel.getRoles()).isEqualTo(asSet(ChannelRole.INVENTORY_SUPPLY));
    }

    private String channelKey() {
        return ChannelCreateCommandTest.class.getSimpleName();
    }
}