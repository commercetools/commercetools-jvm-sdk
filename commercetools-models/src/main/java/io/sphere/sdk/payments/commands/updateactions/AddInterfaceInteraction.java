package io.sphere.sdk.payments.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.types.customupdateactions.SetCustomTypeBase;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

/**
 * Adds a new interaction with the interface. These can be notifications received from the PSP or requests sent to the PSP.
 * Some interactions may result in a transaction. If so, the interactionId in the transaction should be set to match the ID of the PSP for the interaction.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#addInterfaceInteraction()}
 *
 * @see Payment
 * @see Payment#getInterfaceInteractions()
 * @see io.sphere.sdk.payments.messages.PaymentInteractionAddedMessage
 */
public final class AddInterfaceInteraction extends SetCustomTypeBase<Payment> {
    /**
     * Gets the resource type id for the custom field creation.
     *
     * @return resource ID
     *
     * @see io.sphere.sdk.types.TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     */
    public static String resourceTypeId() {
        return "payment-interface-interaction";
    }

    private AddInterfaceInteraction(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        super("addInterfaceInteraction", typeId, typeKey, fields);
    }

    public static AddInterfaceInteraction ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson);
    }

    public static AddInterfaceInteraction ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson);
    }

    public static AddInterfaceInteraction ofTypeKeyAndObjects(final String typeKey, final String fieldName, final Object value) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeKeyAndObjects(typeKey, fields);
    }

    public static AddInterfaceInteraction ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields);
    }

    public static AddInterfaceInteraction ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields) {
        return new AddInterfaceInteraction(typeId, null, fields);
    }

    public static AddInterfaceInteraction ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields) {
        return new AddInterfaceInteraction(null, typeKey, fields);
    }
}
