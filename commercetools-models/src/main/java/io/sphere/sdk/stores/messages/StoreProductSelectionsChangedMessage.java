package io.sphere.sdk.stores.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.stores.ProductSelectionSetting;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;


@JsonDeserialize(as = StoreProductSelectionsChangedMessage.class)//important to override annotation in Message class
public final class StoreProductSelectionsChangedMessage extends GenericMessageImpl<Store> {
    public static final String MESSAGE_TYPE = "StoreProductSelectionsChanged";
    public static final MessageDerivateHint<StoreProductSelectionsChangedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, StoreProductSelectionsChangedMessage.class, Store.referenceTypeId());

    @Nullable
    private final List<ProductSelectionSetting> addedProductSelections;

    @Nullable
    private final List<ProductSelectionSetting> removedProductSelections;

    @Nullable
    private final List<ProductSelectionSetting> updatedProductSelections;

    @JsonCreator
    private StoreProductSelectionsChangedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final List<ProductSelectionSetting> addedProductSelections, final List<ProductSelectionSetting> removedProductSelections, final List<ProductSelectionSetting> updatedProductSelections) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, resourceUserProvidedIdentifiers, Store.class);
        this.addedProductSelections = addedProductSelections;
        this.removedProductSelections = removedProductSelections;
        this.updatedProductSelections = updatedProductSelections;
    }

    @Nullable
    public List<ProductSelectionSetting> getAddedProductSelections() {
        return addedProductSelections;
    }
    @Nullable
    public List<ProductSelectionSetting> getRemovedProductSelections() {
        return removedProductSelections;
    }
    @Nullable
    public List<ProductSelectionSetting> getUpdatedProductSelections() {
        return updatedProductSelections;
    }
}
