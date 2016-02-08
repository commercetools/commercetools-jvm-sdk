package io.sphere.sdk.customobjects.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;

final class CustomObjectUpsertCommandImpl<T> extends CreateCommandImpl<CustomObject<T>, CustomObjectDraft<T>> implements CustomObjectUpsertCommand<T> {

    CustomObjectUpsertCommandImpl(final CustomObjectDraft<T> draft) {
        super(draft, CustomObjectEndpoint.PATH, draft.getJavaType());
    }
}
