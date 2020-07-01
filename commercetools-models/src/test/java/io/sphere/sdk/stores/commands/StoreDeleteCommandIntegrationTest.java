package io.sphere.sdk.stores.commands;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.stores.StoreDraft;
import io.sphere.sdk.stores.queries.StoreQuery;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class StoreDeleteCommandIntegrationTest extends IntegrationTest {
    
    @Test
    public void deleteById(){

        final String key = SphereTestUtils.randomKey();
        final LocalizedString name = SphereTestUtils.randomLocalizedString();
        final List<Locale> languages = Collections.singletonList(Locale.ENGLISH);
        final StoreDraft storeDraft = StoreDraft.of(key, name);
        final Store store = client().executeBlocking(StoreCreateCommand.of(storeDraft));
        Assertions.assertThat(store).isNotNull();

        client().executeBlocking(StoreDeleteCommand.of(store));
        final Query<Store> query = StoreQuery.of()
                .withPredicates(m -> m.id().is(store.getId()));

        Assertions.assertThat(client().executeBlocking(query).head()).isEmpty();
    }

    @Test
    public void deleteByKey(){

        final String key = SphereTestUtils.randomKey();
        final LocalizedString name = SphereTestUtils.randomLocalizedString();
        final List<Locale> languages = Collections.singletonList(Locale.ENGLISH);
        final StoreDraft storeDraft = StoreDraft.of(key, name);
        final Store store = client().executeBlocking(StoreCreateCommand.of(storeDraft));
        Assertions.assertThat(store).isNotNull();

        client().executeBlocking(StoreDeleteCommand.ofKey(store.getKey(), store.getVersion()));
        final Query<Store> query = StoreQuery.of()
                .withPredicates(m -> m.id().is(store.getId()));

        Assertions.assertThat(client().executeBlocking(query).head()).isEmpty();
    }
    
}
