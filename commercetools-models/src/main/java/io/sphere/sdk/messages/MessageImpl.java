package io.sphere.sdk.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.ZonedDateTime;

public class MessageImpl extends GenericMessageImpl<JsonNode> {

    @JsonCreator
    public MessageImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, JsonNode.class);
    }
}
