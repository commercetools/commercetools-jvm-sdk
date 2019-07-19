package io.sphere.sdk.categories;

import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithKey;
import org.junit.Test;

import javax.annotation.Nonnull;

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
        final Category category = getCategory1();
        final Reference<Category> categoryReference = category.toReference();
        assertThat(category.getId()).isEqualTo(categoryReference.getId());
    }

    @Test
    public void toStringContainsFields() {
        final String s = getCategory2().toString();
        assertThat(s)
                .contains("name=LocalizedString(de -> Jacken, en -> Jackets, it -> Giacche)")
                .contains("id=32a65cad-9865-413c-ac7b-aab3c51c63e0")
                .contains("LocalizedString(de -> meta-title-example)");
    }

    @Test
    public void categoryImplementsWithKey() {
        final Category category = getCategory1();

        final String key = getKey(category);

        assertThat(key).isEqualTo(category.getKey());
    }

    private <T extends WithKey> String getKey(@Nonnull final T resource) {
        return resource.getKey();
    }

    private Category getCategory1() {
        return SphereJsonUtils.readObjectFromResource("category1.json", Category.class);
    }

    private Category getCategory2() {
        return SphereJsonUtils.readObjectFromResource("category2.json", Category.class);
    }
}