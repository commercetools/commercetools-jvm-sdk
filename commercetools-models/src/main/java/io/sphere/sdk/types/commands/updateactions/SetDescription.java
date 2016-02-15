package io.sphere.sdk.types.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;

/**
  Changes the description of the type.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.types.commands.TypeUpdateCommandIntegrationTest#setDescription()}
 */
public final class SetDescription extends UpdateActionImpl<Type> {
    @Nullable
    private final LocalizedString description;

    private SetDescription(@Nullable final LocalizedString description) {
        super("setDescription");
        this.description = description;
    }

    public static SetDescription of(@Nullable final LocalizedString description) {
        return new SetDescription(description);
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }
}
