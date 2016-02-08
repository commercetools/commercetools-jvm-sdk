package io.sphere.sdk.categories;

import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Reference;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CategoryTest {
    @Test
    public void referenceOfId() {
        final String categoryIdFromFormOrSession = "84ac4271-0fec-49d0-9fee-55586c565c58";
        final Reference<Category> categoryReference = Category.referenceOfId(categoryIdFromFormOrSession);
        assertThat(categoryReference.getId()).isEqualTo(categoryIdFromFormOrSession);
    }

    @Test
    public void toReference() {
        final Category category = getCategory();
        final Reference<Category> categoryReference = category.toReference();
        assertThat(category.getId()).isEqualTo(categoryReference.getId());
    }

    private Category getCategory() {
        return SphereJsonUtils.readObjectFromResource("category1.json", Category.class);
    }
}