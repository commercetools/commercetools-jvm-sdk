package io.sphere.sdk.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * @see Custom
 * @see TypeDraftBuilder
 */
@JsonDeserialize(as = TypeDraftImpl.class)
public interface TypeDraft extends WithKey {
    String getKey();

    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    Set<String> getResourceTypeIds();

    @Nullable
    List<FieldDefinition> getFieldDefinitions();
}
