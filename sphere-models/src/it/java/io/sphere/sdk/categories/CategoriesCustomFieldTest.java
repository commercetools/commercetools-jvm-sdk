package io.sphere.sdk.categories;

import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.commands.updateactions.SetCustomType;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import java.util.HashMap;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoriesCustomFieldTest extends IntegrationTest {
    @Test
    public void createCategoryWithCustomType() {
        withUpdateableType(client(), type -> {
            final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).addObject(STRING_FIELD_NAME, "a value").build();
            final CategoryDraft categoryDraft = CategoryDraftBuilder.of(randomSlug(), randomSlug()).custom(customFieldsDraft).build();
            final Category category = execute(CategoryCreateCommand.of(categoryDraft));
            assertThat(category.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a value");

            //clean up
            execute(CategoryDeleteCommand.of(category));
            return type;
        });
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
           withCategory(client(), category -> {
               final HashMap<String, Object> fields = new HashMap<>();
               fields.put(STRING_FIELD_NAME, "hello");
               final Category updatedCategory = execute(CategoryUpdateCommand.of(category, SetCustomType.ofTypeIdAndObjects(type.getId(), fields)));
               assertThat(updatedCategory.getCustom().getType()).isEqualTo(type.toReference());
               assertThat(updatedCategory.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("hello");

               final Category updated2 = execute(CategoryUpdateCommand.of(updatedCategory, SetCustomType.ofRemoveType()));
               assertThat(updated2.getCustom()).isNull();
           });
            return type;
        });
    }
}
