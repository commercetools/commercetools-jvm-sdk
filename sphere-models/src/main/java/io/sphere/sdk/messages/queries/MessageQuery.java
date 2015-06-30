package io.sphere.sdk.messages.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.MessageDerivatHint;
import io.sphere.sdk.messages.expansion.MessageExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.QueryPredicate;

/**

 {@doc.gen summary messages}

 <p>Query for any message:</p>
 {@include.example io.sphere.sdk.messages.queries.MessageQueryTest#queryForASpecificMessage()}

 <p>Query for specific messages:</p>
 {@include.example io.sphere.sdk.messages.queries.MessageQueryTest#queryForAllMessages()}
 */
public interface MessageQuery extends MetaModelQueryDsl<Message, MessageQuery, MessageQueryModel, MessageExpansionModel<Message>> {
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

    default <T> Query<T> forMessageType(final MessageDerivatHint<T> hint) {
        final QueryPredicate<Message> additionalPredicate = MessageQueryModel.of().type().is(hint.type());
        final MessageQuery queryWithPredicateForType =
                withPredicate(predicate().map(p -> p.and(additionalPredicate)).orElse(additionalPredicate));
        return new TypedMessageQuery<>(queryWithPredicateForType.httpRequestIntent(), hint.queryResultTypeReference());
    }
}
