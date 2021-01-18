package io.sphere.sdk.stores;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.stores.commands.StoreCreateCommand;
import io.sphere.sdk.stores.commands.StoreDeleteCommand;
import io.sphere.sdk.test.SphereTestUtils;
import org.assertj.core.api.Assertions;

import java.util.function.Consumer;
import java.util.function.Function;

public class StoreFixtures {

    public static void withStore(final BlockingSphereClient client, final StoreDraft draft, final Consumer<Store> consumer) {
        final Store store = client.executeBlocking(StoreCreateCommand.of(draft));
        Assertions.assertThat(store).isNotNull();
        consumer.accept(store);
        //TODO change this to delete by id
        client.executeBlocking(StoreDeleteCommand.of(store));
    }

    public static void withStore(final BlockingSphereClient client, final Consumer<Store> consumer) {
        final String key = SphereTestUtils.randomKey();
        final LocalizedString name = SphereTestUtils.randomLocalizedString();
        final StoreDraft storeDraft = StoreDraft.of(key, name);
        withStore(client, storeDraft, consumer);
    }
    
    public static void withUpdateableStore(final BlockingSphereClient client, final StoreDraft draft, final Function<Store, Store> f) {
        Store store = client.executeBlocking(StoreCreateCommand.of(draft));
        Assertions.assertThat(store).isNotNull();
        store = f.apply(store);
        client.executeBlocking(StoreDeleteCommand.of(store));
    }
    
    public static void withUpdateableStore(final BlockingSphereClient client, final Function<Store, Store> f) {
        final String key = SphereTestUtils.randomKey();
        final LocalizedString name = SphereTestUtils.randomLocalizedString();
        final StoreDraft storeDraft = StoreDraft.of(key, name);
        withUpdateableStore(client, storeDraft, f);
    }
}