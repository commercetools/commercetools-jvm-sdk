package io.sphere.sdk.types;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.utils.SphereInternalUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class TypeDraftBuilder extends Base implements Builder<TypeDraft> {
    @Nullable
    private LocalizedString description;

    @Nullable
    private List<FieldDefinition> fieldDefinitions;

    private String key;

    private LocalizedString name;

    private Set<String> resourceTypeIds;

    TypeDraftBuilder() {
    }

    TypeDraftBuilder(@Nullable final LocalizedString description,
                     @Nullable final List<FieldDefinition> fieldDefinitions, final String key,
                     final LocalizedString name, final Set<String> resourceTypeIds) {
        this.description = description;
        this.fieldDefinitions = fieldDefinitions;
        this.key = key;
        this.name = name;
        this.resourceTypeIds = resourceTypeIds;
    }

    public TypeDraftBuilder description(@Nullable final LocalizedString description) {
        this.description = description;
        return this;
    }

    public TypeDraftBuilder fieldDefinitions(@Nullable final List<FieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = fieldDefinitions;
        return this;
    }

    public TypeDraftBuilder key(final String key) {
        this.key = key;
        return this;
    }

    public TypeDraftBuilder name(final LocalizedString name) {
        this.name = name;
        return this;
    }

    public TypeDraftBuilder resourceTypeIds(final Set<String> resourceTypeIds) {
        this.resourceTypeIds = resourceTypeIds;
        return this;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    @Nullable
    public List<FieldDefinition> getFieldDefinitions() {
        return fieldDefinitions;
    }

    public String getKey() {
        return key;
    }

    public LocalizedString getName() {
        return name;
    }

    public Set<String> getResourceTypeIds() {
        return resourceTypeIds;
    }

    public TypeDraftDsl build() {
        return new TypeDraftDsl(description, fieldDefinitions, key, name, resourceTypeIds);
    }

    public static TypeDraftBuilder of(final String key, final LocalizedString name,
                                      final Set<String> resourceTypeIds) {
        return new TypeDraftBuilder(null, null, key, name, resourceTypeIds);
    }

    public static TypeDraftBuilder of(final TypeDraft template) {
        return new TypeDraftBuilder(template.getDescription(), template.getFieldDefinitions(), template.getKey(), template.getName(), template.getResourceTypeIds());
    }

    public static TypeDraftBuilder of(final String key, final LocalizedString name, final ResourceTypeIdsSetBuilder resourceTypeIdsSetBuilder) {
        return of(key, name, resourceTypeIdsSetBuilder.build());
    }

    public TypeDraftBuilder plusFieldDefinitions(final List<FieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = SphereInternalUtils.listOf(getFieldDefinitions(), fieldDefinitions);
        return this;
    }

    public TypeDraftBuilder plusFieldDefinitions(final FieldDefinition fieldDefinition) {
        return plusFieldDefinitions(Collections.singletonList(fieldDefinition));
    }
}
