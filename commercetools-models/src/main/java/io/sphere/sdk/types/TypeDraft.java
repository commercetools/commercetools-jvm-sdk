package io.sphere.sdk.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * @see Custom
 * @see TypeDraftBuilder
 * @see TypeDraftDsl
 */
@JsonDeserialize(as = TypeDraftDsl.class)
@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {"key", "name", "resourceTypeIds"}), abstractBuilderClass = true,
        additionalDslClassContents = "        public static TypeDraftDsl of(final String key, final LocalizedString name, final ResourceTypeIdsSetBuilder resourceTypeIdsSetBuilder) {\n" +
        "            return of(key, name, resourceTypeIdsSetBuilder.build());\n" +
        "        }",
gettersForBuilder = true, additionalBuilderInterfaces = "io.sphere.sdk.models.WithKey")
public interface TypeDraft extends WithKey {
    String getKey();

    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    Set<String> getResourceTypeIds();

    @Nullable
    List<FieldDefinition> getFieldDefinitions();
}
