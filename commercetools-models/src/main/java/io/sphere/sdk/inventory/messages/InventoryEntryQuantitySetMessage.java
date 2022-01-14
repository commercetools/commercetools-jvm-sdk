package io.sphere.sdk.inventory.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = InventoryEntryQuantitySetMessage.class)//important to override annotation in Message class
public final class InventoryEntryQuantitySetMessage extends GenericMessageImpl<InventoryEntry> {
    public static final String MESSAGE_TYPE = "InventoryEntryQuantitySet";
    public static final MessageDerivateHint<InventoryEntryQuantitySetMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, InventoryEntryQuantitySetMessage.class, InventoryEntry.referenceTypeId());

    private final Long oldQuantityOnStock;
    private final Long newQuantityOnStock;
    private final Long oldAvailableQuantity;
    private final Long newAvailableQuantity;
    @Nullable
    private final Reference<Channel> supplyChannel;

    @JsonCreator
    private InventoryEntryQuantitySetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, Long oldQuantityOnStock, Long newQuantityOnStock, Long oldAvailableQuantity, Long newAvailableQuantity, final Reference<Channel> supplyChannel) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, InventoryEntry.class);
        this.oldQuantityOnStock = oldQuantityOnStock;
        this.newQuantityOnStock = newQuantityOnStock;
        this.oldAvailableQuantity = oldAvailableQuantity;
        this.newAvailableQuantity = newAvailableQuantity;
        this.supplyChannel = supplyChannel;
    }

    public Long getOldQuantityOnStock() {
        return oldQuantityOnStock;
    }

    public Long getNewQuantityOnStock() {
        return newQuantityOnStock;
    }

    public Long getOldAvailableQuantity() {
        return oldAvailableQuantity;
    }

    public Long getNewAvailableQuantity() {
        return newAvailableQuantity;
    }

    @Nullable
    public Reference<Channel> getSupplyChannel() {
        return supplyChannel;
    }
}
