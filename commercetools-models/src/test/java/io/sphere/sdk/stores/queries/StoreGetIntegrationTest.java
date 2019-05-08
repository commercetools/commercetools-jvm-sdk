package io.sphere.sdk.stores.queries;

import io.sphere.sdk.stores.Store;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class StoreGetIntegrationTest extends IntegrationTest {
    
    @Test
    public void getById() {
        StoreFixtures.withStore(client(), store -> {
            final Store queriedStore = client().executeBlocking(StoreByIdGet.of(store.getId()));
            Assertions.assertThat(queriedStore).isNotNull();
        });
    }
    
    @Test
    public void getByKey() {
        StoreFixtures.withStore(client(), store -> {
            final Store queriedStore = client().executeBlocking(StoreByKeyGet.of(store.getKey()));
            Assertions.assertThat(queriedStore).isNotNull();
        });
    }
}