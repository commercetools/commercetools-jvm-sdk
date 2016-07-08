package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.queries.CategoryByIdGet;
import io.sphere.sdk.test.IntegrationTest;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static org.assertj.core.api.Assertions.assertThat;

public class JavaAndJsonSphereRequestIntegrationTest extends IntegrationTest {

    @Test
    public void execute() {
        withCategory(client(), category -> {
            final CategoryByIdGet originalRequest = CategoryByIdGet.of(category);
            final JavaAndJsonSphereRequest<Category> request = JavaAndJsonSphereRequest.of(originalRequest);
            final Pair<Category, JsonNode> result = client().executeBlocking(request);
            final Category categoryJavaObject = result.getLeft();
            final JsonNode categoryAsJson = result.getRight();
            assertThat(categoryJavaObject).isEqualTo(category);
            assertThat(categoryAsJson.get("id").asText()).isEqualTo(category.getId());
        });
    }
}