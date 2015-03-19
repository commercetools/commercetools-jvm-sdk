package io.sphere.sdk.customobjects.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.client.JsonEndpoint;

/**
 Command for creating or updating a custom object.
 Upsert is a synthetic word which has its origins from "update" and "insert".

 {@include.example io.sphere.sdk.customobjects.CustomObjectFixtures#createCustomObject()}

 @param <T> the type of the value in the custom object
 */
public class CustomObjectUpsertCommand<T> extends CreateCommandImpl<CustomObject<T>, CustomObjectDraft<T>> {

    private CustomObjectUpsertCommand(final CustomObjectDraft<T> draft) {
        super(draft, JsonEndpoint.of(draft.typeReference(), CustomObjectsEndpoint.PATH));
    }

    public static <T> CustomObjectUpsertCommand<T> of(final CustomObjectDraft<T> draft) {
        return new CustomObjectUpsertCommand<>(draft);
    }


}
