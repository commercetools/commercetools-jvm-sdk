package io.sphere.sdk.types;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.utils.SphereInternalUtils;

import javax.annotation.Nullable;
import java.util.*;

public final class TypeDraftBuilder extends TypeDraftBuilderBase<TypeDraftBuilder> {

    TypeDraftBuilder(@Nullable final LocalizedString description, @Nullable final List<FieldDefinition> fieldDefinitions, final String key, final LocalizedString name, final Set<String> resourceTypeIds) {
        super(description, fieldDefinitions, key, name, resourceTypeIds);
    }

    public static TypeDraftBuilder of(final String key, final LocalizedString name, final ResourceTypeIdsSetBuilder resourceTypeIdsSetBuilder) {
        return of(key, name, resourceTypeIdsSetBuilder.build());
    }

}
