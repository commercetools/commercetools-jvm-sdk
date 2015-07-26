package io.sphere.sdk.zones.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.zones.Zone;

import javax.annotation.Nullable;

/**
 * Updates the description of a zone.
 *
 * {@include.example io.sphere.sdk.zones.commands.ZoneUpdateCommandTest#setDescription()}
 */
public class SetDescription extends UpdateAction<Zone> {
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
