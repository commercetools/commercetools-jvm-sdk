package io.sphere.sdk.messages.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.expansion.MessageExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;

/**

 {@doc.gen summary messages}

 <p>Query for any message:</p>
 {@include.example io.sphere.sdk.messages.queries.MessageQueryTest#queryForAllMessages()}

 <p>Query for a specific message class:</p>
 {@include.example io.sphere.sdk.messages.queries.MessageQueryTest#queryForASpecificMessage()}

<p>Query for any message and then convert into specific messages:</p>
 {@include.example io.sphere.sdk.messages.queries.MessageQueryTest#convertAfterQueryToSpecificMessageClasses()}

<p>Query for any message for a specific resource like orders:</p>
 {@include.example io.sphere.sdk.messages.queries.MessageQueryTest#queryForASpecificResource()}

<p>If you convert a message to the wrong class the behaviour is undefined, but then still null pointer exceptions can occur:</p>
 {@include.example io.sphere.sdk.messages.queries.MessageQueryTest#convertAfterQueryToSpecificMessageClassesButToTheWrongOne()}

 */
public interface MessageQuery extends MetaModelQueryDsl<Message, MessageQuery, MessageQueryModel, MessageExpansionModel<Message>> {
    /**
     * Creates a container which contains the full Java type information to deserialize the query result (NOT this class) from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<PagedQueryResult<Message>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Message>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Message>>";
            }
        };
    }

    static MessageQuery of() {
        return new MessageQueryImpl();
    }

    /**
     * Creates a new query that queries only for messages that can be mapped to a certain Java type. This is a terminal operation so no changes can be done on the query.
     *
     * {@include.example io.sphere.sdk.messages.queries.MessageQueryTest#queryForASpecificMessage()}
     *
     * @param hint a container containing the message type and type references. You can find it as static field on the message you want, e.g., {@link io.sphere.sdk.orders.messages.DeliveryAddedMessage#MESSAGE_HINT}.
     * @param <T> the type of a single message that should be the outcome of a query.
     * @return a new query for a certain Java type of messages
     */
    default <T> Query<T> forMessageType(final MessageDerivateHint<T> hint) {
        final MessageQuery queryWithPredicateForType =
                plusPredicates(hint.predicate());
        return new TypedMessageQuery<>(queryWithPredicateForType.httpRequestIntent(), hint.javaType());
    }
}
