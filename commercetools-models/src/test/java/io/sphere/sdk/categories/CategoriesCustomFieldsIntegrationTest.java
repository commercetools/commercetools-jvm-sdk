package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.commands.updateactions.SetCustomField;
import io.sphere.sdk.categories.commands.updateactions.SetCustomType;
import io.sphere.sdk.categories.queries.CategoryByIdGet;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.TextInputHint;
import io.sphere.sdk.models.errors.RequiredField;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.*;
import io.sphere.sdk.types.commands.TypeCreateCommand;
import io.sphere.sdk.types.commands.TypeDeleteCommand;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CategoriesCustomFieldsIntegrationTest extends IntegrationTest {
    public static final Map<String, Object> CUSTOM_FIELDS_MAP = Collections.singletonMap(STRING_FIELD_NAME, "hello");
    public static final TypeReference<Reference<Category>> TYPE_REFERENCE = new TypeReference<Reference<Category>>() {
    };

    @Test
    public void createCategoryWithCustomTypeById() {
        createCategoryWithCustomType(type -> CustomFieldsDraftBuilder.ofTypeId(type.getId()));
    }

    @Test
    public void createCategoryWithCustomTypeByKey() {
        createCategoryWithCustomType(type -> CustomFieldsDraftBuilder.ofTypeKey(type.getKey()));
    }

    private void createCategoryWithCustomType(final Function<Type, CustomFieldsDraftBuilder> draftCreator) {
        withUpdateableType(client(), type -> {
            final CustomFieldsDraftBuilder customFieldsDraftBuilder = draftCreator.apply(type);
            final CustomFieldsDraft customFieldsDraft = customFieldsDraftBuilder.addObject(STRING_FIELD_NAME, "a value").build();
            final CategoryDraft categoryDraft = CategoryDraftBuilder.of(randomSlug(), randomSlug()).custom(customFieldsDraft).build();
            final Category category = client().executeBlocking(CategoryCreateCommand.of(categoryDraft));
            assertThat(category.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a value");

            final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(category, SetCustomField.ofObject(STRING_FIELD_NAME, "a new value")));
            assertThat(updatedCategory.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a new value");

            //clean up
            client().executeBlocking(CategoryDeleteCommand.of(updatedCategory));
            return type;
        });
    }

    @Test
    public void setCustomTypeById() {
        setCustomType(type -> SetCustomType.ofTypeIdAndObjects(type.getId(), CUSTOM_FIELDS_MAP));
    }

    @Test
    public void setCustomTypeByKey() {
        setCustomType(type -> SetCustomType.ofTypeKeyAndObjects(type.getKey(), CUSTOM_FIELDS_MAP));
    }

    private void setCustomType(final Function<Type, SetCustomType> updateActionCreator) {
        withUpdateableType(client(), type -> {
           withCategory(client(), category -> {
               final SetCustomType updateAction = updateActionCreator.apply(type);
               final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(category, updateAction));
               assertThat(updatedCategory.getCustom().getType()).isEqualTo(type.toReference());
               assertThat(updatedCategory.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("hello");

               final Category updated2 = client().executeBlocking(CategoryUpdateCommand.of(updatedCategory, SetCustomType.ofRemoveType()));
               assertThat(updated2.getCustom()).isNull();
           });
            return type;
        });
    }

    @Test
    public void referenceExpansion() {
        withUpdateableType(client(), type -> {
           withCategory(client(), referencedCategory -> {
               withCategory(client(), category -> {
                   final Map<String, Object> fields = Collections.singletonMap(CAT_REFERENCE_FIELD_NAME, referencedCategory.toReference());
                   final CategoryUpdateCommand categoryUpdateCommand = CategoryUpdateCommand.of(category, SetCustomType.ofTypeIdAndObjects(type.getId(), fields));
                   final ExpansionPath<Category> expansionPath = ExpansionPath.of("custom.fields." + CAT_REFERENCE_FIELD_NAME);
                   final Category updatedCategory = client().executeBlocking(categoryUpdateCommand.withExpansionPaths(expansionPath));

                   final Reference<Category> createdReference = updatedCategory.getCustom().getField(CAT_REFERENCE_FIELD_NAME, TYPE_REFERENCE);
                   assertThat(createdReference).isEqualTo(referencedCategory.toReference());
                   assertThat(createdReference.getObj()).isNotNull();

                   final Category loadedCat = client().executeBlocking(CategoryByIdGet.of(updatedCategory)
                           .withExpansionPaths(expansionPath));

                   assertThat(loadedCat.getCustom().getField(CAT_REFERENCE_FIELD_NAME, TYPE_REFERENCE).getObj())
                           .overridingErrorMessage("is expanded")
                           .isNotNull();


                   final Category updated2 = client().executeBlocking(CategoryUpdateCommand.of(updatedCategory, SetCustomType.ofRemoveType()));
                   assertThat(updated2.getCustom()).isNull();
               });
           });
            return type;
        });
    }

    @Test
    public void requiredValidation() {
        final FieldDefinition stringFieldDefinition =
                FieldDefinition.of(StringFieldType.of(), STRING_FIELD_NAME, en("label"), true, TextInputHint.SINGLE_LINE);
        final String typeKey = randomKey();
        final TypeDraft typeDraft = TypeDraftBuilder.of(typeKey, en("name of the custom type"), TYPE_IDS)
                .description(en("description"))
                .fieldDefinitions(asList(stringFieldDefinition))
                .build();
        final Type type = client().executeBlocking(TypeCreateCommand.of(typeDraft));

        withCategory(client(), category -> {
            assertThatThrownBy(() -> client().executeBlocking(CategoryUpdateCommand.of(category, SetCustomType.ofTypeIdAndObjects(type.getId(), Collections.emptyMap()))))
            .isInstanceOf(ErrorResponseException.class)
                    .matches(e -> {
                        final ErrorResponseException errorResponseException = (ErrorResponseException) e;
                        final String errorCode = RequiredField.CODE;
                        return errorResponseException.hasErrorCode(errorCode)
                                && errorResponseException.getErrors().stream()
                                .filter(err -> err.getCode().equals(errorCode))
                                .anyMatch(err -> STRING_FIELD_NAME.equals(err.as(RequiredField.class).getField()));
                    });
        });

        client().executeBlocking(TypeDeleteCommand.of(type));
    }

    @Test
    public void queryByField() {
        withUpdateableType(client(), type -> {
            withCategory(client(), category -> {
                final Map<String, Object> fields = new HashMap<>();
                fields.put(STRING_FIELD_NAME, "foo");
                fields.put(LOC_STRING_FIELD_NAME, LocalizedString.of(ENGLISH, "bar"));
                final CategoryUpdateCommand categoryUpdateCommand = CategoryUpdateCommand.of(category, SetCustomType.ofTypeIdAndObjects(type.getId(), fields));
                final Category updatedCategory = client().executeBlocking(categoryUpdateCommand);

                final CategoryQuery categoryQuery = CategoryQuery.of()
                        .plusPredicates(m -> m.is(category))
                        .plusPredicates(m -> m.custom().fields().ofString(STRING_FIELD_NAME).is("foo"))
                        .plusPredicates(m -> m.custom().fields().ofString("njetpresent").isNotPresent())
                        .plusPredicates(m -> m.custom().fields().ofLocalizedString(LOC_STRING_FIELD_NAME).locale(ENGLISH).is("bar"));

                assertThat(client().executeBlocking(categoryQuery).head()).contains(updatedCategory);
            });
            return type;
        });
    }
}
