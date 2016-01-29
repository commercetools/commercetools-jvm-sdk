package io.sphere.sdk.channels;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.SyncInfo;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewRatingStatistics;

import javax.annotation.Nullable;
import java.util.Set;

/** Channels represent a source or destination of different entities.

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
public interface Channel extends Resource<Channel> {
    String getKey();

    Set<ChannelRole> getRoles();

    @Nullable
    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

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
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId(){
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
    static TypeReference<Channel> typeReference(){
        return new TypeReference<Channel>() {
            @Override
            public String toString() {
                return "TypeReference<Channel>";
            }
        };
    }

    static Reference<Channel> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
