package io.sphere.sdk.types;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

final class TypeDraftImpl extends Base implements TypeDraft {
    private final String key;
    private final LocalizedString name;
    @Nullable
    private final LocalizedString description;
    private final Set<String> resourceTypeIds;

    @Nullable
    private final List<FieldDefinition> fieldDefinitions;

    public TypeDraftImpl(final String key, final LocalizedString name, @Nullable final LocalizedString description, final Set<String> resourceTypeIds, @Nullable final List<FieldDefinition> fieldDefinitions) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.resourceTypeIds = resourceTypeIds;
        this.fieldDefinitions = fieldDefinitions;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    @Override
    public Set<String> getResourceTypeIds() {
        return resourceTypeIds;
    }

    @Override
    @Nullable
    public List<FieldDefinition> getFieldDefinitions() {
        return fieldDefinitions;
    }
}
