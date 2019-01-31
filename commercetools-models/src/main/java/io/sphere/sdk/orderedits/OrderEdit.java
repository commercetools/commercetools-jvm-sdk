package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.orderedits.commands.stagedactions.OrderEditStagedUpdateAction;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@JsonDeserialize(as = OrderEditImpl.class)
@ResourceValue
@ResourceInfo(pluralName = "orderedits", pathElement = "orders/edits")
@HasByIdGetEndpoint
@HasByKeyGetEndpoint
@HasCreateCommand
@HasUpdateCommand(updateWith = "key")
@HasDeleteCommand(deleteWith = {"key","id"})
public interface OrderEdit extends Resource<OrderEdit> {

    String getId();

    Long getVersion();

    ZonedDateTime getCreatedAt();

    ZonedDateTime getLastModifiedAt();

    @Nullable
    @HasUpdateAction
    String getKey();

    Reference<Order> getResource();

    @HasUpdateAction(value = "setStagedActions")
    List<OrderEditStagedUpdateAction> getStagedActions();

    @Nullable
    CustomFields getCustom();

    OrderEditResult getResult();

    @Nullable
    @HasUpdateAction
    String getComment();

    @Override
    default Reference<OrderEdit> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "orderedit";
    }


    static TypeReference<OrderEdit> typeReference() {
        return new TypeReference<OrderEdit>() {
            @Override
            public String toString() {
                return "TypeReference<OrderEdit>";
            }
        };
    }
}
