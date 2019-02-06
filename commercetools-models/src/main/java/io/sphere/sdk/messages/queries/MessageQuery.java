package io.sphere.sdk.messages.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.expansion.MessageExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.QueryPredicate;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**

 {@doc.gen summary messages}

 <h3 id="query-any-message">Query for any message</h3>
 {@include.example io.sphere.sdk.messages.queries.MessageQueryIntegrationTest#queryForAllMessages()}

 <h3 id="query-one-specific-message-class">Query for a specific message class</h3>
 {@include.example io.sphere.sdk.messages.queries.MessageQueryIntegrationTest#queryForASpecificMessage()}

 <h3 id="query-multiple-specific-message-classes">Query for multiple specific message classes</h3>
 {@include.example io.sphere.sdk.messages.queries.MessageQueryIntegrationTest#queryForMultipleSpecificMessageClasses()}

 <h3 id="query-any-message-convert-to-specific-message">Query for any message and then convert into specific messages</h3>
 {@include.example io.sphere.sdk.messages.queries.MessageQueryIntegrationTest#convertAfterQueryToSpecificMessageClasses()}

 <h3 id="query-for-specific-resource">Query for any message for a specific resource like orders</h3>
 {@include.example io.sphere.sdk.messages.queries.MessageQueryIntegrationTest#queryForASpecificResource()}

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
     * {@include.example io.sphere.sdk.messages.queries.MessageQueryIntegrationTest#queryForASpecificMessage()}
     *
     * @param hint a container containing the message type and type references. You can find it as static field on the message you want, e.g., {@link io.sphere.sdk.orders.messages.DeliveryAddedMessage#MESSAGE_HINT}.
     * @param <T> the type of a single message that should be the outcome of a query.
     * @return a new query for a certain Java type of messages
     */
    default <T extends Message> Query<T> forMessageType(final MessageDerivateHint<T> hint) {
        final MessageQuery queryWithPredicateForType =
                plusPredicates(hint.predicate());
        return new TypedMessageQuery<>(queryWithPredicateForType.httpRequestIntent(), hint.javaType());
    }

    /**
     * Creates a new query that queries only for messages that can be mapped to certain Java types. This is a terminal operation so no changes can be done on the query.
     *
     * {@include.example io.sphere.sdk.messages.queries.MessageQueryIntegrationTest#queryForMultipleSpecificMessageClasses()}
     *
     * @param messageHints internal containers which register the known messages
     * @return new query
     */
    default Query<Message> forMessageTypes(final List<MessageDerivateHint<? extends Message>> messageHints) {
        final MessageQuery queryWithPredicateForType = messageHints.stream()
                .map(hint -> hint.predicate())
                .reduce((left, right) -> left.or(right))
                .map(predicate -> plusPredicates(predicate))
                .orElse(this);
        return new MultiTypedMessageQuery(queryWithPredicateForType, messageHints);
    }
}
