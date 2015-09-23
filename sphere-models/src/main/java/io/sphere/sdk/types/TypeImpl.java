package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceImpl;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

final class TypeImpl extends ResourceImpl<Type> implements Type {
    private final String key;
    private final LocalizedString name;
    @Nullable
    private final LocalizedString description;
    private final Set<String> resourceTypeIds;
    private final List<FieldDefinition> fieldDefinitions;

    @JsonCreator
    TypeImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,final String key, final LocalizedString name, @Nullable final LocalizedString description, final Set<String> resourceTypeIds, final List<FieldDefinition> fieldDefinitions) {
        super(id, version, createdAt, lastModifiedAt);
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
    public List<FieldDefinition> getFieldDefinitions() {
        return fieldDefinitions;
    }

    @Nullable
    @Override
    public FieldDefinition getFieldDefinitionByName(final String name) {
        return getFieldDefinitions().stream().filter(def -> def.getName().equals(name)).findFirst().orElse(null);
    }
}
