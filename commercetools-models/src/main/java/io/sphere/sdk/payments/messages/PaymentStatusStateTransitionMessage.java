package io.sphere.sdk.payments.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.states.State;

import java.time.ZonedDateTime;

/**
 This message is the result of the {@link io.sphere.sdk.payments.commands.updateactions.TransitionState} update action.

 {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#transitionState()}

 @see Payment
 @see io.sphere.sdk.payments.commands.updateactions.TransitionState
 */
@JsonDeserialize(as = PaymentStatusStateTransitionMessage.class)//important to override annotation in Message class
public final class PaymentStatusStateTransitionMessage extends GenericMessageImpl<Payment> {
    public static final String MESSAGE_TYPE = "PaymentStatusStateTransition";
    public static final MessageDerivateHint<PaymentStatusStateTransitionMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, PaymentStatusStateTransitionMessage.class, Payment.referenceTypeId());

    private final Reference<State> state;

    @JsonCreator
    private PaymentStatusStateTransitionMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Reference<State> state) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Payment.class);
        this.state = state;
    }

    public Reference<State> getState() {
        return state;
    }
}
