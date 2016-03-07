package io.sphere.sdk.types.commands;

import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.ResourceTypeIdsSetBuilder;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.TypeDraft;
import io.sphere.sdk.types.TypeDraftBuilder;
import io.sphere.sdk.types.queries.TypeByIdGet;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeDeleteCommandIntegrationTest extends IntegrationTest {
    @Test
    public void deleteByKey() {
        final ResourceTypeIdsSetBuilder idsSetBuilder = ResourceTypeIdsSetBuilder.of().addCustomers();
        final TypeDraft draft = TypeDraftBuilder.of(randomKey(), randomSlug(), idsSetBuilder)
                .build();
        final Type type = client().executeBlocking(TypeCreateCommand.of(draft));
        final String key = type.getKey();
        final Long version = type.getVersion();
        client().executeBlocking(TypeDeleteCommand.ofKey(key, version));
        assertThat(client().executeBlocking(TypeByIdGet.of(type))).isNull();
    }
}