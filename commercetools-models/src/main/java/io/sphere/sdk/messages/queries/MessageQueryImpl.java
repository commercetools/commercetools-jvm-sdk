package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.expansion.MessageExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

final class MessageQueryImpl extends MetaModelQueryDslImpl<Message, MessageQuery, MessageQueryModel, MessageExpansionModel<Message>> implements MessageQuery {
    MessageQueryImpl(){
        super(MessageEndpoint.ENDPOINT.endpoint(), MessageQuery.resultTypeReference(), MessageQueryModel.of(), MessageExpansionModel.of(), MessageQueryImpl::new);
    }

    private MessageQueryImpl(final MetaModelQueryDslBuilder<Message, MessageQuery, MessageQueryModel, MessageExpansionModel<Message>> builder) {
        super(builder);
    }
}