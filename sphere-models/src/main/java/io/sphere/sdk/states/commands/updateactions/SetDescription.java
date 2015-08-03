package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.states.State;

public class SetDescription extends UpdateActionImpl<State> {
    private final LocalizedStrings description;

    private SetDescription(final LocalizedStrings description) {
        super("setDescription");
        this.description = description;
    }

    public static SetDescription of(final LocalizedStrings description) {
        return new SetDescription(description);
    }

    public LocalizedStrings getDescription() {
        return description;
    }
}
