package io.sphere.sdk.customergroups;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

final class CustomerGroupDraftImpl extends Base implements CustomerGroupDraft {
    private final String groupName;

    @JsonCreator
    CustomerGroupDraftImpl(final String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String getGroupName() {
        return groupName;
    }
}
