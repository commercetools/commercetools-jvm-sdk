package io.sphere.sdk.states;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;
import java.util.Set;

/** A State represents a state of a particular resource (defines a finite state machine). States can be combined together
    by defining transitions between each state, thus allowing to create work-flows.
    Each project has by default an initial LineItemState (inherited also by custom Line Items)

 @see io.sphere.sdk.states.commands.StateCreateCommand
 @see io.sphere.sdk.states.commands.StateUpdateCommand
 @see io.sphere.sdk.states.commands.StateDeleteCommand
 @see io.sphere.sdk.states.queries.StateQuery
 @see io.sphere.sdk.states.queries.StateByIdGet
 @see ItemState#getState()
 @see io.sphere.sdk.orders.Order#getState()
 @see io.sphere.sdk.payments.PaymentStatus#getState()
 @see io.sphere.sdk.products.Product#getState()
 @see io.sphere.sdk.reviews.Review#getState()
 */
@JsonDeserialize(as = StateImpl.class)
@ResourceValue
@HasQueryEndpoint(additionalContentsQueryInterface = "    default StateQuery byKey(final String key) {\n" +
        "        return withPredicates(StateQueryModel.of().key().is(key));\n" +
        "    }")
@ResourceInfo(pluralName = "states", pathElement = "states")
@HasByIdGetEndpoint
@HasByKeyGetEndpoint(javadocSummary = "Fetches a state by a known key.", includeExamples = "io.sphere.sdk.states.queries.StateByKeyGetIntegrationTest#execution()")
@HasCreateCommand(includeExamples = "io.sphere.sdk.states.commands.StateCreateCommandIntegrationTest#execution()")
@HasUpdateCommand(javadocSummary = "Updates a state.", updateWith = "key")
@HasDeleteCommand(javadocSummary = "Deletes a state.", includeExamples = "io.sphere.sdk.states.commands.StateDeleteCommandIntegrationTest#execution()", deleteWith = {"key","id"})
@HasQueryModel
public interface State extends Resource<State>, WithKey {
    String getKey();

    StateType getType();

    @Nullable
    @IgnoreInQueryModel
    LocalizedString getName();

    @Nullable
    @IgnoreInQueryModel
    LocalizedString getDescription();

    @JsonProperty("initial")
    @IgnoreInQueryModel
    Boolean isInitial();

    @IgnoreInQueryModel
    @JsonProperty("builtin")
    Boolean isBuiltIn();

    @Nullable
    @IgnoreInQueryModel
    Set<Reference<State>> getTransitions();

    @Nullable
    @IgnoreInQueryModel
    Set<StateRole> getRoles();

    default Reference<State> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "state";
    }

    static String resourceTypeId() {
        return "state";
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
    static TypeReference<State> typeReference() {
        return new TypeReference<State>() {
            @Override
            public String toString() {
                return "TypeReference<State>";
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
    static Reference<State> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
