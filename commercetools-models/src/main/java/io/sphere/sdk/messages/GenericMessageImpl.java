package io.sphere.sdk.messages;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.ResourceImpl;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class GenericMessageImpl<R> extends ResourceImpl<Message> implements GenericMessage<R> {
    protected final Long sequenceNumber;
    protected final JsonNode resource;
    protected final Long resourceVersion;
    protected final String type;
    protected final UserProvidedIdentifiers resourceUserProvidedIdentifiers;

    @JsonIgnore
    private final Map<String, JsonNode> furtherFields = new HashMap<>();
    @JsonIgnore
    private final JavaType referenceJavaType;

    public GenericMessageImpl(final String id, final Long version, final ZonedDateTime createdAt,
                              final ZonedDateTime lastModifiedAt, final JsonNode resource,
                              final Long sequenceNumber, final Long resourceVersion,
                              final String type,final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Class<R> clazz) {
        super(id, version, createdAt, lastModifiedAt);
        this.resource = resource;
        this.sequenceNumber = sequenceNumber;
        this.resourceVersion = resourceVersion;
        this.type = type;
        this.resourceUserProvidedIdentifiers = resourceUserProvidedIdentifiers;
        requireNonNull(clazz, "class of reference must be explicitly given, it cannot be part of a JsonCreator.");
        final JavaType javaType = SphereJsonUtils.convertToJavaType(clazz);
        final TypeFactory typeFactory = TypeFactory.defaultInstance();
        this.referenceJavaType = typeFactory.constructParametricType(Reference.class, javaType);

        final ObjectMapper objectMapper = SphereJsonUtils.newObjectMapper();
        JsonNode node = null;

        if(resourceUserProvidedIdentifiers != null) {
            node = objectMapper.createObjectNode()
                    .put("key", resourceUserProvidedIdentifiers.getKey())
                    .put("externalId", resourceUserProvidedIdentifiers.getExternalId())
                    .put("orderNumber", resourceUserProvidedIdentifiers.getOrderNumber())
                    .put("sku", resourceUserProvidedIdentifiers.getSku())
                    .put("customerNumber", resourceUserProvidedIdentifiers.getCustomerNumber());
        }
        this.furtherFields.put("resourceUserProvidedIdentifiers", node);
    }

    @Override
    public Long getResourceVersion() {
        return resourceVersion;
    }

    @Override
    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public UserProvidedIdentifiers getResourceUserProvidedIdentifiers() {
        return resourceUserProvidedIdentifiers;
    }

    @Override
    public Reference<Message> toReference() {
        return Reference.of(Message.referenceTypeId(), getId(), this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Reference<R> getResource() {
        return SphereJsonUtils.readObject(resource, referenceJavaType);
    }

    @Override
    public JsonNode getPayload() {
        final ObjectMapper objectMapper = SphereJsonUtils.newObjectMapper();
        final ObjectNode jsonNode = objectMapper.createObjectNode();
        furtherFields.entrySet().forEach(entry -> jsonNode.replace(entry.getKey(), entry.getValue()));
        return jsonNode;
    }

    @Override
    public <T> T as(final Class<T> messageClass) {
        final ObjectMapper objectMapper = SphereJsonUtils.newObjectMapper();

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
        return SphereJsonUtils.readObject(jsonNode, messageClass);
    }

    @JsonAnySetter
    private void set(final String key, final JsonNode value) {
        furtherFields.put(key, value);
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericMessageImpl)) return false;
        //
        //important, do not use this:
        //
        //if (!super.equals(o)) return false;
        final GenericMessageImpl<?> that = (GenericMessageImpl<?>) o;
        return Objects.equals(getSequenceNumber(), that.getSequenceNumber()) &&
                Objects.equals(getResource(), that.getResource()) &&
                Objects.equals(getResourceVersion(), that.getResourceVersion()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(furtherFields, that.furtherFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSequenceNumber(), getResource(), getResourceVersion(), getType(), furtherFields);
    }
}
