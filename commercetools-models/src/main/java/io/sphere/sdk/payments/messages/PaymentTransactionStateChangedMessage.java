package io.sphere.sdk.payments.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.TransactionState;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.payments.commands.updateactions.ChangeTransactionState} update action.
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandTest#changeTransactionState()}
 *
 */
@JsonDeserialize(as = PaymentTransactionStateChangedMessage.class)//important to override annotation in Message class
public final class PaymentTransactionStateChangedMessage extends GenericMessageImpl<Payment> {
    public static final String MESSAGE_TYPE = "PaymentTransactionStateChanged";
    public static final MessageDerivateHint<PaymentTransactionStateChangedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, PaymentTransactionStateChangedMessage.class, Payment.referenceTypeId());

    private final TransactionState state;

    @JsonCreator
    private PaymentTransactionStateChangedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final TransactionState state) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, Payment.class);
        this.state = state;
    }

    public TransactionState getState() {
        return state;
    }
}
