package io.sphere.sdk.products.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.sphere.sdk.attributes.*;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.updateactions.*;
import io.sphere.sdk.products.queries.ProductByIdFetch;
import io.sphere.sdk.search.SearchKeyword;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.search.tokenizer.CustomSuggestTokenizer;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Colors;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Sizes;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;
import io.sphere.sdk.test.IntegrationTest;

import io.sphere.sdk.test.ReferenceAssert;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.time.ZoneOffset;
import java.util.*;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.models.LocalizedStrings.ofEnglishLocale;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.MONEY_ATTRIBUTE_NAME;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.products.ProductFixtures.*;

public class ProductUpdateCommandTest extends IntegrationTest {
    public static final Random RANDOM = new Random();

    @Test
    public void addExternalImage() throws Exception {
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);

            final Image image = Image.ofWidthAndHeight("http://www.commercetools.com/assets/img/ct_logo_farbe.gif", 460, 102, "commercetools logo");
            final Product updatedProduct = execute(ProductUpdateCommand.of(product, AddExternalImage.of(image, MASTER_VARIANT_ID)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));
            return updatedProduct;
        });
    }

    @Test
    public void addPrice() throws Exception {
        final Price expectedPrice = Price.of(MoneyImpl.of(123, EUR));
        testAddPrice(expectedPrice);
    }

    @Test
    public void addPriceWithValidityPeriod() throws Exception {
        final Price expectedPrice = Price.of(MoneyImpl.of(123, EUR))
                .withValidFrom(SphereTestUtils.now())
                .withValidUntil(SphereTestUtils.now().withZoneSameLocal(ZoneOffset.UTC).plusHours(2));
        testAddPrice(expectedPrice);
    }

    private void testAddPrice(final Price expectedPrice) throws Exception {
        withUpdateableProduct(client(), product -> {
            final Product updatedProduct = client()
                    .execute(ProductUpdateCommand.of(product, AddPrice.of(1, expectedPrice)));

            final Price actualPrice = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices()
                    .stream()
                    .filter(p -> p.equals(expectedPrice))
                    .findFirst()
                    .get();

            assertThat(actualPrice.withId(null)).isEqualTo(expectedPrice);
            return updatedProduct;
        });
    }

    //and remove from category
    @Test
    public void addToCategory() throws Exception {
        withProductAndUnconnectedCategory(client(), (final Product product, final Category category) -> {
            assertThat(product.getMasterData().getStaged().getCategories()).isEmpty();

            final Product productWithCategory = client()
                    .execute(ProductUpdateCommand.of(product, AddToCategory.of(category)));

            ReferenceAssert.assertThat(productWithCategory.getMasterData().getStaged().getCategories().stream().findAny().get()).references(category);

            final Product productWithoutCategory = client()
                    .execute(ProductUpdateCommand.of(productWithCategory, RemoveFromCategory.of(category)));

            assertThat(productWithoutCategory.getMasterData().getStaged().getCategories()).isEmpty();
        });
    }

    @Test
    public void changeName() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedStrings newName = ofEnglishLocale("newName " + RANDOM.nextInt());
            final Product updatedProduct = execute(ProductUpdateCommand.of(product, ChangeName.of(newName)));

            assertThat(updatedProduct.getMasterData().getStaged().getName()).isEqualTo(newName);
            return updatedProduct;
        });
    }

    @Test
    public void changePrice() throws Exception {
        withUpdateablePricedProduct(client(), product -> {
            final Price newPrice = Price.of(MoneyImpl.of(234, EUR));
            final List<Price> prices = product.getMasterData().getStaged().getMasterVariant()
                    .getPrices();
            assertThat(prices.stream().anyMatch(p -> p.equals(newPrice))).isFalse();

            final Product updatedProduct = client()
                    .execute(ProductUpdateCommand.of(product, ChangePrice.of(prices.get(0), newPrice)));

            final Price actualPrice = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
            assertThat(actualPrice).isEqualTo(newPrice);

            return updatedProduct;
        });
    }

    @Test
    public void changeSlug() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedStrings newSlug = ofEnglishLocale("new-slug-" + RANDOM.nextInt());
            final Product updatedProduct = execute(ProductUpdateCommand.of(product, ChangeSlug.of(newSlug)));

            assertThat(updatedProduct.getMasterData().getStaged().getSlug()).isEqualTo(newSlug);
            return updatedProduct;
        });
    }

    @Test
    public void publish() throws Exception {
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().isPublished()).isFalse();

            final Product publishedProduct = execute(ProductUpdateCommand.of(product, Publish.of()));
            assertThat(publishedProduct.getMasterData().isPublished()).isTrue();

            final Product unpublishedProduct = execute(ProductUpdateCommand.of(publishedProduct, Unpublish.of()));
            assertThat(unpublishedProduct.getMasterData().isPublished()).isFalse();
            return unpublishedProduct;
        });
    }

    @Test
    public void removeImage() throws Exception {
        final Image image = Image.ofWidthAndHeight("http://www.commercetools.com/assets/img/ct_logo_farbe.gif", 460, 102, "commercetools logo");
        withUpdateableProduct(client(), product -> {
            final Product productWithImage = client().execute(ProductUpdateCommand.of(product, AddExternalImage.of(image, MASTER_VARIANT_ID)));
            assertThat(productWithImage.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));

            final Product updatedProduct = execute(ProductUpdateCommand.of(productWithImage, RemoveImage.of(image, MASTER_VARIANT_ID)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);
            return updatedProduct;
        });
    }

    @Test
    public void removePrice() throws Exception {
        withUpdateablePricedProduct(client(), product -> {
            final Price oldPrice = product.getMasterData().getStaged().getMasterVariant().getPrices().get(0);

            final Product updatedProduct = client()
                    .execute(ProductUpdateCommand.of(product, RemovePrice.of(oldPrice)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant()
                    .getPrices().stream().anyMatch(p -> p.equals(oldPrice))).isFalse();

            return updatedProduct;
        });
    }

    @Test
    public void setDescription() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedStrings newDescription = ofEnglishLocale("new description " + RANDOM.nextInt());
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, SetDescription.of(newDescription));
            final Product updatedProduct = execute(cmd);

            assertThat(updatedProduct.getMasterData().getStaged().getDescription()).isEqualTo(newDescription);
            return updatedProduct;
        });
    }

    @Test
    public void setMetaKeywords() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedStrings metaKeywords = LocalizedStrings
                    .of(ENGLISH, "Platform-as-a-Service, e-commerce, http, api, tool");
            final SetMetaKeywords action = SetMetaKeywords.of(metaKeywords);

            final Product updatedProduct = client().execute(ProductUpdateCommand.of(product, action));

            assertThat(updatedProduct.getMasterData().getStaged().getMetaKeywords()).isEqualTo(metaKeywords);

            return updatedProduct;
        });
    }

    @Test
    public void setMetaDescription() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedStrings metaDescription = LocalizedStrings
                    .of(ENGLISH, "SPHERE.IO&#8482; is the first Platform-as-a-Service solution for eCommerce.");
            final SetMetaDescription action = SetMetaDescription.of(metaDescription);

            final Product updatedProduct = client().execute(ProductUpdateCommand.of(product, action));

            assertThat(updatedProduct.getMasterData().getStaged().getMetaDescription()).isEqualTo(metaDescription);

            return updatedProduct;
        });
    }

    @Test
    public void setMetaTitle() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedStrings metaTitle = LocalizedStrings
                    .of(ENGLISH, "commercetools SPHERE.IO&#8482; - Next generation eCommerce");
            final SetMetaTitle action = SetMetaTitle.of(metaTitle);

            final Product updatedProduct = client().execute(ProductUpdateCommand.of(product, action));

            assertThat(updatedProduct.getMasterData().getStaged().getMetaTitle()).isEqualTo(metaTitle);

            return updatedProduct;
        });
    }

    @Test
    public void setMetaAttributes() throws Exception {
        withUpdateableProduct(client(), product -> {
            final MetaAttributes metaAttributes = MetaAttributes.metaAttributesOf(ENGLISH,
                    "commercetools SPHERE.IO&#8482; - Next generation eCommerce",
                    "SPHERE.IO&#8482; is the first and leading Platform-as-a-Service solution for eCommerce.",
                    "Platform-as-a-Service, e-commerce, http, api, tool");
            final List<UpdateAction<Product>> updateActions =
                    MetaAttributesUpdateActions.of(metaAttributes);
            final Product updatedProduct = client().execute(ProductUpdateCommand.of(product, updateActions));

            final ProductData productData = updatedProduct.getMasterData().getStaged();
            assertThat(productData.getMetaTitle()).isEqualTo(metaAttributes.getMetaTitle());
            assertThat(productData.getMetaDescription()).isEqualTo(metaAttributes.getMetaDescription());
            assertThat(productData.getMetaKeywords()).isEqualTo(metaAttributes.getMetaKeywords());
            return updatedProduct;
        });
    }

    @Test
    public void setAttribute() throws Exception {
        withUpdateableProduct(client(), product -> {
            //the setter contains the name and a JSON mapper, declare it only one time in your project per attribute
            //example for MonetaryAmount attribute
            final String moneyAttributeName = MONEY_ATTRIBUTE_NAME;
            final NamedAttributeAccess<MonetaryAmount> moneyAttribute =
                    AttributeAccess.ofMoney().ofName(moneyAttributeName);
            final MonetaryAmount newValueForMoney = EURO_10;

            //example for LocalizedEnumValue attribute
            final NamedAttributeAccess<LocalizedEnumValue> colorAttribute = Colors.ATTRIBUTE;
            final LocalizedEnumValue oldValueForColor = Colors.GREEN;
            final LocalizedEnumValue newValueForColor = Colors.RED;

            assertThat(product.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).isEmpty();
            assertThat(product.getMasterData().getStaged().getMasterVariant().findAttribute(colorAttribute)).contains(oldValueForColor);

            final SetAttribute moneyUpdate = SetAttribute.of(MASTER_VARIANT_ID, moneyAttribute, newValueForMoney);
            final SetAttribute localizedEnumUpdate = SetAttribute.of(MASTER_VARIANT_ID, colorAttribute, newValueForColor);

            final Product updatedProduct = client().execute(ProductUpdateCommand.of(product, asList(moneyUpdate, localizedEnumUpdate)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).contains(newValueForMoney);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(colorAttribute)).contains(newValueForColor);

            final SetAttribute unsetAction = SetAttribute.ofUnsetAttribute(MASTER_VARIANT_ID, moneyAttribute);
            final Product productWithoutMoney = client().execute(ProductUpdateCommand.of(updatedProduct, unsetAction));

            assertThat(productWithoutMoney.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).isEmpty();

            return productWithoutMoney;
        });
    }

    @Test
    public void setAttributeInAllVariants() throws Exception {
        withUpdateableProduct(client(), product -> {
            //the setter contains the name and a JSON mapper, declare it only one time in your project per attribute
            //example for MonetaryAmount attribute
            final String moneyAttributeName = MONEY_ATTRIBUTE_NAME;
            final NamedAttributeAccess<MonetaryAmount> moneyAttribute =
                    AttributeAccess.ofMoney().ofName(moneyAttributeName);
            final MonetaryAmount newValueForMoney = EURO_10;

            //example for LocalizedEnumValue attribute
            final NamedAttributeAccess<LocalizedEnumValue> colorAttribute = Colors.ATTRIBUTE;
            final LocalizedEnumValue oldValueForColor = Colors.GREEN;
            final LocalizedEnumValue newValueForColor = Colors.RED;

            assertThat(product.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).isEmpty();
            assertThat(product.getMasterData().getStaged().getMasterVariant().findAttribute(colorAttribute)).contains(oldValueForColor);

            final SetAttributeInAllVariants moneyUpdate = SetAttributeInAllVariants.of(moneyAttribute, newValueForMoney);
            final SetAttributeInAllVariants localizedEnumUpdate = SetAttributeInAllVariants.of(colorAttribute, newValueForColor);

            final Product updatedProduct = client().execute(ProductUpdateCommand.of(product, asList(moneyUpdate, localizedEnumUpdate)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).contains(newValueForMoney);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(colorAttribute)).contains(newValueForColor);

            final SetAttributeInAllVariants unsetAction = SetAttributeInAllVariants.ofUnsetAttribute(moneyAttribute);
            final Product productWithoutMoney = client().execute(ProductUpdateCommand.of(updatedProduct, unsetAction));

            assertThat(productWithoutMoney.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).isEmpty();

            return productWithoutMoney;
        });
    }

    @Test
    public void revertStagedChanges() throws Exception {
        withUpdateableProduct(client(), product -> {
            //changing only staged and not current
            final LocalizedStrings oldDescriptionOption = product.getMasterData().getStaged().getDescription();
            final LocalizedStrings newDescription = ofEnglishLocale("new description " + RANDOM.nextInt());
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, asList(Publish.of(), SetDescription.of(newDescription)));
            final Product updatedProduct = execute(cmd);
            assertThat(oldDescriptionOption).isNotEqualTo(newDescription);
            assertThat(updatedProduct.getMasterData().getStaged().getDescription()).isEqualTo(newDescription);
            assertThat(updatedProduct.getMasterData().getCurrent().getDescription()).isEqualTo(oldDescriptionOption);

            final Product revertedProduct = execute(ProductUpdateCommand.of(updatedProduct, RevertStagedChanges.of()));
            assertThat(revertedProduct.getMasterData().getStaged().getDescription()).isEqualTo(oldDescriptionOption);
            assertThat(revertedProduct.getMasterData().getCurrent().getDescription()).isEqualTo(oldDescriptionOption);

            return revertedProduct;
        });
    }

    @Test
    public void setTaxCategory() throws Exception {
        TaxCategoryFixtures.withTransientTaxCategory(client(), taxCategory ->
            withUpdateableProduct(client(), product -> {
                assertThat(product.getTaxCategory()).isNotEqualTo(taxCategory);
                final ProductUpdateCommand command = ProductUpdateCommand.of(product, SetTaxCategory.of(taxCategory));
                final Product updatedProduct = execute(command);
                assertThat(updatedProduct.getTaxCategory()).isEqualTo(taxCategory.toReference());
                return updatedProduct;
            })
        );
    }

    @Test
    public void setSearchKeywords() throws Exception {
        withUpdateableProduct(client(), product -> {
            final SearchKeywords searchKeywords = SearchKeywords.of(Locale.ENGLISH, asList(SearchKeyword.of("foo bar baz", CustomSuggestTokenizer.of(asList("foo, baz")))));
            final ProductUpdateCommand command = ProductUpdateCommand.of(product, SetSearchKeywords.of(searchKeywords));
            final Product updatedProduct = execute(command);

            final SearchKeywords actualKeywords = updatedProduct.getMasterData().getStaged().getSearchKeywords();
            assertThat(actualKeywords).isEqualTo(searchKeywords);
            return updatedProduct;
        });
    }

    @Test
    public void addVariant() throws Exception {
        final NamedAttributeAccess<MonetaryAmount> moneyAttribute =
                AttributeAccess.ofMoney().ofName(MONEY_ATTRIBUTE_NAME);
        final AttributeDraft moneyAttributeValue = AttributeDraft.of(moneyAttribute, EURO_10);

        final NamedAttributeAccess<LocalizedEnumValue> colorAttribute = Colors.ATTRIBUTE;
        final LocalizedEnumValue color = Colors.RED;
        final AttributeDraft colorAttributeValue = AttributeDraft.of(colorAttribute, color);

        final NamedAttributeAccess<PlainEnumValue> sizeAttribute = Sizes.ATTRIBUTE;
        final AttributeDraft sizeValue = AttributeDraft.of(sizeAttribute, Sizes.M);


        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().getStaged().getVariants()).isEmpty();

            final Price price = PRICE;
            final List<Price> prices = asList(price);
            final List<AttributeDraft> attributeValues = asList(moneyAttributeValue, colorAttributeValue, sizeValue);
            final ProductUpdateCommand addVariantCommand =
                    ProductUpdateCommand.of(product, AddVariant.of(attributeValues, prices, randomKey()));

            final Product productWithVariant = client().execute(addVariantCommand);
            final ProductVariant variant = productWithVariant.getMasterData().getStaged().getVariants().get(0);
            assertThat(variant.getId()).isEqualTo(2);
            assertThat(variant.findAttribute(moneyAttribute).get()).isEqualTo(EURO_10);
            assertThat(variant.findAttribute(colorAttribute).get()).isEqualTo(color);
            assertThat(variant.findAttribute(sizeAttribute).get()).isEqualTo(Sizes.M);

            final Product productWithoutVariant = client().execute(ProductUpdateCommand.of(productWithVariant, RemoveVariant.of(variant)));
            assertThat(productWithoutVariant.getMasterData().getStaged().getVariants()).isEmpty();

            return productWithoutVariant;
        });
    }

    private static class StagedWrapper extends Base implements UpdateAction<Product> {
        private final UpdateAction<Product> delegate;
        @JsonProperty("staged")
        private final boolean staged;

        private StagedWrapper(final UpdateAction<Product> action, final boolean staged) {
            this.delegate = action;
            this.staged = staged;
        }

        @Override
        public String getAction() {
            return delegate.getAction();
        }

        @JsonUnwrapped
        public UpdateAction<Product> getDelegate() {
            return delegate;
        }
    }

    @Test
    public void possibleToHackUpdateForStagedAndCurrent() throws Exception {
         withUpdateableProduct(client(), product -> {
             final LocalizedStrings newName = randomSlug();
             final UpdateAction<Product> stagedWrapper = new StagedWrapper(ChangeName.of(newName), false);
             final Product updatedProduct = execute(ProductUpdateCommand.of(product, asList(Publish.of(), stagedWrapper)));

             final Product fetchedProduct = execute(ProductByIdFetch.of(product));
             assertThat(fetchedProduct.getMasterData().getCurrent().getName())
                     .isEqualTo(fetchedProduct.getMasterData().getStaged().getName())
                     .isEqualTo(newName);

             return updatedProduct;
         });
    }
}