package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.states.State;

/**
 * Sets the description of a state.
 *
 * {@doc.gen intro}
 */
public final class SetDescription extends UpdateActionImpl<State> {
    private final LocalizedString description;

    private SetDescription(final LocalizedString description) {
        super("setDescription");
        this.description = description;
    }

    public static SetDescription of(final LocalizedString description) {
        return new SetDescription(description);
    }

    public LocalizedString getDescription() {
        return description;
    }
}
