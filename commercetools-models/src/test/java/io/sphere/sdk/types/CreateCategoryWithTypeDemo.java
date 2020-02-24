package io.sphere.sdk.types;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.models.Reference;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static io.sphere.sdk.test.SphereTestUtils.asList;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static org.assertj.core.api.Assertions.*;

public class CreateCategoryWithTypeDemo {
    public static Category createCategoryWithType(final BlockingSphereClient client, final Category category1,
                                                  final Category category2) throws Exception {
        final Map<String, Object> fieldValues = new HashMap<>();
        fieldValues.put("state", "published");//in the type it was enum, but for enums only keys are set
        fieldValues.put("imageUrl", "https://docs.commercetools.com/assets/img/CT-logo.svg");
        final Set<Reference<Category>> relatedCategories =
                new HashSet<>(asList(category1.toReference(), category2.toReference()));
        fieldValues.put("relatedCategories", relatedCategories);

        final CategoryDraft categoryDraft = CategoryDraftBuilder.of(randomSlug(), randomSlug())
                .custom(CustomFieldsDraft.ofTypeKeyAndObjects("category-customtype-key", fieldValues))
                .build();
        final Category category = client.executeBlocking(CategoryCreateCommand.of(categoryDraft));

        final CustomFields custom = category.getCustom();
        assertThat(custom.getFieldAsEnumKey("state")).isEqualTo("published");
        assertThat(custom.getFieldAsString("imageUrl"))
                .isEqualTo("https://docs.commercetools.com/assets/img/CT-logo.svg");
        final TypeReference<Set<Reference<Category>>> typeReference =
                new TypeReference<Set<Reference<Category>>>() { };
        assertThat(custom.getField("relatedCategories", typeReference))
                .isEqualTo(relatedCategories);
        //error cases
        assertThat(custom.getFieldAsString("notpresent"))
                .overridingErrorMessage("missing fields are null")
                .isNull();
        assertThatThrownBy(() -> custom.getFieldAsString("relatedCategories"))
                .overridingErrorMessage("present field with wrong type causes exception")
                .isInstanceOf(JsonException.class);

        return category;
    }
}
