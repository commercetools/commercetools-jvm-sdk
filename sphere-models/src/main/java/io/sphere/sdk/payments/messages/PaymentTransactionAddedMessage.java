package io.sphere.sdk.payments.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.Transaction;
import io.sphere.sdk.queries.PagedQueryResult;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.payments.commands.updateactions.AddTransaction} update action.
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandTest#transActions()}
 *
 * @see io.sphere.sdk.payments.Payment
 * @see io.sphere.sdk.payments.commands.updateactions.AddTransaction
 */
@JsonDeserialize(as = PaymentTransactionAddedMessage.class)//important to override annotation in Message class
public class PaymentTransactionAddedMessage extends GenericMessageImpl<Payment> {
    public static final String MESSAGE_TYPE = "PaymentTransactionAdded";
    public static final MessageDerivateHint<PaymentTransactionAddedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE,
                    new TypeReference<PagedQueryResult<PaymentTransactionAddedMessage>>() {
                    },
                    new TypeReference<PaymentTransactionAddedMessage>() {
                    }
            );

    private final Transaction transaction;

    @JsonCreator
    private PaymentTransactionAddedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final TypeReference<Reference<Payment>> typeReference, final Transaction transaction) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, typeReference);
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
