package io.sphere.sdk.channels;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.reviews.ReviewRatingStatistics;

import javax.annotation.Nullable;
import java.util.Set;

/** Channels represent a source or destination of different entities.

    <p id=operations>Operations:</p>
    <ul>
        <li>Create a channel with {@link io.sphere.sdk.channels.commands.ChannelCreateCommand}.</li>
        <li>Query a channel with {@link io.sphere.sdk.channels.queries.ChannelQuery}.</li>
        <li>Delete a channel with {@link io.sphere.sdk.channels.commands.ChannelDeleteCommand}.</li>
    </ul>
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
