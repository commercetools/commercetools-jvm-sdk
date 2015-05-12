package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.commands.updateactions.ChangeName;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.models.LocalizedStrings.ofEnglishLocale;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryUpdateCommandTest extends IntegrationTest {
    @Test
    public void updateCommandDsl() throws Exception {
        withCategory(client(), category -> {
            final LocalizedStrings newName = ofEnglishLocale("new name");
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(category, asList(ChangeName.of(newName)));
            final Category updatedCategory = execute(command);
            assertThat(updatedCategory.getName()).isEqualTo(newName);

            final LocalizedStrings newName2 = ofEnglishLocale("new name2");
            final CategoryUpdateCommand command2 = CategoryUpdateCommand.of(category /** with old version */, asList(ChangeName.of(newName2)));
            final Category againUpdatedCategory = execute(command2.withVersion(updatedCategory));
            assertThat(againUpdatedCategory.getName()).isEqualTo(newName2);
            assertThat(againUpdatedCategory.getVersion()).isGreaterThan(updatedCategory.getVersion());
        });
    }
}
