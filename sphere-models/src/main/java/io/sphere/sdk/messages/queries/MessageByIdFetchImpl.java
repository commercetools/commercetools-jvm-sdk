package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.expansion.MessageExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

final class MessageByIdFetchImpl extends MetaModelFetchDslImpl<Message, MessageByIdFetch, MessageExpansionModel<Message>> implements MessageByIdFetch {
    MessageByIdFetchImpl(final String id) {
        super(id, MessageEndpoint.ENDPOINT, MessageExpansionModel.of(), MessageByIdFetchImpl::new);
    }

    public MessageByIdFetchImpl(MetaModelFetchDslBuilder<Message, MessageByIdFetch, MessageExpansionModel<Message>> builder) {
        super(builder);
    }
}
