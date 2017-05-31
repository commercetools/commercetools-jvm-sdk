package io.sphere.sdk.payments.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.payments.Payment;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;


@JsonDeserialize(as = PaymentStatusInterfaceCodeSetMessage.class)//important to override annotation in Message class
public final class PaymentStatusInterfaceCodeSetMessage extends GenericMessageImpl<Payment> {
    public static final String MESSAGE_TYPE = "PaymentStatusInterfaceCodeSet";
    public static final MessageDerivateHint<PaymentStatusInterfaceCodeSetMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, PaymentStatusInterfaceCodeSetMessage.class, Payment.referenceTypeId());
    @Nullable
    private final String interfaceCode;

    private final String paymentId;

    @JsonCreator
    private PaymentStatusInterfaceCodeSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final String interfaceCode,final String paymentId) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, Payment.class);
        this.interfaceCode = interfaceCode;
        this.paymentId = paymentId;
    }

    @Nullable
    public String getInterfaceCode() {
        return interfaceCode;
    }

    public String getPaymentId() {
        return paymentId;
    }
}
