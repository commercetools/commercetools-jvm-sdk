package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.ResourceIdentifier;

import javax.annotation.Nullable;

public final class SetLineItemDistributionChannel extends OrderEditStagedUpdateActionBase {

    private final String lineItemId;
    @Nullable
    private final ResourceIdentifier<Channel> distributionChannel;

    @JsonCreator
    private SetLineItemDistributionChannel(final String lineItemId, @Nullable final ResourceIdentifier<Channel> distributionChannel) {
        super("setLineItemDistributionChannel");
        this.lineItemId = lineItemId;
        this.distributionChannel = distributionChannel;
    }

    public static SetLineItemDistributionChannel of(final String lineItemId) {
        return new SetLineItemDistributionChannel(lineItemId, null);
    }

    public String getLineItemId() {
        return lineItemId;
    }

    @Nullable
    public ResourceIdentifier<Channel> getDistributionChannel() {
        return distributionChannel;
    }

    public SetLineItemDistributionChannel withDistributionChannel(@Nullable final ResourceIdentifier<Channel> distributionChannel) {
        return new SetLineItemDistributionChannel(getLineItemId(), distributionChannel);
    }
}
