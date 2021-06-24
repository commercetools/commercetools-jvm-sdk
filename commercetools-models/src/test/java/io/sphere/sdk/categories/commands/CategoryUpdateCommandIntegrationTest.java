package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryBuilder;
import io.sphere.sdk.categories.commands.updateactions.*;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommand;
import io.sphere.sdk.models.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import io.sphere.sdk.types.Type;
import net.jcip.annotations.NotThreadSafe;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.*;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.categories.CategoryFixtures.withCategoryHavingAssets;
import static io.sphere.sdk.categories.CategoryFixtures.withPersistentCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
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
    public void changeKey() throws Exception {
        withCategory(client(), category -> {
            final String key = randomKey();
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(category, SetKey.of(key));

            final Category updatedCategory = client().executeBlocking(command);

            assertThat(updatedCategory.getKey()).isEqualTo(key);
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

    @Test
    public void addAsset() {
        withCategory(client(), category -> {
            final AssetSource assetSource = AssetSourceBuilder.ofUri("https://commercetools.com/binaries/content/gallery/commercetoolswebsite/homepage/cases/rewe.jpg")
                    .key("rewe-showcase")
                    .contentType("image/jpg")
                    .dimensionsOfWidthAndHeight(1934, 1115)
                    .build();
            final LocalizedString name = LocalizedString.ofEnglish("REWE show case");
            final LocalizedString description = LocalizedString.ofEnglish("screenshot of the REWE webshop on a mobile and a notebook");
            final AssetDraft assetDraft = AssetDraftBuilder.of(singletonList(assetSource), name)
                    .description(description)
                    .tags("desktop-sized", "jpg-format", "REWE", "awesome")
                    .build();
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(category, AddAsset.of(assetDraft,0));

            final Category updatedCategory = client().executeBlocking(command);
            final List<Asset> assets = updatedCategory.getAssets();
            assertThat(assets).hasSize(1);
            final Asset asset = assets.get(0);
            assertThat(asset.getId()).isNotEmpty();
            assertThat(asset.getDescription()).isEqualTo(description);
            assertThat(asset.getName()).isEqualTo(name);
            assertThat(asset.getSources()).hasSize(1);
            final AssetSource source = asset.getSources().get(0);
            assertThat(source.getUri()).isEqualTo("https://commercetools.com/binaries/content/gallery/commercetoolswebsite/homepage/cases/rewe.jpg");
            assertThat(source.getKey()).isEqualTo("rewe-showcase");
            assertThat(source.getContentType()).isEqualTo("image/jpg");
            assertThat(source.getDimensions()).isEqualTo(AssetDimensions.ofWidthAndHeight(1934, 1115));
        });
    }

    @Test
    public void changeAssetNameById() {
        withCategoryHavingAssets(client(), category -> {
            final LocalizedString newName = LocalizedString.ofEnglish("new name");
            final String assetId = category.getAssets().get(0).getId();
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(category, ChangeAssetName.of(assetId, newName));

            final Category updatedCategory = client().executeBlocking(command);

            final Asset updatedAsset = updatedCategory.getAssets().get(0);
            assertThat(updatedAsset.getName()).isEqualTo(newName);
        });
    }

    @Test
    public void changeAssetNameByKey() {
        withCategoryHavingAssets(client(), category -> {
            final LocalizedString newName = LocalizedString.ofEnglish("new name");
            final String assetKey = category.getAssets().get(0).getKey();
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(category, ChangeAssetName.ofKey(assetKey, newName));

            final Category updatedCategory = client().executeBlocking(command);

            final Asset updatedAsset = updatedCategory.getAssets().get(0);
            assertThat(updatedAsset.getName()).isEqualTo(newName);
        });
    }

    @Test
    public void changeAssetOrder() {
        withCategoryHavingAssets(client(), category -> {
            final List<Asset> originalAssets = category.getAssets();

            final List<String> newAssetOrder =
                    new LinkedList<>(originalAssets.stream().map(Asset::getId).collect(toList()));
            Collections.reverse(newAssetOrder);

            final CategoryUpdateCommand command = CategoryUpdateCommand.of(category, ChangeAssetOrder.of(newAssetOrder));
            final Category updatedCategory = client().executeBlocking(command);

            final List<Asset> assets = updatedCategory.getAssets();
            assertThat(assets).extracting(Asset::getId).isEqualTo(newAssetOrder);
        });
    }

    @Test
    public void removeAssetById() {
        withCategoryHavingAssets(client(), category -> {
            final String assetId = category.getAssets().get(0).getId();
            final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(category, RemoveAsset.of(assetId)));

            final List<Asset> assets = updatedCategory.getAssets();
            assertThat(assets).allMatch(asset -> !asset.getId().equals(assetId));
        });
    }

    @Test
    public void removeAssetByKey() {
        withCategoryHavingAssets(client(), category -> {
            final String assetKey = category.getAssets().get(0).getKey();
            final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(category, RemoveAsset.ofKey(assetKey)));

            final List<Asset> assets = updatedCategory.getAssets();
            assertThat(assets).allMatch(asset -> !asset.getKey().equals(assetKey));
        });
    }

    @Test
    public void setAssetKey() {
        withCategoryHavingAssets(client(), category -> {
            final String assetid = category.getAssets().get(0).getId();
            final String newAssetKey = randomKey();
            final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(category, SetAssetKey.of(assetid,newAssetKey)));

            final List<Asset> assets = updatedCategory.getAssets();
            assertThat(assets).haveAtLeastOne(new Condition<>(asset -> asset.getKey().equals(newAssetKey),"Check if key is updated"));
        });
    }

    @Test
    public void removeAssetKey() {
        withCategoryHavingAssets(client(), category -> {
            final String assetid = category.getAssets().get(0).getId();
            final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(category, SetAssetKey.of(assetid,null)));

            final List<Asset> assets = updatedCategory.getAssets();
            assertThat(assets).haveAtLeastOne(new Condition<>(asset -> asset.getKey() == null,"Check if key is removed"));
        });
    }

    @Test
    public void assetCustomType() {
        withUpdateableType(client(), (Type type) -> {
            withCategoryHavingAssets(client(), category -> {
                final Asset assetWithoutCustomType = category.getAssets().get(0);
                final String assetId = assetWithoutCustomType.getId();

                final String firstFieldValue = "commercetools";
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type)
                        .addObject(STRING_FIELD_NAME, firstFieldValue)
                        .build();

                final Category updatedCategoryWithCustomTypeInAssets = client().executeBlocking(CategoryUpdateCommand.of(category,
                        SetAssetCustomType.of(assetId, customFieldsDraft)));

                final String actualFieldValue = updatedCategoryWithCustomTypeInAssets.getAssets().get(0)
                        .getCustom().getFieldAsString(STRING_FIELD_NAME);
                assertThat(firstFieldValue).isEqualTo(actualFieldValue);

                final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(updatedCategoryWithCustomTypeInAssets,
                        SetAssetCustomField.of(assetId, STRING_FIELD_NAME, "new")));

                assertThat(updatedCategory.getAssets().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo("new");
            });
            return type;
        });
    }

    @Test
    public void setAssetDescription() {
        withCategoryHavingAssets(client(), category -> {
            final LocalizedString newDescription = LocalizedString.ofEnglish("new description");
            final String assetId = category.getAssets().get(0).getId();

            final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(category, SetAssetDescription.of(assetId, newDescription)));

            final Asset updatedAsset = updatedCategory.getAssets().get(0);
            assertThat(updatedAsset.getDescription()).isEqualTo(newDescription);
        });
    }

    @Test
    public void setAssetSources() {
        withCategoryHavingAssets(client(), category -> {
            final AssetSource assetSource = AssetSourceBuilder.ofUri("https://docs.commercetools.com/assets/img/CT-logo.svg")
                    .key("commercetools-logo")
                    .contentType("image/svg+xml")
                    .build();
            final String assetId = category.getAssets().get(0).getId();

            final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(category, SetAssetSources.of(assetId, Collections.singletonList(assetSource))));

            final Asset updatedAsset = updatedCategory.getAssets().get(0);
            assertThat(updatedAsset.getSources()).hasSize(1);
            final AssetSource source = updatedAsset.getSources().get(0);
            assertThat(source.getUri()).isEqualTo("https://docs.commercetools.com/assets/img/CT-logo.svg");
            assertThat(source.getKey()).isEqualTo("commercetools-logo");
            assertThat(source.getContentType()).isEqualTo("image/svg+xml");
        });
    }

    @Test
    public void setAssetTags() {
        withCategoryHavingAssets(client(), category -> {
            final Set<String> newTags = new HashSet<>(asList("tag1", "tag2"));
            final String assetId = category.getAssets().get(0).getId();

            final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(category, SetAssetTags.of(assetId, newTags)));

            final Asset updatedAsset = updatedCategory.getAssets().get(0);
            assertThat(updatedAsset.getTags()).isEqualTo(newTags);
        });
    }

    @Test
    public void expandByString() {
        final Category category = CategoryBuilder.of("abc", randomSlug(), randomSlug()).build();
        final CategoryUpdateCommand actual = CategoryUpdateCommand.of(category, ChangeName.of(randomSlug())).withExpansionPaths("id").plusExpansionPaths("name");
        assertThat(actual.httpRequestIntent().toHttpRequest("").getUrl()).isEqualTo("/categories/abc?expand=id&expand=name");
    }
}
