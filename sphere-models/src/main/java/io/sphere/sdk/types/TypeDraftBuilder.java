package io.sphere.sdk.types;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.utils.ListUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @see Custom
 * @see TypeDraft
 */
public class TypeDraftBuilder extends Base implements Builder<TypeDraft> {

    private String key;
    private LocalizedString name;
    @Nullable
    private LocalizedString description;
    private Set<String> resourceTypeIds;

    @Nullable
    private List<FieldDefinition> fieldDefinitions;

    private TypeDraftBuilder(final String key, final LocalizedString name, @Nullable final LocalizedString description, final Set<String> resourceTypeIds, @Nullable final List<FieldDefinition> fieldDefinitions) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.resourceTypeIds = resourceTypeIds;
        this.fieldDefinitions = fieldDefinitions;
    }

    public static TypeDraftBuilder of(final String key, final LocalizedString name, final ResourceTypeIdsSetBuilder resourceTypeIdsSetBuilder) {
        return of(key, name, resourceTypeIdsSetBuilder.build());
    }

    public static TypeDraftBuilder of(final String key, final LocalizedString name, final Set<String> resourceTypeIds) {
        return new TypeDraftBuilder(key, name, null, resourceTypeIds, null);
    }

    public TypeDraftBuilder key(final String key) {
        this.key = key;
        return this;
    }

    public TypeDraftBuilder description(final LocalizedString description) {
        this.description = description;
        return this;
    }

    public TypeDraftBuilder fieldDefinitions(final List<FieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = fieldDefinitions;
        return this;
    }

    public TypeDraftBuilder plusFieldDefinitions(final List<FieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = ListUtils.listOf(getFieldDefinitions(), fieldDefinitions);
        return this;
    }

    public TypeDraftBuilder plusFieldDefinitions(final FieldDefinition fieldDefinition) {
        return plusFieldDefinitions(Collections.singletonList(fieldDefinition));
    }

    @Override
    public TypeDraft build() {
        return new TypeDraftImpl(key, name, description, resourceTypeIds, fieldDefinitions);
    }

    public String getKey() {
        return key;
    }

    public LocalizedString getName() {
        return name;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    public Set<String> getResourceTypeIds() {
        return resourceTypeIds;
    }

    @Nullable
    public List<FieldDefinition> getFieldDefinitions() {
        return fieldDefinitions;
    }
}
