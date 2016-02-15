package io.sphere.sdk.zones.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.zones.Zone;

import javax.annotation.Nullable;

/**
 * Updates the description of a zone.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.zones.commands.ZoneUpdateCommandIntegrationTest#setDescription()}
 */
public final class SetDescription extends UpdateActionImpl<Zone> {
    @Nullable
    private final String description;

    private SetDescription(@Nullable final String description) {
        super("setDescription");
        this.description = description;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public static SetDescription of(@Nullable final String description) {
        return new SetDescription(description);
    }
}
