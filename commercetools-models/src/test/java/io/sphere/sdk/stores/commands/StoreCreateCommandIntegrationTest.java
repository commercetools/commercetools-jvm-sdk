package io.sphere.sdk.stores.commands;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.stores.StoreDraft;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class StoreCreateCommandIntegrationTest extends IntegrationTest {
    
    @Test
    public void execute(){
        final String key = SphereTestUtils.randomKey();
        final LocalizedString name = SphereTestUtils.randomLocalizedString();
        final StoreDraft storeDraft = StoreDraft.of(key, name);

        StoreFixtures.withStore(client(), storeDraft, store -> {
            Assertions.assertThat(store).isNotNull();
            Assertions.assertThat(store.getKey()).isEqualTo(key);
            Assertions.assertThat(store.getName()).isEqualTo(name);
        });
    }
}