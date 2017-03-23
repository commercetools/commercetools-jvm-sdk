package io.sphere.sdk.channels;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.models.*;
import io.sphere.sdk.orders.SyncInfo;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.ReviewRatingStatistics;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/** Channels represent a source or destination of different entities.

 <p>A Customer can have {@link io.sphere.sdk.types.Custom custom fields}.</p>

 @see io.sphere.sdk.channels.commands.ChannelCreateCommand
 @see io.sphere.sdk.channels.commands.ChannelUpdateCommand
 @see io.sphere.sdk.channels.commands.ChannelDeleteCommand
 @see io.sphere.sdk.channels.queries.ChannelQuery
 @see io.sphere.sdk.channels.queries.ChannelByIdGet
 @see LineItem#getSupplyChannel()
 @see io.sphere.sdk.inventory.InventoryEntry#getSupplyChannel()
 @see SyncInfo#getChannel()
 @see io.sphere.sdk.products.Price#getChannel()
 @see Review#getTarget()
 */
@JsonDeserialize(as = ChannelImpl.class)
@ResourceValue
@HasQueryEndpoint(additionalContentsQueryInterface = "    default ChannelQuery byKey(final String key) {\n" +
        "        return withPredicates(m -> m.key().is(key));\n" +
        "    }")
@ResourceInfo(pluralName = "channels", pathElement = "channels")
@HasByIdGetEndpoint(javadocSummary = "Fetches a channel by a known ID.", includeExamples = "io.sphere.sdk.channels.queries.ChannelByIdGetIntegrationTest#execution()")
@HasCreateCommand(javadocSummary = "Creates a channel.\n" +
        "\n" +
        " <p>Example:</p>\n" +
        " {@include.example io.sphere.sdk.channels.commands.ChannelCreateCommandIntegrationTest#execution()}\n" +
        "\n" +
        "\n" +
        "  <p>Example for creation a channel with custom fields:</p>\n" +
        "  {@include.example io.sphere.sdk.channels.ChannelCustomFieldsIntegrationTest#createChannelWithCustomType()}")
@HasUpdateCommand(javadocSummary = "Updates a channel.")
@HasDeleteCommand(javadocSummary = "Deletes a channel.", includeExamples = "io.sphere.sdk.channels.commands.ChannelDeleteCommandIntegrationTest#execution()")
@HasQueryModel
public interface Channel extends Resource<Channel>, WithKey, Custom {
    /**
     * Any arbitrary string key that unique identifies this channel within the project.
     *
     * @see io.sphere.sdk.channels.commands.updateactions.ChangeKey
     *
     * @return key
     */
    String getKey();

    /**
     * The roles of this channel. Each channel must have at least one role.
     *
     * @see io.sphere.sdk.channels.commands.updateactions.SetRoles
     * @see io.sphere.sdk.channels.commands.updateactions.AddRoles
     * @see io.sphere.sdk.channels.commands.updateactions.RemoveRoles
     *
     * @return roles
     */
    @Nonnull
    Set<ChannelRole> getRoles();

    /**
     * Name of this channel.
     *
     * @see io.sphere.sdk.channels.commands.updateactions.ChangeName
     *
     * @return name
     */
    @Nullable
    LocalizedString getName();

    /**
     * Description of this channel.
     *
     * @see io.sphere.sdk.channels.commands.updateactions.ChangeDescription
     *
     * @return description
     */
    @Nullable
    LocalizedString getDescription();

    /**
     * The {@link Review} ratings of this channel.
     *
     * @see Review
     * @see ReviewDraft#getTarget()
     *
     * @return ratings or null
     */
    @Nullable
    ReviewRatingStatistics getReviewRatingStatistics();


    default Reference<Channel> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "channel";
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
    static TypeReference<Channel> typeReference() {
        return new TypeReference<Channel>() {
            @Override
            public String toString() {
                return "TypeReference<Channel>";
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
    static Reference<Channel> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId(){
        return "channel";
    }

    @Nullable
    @Override
    CustomFields getCustom();

    /**
     * The address of a channel.
     * @return null or address
     * @see io.sphere.sdk.channels.commands.updateactions.SetAddress
     */
    @Nullable
    @IgnoreInQueryModel
    Address getAddress();

    /**
     * Returns the geo location of this channel.
     *
     * @return the geo location of this channel or null
     */
    @Nullable
    @QueryModelHint(type = "GeoJSONQueryModel<Channel>", impl = "return geoJSONQueryModel(fieldName);")
    GeoJSON getGeoLocation();
}
