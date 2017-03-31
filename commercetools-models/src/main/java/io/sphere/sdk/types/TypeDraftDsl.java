package io.sphere.sdk.types;

import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public final class TypeDraftDsl extends TypeDraftDslBase<TypeDraftDsl> {

    TypeDraftDsl(@Nullable final LocalizedString description, @Nullable final List<FieldDefinition> fieldDefinitions, final String key, final LocalizedString name, final Set<String> resourceTypeIds) {
        super(description, fieldDefinitions, key, name, resourceTypeIds);
    }

    public static TypeDraftDsl of(final String key, final LocalizedString name, final ResourceTypeIdsSetBuilder resourceTypeIdsSetBuilder) {
        return of(key, name, resourceTypeIdsSetBuilder.build());
    }
}
