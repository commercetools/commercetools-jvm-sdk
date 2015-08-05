package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.expansion.MessageExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class MessageByIdGetImpl extends MetaModelGetDslImpl<Message, Message, MessageByIdGet, MessageExpansionModel<Message>> implements MessageByIdGet {
    MessageByIdGetImpl(final String id) {
        super(id, MessageEndpoint.ENDPOINT, MessageExpansionModel.of(), MessageByIdGetImpl::new);
    }

    public MessageByIdGetImpl(MetaModelGetDslBuilder<Message, Message, MessageByIdGet, MessageExpansionModel<Message>> builder) {
        super(builder);
    }
}
