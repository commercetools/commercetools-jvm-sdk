package io.sphere.sdk.stores.commands;

import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.stores.StoreDraft;
import io.sphere.sdk.stores.StoreDraftDsl;
import io.sphere.sdk.stores.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.sphere.sdk.channels.ChannelFixtures.withChannel;
import static io.sphere.sdk.stores.StoreFixtures.withUpdateableStore;
import static io.sphere.sdk.test.SphereTestUtils.*;

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

    @Test
    public void setDistributionChannels() {
        final LocalizedString name = randomSlug();
        final LocalizedString description = randomSlug();
        final ChannelDraft channelDraft =
                ChannelDraft.of(randomKey())
                        .withName(name)
                        .withRoles(ChannelRole.PRODUCT_DISTRIBUTION)
                        .withDescription(description);
        withChannel(client(), channelDraft, channel -> {
            withUpdateableStore(client(), store -> {
                final Store updatedStore = client().executeBlocking(StoreUpdateCommand.of(store, SetDistributionChannels.of(asList(channel))));
                Assertions.assertThat(updatedStore.getDistributionChannels().stream().findFirst().get()).isEqualTo(channel.toReference());

                return updatedStore;
            });
        });
    }

    @Test
    public void addDistributionChannel() {
        final LocalizedString name = randomSlug();
        final LocalizedString description = randomSlug();
        final ChannelDraft channelDraft =
                ChannelDraft.of(randomKey())
                        .withName(name)
                        .withRoles(ChannelRole.PRODUCT_DISTRIBUTION)
                        .withDescription(description);
        withChannel(client(), channelDraft, channel -> {
            withUpdateableStore(client(), store -> {
                final Store updatedStore = client().executeBlocking(StoreUpdateCommand.of(store, AddDistributionChannel.of(channel)));
                Assertions.assertThat(updatedStore.getDistributionChannels().stream().findFirst().get()).isEqualTo(channel.toReference());

                return updatedStore;
            });
        });
    }

    @Test
    public void removeDistributionChannel() {
        final LocalizedString name = randomSlug();
        final LocalizedString description = randomSlug();
        final ChannelDraft channelDraft =
                ChannelDraft.of(randomKey())
                        .withName(name)
                        .withRoles(ChannelRole.PRODUCT_DISTRIBUTION)
                        .withDescription(description);
        withChannel(client(), channelDraft, channel -> {
            final StoreDraftDsl storeDraft = StoreDraft.of("removeChannelStore", LocalizedString.ofEnglish("removeChannelStore")).withDistributionChannels(asList(channel.toReference()));
            withUpdateableStore(client(), storeDraft, store -> {
                final Store updatedStore = client().executeBlocking(StoreUpdateCommand.of(store, RemoveDistributionChannel.of(channel)));
                Assertions.assertThat(updatedStore.getDistributionChannels()).isEmpty();

                return updatedStore;
            });
        });
    }

    @Test
    public void setSupplyChannels() {
        final LocalizedString name = randomSlug();
        final LocalizedString description = randomSlug();
        final ChannelDraft channelDraft =
                ChannelDraft.of(randomKey())
                        .withName(name)
                        .withRoles(ChannelRole.INVENTORY_SUPPLY)
                        .withDescription(description);
        withChannel(client(), channelDraft, channel -> {
            withUpdateableStore(client(), store -> {
                final Store updatedStore = client().executeBlocking(StoreUpdateCommand.of(store, SetSupplyChannels.of(asList(channel))));
                Assertions.assertThat(updatedStore.getSupplyChannels().stream().findFirst().get()).isEqualTo(channel.toReference());

                return updatedStore;
            });
        });
    }

    @Test
    public void addSupplyChannel() {
        final LocalizedString name = randomSlug();
        final LocalizedString description = randomSlug();
        final ChannelDraft channelDraft =
                ChannelDraft.of(randomKey())
                        .withName(name)
                        .withRoles(ChannelRole.INVENTORY_SUPPLY)
                        .withDescription(description);
        withChannel(client(), channelDraft, channel -> {
            withUpdateableStore(client(), store -> {
                final Store updatedStore = client().executeBlocking(StoreUpdateCommand.of(store, AddSupplyChannel.of(channel)));
                Assertions.assertThat(updatedStore.getSupplyChannels().stream().findFirst().get()).isEqualTo(channel.toReference());

                return updatedStore;
            });
        });
    }

    @Test
    public void removeSupplyChannel() {
        final LocalizedString name = randomSlug();
        final LocalizedString description = randomSlug();
        final ChannelDraft channelDraft =
                ChannelDraft.of(randomKey())
                        .withName(name)
                        .withRoles(ChannelRole.INVENTORY_SUPPLY)
                        .withDescription(description);
        withChannel(client(), channelDraft, channel -> {
            final StoreDraftDsl storeDraft = StoreDraft.of("removeSupplyChannelStore", LocalizedString.ofEnglish("removeSupplyChannelStore")).withSupplyChannels(asList(channel.toReference()));
            withUpdateableStore(client(), storeDraft, store -> {
                final Store updatedStore = client().executeBlocking(StoreUpdateCommand.of(store, RemoveSupplyChannel.of(channel)));
                Assertions.assertThat(updatedStore.getSupplyChannels()).isEmpty();

                return updatedStore;
            });
        });
    }

}
