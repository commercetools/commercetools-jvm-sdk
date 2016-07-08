package io.sphere.sdk.client;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.queries.CategoryByIdGet;
import io.sphere.sdk.http.HttpHeaders;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.test.IntegrationTest;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static org.assertj.core.api.Assertions.assertThat;

public class JavaAndHttpResponseSphereRequestIntegrationTest extends IntegrationTest {
    @Test
    public void execute() {
        withCategory(client(), category -> {
            final CategoryByIdGet originalRequest = CategoryByIdGet.of(category);
            final JavaAndHttpResponseSphereRequest<Category> request =
                    JavaAndHttpResponseSphereRequest.of(originalRequest);
            final Pair<Category, HttpResponse> result = client().executeBlocking(request);
            final Category categoryJavaObject = result.getLeft();
            final HttpResponse httpResponse = result.getRight();
            assertThat(categoryJavaObject).isEqualTo(category);
            assertThat(httpResponse.getHeaders().findFlatHeader(HttpHeaders.CONTENT_TYPE).get())
                    .contains("application/json");
        });
    }
}
