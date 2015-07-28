package io.sphere.sdk.customobjects.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;

/**
 Command for creating or updating a custom object.
 Upsert is a synthetic word which has its origins from "update" and "insert".

 {@include.example io.sphere.sdk.customobjects.CustomObjectFixtures#createCustomObject()}

 @param <T> the type of the value in the custom object
 */
public interface CustomObjectUpsertCommand<T> extends CreateCommand<CustomObject<T>> {

    static <T> CustomObjectUpsertCommand<T> of(final CustomObjectDraft<T> draft) {
        return new CustomObjectUpsertCommandImpl<>(draft);
    }


}
