package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.http.HttpMethod.*;
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
}
