package io.sphere.sdk.stores.commands;

import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.stores.StoreDraft;
import io.sphere.sdk.stores.StoreDraftBuilder;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.stores.messages.StoreCreatedMessage;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.*;

import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

public class StoreCreateCommandIntegrationTest extends IntegrationTest {
    
    @Test
    public void execute(){
        final String key = SphereTestUtils.randomKey();
        final LocalizedString name = SphereTestUtils.randomLocalizedString();
        final List<String> languages = Collections.singletonList(Locale.ENGLISH.toLanguageTag());
        final StoreDraft storeDraft = StoreDraftBuilder.of(key, name, languages).build();

        StoreFixtures.withStore(client(), storeDraft, store -> {
            Assertions.assertThat(store).isNotNull();
            Assertions.assertThat(store.getKey()).isEqualTo(key);
            Assertions.assertThat(store.getName()).isEqualTo(name);
            Assertions.assertThat(store.getLanguages()).isEqualTo(languages);

            assertEventually(() -> {
                final PagedQueryResult<StoreCreatedMessage> pagedQueryResult = client().executeBlocking(
                        MessageQuery.of().withPredicates(m -> m.resource().is(store))
                            .forMessageType(StoreCreatedMessage.MESSAGE_HINT)
                );

                final Optional<StoreCreatedMessage> storeCreatedMessage = pagedQueryResult.head();

                assertThat(storeCreatedMessage).isPresent();
                assertThat(storeCreatedMessage.get().getResource().getId()).isEqualTo(store.getId());
            });
        });
    }
}