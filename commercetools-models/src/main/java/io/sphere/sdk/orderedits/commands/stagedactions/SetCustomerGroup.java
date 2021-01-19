package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.models.ResourceIdentifier;

import javax.annotation.Nullable;
import java.util.Optional;

public final class SetCustomerGroup extends OrderEditStagedUpdateActionBase {

    @Nullable
    private final ResourceIdentifier<CustomerGroup> customerGroup;

    @JsonCreator
    private SetCustomerGroup(final ResourceIdentifier<CustomerGroup> customerGroup) {
        super("setCustomerGroup");
        this.customerGroup = customerGroup;
    }

    public static SetCustomerGroup of(@Nullable final ResourceIdentifiable<CustomerGroup> customerGroup) {
        final ResourceIdentifier<CustomerGroup> resourceIdentifier = Optional.ofNullable(customerGroup)
                .map(ResourceIdentifiable::toResourceIdentifier)
                .orElse(null);
        return new SetCustomerGroup(resourceIdentifier);
    }

    public ResourceIdentifier<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }
}
