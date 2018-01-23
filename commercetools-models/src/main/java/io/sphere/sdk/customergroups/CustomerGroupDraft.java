package io.sphere.sdk.customergroups;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;

/**
 * <p>Draft for a customer group.</p>
 *
 * @see io.sphere.sdk.customergroups.commands.CustomerGroupCreateCommand
 * @see CustomerGroup
 */
@JsonDeserialize(as = CustomerGroupDraftDsl.class)
@ResourceDraftValue(
        factoryMethods = @FactoryMethod(parameterNames = "groupName"))
public interface CustomerGroupDraft extends WithKey ,Custom{

    @Override
    @Nullable
    String getKey();

    String getGroupName();

    @Nullable
    @Override
    CustomFields getCustom();

    static CustomerGroupDraft of(final String groupName) {
        return CustomerGroupDraftDsl.of(groupName);
    }

    static CustomerGroupDraft of(final String groupName, @Nullable final String key) {
        return CustomerGroupDraftDsl.of(groupName).withKey(key);
    }

}
