package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import java.util.Locale;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.http.HttpMethod.*;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonNodeSphereRequestIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCategory(client(), category -> {
            final ObjectMapper objectMapper = SphereJsonUtils.newObjectMapper();
            final ObjectNode body = objectMapper.createObjectNode();
            body.put("version", category.getVersion());
            final ArrayNode actions = body.putArray("actions");
            final ObjectNode changeNameUpdateAction = objectMapper.createObjectNode();
            final String newName = "New Name";
            changeNameUpdateAction.put("action", "changeName").set("name", objectMapper.createObjectNode().put("en", newName));
            actions.add(changeNameUpdateAction);
            final JsonNode jsonNode = client().executeBlocking(JsonNodeSphereRequest.of(POST, "/categories/" + category.getId(), body));
            assertThat(jsonNode.get("name").get("en").asText()).isEqualTo(newName);
        });
    }

    @Test
    public void execution2() throws Exception {
        withProduct(client(), product -> {
            final String slug = product.getMasterData().getStaged().getSlug().get(Locale.ENGLISH);
            final ProductProjectionQuery normalSphereRequest = ProductProjectionQuery.ofStaged()
                    .withPredicates(m -> m.slug().locale(Locale.ENGLISH).is(slug))
                    .plusExpansionPaths(m -> m.productType());
            final JsonNodeSphereRequest jsonNodeSphereRequest = JsonNodeSphereRequest.of(normalSphereRequest);
            assertThat(normalSphereRequest.httpRequestIntent())
                    .as("a JsonNodeSphereRequest creates the same request to the platform, but differs in the response")
                    .isEqualTo(jsonNodeSphereRequest.httpRequestIntent());
            final PagedQueryResult<ProductProjection> productProjectionPagedSearchResult =
                    client().executeBlocking(normalSphereRequest);
            final JsonNode jsonNode = client().executeBlocking(jsonNodeSphereRequest);//all will be returned as JSON
            assertThat(jsonNode.get("results").get(0).get("productType").get("obj").get("description").asText())
                    .as("the expansion paths are honored")
                    .isEqualTo("a 'T' shaped cloth");
        });
    }
}
