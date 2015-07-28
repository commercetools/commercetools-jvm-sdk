package io.sphere.sdk.customobjects.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;

/**
 Command for creating or updating a custom object.
 Upsert is a synthetic word which has its origins from "update" and "insert".

 {@include.example io.sphere.sdk.customobjects.CustomObjectFixtures#createCustomObject()}

 @param <T> the type of the value in the custom object
 */
public class CustomObjectUpsertCommandImpl<T> extends CreateCommandImpl<CustomObject<T>, CustomObjectDraft<T>> {

    private CustomObjectUpsertCommandImpl(final CustomObjectDraft<T> draft) {
        super(draft, JsonEndpoint.of(draft.typeReference(), CustomObjectEndpoint.PATH));
    }

    public static <T> CustomObjectUpsertCommandImpl<T> of(final CustomObjectDraft<T> draft) {
        return new CustomObjectUpsertCommandImpl<>(draft);
    }


}
