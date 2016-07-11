package io.sphere.sdk.categories.messages;

import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static io.sphere.sdk.test.SphereTestUtils.en;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.categories.messages.CategoryCreatedMessage;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;

public class CategoryCreatedMessageIntegrationTest extends IntegrationTest {
    @Test
    public void categoryCreatedMessage() {
        final LocalizedString slug = randomSlug();
        final String id = randomKey();
        final CategoryDraftBuilder catSupplier = CategoryDraftBuilder.of(en(slug.get(ENGLISH) + " name"), slug).externalId(id);
        
        CategoryFixtures.withCategory(client(), catSupplier, category -> {
            Query<CategoryCreatedMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(category))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .forMessageType(CategoryCreatedMessage.MESSAGE_HINT);
            
            assertEventually(() ->{
                final PagedQueryResult<CategoryCreatedMessage> queryResult = client().executeBlocking(messageQuery);
                assertThat(queryResult.head()).isPresent();
                final CategoryCreatedMessage message = queryResult.head().get();
                assertThat(message.getCategory().getSlug()).isEqualTo(slug);
                assertThat(message.getCategory().getExternalId()).isEqualTo(id);
                assertThat(message.getResource().getId()).isEqualTo(category.getId());
            });
        });
    }
}
