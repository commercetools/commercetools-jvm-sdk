package io.sphere.sdk.channels;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import java.util.Optional;
import java.util.Set;

/** Channels represent a source or destination of different entities.

    <p id=operations>Operations:</p>
    <ul>
        <li>Create a channel with {@link io.sphere.sdk.channels.commands.ChannelCreateCommand}.</li>
        <li>Fetch a channel by key with {@link io.sphere.sdk.channels.queries.FetchChannelByKey}.</li>
        <li>Query a channel with {@link io.sphere.sdk.channels.queries.ChannelQuery}.</li>
        <li>Delete a channel with {@link io.sphere.sdk.channels.commands.ChannelDeleteByIdCommand}.</li>
    </ul>
 */
@JsonDeserialize(as = ChannelImpl.class)
public interface Channel extends DefaultModel<Channel> {
    public String getKey();

    public Set<ChannelRoles> getRoles();

    public Optional<LocalizedString> getName();

    public Optional<LocalizedString> getDescription();


    public default Reference<Channel> toReference() {
        return Reference.of(typeId(), getId());
    }

    public static String typeId(){
        return "channel";
    }

    public static TypeReference<Channel> typeReference(){
        return new TypeReference<Channel>() {
            @Override
            public String toString() {
                return "TypeReference<Channel>";
            }
        };
    }
}
