package io.sphere.sdk.stores.commands;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.stores.commands.updateactions.SetName;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class StoreUpdateCommandIntegrationTest extends IntegrationTest {
    
    @Test
    public void setNameByKey() {
        StoreFixtures.withUpdateableStore(client(), store -> {
            final LocalizedString newName = SphereTestUtils.randomSlug();
            final Store updatedStore = client().executeBlocking(StoreUpdateCommand.of(store, SetName.of(newName)));
            Assertions.assertThat(updatedStore.getName()).isEqualTo(newName);
            return updatedStore;
        });
    }
}