package io.sphere.sdk.types;

import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * @see Custom
 * @see TypeDraftBuilder
 */
public interface TypeDraft {
    String getKey();

    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    Set<String> getResourceTypeIds();

    @Nullable
    List<FieldDefinition> getFieldDefinitions();
}
