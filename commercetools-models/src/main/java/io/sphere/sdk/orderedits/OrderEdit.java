package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.orderedits.commands.stagedactions.OrderEditStagedUpdateAction;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@JsonDeserialize(as = OrderEditImpl.class)
@ResourceValue
@ResourceInfo(pluralName = "orderedits", pathElement = "orders/edits")
@HasByIdGetEndpoint(javadocSummary = "Gets an order edit by ID.", includeExamples = "io.sphere.sdk.orderedit.queries.OrderEditByIdGetIntegrationTest#execute()")
@HasByKeyGetEndpoint(javadocSummary = "Gets an order edit by key.", includeExamples = "io.sphere.sdk.orderedit.queries.OrderEditByKeyGetIntegrationTest#execute()")
@HasCreateCommand(includeExamples = "io.sphere.sdk.orderedit.commands.OrderEditCreateCommandIntegrationTest#execute()")
@HasUpdateCommand(updateWith = "key")
@HasDeleteCommand(deleteWith = {"key","id"}, includeExamples = "io.sphere.sdk.orderedit.commands.OrderEditCreateCommandIntegrationTest#execute()")
@HasQueryEndpoint(additionalContentsQueryInterface = "\n" +
        "    default OrderEditQuery byKey(final String key) {\n" +
        "        return withPredicates(m -> m.key().is(key));\n" +
        "    }\n"
        )
@HasQueryModel()
public interface OrderEdit extends Resource<OrderEdit>, Custom {

    String getId();

    Long getVersion();

    ZonedDateTime getCreatedAt();

    ZonedDateTime getLastModifiedAt();

    @Nullable
    @HasUpdateAction
    String getKey();

    Reference<Order> getResource();

    @IgnoreInQueryModel
    @HasUpdateAction(value = "setStagedActions")
    List<OrderEditStagedUpdateAction> getStagedActions();

    @Nullable
    CustomFields getCustom();

    @IgnoreInQueryModel
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
        return "order-edit";
    }


    static TypeReference<OrderEdit> typeReference() {
        return new TypeReference<OrderEdit>() {
            @Override
            public String toString() {
                return "TypeReference<OrderEdit>";
            }
        };
    }

    /**
     * Creates a reference for one item of this class by a known ID.
     *
     * <p>An example for categories but this applies for other resources, too:</p>
     * {@include.example io.sphere.sdk.categories.CategoryTest#referenceOfId()}
     *
     * <p>If you already have a resource object, then use {@link #toReference()} instead:</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTest#toReference()}
     *
     * @param id the ID of the resource which should be referenced.
     * @return reference
     */
    static Reference<OrderEdit> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
