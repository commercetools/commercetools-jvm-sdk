package io.sphere.sdk.types;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import org.junit.Test;

import java.util.Locale;

public class TypeDraftBuilderTest {

    @Test
    public void plusFieldDefinitionsTests() {
        final LocalizedString l = LocalizedString.of(Locale.GERMAN, "l");
        final TypeDraftBuilder builder = TypeDraftBuilder.of("type-key", l, ResourceTypeIdsSetBuilder.of().addCategories());
        builder.plusFieldDefinitions(
                    FieldDefinition.of(
                        ReferenceFieldType.of(Category.referenceTypeId()), "fieldName", l, false)
        ).build();
    }
}