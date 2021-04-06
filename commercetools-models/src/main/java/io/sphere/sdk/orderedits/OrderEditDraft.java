package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.commands.StagedUpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(as = OrderEditDraftDsl.class)
@ResourceDraftValue(factoryMethods = {
        @FactoryMethod(parameterNames = {"key", "resource", "stagedActions", "custom", "comment", "dryRun"}),
        @FactoryMethod(parameterNames = {"resource", "stagedActions"})
        })
public interface OrderEditDraft extends WithKey, CustomDraft {

    @Nullable
    String getKey();

    Reference<Order> getResource();

    List<StagedUpdateAction<OrderEdit>> getStagedActions();

    @Nullable
    CustomFieldsDraft getCustom();

    @Nullable
    String getComment();

    @JsonProperty(value = "dryRun", defaultValue = "false")
    @Nullable
    Boolean isDryRun();
}
