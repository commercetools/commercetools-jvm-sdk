package io.sphere.sdk.channels;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Template to create a new Channel.
 *
 * {@include.example io.sphere.sdk.channels.commands.ChannelCreateCommandTest#execution()}
 *
 * @see ChannelDraftBuilder
 * @see ChannelDraftDsl
 */
@JsonDeserialize(as = ChannelDraftDsl.class)
public interface ChannelDraft {
    String getKey();

    @Nullable
    Set<ChannelRole> getRoles();

    @Nullable
    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    static ChannelDraftDsl of(final String key) {
        return ChannelDraftDsl.of(key);
    }
}
