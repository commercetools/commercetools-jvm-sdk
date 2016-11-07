package io.sphere.sdk.customergroups;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.types.CustomDraft;

/**
 * <p>Draft for a customer group.</p>
 *
 * @see io.sphere.sdk.customergroups.commands.CustomerGroupCreateCommand
 * @see CustomerGroup
 */
@JsonDeserialize(as = CustomerGroupDraftDsl.class)
@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = "groupName"))
public interface CustomerGroupDraft {
    String getGroupName();

    static CustomerGroupDraft of(final String groupName) {
        return CustomerGroupDraftDsl.of(groupName);
    }
}
