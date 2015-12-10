package io.sphere.sdk.types;

import io.sphere.sdk.categories.commands.updateactions.SetCustomField;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.commands.UpdateAction;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateFieldValueDemo {
    public static Category updateFieldValues(final BlockingSphereClient client, final Category category) {
        assertThat(category.getCustom().getFieldAsEnumKey("state")).isEqualTo("published");

        final List<? extends UpdateAction<Category>> updateActions = asList(
                SetCustomField.ofObject("state", "draft"),//set new value
                SetCustomField.ofObject("imageUrl", null)//remove value
        );

        final Category updatedCategory = client.executeBlocking(CategoryUpdateCommand.of(category, updateActions));

        final CustomFields custom = updatedCategory.getCustom();
        assertThat(custom.getFieldAsEnumKey("state")).isEqualTo("draft");
        assertThat(custom.getFieldAsString("imageUrl")).isNull();

        return updatedCategory;
    }
}
