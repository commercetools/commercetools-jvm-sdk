package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessage;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.products.Product;

/**
 * Messages for a {@link io.sphere.sdk.products.Product}.
 */
@JsonDeserialize(as = SimpleProductMessageImpl.class)
public interface SimpleProductMessage extends GenericMessage<Product> {
        MessageDerivateHint<SimpleProductMessage> MESSAGE_HINT =
            MessageDerivateHint.ofResourceType(Product.referenceTypeId(), SimpleProductMessage.class, Product.referenceTypeId());
}
