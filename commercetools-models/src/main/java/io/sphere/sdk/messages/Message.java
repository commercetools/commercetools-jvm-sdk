package io.sphere.sdk.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.HasByIdGetEndpoint;
import io.sphere.sdk.annotations.ResourceInfo;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;

import javax.annotation.Nullable;

/**
 * A message represents a change or an action performed on a resource (like an Order or a Product). Messages can be seen as a subset of the change history for a resource inside a project. It is a subset because not all changes on resources result in messages.
 *
 * Consult {@link io.sphere.sdk.messages.queries.MessageQuery} how to use messages.
 */
@JsonDeserialize(as = MessageImpl.class)
@ResourceInfo(pluralName = "messages", pathElement = "messages")
@HasByIdGetEndpoint(javadocSummary = "{@include.example io.sphere.sdk.messages.queries.MessageByIdGetIntegrationTest#execution()}\n" +
        "\n" +
        "If you need to receive one specific message class, like {@link io.sphere.sdk.orders.messages.DeliveryAddedMessage},\n" +
        "use {@link MessageQuery} with a predicate by id.")
public interface Message extends Resource<Message> {
    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "message";
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<Message> typeReference() {
        return new TypeReference<Message>() {
            @Override
            public String toString() {
                return "TypeReference<Message>";
            }
        };
    }

    Reference<?> getResource();

    Long getResourceVersion();

    Long getSequenceNumber();

    String getType();

    @Nullable
    UserProvidedIdentifiers getResourceUserProvidedIdentifiers();

    /**
     * Gets the top level fields not mapped by the current message class.
     * @return json
     */
    JsonNode getPayload();

    @Override
    default Reference<Message> toReference() {
        return Reference.of(Message.referenceTypeId(), getId());
    }

    <T> T as(final Class<T> messageClass);

    /**
     * Creates a reference for one item of this class by a known ID.
     *
     * <p>An example for categories but this applies for other resources, too:</p>
     * {@include.example io.sphere.sdk.categories.CategoryTest#referenceOfId()}
     *
     * <p>If you already have a resource object, then use {@link #toReference()} instead:</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTest#toReference()}
     *
     * @param id the ID of the resource which should be referenced.
     * @return reference
     */
    static Reference<Message> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
