package io.sphere.sdk.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Referenceable;

import java.util.HashMap;
import java.util.Map;

public class JsonReferenceResolver extends Base {
    private final Map<String, String> keyToIdMap = new HashMap<>();

    public void addResourceByKey(final String key, final String id) {
        keyToIdMap.put(key, id);
    }

    public void addResourceByKey(final String key, final Referenceable<?> resource) {
        addResourceByKey(key, resource.toReference().getId());
    }

    public void replaceReferences(final JsonNode resourceDraftNode) {
        if (resourceDraftNode instanceof ObjectNode) {
            resourceDraftNode.findParents("typeId")
                    .stream()
                    .filter(node -> node.isObject())
                    .map(node -> (ObjectNode)node)
                    .filter(node -> node.size() == 2 && node.has("id"))
                    .forEach(node -> {
                        final String id = keyToIdMap.get(node.get("id").asText());
                        node.replace("id", new TextNode(id));
                    });
        } else {
            throw new IllegalArgumentException("" + resourceDraftNode + " should be instance of " + ObjectNode.class.getSimpleName());
        }
    }
}
