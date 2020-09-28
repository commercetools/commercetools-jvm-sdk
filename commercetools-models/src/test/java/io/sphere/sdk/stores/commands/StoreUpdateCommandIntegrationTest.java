package io.sphere.sdk.stores.commands;

import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.ChannelFixtures;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommand;
import io.sphere.sdk.shoppinglists.commands.updateactions.SetCustomer;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.stores.commands.updateactions.SetDistributionChannels;
import io.sphere.sdk.stores.commands.updateactions.SetLanguages;
import io.sphere.sdk.stores.commands.updateactions.SetName;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.sphere.sdk.channels.ChannelFixtures.withChannel;
import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.stores.StoreFixtures.withUpdateableStore;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static org.assertj.core.api.Assertions.assertThat;

public class StoreUpdateCommandIntegrationTest extends IntegrationTest {
    
    @Test
    public void setNameByKey() {
        withUpdateableStore(client(), store -> {
            final LocalizedString newName = SphereTestUtils.randomSlug();
            final Store updatedStore = client().executeBlocking(StoreUpdateCommand.of(store, SetName.of(newName)));
            Assertions.assertThat(updatedStore.getName()).isEqualTo(newName);
            return updatedStore;
        });
    }

    @Test
    public void setLanguages() {
        withUpdateableStore(client(), store -> {
            final List<String> newLanguages = Arrays.asList("en", "de");
            final Store updatedStore = client().executeBlocking(StoreUpdateCommand.of(store, SetLanguages.of(newLanguages)));
            Assertions.assertThat(updatedStore.getLanguages()).isEqualTo(newLanguages);

            return updatedStore;
        });
    }

//    @Test
//    public void setDistributionChannels() {
//        final LocalizedString name = randomSlug();
//        final LocalizedString description = randomSlug();
//        final ChannelDraft channelDraft =
//                ChannelDraft.of(randomKey())
//                        .withName(name)
//                        .withDescription(description);
//        withChannel(client(), channelDraft, channel -> {
//            withUpdateableStore(client(), store -> {
//                final Store updatedStore = client().executeBlocking(StoreUpdateCommand.of(store, SetDistributionChannels.of(channel)));
//                Assertions.assertThat(updatedStore.getDistributionChannels()).isEqualTo(channel.toReference());
//
//                return updatedStore;
//            });
//        });
//    }
}