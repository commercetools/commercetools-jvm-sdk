package io.sphere.sdk.payments.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.payments.Payment;

import java.time.ZonedDateTime;

/**
 * This message is the result of a {@link io.sphere.sdk.payments.commands.PaymentCreateCommand}.
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentCreateCommandIntegrationTest#payingPerCreditCart()}
 *
 * @see Payment
 * @see io.sphere.sdk.payments.commands.PaymentCreateCommand
 *
 */
@JsonDeserialize(as = PaymentCreatedMessage.class)//important to override annotation in Message class
public final class PaymentCreatedMessage extends GenericMessageImpl<Payment> {
    public static final String MESSAGE_TYPE = "PaymentCreated";
    public static final MessageDerivateHint<PaymentCreatedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, PaymentCreatedMessage.class, Payment.referenceTypeId());

    private final Payment payment;

    @JsonCreator
    private PaymentCreatedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Payment payment) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Payment.class);
        this.payment = payment;
    }

    /**
     * Gets the payment at creation time
     * @return possible outdated payment
     */
    public Payment getPayment() {
        return payment;
    }
}
