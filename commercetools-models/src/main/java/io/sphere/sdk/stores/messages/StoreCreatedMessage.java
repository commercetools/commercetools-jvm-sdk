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
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;


@JsonDeserialize(as = StoreCreatedMessage.class)//important to override annotation in Message class
public final class StoreCreatedMessage extends GenericMessageImpl<Store> {
    public static final String MESSAGE_TYPE = "StoreCreated";
    public static final MessageDerivateHint<StoreCreatedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, StoreCreatedMessage.class, Store.referenceTypeId());

    private final LocalizedString name;
    private final List<String> languages;
    private final List<Reference<Channel>> distributionChannels;
    private final List<Reference<Channel>> supplyChannels;
    @Nullable
    private final CustomFields custom;

    @JsonCreator
    private StoreCreatedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final LocalizedString name, final List<String> languages, final List<Reference<Channel>> distributionChannels, final List<Reference<Channel>> supplyChannels, final CustomFields custom) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, resourceUserProvidedIdentifiers, Store.class);
        this.name = name;
        this.languages = languages;
        this.distributionChannels = distributionChannels;
        this.supplyChannels = supplyChannels;
        this.custom = custom;

    }

    public LocalizedString getName() {
        return name;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public List<Reference<Channel>> getDistributionChannels() {
        return distributionChannels;
    }

    public List<Reference<Channel>> getSupplyChannels() {
        return supplyChannels;
    }

    @Nullable
    public CustomFields getCustom() {
        return custom;
    }
}
