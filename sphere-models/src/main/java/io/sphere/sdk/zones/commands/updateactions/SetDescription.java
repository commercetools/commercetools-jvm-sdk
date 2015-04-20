package io.sphere.sdk.zones.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.zones.Zone;

import java.util.Optional;

/**
 * Updates the description of a zone.
 *
 * {@include.example io.sphere.sdk.zones.commands.ZoneUpdateCommandTest#setDescription()}
 */
public class SetDescription extends UpdateAction<Zone> {
    private final Optional<String> description;

    private SetDescription(final Optional<String> description) {
        super("setDescription");
        this.description = description;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public static SetDescription of(final String description) {
        return new SetDescription(Optional.of(description));
    }

    public static SetDescription of(final Optional<String> description) {
        return new SetDescription(description);
    }
}
