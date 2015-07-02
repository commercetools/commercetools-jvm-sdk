package io.sphere.sdk.messages;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.json.JsonUtils;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class GenericMessageImpl<R> extends DefaultModelImpl<Message> implements GenericMessage<R> {
    protected final long sequenceNumber;
    protected final JsonNode resource;
    protected final long resourceVersion;
    protected final String type;
    @JsonIgnore
    private final Map<String, JsonNode> furtherFields = new HashMap<>();

    public GenericMessageImpl(final String id, final long version, final ZonedDateTime createdAt,
                              final ZonedDateTime lastModifiedAt, final JsonNode resource,
                              final long sequenceNumber, final long resourceVersion,
                              final String type) {
        super(id, version, createdAt, lastModifiedAt);
        this.resource = resource;
        this.sequenceNumber = sequenceNumber;
        this.resourceVersion = resourceVersion;
        this.type = type;
    }

    @Override
    public long getResourceVersion() {
        return resourceVersion;
    }

    @Override
    public long getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Reference<Message> toReference() {
        return Reference.of(Message.typeId(), getId(), this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Reference<R> getResource() {
        return (Reference<R>)JsonUtils.readObject(new TypeReference<Reference<Order>>() { }, resource);
    }

    @Override
    public <T extends Message> T as(final Class<T> messageClass) {
        final ObjectMapper objectMapper = JsonUtils.newObjectMapper();
        final ObjectNode jsonNode = objectMapper.createObjectNode()
                .put("id", getId())
                .put("version", getVersion())
                .put("createdAt", getCreatedAt().toString())
                .put("lastModifiedAt", getLastModifiedAt().toString())
                .put("sequenceNumber", sequenceNumber)
                .put("resourceVersion", resourceVersion)
                .put("type", type);
        furtherFields.entrySet().forEach(entry -> jsonNode.replace(entry.getKey(), entry.getValue()));
        jsonNode.replace("resource", resource);
        return JsonUtils.readObject(messageClass, jsonNode);
    }

    @JsonAnySetter
    private void set(final String key, final JsonNode value) {
        furtherFields.put(key, value);
    }
}
