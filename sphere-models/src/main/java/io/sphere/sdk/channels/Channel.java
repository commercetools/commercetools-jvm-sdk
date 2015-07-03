package io.sphere.sdk.channels;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.util.Optional;
import java.util.Set;

/** Channels represent a source or destination of different entities.

    <p id=operations>Operations:</p>
    <ul>
        <li>Create a channel with {@link io.sphere.sdk.channels.commands.ChannelCreateCommand}.</li>
        <li>Fetch a channel by key with {@link io.sphere.sdk.channels.queries.ChannelByKeyFetch}.</li>
        <li>Query a channel with {@link io.sphere.sdk.channels.queries.ChannelQuery}.</li>
        <li>Delete a channel with {@link io.sphere.sdk.channels.commands.ChannelDeleteCommand}.</li>
    </ul>
 */
@JsonDeserialize(as = ChannelImpl.class)
public interface Channel extends DefaultModel<Channel> {
    String getKey();

    Set<ChannelRole> getRoles();

    Optional<LocalizedStrings> getName();

    Optional<LocalizedStrings> getDescription();


    default Reference<Channel> toReference() {
        return Reference.of(typeId(), getId(), this);
    }

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
}
