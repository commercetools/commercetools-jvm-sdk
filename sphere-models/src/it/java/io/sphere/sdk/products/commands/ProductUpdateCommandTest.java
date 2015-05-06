package io.sphere.sdk.products.commands;

import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.attributes.AttributeAccess;
import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductData;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.commands.updateactions.*;
import io.sphere.sdk.search.SearchKeyword;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.search.tokenizer.CustomSuggestTokenizer;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Colors;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Sizes;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;
import io.sphere.sdk.test.IntegrationTest;

import io.sphere.sdk.test.ReferenceAssert;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.models.LocalizedStrings.ofEnglishLocale;
import static io.sphere.sdk.products.ProductUpdateScope.ONLY_STAGED;
import static io.sphere.sdk.products.ProductUpdateScope.STAGED_AND_CURRENT;
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
            final Product updatedProduct = execute(ProductUpdateCommand.of(product, AddExternalImage.of(image, MASTER_VARIANT_ID, STAGED_AND_CURRENT)));

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
                .withValidFrom(Instant.now())
                .withValidUntil(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC));
        testAddPrice(expectedPrice);
    }

    private void testAddPrice(Price expectedPrice) throws Exception {
        withUpdateableProduct(client(), product -> {
            final Product updatedProduct = client()
                    .execute(ProductUpdateCommand.of(product, AddPrice.of(1, expectedPrice, STAGED_AND_CURRENT)));

            final Price actualPrice = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
            assertThat(actualPrice).isEqualTo(expectedPrice);
            return updatedProduct;
        });
    }

    //and remove from category
    @Test
    public void addToCategory() throws Exception {
        withProductAndCategory(client(), (final Product product, final Category category) -> {
            assertThat(product.getMasterData().getStaged().getCategories()).isEmpty();

            final Product productWithCategory = client()
                    .execute(ProductUpdateCommand.of(product, AddToCategory.of(category, STAGED_AND_CURRENT)));

            ReferenceAssert.assertThat(productWithCategory.getMasterData().getStaged().getCategories().stream().findAny().get()).references(category);

            final Product productWithoutCategory = client()
                    .execute(ProductUpdateCommand.of(productWithCategory, RemoveFromCategory.of(category, STAGED_AND_CURRENT)));

            assertThat(productWithoutCategory.getMasterData().getStaged().getCategories()).isEmpty();
        });
    }

    @Test
    public void changeName() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedStrings newName = ofEnglishLocale("newName " + RANDOM.nextInt());
            final Product updatedProduct = execute(ProductUpdateCommand.of(product, ChangeName.of(newName, STAGED_AND_CURRENT)));

            assertThat(updatedProduct.getMasterData().getStaged().getName()).isEqualTo(newName);
            return updatedProduct;
        });
    }

    @Test
    public void changePrice() throws Exception {
        withUpdateablePricedProduct(client(), product -> {
            final Price newPrice = Price.of(MoneyImpl.of(234, EUR));
            assertThat(product.getMasterData().getStaged().getMasterVariant()
                    .getPrices().stream().anyMatch(p -> p.equals(newPrice))).isFalse();

            final Product updatedProduct = client()
                    .execute(ProductUpdateCommand.of(product, ChangePrice.of(MASTER_VARIANT_ID, newPrice, STAGED_AND_CURRENT)));

            final Price actualPrice = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
            assertThat(actualPrice).isEqualTo(newPrice);

            return updatedProduct;
        });
    }

    @Test
    public void changeSlug() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedStrings newSlug = ofEnglishLocale("new-slug-" + RANDOM.nextInt());
            final Product updatedProduct = execute(ProductUpdateCommand.of(product, ChangeSlug.of(newSlug, STAGED_AND_CURRENT)));

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
            final Product productWithImage = client().execute(ProductUpdateCommand.of(product, AddExternalImage.of(image, MASTER_VARIANT_ID, STAGED_AND_CURRENT)));
            assertThat(productWithImage.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));

            final Product updatedProduct = execute(ProductUpdateCommand.of(productWithImage, RemoveImage.of(image, MASTER_VARIANT_ID, STAGED_AND_CURRENT)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);
            return updatedProduct;
        });
    }

    @Test
    public void removePrice() throws Exception {
        withUpdateablePricedProduct(client(), product -> {
            final Price oldPrice = product.getMasterData().getStaged().getMasterVariant().getPrices().get(0);

            final Product updatedProduct = client()
                    .execute(ProductUpdateCommand.of(product, RemovePrice.of(MASTER_VARIANT_ID, oldPrice, STAGED_AND_CURRENT)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant()
                    .getPrices().stream().anyMatch(p -> p.equals(oldPrice))).isFalse();

            return updatedProduct;
        });
    }

    @Test
    public void setDescription() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedStrings newDescription = ofEnglishLocale("new description " + RANDOM.nextInt());
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, SetDescription.of(newDescription, STAGED_AND_CURRENT));
            final Product updatedProduct = execute(cmd);

            assertThat(updatedProduct.getMasterData().getStaged().getDescription()).contains(newDescription);
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
            final Product updatedProduct = client()
                    .execute(ProductUpdateCommand.of(product, SetMetaAttributes.of(metaAttributes, STAGED_AND_CURRENT)));

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
            final AttributeGetterSetter<Product, MonetaryAmount> moneyAttribute =
                    AttributeAccess.ofMoney().ofName(moneyAttributeName);
            final MonetaryAmount newValueForMoney = EURO_10;

            //example for LocalizedEnumValue attribute
            final AttributeGetterSetter<Product, LocalizedEnumValue> colorAttribute = Colors.ATTRIBUTE;
            final LocalizedEnumValue oldValueForColor = Colors.GREEN;
            final LocalizedEnumValue newValueForColor = Colors.RED;

            assertThat(product.getMasterData().getStaged().getMasterVariant().getAttribute(moneyAttribute)).isEmpty();
            assertThat(product.getMasterData().getStaged().getMasterVariant().getAttribute(colorAttribute)).contains(oldValueForColor);

            final SetAttribute moneyUpdate = SetAttribute.of(MASTER_VARIANT_ID, moneyAttribute, newValueForMoney, STAGED_AND_CURRENT);
            final SetAttribute localizedEnumUpdate = SetAttribute.of(MASTER_VARIANT_ID, colorAttribute, newValueForColor, STAGED_AND_CURRENT);

            final Product updatedProduct = client().execute(ProductUpdateCommand.of(product, asList(moneyUpdate, localizedEnumUpdate)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getAttribute(moneyAttribute)).contains(newValueForMoney);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getAttribute(colorAttribute)).contains(newValueForColor);

            final SetAttribute unsetAction = SetAttribute.ofUnsetAttribute(MASTER_VARIANT_ID, moneyAttribute, STAGED_AND_CURRENT);
            final Product productWithoutMoney = client().execute(ProductUpdateCommand.of(updatedProduct, unsetAction));

            assertThat(productWithoutMoney.getMasterData().getStaged().getMasterVariant().getAttribute(moneyAttribute)).isEmpty();

            return productWithoutMoney;
        });
    }

    @Test
    public void setAttributeInAllVariants() throws Exception {
        withUpdateableProduct(client(), product -> {
            //the setter contains the name and a JSON mapper, declare it only one time in your project per attribute
            //example for MonetaryAmount attribute
            final String moneyAttributeName = MONEY_ATTRIBUTE_NAME;
            final AttributeGetterSetter<Product, MonetaryAmount> moneyAttribute =
                    AttributeAccess.ofMoney().ofName(moneyAttributeName);
            final MonetaryAmount newValueForMoney = EURO_10;

            //example for LocalizedEnumValue attribute
            final AttributeGetterSetter<Product, LocalizedEnumValue> colorAttribute = Colors.ATTRIBUTE;
            final LocalizedEnumValue oldValueForColor = Colors.GREEN;
            final LocalizedEnumValue newValueForColor = Colors.RED;

            assertThat(product.getMasterData().getStaged().getMasterVariant().getAttribute(moneyAttribute)).isEmpty();
            assertThat(product.getMasterData().getStaged().getMasterVariant().getAttribute(colorAttribute)).contains(oldValueForColor);

            final SetAttributeInAllVariants moneyUpdate = SetAttributeInAllVariants.of(moneyAttribute, newValueForMoney, STAGED_AND_CURRENT);
            final SetAttributeInAllVariants localizedEnumUpdate = SetAttributeInAllVariants.of(colorAttribute, newValueForColor, STAGED_AND_CURRENT);

            final Product updatedProduct = client().execute(ProductUpdateCommand.of(product, asList(moneyUpdate, localizedEnumUpdate)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getAttribute(moneyAttribute)).contains(newValueForMoney);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getAttribute(colorAttribute)).contains(newValueForColor);

            final SetAttributeInAllVariants unsetAction = SetAttributeInAllVariants.ofUnsetAttribute(moneyAttribute, STAGED_AND_CURRENT);
            final Product productWithoutMoney = client().execute(ProductUpdateCommand.of(updatedProduct, unsetAction));

            assertThat(productWithoutMoney.getMasterData().getStaged().getMasterVariant().getAttribute(moneyAttribute)).isEmpty();

            return productWithoutMoney;
        });
    }

    @Test
    public void revertStagedChanges() throws Exception {
        withUpdateableProduct(client(), product -> {
            //changing only staged and not current
            final Optional<LocalizedStrings> oldDescriptionOption = product.getMasterData().getStaged().getDescription();
            final LocalizedStrings newDescription = ofEnglishLocale("new description " + RANDOM.nextInt());
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, asList(Publish.of(), SetDescription.of(newDescription, ONLY_STAGED)));
            final Product updatedProduct = execute(cmd);
            assertThat(oldDescriptionOption).isNotEqualTo(Optional.of(newDescription));
            assertThat(updatedProduct.getMasterData().getStaged().getDescription()).contains(newDescription);
            assertThat(updatedProduct.getMasterData().getCurrent().get().getDescription()).isEqualTo(oldDescriptionOption);

            final Product revertedProduct = execute(ProductUpdateCommand.of(updatedProduct, RevertStagedChanges.of()));
            assertThat(revertedProduct.getMasterData().getStaged().getDescription()).isEqualTo(oldDescriptionOption);
            assertThat(revertedProduct.getMasterData().getCurrent().get().getDescription()).isEqualTo(oldDescriptionOption);

            return revertedProduct;
        });
    }

    @Test
    public void setTaxCategory() throws Exception {
        TaxCategoryFixtures.withTransientTaxCategory(client(), taxCategory ->
            withUpdateableProduct(client(), product -> {
                assertThat(product.getTaxCategory()).isNotEqualTo(Optional.of(taxCategory));
                final ProductUpdateCommand command = ProductUpdateCommand.of(product, SetTaxCategory.of(taxCategory));
                final Product updatedProduct = execute(command);
                assertThat(updatedProduct.getTaxCategory()).contains(taxCategory.toReference());
                return updatedProduct;
            })
        );
    }

    @Test
    public void setSearchKeywords() throws Exception {
        withUpdateableProduct(client(), product -> {
            final SearchKeywords searchKeywords = SearchKeywords.of(Locale.ENGLISH, asList(SearchKeyword.of("foo bar baz", CustomSuggestTokenizer.of(asList("foo, baz")))));
            final ProductUpdateCommand command = ProductUpdateCommand.of(product, SetSearchKeywords.of(searchKeywords, STAGED_AND_CURRENT));
            final Product updatedProduct = execute(command);

            final SearchKeywords actualKeywords = updatedProduct.getMasterData().getStaged().getSearchKeywords();
            assertThat(actualKeywords).isEqualTo(searchKeywords);
            return updatedProduct;
        });
    }

    @Test
    public void addVariant() throws Exception {
        final AttributeGetterSetter<Product, MonetaryAmount> moneyAttribute =
                AttributeAccess.ofMoney().ofName(MONEY_ATTRIBUTE_NAME);
        final Attribute moneyAttributeValue = Attribute.of(moneyAttribute, EURO_10);

        final AttributeGetterSetter<Product, LocalizedEnumValue> colorAttribute = Colors.ATTRIBUTE;
        final LocalizedEnumValue color = Colors.RED;
        final Attribute colorAttributeValue = Attribute.of(colorAttribute, color);

        final AttributeGetterSetter<Product, PlainEnumValue> sizeAttribute = Sizes.ATTRIBUTE;
        final Attribute sizeValue = Attribute.of(sizeAttribute, Sizes.M);


        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().getStaged().getVariants()).isEmpty();

            final Price price = PRICE;
            final List<Price> prices = asList(price);
            final List<Attribute> attributeValues = asList(moneyAttributeValue, colorAttributeValue, sizeValue);
            final Optional<String> sku = Optional.of(randomKey());
            final ProductUpdateCommand addVariantCommand =
                    ProductUpdateCommand.of(product, AddVariant.of(attributeValues, prices, sku, STAGED_AND_CURRENT));

            final Product productWithVariant = client().execute(addVariantCommand);
            final ProductVariant variant = productWithVariant.getMasterData().getStaged().getVariants().get(0);
            assertThat(variant.getId()).isEqualTo(2);
            assertThat(variant.getAttribute(moneyAttribute).get()).isEqualTo(EURO_10);
            assertThat(variant.getAttribute(colorAttribute).get()).isEqualTo(color);
            assertThat(variant.getAttribute(sizeAttribute).get()).isEqualTo(Sizes.M);

            final Product productWithoutVariant = client().execute(ProductUpdateCommand.of(productWithVariant, RemoveVariant.of(variant, STAGED_AND_CURRENT)));
            assertThat(productWithoutVariant.getMasterData().getStaged().getVariants()).isEmpty();

            return productWithoutVariant;
        });
    }
}