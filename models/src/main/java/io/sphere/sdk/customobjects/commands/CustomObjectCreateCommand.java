package io.sphere.sdk.customobjects.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.http.JsonEndpoint;

/**
 Command for creating a custom object.

 {@include.example io.sphere.sdk.customobjects.CustomObjectFixtures#createCustomObject()}

 @param <T> the type of the value in the custom object
 */
public class CustomObjectCreateCommand<T> extends CreateCommandImpl<CustomObject<T>, CustomObjectDraft<T>> {

    private CustomObjectCreateCommand(final CustomObjectDraft<T> draft) {
        super(draft, JsonEndpoint.of(draft.typeReference(), CustomObjectsEndpoint.PATH));
    }

    public static <T> CustomObjectCreateCommand<T> of(final CustomObjectDraft<T> draft) {
        return new CustomObjectCreateCommand<>(draft);
    }
}
