package io.sphere.sdk.types;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.commands.updateactions.SetCustomType;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.Reference;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static io.sphere.sdk.test.SphereTestUtils.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeAssigningInUpdateActionDemo {
    public static Category updateCategoryWithType(final BlockingSphereClient client, final Category category1,
                                                  final Category category2) {
        final Category category =
                CategoryFixtures.createCategory(client);

        assertThat(category.getCustom()).isNull();

        final Map<String, Object> fieldValues = new HashMap<>();
        fieldValues.put("state", "published");//in the type it was enum, but for enums only keys are set
        fieldValues.put("imageUrl", "https://docs.commercetools.com/assets/img/CT-logo.svg");
        final Set<Reference<Category>> relatedCategories =
                new HashSet<>(asList(category1.toReference(), category2.toReference()));
        fieldValues.put("relatedCategories", relatedCategories);

        final SetCustomType action = SetCustomType
                .ofTypeKeyAndObjects("category-customtype-key", fieldValues);
        final Category updatedCategory = client.executeBlocking(CategoryUpdateCommand.of(category, action));

        final CustomFields custom = updatedCategory.getCustom();
        assertThat(custom.getFieldAsEnumKey("state")).isEqualTo("published");
        assertThat(custom.getFieldAsString("imageUrl"))
                .isEqualTo("https://docs.commercetools.com/assets/img/CT-logo.svg");
        final TypeReference<Set<Reference<Category>>> typeReference =
                new TypeReference<Set<Reference<Category>>>() { };
        assertThat(custom.getField("relatedCategories", typeReference))
                .isEqualTo(relatedCategories);

        return updatedCategory;
    }
}
