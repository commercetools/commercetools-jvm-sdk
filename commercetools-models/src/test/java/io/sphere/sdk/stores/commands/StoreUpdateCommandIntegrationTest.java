package io.sphere.sdk.stores.commands;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.stores.commands.updateactions.SetLanguages;
import io.sphere.sdk.stores.commands.updateactions.SetName;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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

    @Test
    public void setLanguages() {
        StoreFixtures.withUpdateableStore(client(), store -> {
            final List<String> newLanguages = Arrays.asList("en", "de");
            final Store updatedStore = client().executeBlocking(StoreUpdateCommand.of(store, SetLanguages.of(newLanguages)));
            Assertions.assertThat(updatedStore.getLanguages()).isEqualTo(newLanguages);

            return updatedStore;
        });
    }
}