package io.sphere.sdk.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Referenceable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonNodeReferenceResolver extends Base {
    public static final String PRODUCT_ID_FIELD_NAME = "productId";
    private final Map<String, String> keyToIdMap = new HashMap<>();

    public void addResourceByKey(final String key, final String id) {
        keyToIdMap.put(key, id);
    }

    public void addResourceByKey(final String key, final Referenceable<?> resource) {
        addResourceByKey(key, resource.toReference().getId());
    }

    public void replaceIds(final JsonNode resourceDraftNode) {
        if (resourceDraftNode instanceof ObjectNode) {
            references(resourceDraftNode);
            productIdInLineItems(resourceDraftNode);
            customerIdInOrders(resourceDraftNode);
        } else {
            throw new IllegalArgumentException("" + resourceDraftNode + " should be instance of " + ObjectNode.class.getSimpleName());
        }
    }

    private void customerIdInOrders(final JsonNode resourceDraftNode) {
        final String fieldName = "customerId";
        final List<JsonNode> nodesContainingCustomerId = resourceDraftNode.findParents(fieldName);
        nodesContainingCustomerId.forEach(node -> {
            if (node != null && node.isObject()) {
                final String newId = keyToIdMap.get(node.get(fieldName).asText());
                ((ObjectNode) node).replace(fieldName, new TextNode(newId));
            }
        });
    }

    private void productIdInLineItems(final JsonNode resourceDraftNode) {
        final JsonNode lineItems = resourceDraftNode.get("lineItems");
        if (lineItems != null && lineItems.isArray()) {
            final List<JsonNode> parents = resourceDraftNode.findParents(PRODUCT_ID_FIELD_NAME);
            parents.forEach(node -> {
                final String newId = keyToIdMap.get(node.get(PRODUCT_ID_FIELD_NAME).asText());
                ((ObjectNode) node).replace(PRODUCT_ID_FIELD_NAME, new TextNode(newId));
            });
        }
    }

    private void references(final JsonNode resourceDraftNode) {
        resourceDraftNode.findParents("typeId")
                .stream()
                .filter(node -> node.isObject())
                .map(node -> (ObjectNode)node)
                .filter(node -> node.size() == 2 && node.has("id"))
                .forEach(node -> {
                    final String id = keyToIdMap.get(node.get("id").asText());
                    node.replace("id", new TextNode(id));
                });
    }
}
