package io.sphere.sdk.messages;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.HasByIdGetEndpoint;
import io.sphere.sdk.annotations.ResourceInfo;
import io.sphere.sdk.categories.messages.CategoryCreatedMessage;
import io.sphere.sdk.categories.messages.CategorySlugChangedMessage;
import io.sphere.sdk.customers.messages.CustomerCreatedMessage;
import io.sphere.sdk.inventory.messages.InventoryEntryDeletedMessage;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.orders.messages.*;
import io.sphere.sdk.payments.messages.*;
import io.sphere.sdk.products.messages.*;
import io.sphere.sdk.reviews.messages.ReviewCreatedMessage;
import io.sphere.sdk.reviews.messages.ReviewRatingSetMessage;
import io.sphere.sdk.reviews.messages.ReviewStateTransitionMessage;

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
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CategoryCreatedMessage.class, name = CategoryCreatedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = CategorySlugChangedMessage.class, name = CategorySlugChangedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = CustomerCreatedMessage.class, name = CustomerCreatedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = CustomLineItemStateTransitionMessage.class, name = CustomLineItemStateTransitionMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = DeliveryAddedMessage.class, name = DeliveryAddedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = InventoryEntryDeletedMessage.class, name = InventoryEntryDeletedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = LineItemStateTransitionMessage.class, name = LineItemStateTransitionMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = OrderBillingAddressSetMessage.class, name = ProductStateTransitionMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = OrderCreatedMessage.class, name = OrderCreatedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = OrderImportedMessage.class, name = OrderImportedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = OrderShippingAddressSetMessage.class, name = OrderShippingAddressSetMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = OrderStateChangedMessage.class, name = OrderStateChangedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = OrderStateTransitionMessage.class, name = OrderStateTransitionMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = ParcelAddedToDeliveryMessage.class, name = ParcelAddedToDeliveryMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = PaymentCreatedMessage.class, name = PaymentCreatedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = PaymentInteractionAddedMessage.class, name = PaymentInteractionAddedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = PaymentStatusStateTransitionMessage.class, name = PaymentStatusStateTransitionMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = PaymentTransactionAddedMessage.class, name = PaymentTransactionAddedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = PaymentTransactionStateChangedMessage.class, name = PaymentTransactionStateChangedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = ProductCreatedMessage.class, name = ProductCreatedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = ProductPublishedMessage.class, name = ProductPublishedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = ProductSlugChangedMessage.class, name = ProductSlugChangedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = ProductStateTransitionMessage.class, name = ProductStateTransitionMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = ProductUnpublishedMessage.class, name = ProductUnpublishedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = ReturnInfoAddedMessage.class, name = ReturnInfoAddedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = ReviewCreatedMessage.class, name = ReviewCreatedMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = ReviewRatingSetMessage.class, name = ReviewRatingSetMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = ReviewStateTransitionMessage.class, name = ReviewStateTransitionMessage.MESSAGE_TYPE),
        @JsonSubTypes.Type(value = ReviewStateTransitionMessage.class, name = ReviewStateTransitionMessage.MESSAGE_TYPE)
})
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
