package io.sphere.sdk.categories.messages;

import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.commands.updateactions.ChangeSlug;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;

public class CategorySlugChangedMessageIntegrationTest extends IntegrationTest {
    @Test
    public void categorySlugChangedMessage() {
        CategoryFixtures.withCategory(client(), category -> {
            LocalizedString newSlug = randomSlug();
            client().executeBlocking(CategoryUpdateCommand.of(category, ChangeSlug.of(newSlug)));

            Query<CategorySlugChangedMessage> messageQuery = MessageQuery.of()
                .withPredicates(m -> m.resource().is(category))
                .withSort(m -> m.createdAt().sort().desc())
                .withLimit(1L)
                .forMessageType(CategorySlugChangedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<CategorySlugChangedMessage> queryResult = client().executeBlocking(messageQuery);
                assertThat(queryResult.head()).isPresent();
                final CategorySlugChangedMessage message = queryResult.head().get();
                assertThat(message.getSlug()).isEqualTo(newSlug);
                assertThat(message.getOldSlug()).isEqualTo(category.getSlug());
                assertThat(message.getResource().getId()).isEqualTo(category.getId());
            });

        });
    }
}
