package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.commands.updateactions.*;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.categories.CategoryFixtures.*;
import static io.sphere.sdk.models.LocalizedString.ofEnglishLocale;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryUpdateCommandTest extends IntegrationTest {
    @Test
    public void updateCommandDsl() throws Exception {
        withCategory(client(), category -> {
            final LocalizedString newName = ofEnglishLocale("new name");
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(category, asList(ChangeName.of(newName)));
            final Category updatedCategory = execute(command);
            assertThat(updatedCategory.getName()).isEqualTo(newName);

            final LocalizedString newName2 = ofEnglishLocale("new name2");
            final CategoryUpdateCommand command2 = CategoryUpdateCommand.of(category /** with old version */, asList(ChangeName.of(newName2)));
            final Category againUpdatedCategory = execute(command2.withVersion(updatedCategory));
            assertThat(againUpdatedCategory.getName()).isEqualTo(newName2);
            assertThat(againUpdatedCategory.getVersion()).isGreaterThan(updatedCategory.getVersion());
        });
    }

    @Test
    public void setMetaDescription() throws Exception {
        withPersistentCategory(client(), category -> {
            final LocalizedString newValue = randomSlug();
            final Category updatedCategory = execute(CategoryUpdateCommand.of(category, SetMetaDescription.of(newValue)));
            assertThat(updatedCategory.getMetaDescription()).isEqualTo(newValue);
        });
    }

    @Test
    public void setMetaTitle() throws Exception {
        withPersistentCategory(client(), category -> {
            final LocalizedString newValue = randomSlug();
            final Category updatedCategory = execute(CategoryUpdateCommand.of(category, SetMetaTitle.of(newValue)));
            assertThat(updatedCategory.getMetaTitle()).isEqualTo(newValue);
        });
    }

    @Test
    public void setMetaKeywords() throws Exception {
        withPersistentCategory(client(), category -> {
            final LocalizedString newValue = randomSlug();
            final Category updatedCategory = execute(CategoryUpdateCommand.of(category, SetMetaKeywords.of(newValue)));
            assertThat(updatedCategory.getMetaKeywords()).isEqualTo(newValue);
        });
    }

    @Test
    public void changeParent() throws Exception {
        withCategory(client(), categoryA ->
            withCategory(client(), categoryB -> {
                assertThat(categoryA.getParent()).isNull();
                assertThat(categoryB.getParent()).isNull();

                final CategoryUpdateCommand updateCommand =
                        CategoryUpdateCommand.of(categoryB, ChangeParent.of(categoryA))
                        .plusExpansionPaths(m -> m.parent());
                final Category updatedB = execute(updateCommand);

                assertThat(updatedB.getParent().getId()).isEqualTo(categoryA.getId());
                assertThat(updatedB.getParent().getObj()).isNotNull().isEqualTo(categoryA);
            })
        );
    }
}
