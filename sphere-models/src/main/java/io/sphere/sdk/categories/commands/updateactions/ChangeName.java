package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedStrings;

public class ChangeName extends UpdateActionImpl<Category> {
    private final LocalizedStrings name;

    private ChangeName(final LocalizedStrings name) {
        super("changeName");
        this.name = name;
    }

    public static ChangeName of(final LocalizedStrings name) {
        return new ChangeName(name);
    }

    public LocalizedStrings getName() {
        return name;
    }
}