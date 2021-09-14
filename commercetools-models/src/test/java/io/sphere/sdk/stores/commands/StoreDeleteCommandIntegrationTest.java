package io.sphere.sdk.stores.commands;

import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.stores.StoreDraft;
import io.sphere.sdk.stores.messages.StoreDeletedMessage;
import io.sphere.sdk.stores.queries.StoreQuery;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.*;

import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

public class StoreDeleteCommandIntegrationTest extends IntegrationTest {
    
    @Test
    public void deleteById(){

        final String key = SphereTestUtils.randomKey();
        final LocalizedString name = SphereTestUtils.randomLocalizedString();
        final StoreDraft storeDraft = StoreDraft.of(key, name);
        final Store store = client().executeBlocking(StoreCreateCommand.of(storeDraft));
        Assertions.assertThat(store).isNotNull();

        client().executeBlocking(StoreDeleteCommand.of(store));
        final Query<Store> query = StoreQuery.of()
                .withPredicates(m -> m.id().is(store.getId()));

        Assertions.assertThat(client().executeBlocking(query).head()).isEmpty();
        final Query<StoreDeletedMessage> queryMessage =
                MessageQuery.of()
                        .withSort(m -> m.createdAt().sort().desc())
                        .withExpansionPaths(m -> m.resource())
                        .withLimit(1L)
                        .withPredicates(m -> m.resource().is(store))
                        .forMessageType(StoreDeletedMessage.MESSAGE_HINT);
        assertEventually(() -> {
            final PagedQueryResult<StoreDeletedMessage> pagedQueryResult = client().executeBlocking(queryMessage);
            final Optional<StoreDeletedMessage> messageOptional = pagedQueryResult.head();
            assertThat(messageOptional).isPresent();
            final StoreDeletedMessage message = messageOptional.get();
            assertThat(message.getResource().getId()).isEqualTo(store.getId());
        });
    }

    @Test
    public void deleteByKey(){

        final String key = SphereTestUtils.randomKey();
        final LocalizedString name = SphereTestUtils.randomLocalizedString();
        final StoreDraft storeDraft = StoreDraft.of(key, name);
        final Store store = client().executeBlocking(StoreCreateCommand.of(storeDraft));
        Assertions.assertThat(store).isNotNull();

        client().executeBlocking(StoreDeleteCommand.ofKey(store.getKey(), store.getVersion()));
        final Query<Store> query = StoreQuery.of()
                .withPredicates(m -> m.id().is(store.getId()));

        Assertions.assertThat(client().executeBlocking(query).head()).isEmpty();
    }
    
}
