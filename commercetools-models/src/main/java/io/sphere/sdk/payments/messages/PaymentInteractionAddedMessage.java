package io.sphere.sdk.payments.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.types.CustomFields;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.payments.commands.updateactions.AddInterfaceInteraction} update action.
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#addInterfaceInteraction()}
 *
 * @see Payment
 * @see io.sphere.sdk.payments.commands.updateactions.AddInterfaceInteraction
 */
@JsonDeserialize(as = PaymentInteractionAddedMessage.class)//important to override annotation in Message class
public final class PaymentInteractionAddedMessage extends GenericMessageImpl<Payment> {
    public static final String MESSAGE_TYPE = "PaymentInteractionAdded";
    public static final MessageDerivateHint<PaymentInteractionAddedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, PaymentInteractionAddedMessage.class, Payment.referenceTypeId());

    private final CustomFields interaction;

    @JsonCreator
    private PaymentInteractionAddedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final CustomFields interaction) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Payment.class);
        this.interaction = interaction;
    }

    public CustomFields getInteraction() {
        return interaction;
    }
}
