package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.sphere.sdk.commands.StagedUpdateAction;
import io.sphere.sdk.orderedits.commands.stagedactions.SetCustomerEmail;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "action")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SetCustomerEmail.class, name = "setCustomerEmail")
})
public interface OrderEditStagedUpdateAction extends StagedUpdateAction<OrderEdit> {
}
