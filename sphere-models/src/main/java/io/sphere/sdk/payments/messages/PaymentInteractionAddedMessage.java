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
import io.sphere.sdk.types.CustomFields;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.payments.commands.updateactions.AddInterfaceInteraction} update action.
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandTest#addInterfaceInteraction()}
 *
 * @see Payment
 * @see io.sphere.sdk.payments.commands.updateactions.AddInterfaceInteraction
 */
@JsonDeserialize(as = PaymentInteractionAddedMessage.class)//important to override annotation in Message class
public class PaymentInteractionAddedMessage extends GenericMessageImpl<Payment> {
    public static final String MESSAGE_TYPE = "PaymentInteractionAdded";
    public static final MessageDerivateHint<PaymentInteractionAddedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE,
                    new TypeReference<PagedQueryResult<PaymentInteractionAddedMessage>>() {
                    },
                    new TypeReference<PaymentInteractionAddedMessage>() {
                    }
            );

    private final CustomFields interaction;

    @JsonCreator
    private PaymentInteractionAddedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final CustomFields interaction) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, new TypeReference<Reference<Payment>>(){});
        this.interaction = interaction;
    }

    public CustomFields getInteraction() {
        return interaction;
    }
}
