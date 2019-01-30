package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.HasCreateCommand;
import io.sphere.sdk.annotations.HasUpdateAction;
import io.sphere.sdk.annotations.ResourceInfo;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@JsonDeserialize(as = OrderEditImpl.class)
@ResourceValue
@ResourceInfo(pluralName = "orderedits", pathElement = "orders/edits")
@HasCreateCommand
public interface OrderEdit {

    String getId();

    Long getVersion();

    ZonedDateTime getCreatedAt();

    ZonedDateTime getLastModifiedAt();

    @Nullable
    String getKey();

    Reference<Order> getResource();

    List<OrderEditStagedUpdateAction> getStagedActions();

    @Nullable
    CustomFields getCustom();

    OrderEditResult getResult();

    @Nullable
    @HasUpdateAction
    String getComment();

    static TypeReference<OrderEdit> typeReference() {
        return new TypeReference<OrderEdit>() {
            @Override
            public String toString() {
                return "TypeReference<OrderEdit>";
            }
        };
    }
}
