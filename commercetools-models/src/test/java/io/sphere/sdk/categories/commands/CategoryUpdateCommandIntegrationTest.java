package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.commands.updateactions.*;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommand;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.categories.CategoryFixtures.withPersistentCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryUpdateCommandIntegrationTest extends IntegrationTest {
    @Test
    public void updateCommandDsl() throws Exception {
        withCategory(client(), category -> {
            final LocalizedString newName = LocalizedString.ofEnglish("new name");
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(category, singletonList(ChangeName.of(newName)));
            final Category updatedCategory = client().executeBlocking(command);
            assertThat(updatedCategory.getName()).isEqualTo(newName);

            final LocalizedString newName2 = LocalizedString.ofEnglish("new name2");
            final CategoryUpdateCommand command2 = CategoryUpdateCommand.of(category /** with old version */, singletonList(ChangeName.of(newName2)));
            final Category againUpdatedCategory = client().executeBlocking(command2.withVersion(updatedCategory));
            assertThat(againUpdatedCategory.getName()).isEqualTo(newName2);
            assertThat(againUpdatedCategory.getVersion()).isGreaterThan(updatedCategory.getVersion());
        });
    }

    @Test
    public void changeName() throws Exception {
        withCategory(client(), category -> {
            final LocalizedString newName = LocalizedString.of(ENGLISH, "new name");
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(category, ChangeName.of(newName));

            final Category updatedCategory = client().executeBlocking(command);

            assertThat(updatedCategory.getName()).isEqualTo(newName);
        });
    }

    @Test
    public void changeSlug() throws Exception {
        withCategory(client(), category -> {
            final LocalizedString newSug = randomSlug();
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(category, ChangeSlug.of(newSug));

            final Category updatedCategory = client().executeBlocking(command);

            assertThat(updatedCategory.getSlug()).isEqualTo(newSug);
        });
    }

    @Test
    public void setDescription() throws Exception {
        withCategory(client(), category -> {
            final LocalizedString newDescription = randomSlug();
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(category, SetDescription.of(newDescription));

            final Category updatedCategory = client().executeBlocking(command);

            assertThat(updatedCategory.getDescription()).isEqualTo(newDescription);
        });
    }

    @Test
    public void changeOrderHint() throws Exception {
        withCategory(client(), category -> {
            final String newOrderHint = randomSortOrder();
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(category, ChangeOrderHint.of(newOrderHint));

            final Category updatedCategory = client().executeBlocking(command);

            assertThat(updatedCategory.getOrderHint()).isEqualTo(newOrderHint);
        });
    }

    @Test
    public void setExternalId() throws Exception {
        withCategory(client(), category -> {
            final String newExternalId = randomKey();
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(category, SetExternalId.of(newExternalId));

            final Category updatedCategory = client().executeBlocking(command);

            assertThat(updatedCategory.getExternalId()).isEqualTo(newExternalId);
        });
    }

    @Test
    public void setMetaDescription() throws Exception {
        withPersistentCategory(client(), category -> {
            final LocalizedString newValue = randomSlug();
            final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(category, SetMetaDescription.of(newValue)));
            assertThat(updatedCategory.getMetaDescription()).isEqualTo(newValue);
        });
    }

    @Test
    public void setMetaTitle() throws Exception {
        withPersistentCategory(client(), category -> {
            final LocalizedString newValue = randomSlug();
            final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(category, SetMetaTitle.of(newValue)));
            assertThat(updatedCategory.getMetaTitle()).isEqualTo(newValue);
        });
    }

    @Test
    public void setMetaKeywords() throws Exception {
        withPersistentCategory(client(), category -> {
            final LocalizedString newValue = randomSlug();
            final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(category, SetMetaKeywords.of(newValue)));
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
                final Category updatedB = client().executeBlocking(updateCommand);

                assertThat(updatedB.getParent().getId()).isEqualTo(categoryA.getId());
                assertThat(updatedB.getParent().getObj()).isNotNull().isEqualTo(categoryA);
            })
        );
    }

    @Test
    public void readAccessForUpdateActions() {
        final List<UpdateAction<Category>> updateActions = asList(SetMetaTitle.of(randomSlug()), SetMetaDescription.of(randomSlug()));
        final UpdateCommand<Category> updateCommand = CategoryUpdateCommand.of(Versioned.of("id", 4L), updateActions);
        assertThat(updateCommand.getUpdateActions()).isEqualTo(updateActions);
    }
}
