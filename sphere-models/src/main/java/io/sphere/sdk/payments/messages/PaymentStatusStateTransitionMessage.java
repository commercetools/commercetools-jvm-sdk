package io.sphere.sdk.payments.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.states.State;

import java.time.ZonedDateTime;

/**
 This message is the result of the {@link io.sphere.sdk.payments.commands.updateactions.TransitionState} update action.

 {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandTest#transitionState()}

 @see Payment
 @see io.sphere.sdk.payments.commands.updateactions.TransitionState
 */
@JsonDeserialize(as = PaymentStatusStateTransitionMessage.class)//important to override annotation in Message class
public class PaymentStatusStateTransitionMessage extends GenericMessageImpl<Payment> {
    public static final String MESSAGE_TYPE = "PaymentStatusStateTransition";
    public static final MessageDerivateHint<PaymentStatusStateTransitionMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE,
                    new TypeReference<PagedQueryResult<PaymentStatusStateTransitionMessage>>() {
                    },
                    new TypeReference<PaymentStatusStateTransitionMessage>() {
                    }
            );

    private final Reference<State> state;

    @JsonCreator
    private PaymentStatusStateTransitionMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final Reference<State> state) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, new TypeReference<Reference<Payment>>(){});
        this.state = state;
    }

    public Reference<State> getState() {
        return state;
    }
}
