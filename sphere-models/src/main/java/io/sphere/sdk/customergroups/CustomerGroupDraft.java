package io.sphere.sdk.customergroups;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

/**
 * <p>Draft for a customer group.</p>
 *
 * @see io.sphere.sdk.customergroups.commands.CustomerGroupCreateCommand
 * @see CustomerGroup
 */
public class CustomerGroupDraft extends Base {
    private final String groupName;

    @JsonCreator
    private CustomerGroupDraft(final String groupName) {
        this.groupName = groupName;
    }

    public static CustomerGroupDraft of(final String groupName) {
        return new CustomerGroupDraft(groupName);
    }

    public String getGroupName() {
        return groupName;
    }
}
