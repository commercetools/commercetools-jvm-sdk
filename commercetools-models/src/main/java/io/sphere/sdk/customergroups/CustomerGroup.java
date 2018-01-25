package io.sphere.sdk.customergroups;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;

/**
 * <p>A Customer can be a member of a customer group (e.g. reseller, gold member). Special prices can be assigned to specific products based on a customer group.</p>
 *
 * @see io.sphere.sdk.customergroups.commands.CustomerGroupCreateCommand
 * @see io.sphere.sdk.customergroups.commands.CustomerGroupUpdateCommand
 * @see io.sphere.sdk.customergroups.commands.CustomerGroupDeleteCommand
 * @see io.sphere.sdk.customergroups.queries.CustomerGroupQuery
 * @see io.sphere.sdk.customergroups.queries.CustomerGroupByIdGet
 * @see Customer#getCustomerGroup()
 * @see io.sphere.sdk.customers.commands.updateactions.SetCustomerGroup
 * @see Cart#getCustomerGroup()
 * @see io.sphere.sdk.orders.Order#getCustomerGroup()
 * @see io.sphere.sdk.products.Price#getCustomerGroup()
 */
@JsonDeserialize(as = CustomerGroupImpl.class)
@ResourceValue
@HasQueryEndpoint(additionalContentsQueryInterface = "    default CustomerGroupQuery byName(final String name) {\n" +
        "        return withPredicates(m -> m.name().is(name));\n" +
        "    }")
@ResourceInfo(pluralName = "customer groups", pathElement = "customer-groups")
@HasByIdGetEndpoint(javadocSummary = "Fetches a customer group based on a known ID.", includeExamples = "io.sphere.sdk.customergroups.queries.CustomerGroupByIdGetIntegrationTest#execution()")
@HasByKeyGetEndpoint(javadocSummary = "Fetches a customer group based on a known key.", includeExamples = "io.sphere.sdk.customergroups.queries.CustomerGroupByKeyGetIntegrationTest#execution()")
@HasCreateCommand(javadocSummary = "Creates a new customer group", includeExamples = "io.sphere.sdk.customergroups.commands.CustomerGroupCreateCommandIntegrationTest#execution()", interfaceContents = "static CustomerGroupCreateCommand of(final String groupName) {\n" +
        "        return of(CustomerGroupDraft.of(groupName));\n" +
        "    }")
@HasUpdateCommand(javadocSummary = "Updates a customer group.", updateWith = "key")
@HasDeleteCommand(javadocSummary = "Deletes a customer group.", includeExamples = "io.sphere.sdk.customergroups.commands.CustomerGroupDeleteCommandByIdIntegrationTest#execution()", deleteWith = "key")
@HasQueryModel
public interface CustomerGroup extends Resource<CustomerGroup>, WithKey,Custom {

    /**
     * The name of the customer group.
     *
     * @see io.sphere.sdk.customergroups.commands.updateactions.ChangeName
     *
     * @return name
     */
    @HasUpdateAction
    String getName();

    @Override
    @Nullable
    @HasUpdateAction
    String getKey();


    @Nullable
    @Override
    CustomFields getCustom();

    @Override
    default Reference<CustomerGroup> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "customer-group";
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<CustomerGroup> typeReference() {
        return new TypeReference<CustomerGroup>() {
            @Override
            public String toString() {
                return "TypeReference<CustomerGroup>";
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
    static Reference<CustomerGroup> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
