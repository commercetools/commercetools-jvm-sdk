package io.sphere.sdk.stores.queries;

import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class StoreQueryIntegrationTest extends IntegrationTest {
    
    @Test
    public void execute(){
        StoreFixtures.withStore(client(), store -> {
            final PagedQueryResult<Store> result = client().executeBlocking(StoreQuery.of().withLimit(10));
            Assertions.assertThat(result.getResults()).isNotEmpty();
        });
    }
}
