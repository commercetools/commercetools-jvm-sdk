package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.ChannelRoles;
import io.sphere.sdk.channels.queries.ChannelByKeyFetch;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.sphere.sdk.utils.SetUtils.asSet;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.test.SphereTestUtils .*;

public class ChannelCreateCommandTest extends IntegrationTest {

    @Before
    @After
    public void setUp() throws Exception {
        execute(ChannelByKeyFetch.of(channelKey())).ifPresent(c -> execute(ChannelDeleteCommand.of(c)));
    }

    @Test
    public void execution() throws Exception {
        final String key = channelKey();
        final ChannelDraft channelDraft = ChannelDraft.of(key)
                .withName(LocalizedStrings.of(ENGLISH, "name"))
                .withDescription(LocalizedStrings.of(ENGLISH, "description"))
                .withRoles(ChannelRoles.INVENTORY_SUPPLY);
        final Channel channel = execute(ChannelCreateCommand.of(channelDraft));
        assertThat(channel.getKey()).isEqualTo(key);
        assertThat(channel.getName()).contains(LocalizedStrings.of(ENGLISH, "name"));
        assertThat(channel.getDescription()).contains(LocalizedStrings.of(ENGLISH, "description"));
        assertThat(channel.getRoles()).isEqualTo(asSet(ChannelRoles.INVENTORY_SUPPLY));
    }

    private String channelKey() {
        return ChannelCreateCommandTest.class.getSimpleName();
    }
}