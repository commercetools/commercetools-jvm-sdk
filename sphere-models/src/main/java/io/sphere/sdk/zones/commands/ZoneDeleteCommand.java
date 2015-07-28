package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.zones.Zone;

public interface ZoneDeleteCommand extends ByIdDeleteCommand<Zone> {
    static DeleteCommand<Zone> of(final Versioned<Zone> versioned) {
        return new ZoneDeleteCommandImpl(versioned);
    }
}
