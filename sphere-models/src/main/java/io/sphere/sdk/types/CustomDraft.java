package io.sphere.sdk.types;

import javax.annotation.Nullable;

/**
 * Interface for draft objects which include custom fields.
 */
public interface CustomDraft {
    @Nullable
    CustomFieldsDraft getCustom();
}
