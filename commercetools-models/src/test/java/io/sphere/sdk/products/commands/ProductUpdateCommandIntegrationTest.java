package io.sphere.sdk.products.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.AttributeAccess;
import io.sphere.sdk.products.attributes.AttributeDraft;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;
import io.sphere.sdk.products.commands.updateactions.*;
import io.sphere.sdk.products.messages.ProductSlugChangedMessage;
import io.sphere.sdk.products.messages.ProductStateTransitionMessage;
import io.sphere.sdk.products.queries.ProductByIdGet;
import io.sphere.sdk.products.queries.ProductProjectionByIdGet;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.search.SearchKeyword;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.search.tokenizer.CustomSuggestTokenizer;
import io.sphere.sdk.states.State;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Colors;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Sizes;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductFixtures.*;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static io.sphere.sdk.states.StateFixtures.withStateByBuilder;
import static io.sphere.sdk.states.StateType.PRODUCT_STATE;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.MONEY_ATTRIBUTE_NAME;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.test.SphereTestUtils.MASTER_VARIANT_ID;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.util.Collections.*;
import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductUpdateCommandIntegrationTest extends IntegrationTest {
    public static final Random RANDOM = new Random();

    @Test
    public void moveImageToPositionByVariantId() throws Exception {
        final String url1 = "http://www.commercetools.com/ct_logo_farbe_1.gif";
        final String url2 = "http://www.commercetools.com/ct_logo_farbe_2.gif";
        final String url3 = "http://www.commercetools.com/ct_logo_farbe_3.gif";
        withProductWithImages(client(), url1, url2, url3, (Product product) -> {
            final List<String> oldImageOrderUrls = product.getMasterData().getStaged().getMasterVariant().getImages()
                    .stream()
                    .map(image -> image.getUrl())
                    .collect(toList());
            assertThat(oldImageOrderUrls).containsExactly(url1, url2, url3);

            final Integer position = 0;
            final List<Image> images = product.getMasterData().getStaged().getMasterVariant().getImages();
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, MoveImageToPosition.ofImageUrlAndVariantId(images.get(1).getUrl(), MASTER_VARIANT_ID, position));

            final Product updatedProduct = client().executeBlocking(cmd);

            final List<String> urls = updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()
                    .stream()
                    .map(image -> image.getUrl())
                    .collect(toList());
            assertThat(urls).containsExactly(url2, url1, url3);

            return updatedProduct;
        });
    }

    @Test
    public void moveImageToPositionBySku() throws Exception {
        final String url1 = "http://www.commercetools.com/ct_logo_farbe_1.gif";
        final String url2 = "http://www.commercetools.com/ct_logo_farbe_2.gif";
        final String url3 = "http://www.commercetools.com/ct_logo_farbe_3.gif";
        withProductWithImages(client(), url1, url2, url3, (Product product) -> {
            final List<String> oldImageOrderUrls = product.getMasterData().getStaged().getMasterVariant().getImages()
                    .stream()
                    .map(image -> image.getUrl())
                    .collect(toList());
            assertThat(oldImageOrderUrls).containsExactly(url1, url2, url3);

            final Integer position = 0;
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final List<Image> images = masterVariant.getImages();
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, MoveImageToPosition.ofImageUrlAndSku(images.get(1).getUrl(), masterVariant.getSku(), position));

            final Product updatedProduct = client().executeBlocking(cmd);

            final List<String> urls = updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()
                    .stream()
                    .map(image -> image.getUrl())
                    .collect(toList());
            assertThat(urls).containsExactly(url2, url1, url3);

            return updatedProduct;
        });
    }

    @Test
    public void addExternalImage() throws Exception {
        withUpdateableProduct(client(), (Product product) -> {
            assertThat(product.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);

            final Image image = Image.ofWidthAndHeight("http://www.commercetools.com/assets/img/ct_logo_farbe.gif", 460, 102, "commercetools logo");
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.of(image, MASTER_VARIANT_ID)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));
            return updatedProduct;
        });
    }

    @Test
    public void addExternalImageByVariantId() throws Exception {
        withUpdateableProduct(client(), (Product product) -> {
            assertThat(product.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);

            final Image image = Image.ofWidthAndHeight("http://www.commercetools.com/assets/img/ct_logo_farbe.gif", 460, 102, "commercetools logo");
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.ofVariantId(MASTER_VARIANT_ID, image)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));
            return updatedProduct;
        });
    }

    @Test
    public void addExternalImageBySku() throws Exception {
        withUpdateableProduct(client(), (Product product) -> {
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            assertThat(masterVariant.getImages()).hasSize(0);

            final Image image = Image.ofWidthAndHeight("http://www.commercetools.com/assets/img/ct_logo_farbe.gif", 460, 102, "commercetools logo");
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.ofSku(masterVariant.getSku(), image)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));
            return updatedProduct;
        });
    }

    //do not inline, it is example code
    @Test
    public void addPrice() throws Exception {
        final PriceDraft expectedPrice = PriceDraft.of(MoneyImpl.of(123, EUR));
        withUpdateableProduct(client(), product -> {
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, AddPrice.of(1, expectedPrice)));


            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            assertThat(prices).hasSize(1);
            final Price actualPrice = prices.get(0);

            assertThat(expectedPrice).isEqualTo(PriceDraft.of(actualPrice));

            return updatedProduct;
        });
    }

    @Test
    public void addPriceYen() throws Exception {
        final PriceDraft expectedPrice = PriceDraft.of(MoneyImpl.of(new BigDecimal("12345"), "JPY"));
        testAddPrice(expectedPrice);
    }

    @Test
    public void addPriceWithValidityPeriod() throws Exception {
        final PriceDraft expectedPrice = PriceDraft.of(MoneyImpl.of(123, EUR))
                .withValidFrom(SphereTestUtils.now())
                .withValidUntil(SphereTestUtils.now().withZoneSameLocal(ZoneOffset.UTC).plusHours(2));
        testAddPrice(expectedPrice);
    }

    @Test
    public void addPriceByVariantId() throws Exception {
        final PriceDraft priceDraft = PriceDraft.of(MoneyImpl.of(new BigDecimal("12345"), "JPY"));
        withUpdateableProduct(client(), product -> {
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, AddPrice.ofVariantId(1, priceDraft)));

            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            assertThat(prices).hasSize(1);
            final Price actualPrice = prices.get(0);

            assertThat(priceDraft).isEqualTo(PriceDraft.of(actualPrice));

            return updatedProduct;
        });
    }

    @Test
    public void addPriceBySku() throws Exception {
        final PriceDraft priceDraft = PriceDraft.of(MoneyImpl.of(new BigDecimal("12345"), "JPY"));
        withUpdateableProduct(client(), product -> {

            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, AddPrice.ofSku(sku, priceDraft)));

            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            assertThat(prices).hasSize(1);
            final Price actualPrice = prices.get(0);

            assertThat(priceDraft).isEqualTo(PriceDraft.of(actualPrice));

            return updatedProduct;
        });
    }

    @Test
    public void setPrices() throws Exception {
        final PriceDraft expectedPrice1 = PriceDraft.of(MoneyImpl.of(123, EUR));
        final PriceDraft expectedPrice2 = PriceDraft.of(MoneyImpl.of(123, EUR)).withCountry(DE);
        final List<PriceDraft> expectedPriceList = asList(expectedPrice1, expectedPrice2);

        withUpdateableProduct(client(), product -> {
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, SetPrices.of(1, expectedPriceList)));

            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            assertThat(prices).hasSize(2);
            List<PriceDraft> draftPricesList = prices.stream().map(PriceDraft::of).collect(toList());
            assertThat(draftPricesList).containsOnly(expectedPrice1, expectedPrice2);

            return updatedProduct;
        });
    }

    @Test
    public void setPricesByVariantId() throws Exception {
        final PriceDraft priceDraft = PriceDraft.of(MoneyImpl.of(123, EUR));
        final PriceDraft priceDraft2 = PriceDraft.of(MoneyImpl.of(123, EUR)).withCountry(DE);
        final List<PriceDraft> expectedPriceList = asList(priceDraft, priceDraft2);

        withUpdateableProduct(client(), product -> {
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, SetPrices.ofVariantId(1, expectedPriceList)));

            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            List<PriceDraft> draftPricesList = prices.stream().map(PriceDraft::of).collect(toList());
            assertThat(draftPricesList).containsOnly(priceDraft, priceDraft2);

            return updatedProduct;
        });
    }

    @Test
    public void setPricesBySku() throws Exception {
        final PriceDraft priceDraft = PriceDraft.of(MoneyImpl.of(123, EUR));
        final PriceDraft priceDraft2 = PriceDraft.of(MoneyImpl.of(123, EUR)).withCountry(DE);
        final List<PriceDraft> expectedPriceList = asList(priceDraft, priceDraft2);

        withUpdateableProduct(client(), product -> {
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, SetPrices.ofSku(sku, expectedPriceList)));

            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            List<PriceDraft> draftPricesList = prices.stream().map(PriceDraft::of).collect(toList());
            assertThat(draftPricesList).containsOnly(priceDraft, priceDraft2);

            return updatedProduct;
        });
    }

    @Test
    public void setPricesWithAlreadyExisting() {
        final PriceDraft expectedPrice1 = PriceDraft.of(MoneyImpl.of(123, EUR));
        final PriceDraft expectedPrice2 = PriceDraft.of(MoneyImpl.of(123, EUR)).withCountry(CountryCode.DE);
        final List<PriceDraft> expectedPriceList = asList(expectedPrice1, expectedPrice2);

        withUpdateablePricedProduct(client(), expectedPrice1, product -> {
            Price oldPrice = product.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, SetPrices.of(1, expectedPriceList)));

            final List<Price> newPrices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            assertThat(newPrices).hasSize(2);
            assertThat(newPrices).doesNotContain(oldPrice);

            List<PriceDraft> draftPricesList = newPrices.stream().map(PriceDraft::of).collect(toList());

            assertThat(draftPricesList).contains(expectedPrice1, expectedPrice2);

            return updatedProduct;
        });
    }

    @Test
    public void setPricesEmptyList() {
        withUpdateablePricedProduct(client(), product -> {
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, SetPrices.of(1, emptyList())));

            final List<Price> newPrices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            assertThat(newPrices).isEmpty();

            return updatedProduct;
        });
    }

    //and remove from category
    @Test
    public void addToCategory() throws Exception {
        withProductAndUnconnectedCategory(client(), (final Product product, final Category category) -> {
            assertThat(product.getMasterData().getStaged().getCategories()).isEmpty();

            final String orderHint = "0.123";
            final Product productWithCategory = client()
                    .executeBlocking(ProductUpdateCommand.of(product, AddToCategory.of(category, orderHint)));

            final Reference<Category> categoryReference = productWithCategory.getMasterData().getStaged().getCategories().stream().findAny().get();
            assertThat(categoryReference.referencesSameResource(category)).isTrue();
            assertThat(productWithCategory.getMasterData().getStaged().getCategoryOrderHints().get(category.getId())).isEqualTo(orderHint);

            final Product productWithoutCategory = client()
                    .executeBlocking(ProductUpdateCommand.of(productWithCategory, RemoveFromCategory.of(category)));

            assertThat(productWithoutCategory.getMasterData().getStaged().getCategories()).isEmpty();
        });
    }

    @Test
    public void setCategoryOrderHint() throws Exception {
        withProductInCategory(client(), (product, category) -> {
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, SetCategoryOrderHint.of(category.getId(), "0.1234")));

            final CategoryOrderHints actual = updatedProduct.getMasterData().getStaged().getCategoryOrderHints();
            assertThat(actual).isEqualTo(CategoryOrderHints.of(category.getId(), "0.1234"));
            assertThat(actual.getAsMap()).isEqualTo(Collections.singletonMap(category.getId(), "0.1234"));
        });
    }

    @Test
    public void changeName() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedString newName = LocalizedString.ofEnglish("newName " + RANDOM.nextInt());
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, ChangeName.of(newName)));

            assertThat(updatedProduct.getMasterData().getStaged().getName()).isEqualTo(newName);
            return updatedProduct;
        });
    }

    @Test
    public void changePrice() throws Exception {
        withUpdateablePricedProduct(client(), product -> {
            final PriceDraft newPrice = PriceDraft.of(MoneyImpl.of(234, EUR));
            final List<Price> prices = product.getMasterData().getStaged().getMasterVariant()
                    .getPrices();
            assertThat(prices.stream().anyMatch(p -> p.equals(newPrice))).isFalse();

            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, ChangePrice.of(prices.get(0), newPrice)));

            final Price actualPrice = getFirstPrice(updatedProduct);
            assertThat(PriceDraft.of(actualPrice)).isEqualTo(newPrice);

            return updatedProduct;
        });
    }

    @Test
    public void changeSlug() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedString newSlug = LocalizedString.ofEnglish("new-slug-" + RANDOM.nextInt());
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, ChangeSlug.of(newSlug)));

            assertThat(updatedProduct.getMasterData().getStaged().getSlug()).isEqualTo(newSlug);

            //query message
            assertEventually(() -> {
                final Query<ProductSlugChangedMessage> query = MessageQuery.of()
                        .withPredicates(m -> m.resource().is(product))
                        .forMessageType(ProductSlugChangedMessage.MESSAGE_HINT);
                final List<ProductSlugChangedMessage> results =
                        client().executeBlocking(query).getResults();
                assertThat(results).hasSize(1);
                final ProductSlugChangedMessage message = results.get(0);
                assertThat(message.getSlug()).isEqualTo(newSlug);
            });

            return updatedProduct;
        });
    }

    @Test
    public void publish() throws Exception {
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().isPublished()).isFalse();

            final Product publishedProduct = client().executeBlocking(ProductUpdateCommand.of(product, Publish.of()));
            assertThat(publishedProduct.getMasterData().isPublished()).isTrue();

            final Product unpublishedProduct = client().executeBlocking(ProductUpdateCommand.of(publishedProduct, Unpublish.of()));
            assertThat(unpublishedProduct.getMasterData().isPublished()).isFalse();
            return unpublishedProduct;
        });
    }

    @Test
    public void removeImage() throws Exception {
        final Image image = Image.ofWidthAndHeight("http://www.commercetools.com/assets/img/ct_logo_farbe.gif", 460, 102, "commercetools logo");
        withUpdateableProduct(client(), product -> {
            final Product productWithImage = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.of(image, MASTER_VARIANT_ID)));
            assertThat(productWithImage.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(productWithImage, RemoveImage.of(image, MASTER_VARIANT_ID)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);
            return updatedProduct;
        });
    }

    @Test
    public void removeImageByVariantId() throws Exception {
        final Image image = Image.ofWidthAndHeight("http://www.commercetools.com/assets/img/ct_logo_farbe.gif", 460, 102, "commercetools logo");
        withUpdateableProduct(client(), product -> {
            final Product productWithImage = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.ofVariantId(MASTER_VARIANT_ID, image)));
            assertThat(productWithImage.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(productWithImage, RemoveImage.ofVariantId(image, MASTER_VARIANT_ID)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);
            return updatedProduct;
        });
    }

    @Test
    public void removeImageBySku() throws Exception {
        final Image image = Image.ofWidthAndHeight("http://www.commercetools.com/assets/img/ct_logo_farbe.gif", 460, 102, "commercetools logo");
        withUpdateableProduct(client(), product -> {
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final Product productWithImage = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.ofSku(sku, image)));
            assertThat(productWithImage.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(productWithImage, RemoveImage.ofSku(image, sku)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);
            return updatedProduct;
        });
    }

    @Test
    public void removePrice() throws Exception {
        withUpdateablePricedProduct(client(), product -> {
            final Price oldPrice = getFirstPrice(product);

            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, RemovePrice.of(oldPrice)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant()
                    .getPrices().stream().anyMatch(p -> p.equals(oldPrice))).isFalse();

            return updatedProduct;
        });
    }

    @Test
    public void setDescription() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedString newDescription = LocalizedString.ofEnglish("new description " + RANDOM.nextInt());
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, SetDescription.of(newDescription));
            final Product updatedProduct = client().executeBlocking(cmd);

            assertThat(updatedProduct.getMasterData().getStaged().getDescription()).isEqualTo(newDescription);
            return updatedProduct;
        });
    }

    @Test
    public void setMetaKeywords() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedString metaKeywords = LocalizedString
                    .of(ENGLISH, "Platform-as-a-Service, e-commerce, http, api, tool");
            final SetMetaKeywords action = SetMetaKeywords.of(metaKeywords);

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, action));

            assertThat(updatedProduct.getMasterData().getStaged().getMetaKeywords()).isEqualTo(metaKeywords);

            return updatedProduct;
        });
    }

    @Test
    public void setMetaDescription() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedString metaDescription = LocalizedString
                    .of(ENGLISH, "SPHERE.IO&#8482; is the first Platform-as-a-Service solution for eCommerce.");
            final SetMetaDescription action = SetMetaDescription.of(metaDescription);

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, action));

            assertThat(updatedProduct.getMasterData().getStaged().getMetaDescription()).isEqualTo(metaDescription);

            return updatedProduct;
        });
    }

    @Test
    public void setMetaTitle() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedString metaTitle = LocalizedString
                    .of(ENGLISH, "commercetools SPHERE.IO&#8482; - Next generation eCommerce");
            final SetMetaTitle action = SetMetaTitle.of(metaTitle);

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, action));

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
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, updateActions));

            final ProductData productData = updatedProduct.getMasterData().getStaged();
            assertThat(productData.getMetaTitle()).isEqualTo(metaAttributes.getMetaTitle());
            assertThat(productData.getMetaDescription()).isEqualTo(metaAttributes.getMetaDescription());
            assertThat(productData.getMetaKeywords()).isEqualTo(metaAttributes.getMetaKeywords());
            return updatedProduct;
        });
    }

    @Test
    public void productProjectionCanBeUsedToUpdateAProduct() throws Exception {
        withUpdateableProduct(client(), product -> {
            final MetaAttributes metaAttributes = MetaAttributes.metaAttributesOf(ENGLISH,
                    "commercetools SPHERE.IO&#8482; - Next generation eCommerce",
                    "SPHERE.IO&#8482; is the first and leading Platform-as-a-Service solution for eCommerce.",
                    "Platform-as-a-Service, e-commerce, http, api, tool");
            final List<UpdateAction<Product>> updateActions =
                    MetaAttributesUpdateActions.of(metaAttributes);

            final ProductProjection productProjection = client().executeBlocking(ProductProjectionByIdGet.of(product, STAGED));

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(productProjection, updateActions));

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

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, asList(moneyUpdate, localizedEnumUpdate)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).contains(newValueForMoney);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(colorAttribute)).contains(newValueForColor);

            final SetAttribute unsetAction = SetAttribute.ofUnsetAttribute(MASTER_VARIANT_ID, moneyAttribute);
            final Product productWithoutMoney = client().executeBlocking(ProductUpdateCommand.of(updatedProduct, unsetAction));

            assertThat(productWithoutMoney.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).isEmpty();

            return productWithoutMoney;
        });
    }

    @Test
    public void setAttributeByVariantId() throws Exception {
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

            final SetAttribute moneyUpdate = SetAttribute.ofVariantId(MASTER_VARIANT_ID, moneyAttribute, newValueForMoney);
            final SetAttribute localizedEnumUpdate = SetAttribute.ofVariantId(MASTER_VARIANT_ID, colorAttribute, newValueForColor);

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, asList(moneyUpdate, localizedEnumUpdate)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).contains(newValueForMoney);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(colorAttribute)).contains(newValueForColor);

            final SetAttribute unsetAction = SetAttribute.ofUnsetAttributeForVariantId(MASTER_VARIANT_ID, moneyAttribute);
            final Product productWithoutMoney = client().executeBlocking(ProductUpdateCommand.of(updatedProduct, unsetAction));

            assertThat(productWithoutMoney.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).isEmpty();

            return productWithoutMoney;
        });
    }

    @Test
    public void setAttributeBySku() throws Exception {
        withUpdateableProduct(client(), product -> {
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();

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

            final SetAttribute moneyUpdate = SetAttribute.ofSku(sku, moneyAttribute, newValueForMoney);
            final SetAttribute localizedEnumUpdate = SetAttribute.ofSku(sku, colorAttribute, newValueForColor);

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, asList(moneyUpdate, localizedEnumUpdate)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).contains(newValueForMoney);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(colorAttribute)).contains(newValueForColor);

            final SetAttribute unsetAction = SetAttribute.ofUnsetAttributeForSku(sku, moneyAttribute);
            final Product productWithoutMoney = client().executeBlocking(ProductUpdateCommand.of(updatedProduct, unsetAction));

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

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, asList(moneyUpdate, localizedEnumUpdate)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).contains(newValueForMoney);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(colorAttribute)).contains(newValueForColor);

            final SetAttributeInAllVariants unsetAction = SetAttributeInAllVariants.ofUnsetAttribute(moneyAttribute);
            final Product productWithoutMoney = client().executeBlocking(ProductUpdateCommand.of(updatedProduct, unsetAction));

            assertThat(productWithoutMoney.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).isEmpty();

            return productWithoutMoney;
        });
    }

    @Test
    public void revertStagedChanges() throws Exception {
        withUpdateableProduct(client(), product -> {
            //changing only staged and not current
            final LocalizedString oldDescriptionOption = product.getMasterData().getStaged().getDescription();
            final LocalizedString newDescription = LocalizedString.ofEnglish("new description " + RANDOM.nextInt());
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, asList(Publish.of(), SetDescription.of(newDescription)));
            final Product updatedProduct = client().executeBlocking(cmd);
            assertThat(oldDescriptionOption).isNotEqualTo(newDescription);
            assertThat(updatedProduct.getMasterData().getStaged().getDescription()).isEqualTo(newDescription);
            assertThat(updatedProduct.getMasterData().getCurrent().getDescription()).isEqualTo(oldDescriptionOption);

            final Product revertedProduct = client().executeBlocking(ProductUpdateCommand.of(updatedProduct, RevertStagedChanges.of()));
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
                final Product updatedProduct = client().executeBlocking(command);
                assertThat(updatedProduct.getTaxCategory()).isEqualTo(taxCategory.toReference());
                return updatedProduct;
            })
        );
    }

    @Test
    public void setSearchKeywords() throws Exception {
        withUpdateableProduct(client(), product -> {
            final SearchKeywords searchKeywords = SearchKeywords.of(Locale.ENGLISH, asList(SearchKeyword.of("Raider", CustomSuggestTokenizer.of(singletonList("Twix")))));
            final ProductUpdateCommand command = ProductUpdateCommand.of(product, SetSearchKeywords.of(searchKeywords));
            final Product updatedProduct = client().executeBlocking(command);

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

        final NamedAttributeAccess<EnumValue> sizeAttribute = Sizes.ATTRIBUTE;
        final AttributeDraft sizeValue = AttributeDraft.of(sizeAttribute, Sizes.M);


        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().getStaged().getVariants()).isEmpty();

            final PriceDraft price = PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
            final List<PriceDraft> prices = asList(price);
            final List<AttributeDraft> attributeValues = asList(moneyAttributeValue, colorAttributeValue, sizeValue);
            final ProductUpdateCommand addVariantCommand =
                    ProductUpdateCommand.of(product, AddVariant.of(attributeValues, prices, randomKey()));

            final Product productWithVariant = client().executeBlocking(addVariantCommand);
            final ProductVariant variant = productWithVariant.getMasterData().getStaged().getVariants().get(0);
            assertThat(variant.getId()).isEqualTo(2);
            assertThat(variant.findAttribute(moneyAttribute).get()).isEqualTo(EURO_10);
            assertThat(variant.findAttribute(colorAttribute).get()).isEqualTo(color);
            assertThat(variant.findAttribute(sizeAttribute).get()).isEqualTo(Sizes.M);

            final Product productWithoutVariant = client().executeBlocking(ProductUpdateCommand.of(productWithVariant, RemoveVariant.of(variant)));
            assertThat(productWithoutVariant.getMasterData().getStaged().getVariants()).isEmpty();

            return productWithoutVariant;
        });
    }

    @Test
    public void removeVariantById() throws Exception {
        final NamedAttributeAccess<MonetaryAmount> moneyAttribute =
                AttributeAccess.ofMoney().ofName(MONEY_ATTRIBUTE_NAME);
        final AttributeDraft moneyAttributeValue = AttributeDraft.of(moneyAttribute, EURO_10);

        final NamedAttributeAccess<LocalizedEnumValue> colorAttribute = Colors.ATTRIBUTE;
        final LocalizedEnumValue color = Colors.RED;
        final AttributeDraft colorAttributeValue = AttributeDraft.of(colorAttribute, color);

        final NamedAttributeAccess<EnumValue> sizeAttribute = Sizes.ATTRIBUTE;
        final AttributeDraft sizeValue = AttributeDraft.of(sizeAttribute, Sizes.M);


        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().getStaged().getVariants()).isEmpty();

            final PriceDraft price = PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
            final List<PriceDraft> prices = asList(price);
            final List<AttributeDraft> attributeValues = asList(moneyAttributeValue, colorAttributeValue, sizeValue);
            final ProductUpdateCommand addVariantCommand =
                    ProductUpdateCommand.of(product, AddVariant.of(attributeValues, prices, randomKey()));

            final Product productWithVariant = client().executeBlocking(addVariantCommand);
            final ProductVariant variant = productWithVariant.getMasterData().getStaged().getVariants().get(0);

            final Product productWithoutVariant = client().executeBlocking(ProductUpdateCommand.of(productWithVariant, RemoveVariant.ofVariantId(variant.getId())));
            assertThat(productWithoutVariant.getMasterData().getStaged().getVariants()).isEmpty();

            return productWithoutVariant;
        });
    }

    @Test
    public void removeVariantBySku() throws Exception {
        final NamedAttributeAccess<MonetaryAmount> moneyAttribute =
                AttributeAccess.ofMoney().ofName(MONEY_ATTRIBUTE_NAME);
        final AttributeDraft moneyAttributeValue = AttributeDraft.of(moneyAttribute, EURO_10);

        final NamedAttributeAccess<LocalizedEnumValue> colorAttribute = Colors.ATTRIBUTE;
        final LocalizedEnumValue color = Colors.RED;
        final AttributeDraft colorAttributeValue = AttributeDraft.of(colorAttribute, color);

        final NamedAttributeAccess<EnumValue> sizeAttribute = Sizes.ATTRIBUTE;
        final AttributeDraft sizeValue = AttributeDraft.of(sizeAttribute, Sizes.M);


        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().getStaged().getVariants()).isEmpty();

            final PriceDraft price = PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
            final List<PriceDraft> prices = asList(price);
            final List<AttributeDraft> attributeValues = asList(moneyAttributeValue, colorAttributeValue, sizeValue);
            final ProductUpdateCommand addVariantCommand =
                    ProductUpdateCommand.of(product, AddVariant.of(attributeValues, prices, randomKey()));

            final Product productWithVariant = client().executeBlocking(addVariantCommand);
            final ProductVariant variant = productWithVariant.getMasterData().getStaged().getVariants().get(0);

            final Product productWithoutVariant = client().executeBlocking(ProductUpdateCommand.of(productWithVariant, RemoveVariant.ofVariantSku(variant.getSku())));
            assertThat(productWithoutVariant.getMasterData().getStaged().getVariants()).isEmpty();

            return productWithoutVariant;
        });
    }

    @Test
    public void changeMasterVariantWithVariantId() {
        withUpdateableProductProjectionOfMultipleVariants(client(), (ProductProjection productProjection) -> {
            //given
            final String sizeAttributeName = Sizes.ATTRIBUTE.getName();
            final List<EnumValue> allVariantsSizeValues = productProjection.getAllVariants()
                    .stream()
                    .map(variant -> variant.getAttribute(sizeAttributeName).getValueAsEnumValue())
                    .collect(Collectors.toList());
            assertThat(allVariantsSizeValues)
                    .as("master variant is of size M")
                    .containsExactly(Sizes.M, Sizes.S, Sizes.X);

            //when
            final Integer variantIdOfSizeX = productProjection.getAllVariants().get(2).getId();
            final ChangeMasterVariant updateAction =
                    ChangeMasterVariant.ofVariantId(variantIdOfSizeX);
            client().executeBlocking(ProductUpdateCommand.of(productProjection, updateAction));

            //then
            final ProductProjection productProjectionWithNewMasterVariant =
                    client().executeBlocking(ProductProjectionByIdGet.of(productProjection, STAGED));
            final ProductVariant newMasterVariant = productProjectionWithNewMasterVariant.getMasterVariant();
            assertThat(newMasterVariant)
                    .as("third variant is now the master variant")
                    .isEqualTo(productProjection.getAllVariants().get(2));
            assertThat(newMasterVariant.getAttribute(sizeAttributeName).getValueAsEnumValue())
            .isEqualTo(Sizes.X);
            final List<EnumValue> reorderedVariantsSizeValues = productProjectionWithNewMasterVariant.getAllVariants()
                    .stream()
                    .map(variant -> variant.getAttribute(sizeAttributeName).getValueAsEnumValue())
                    .collect(Collectors.toList());
            assertThat(reorderedVariantsSizeValues).containsExactly(Sizes.X, Sizes.S, Sizes.M);

            return productProjectionWithNewMasterVariant;
        });
    }

    @Test
    public void changeMasterVariantWithSku() {
        withUpdateableProductProjectionOfMultipleVariants(client(), (ProductProjection productProjection) -> {
            final int originalMasterVariantId = productProjection.getMasterVariant().getId();
            final ProductVariant variantSupposedToBeMaster = productProjection.getAllVariants().get(2);
            final String sku = variantSupposedToBeMaster.getSku();

            final ChangeMasterVariant updateAction = ChangeMasterVariant.ofSku(sku);
            client().executeBlocking(ProductUpdateCommand.of(productProjection, updateAction));

            final ProductProjection productProjectionWithNewMasterVariant =
                    client().executeBlocking(ProductProjectionByIdGet.of(productProjection, STAGED));
            assertThat(productProjectionWithNewMasterVariant.getMasterVariant().getSku()).isEqualTo(sku);
            assertThat(productProjectionWithNewMasterVariant.getMasterVariant().getId())
                    .as("variant IDs don't change in reordering")
                    .isNotEqualTo(originalMasterVariantId)
                    .isEqualTo(variantSupposedToBeMaster.getId());

            return productProjectionWithNewMasterVariant;
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
             final LocalizedString newName = randomSlug();
             final UpdateAction<Product> stagedWrapper = new StagedWrapper(ChangeName.of(newName), false);
             final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, asList(Publish.of(), stagedWrapper)));

             final Product fetchedProduct = client().executeBlocking(ProductByIdGet.of(product));
             assertThat(fetchedProduct.getMasterData().getCurrent().getName())
                     .isEqualTo(fetchedProduct.getMasterData().getStaged().getName())
                     .isEqualTo(newName);

             return updatedProduct;
         });
    }

    @Test
    public void transitionState() {
        withStateByBuilder(client(), builder -> builder.type(PRODUCT_STATE),  state -> {
            withUpdateableProduct(client(), product -> {
                assertThat(product.getState()).isNull();

                final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, asList(TransitionState.of(state))));

                assertThat(updatedProduct.getState()).isEqualTo(state.toReference());

                assertEventually(() -> {
                    final PagedQueryResult<ProductStateTransitionMessage> messageQueryResult =
                            client().executeBlocking(MessageQuery.of()
                                    .withPredicates(m -> m.resource().is(product))
                                    .forMessageType(ProductStateTransitionMessage.MESSAGE_HINT));

                    assertThat(messageQueryResult.getResults()).isNotEmpty();
                    final ProductStateTransitionMessage message = messageQueryResult.head().get();
                    assertThat(message.getState()).isEqualTo(state.toReference());
                });

                //check query model
                final ProductQuery query = ProductQuery.of()
                        .withPredicates(m -> m.id().is(product.getId()).and(m.state().is(state)));
                final Product productByState = client().executeBlocking(query)
                        .head().get();
                assertThat(productByState).isEqualTo(updatedProduct);

                return updatedProduct;
            });
        });
    }

    @Test
    public void transitionStateAndForce() {
        Set<Reference<State>> noTransitions = emptySet();
        withStateByBuilder(client(), builder -> builder.type(PRODUCT_STATE).transitions(noTransitions), stateA -> {
            withStateByBuilder(client(), builder -> builder.type(PRODUCT_STATE), stateB -> {
                withUpdateableProduct(client(), product -> {
                    assertThat(product.getState()).isNull();

                    final Product productInStateA = client().executeBlocking(ProductUpdateCommand.of(product, TransitionState.of(stateA)));

                    //no force usage
                    assertThatThrownBy(() -> client().executeBlocking(ProductUpdateCommand.of(productInStateA, TransitionState.of(stateB))))
                            .hasMessageContaining("InvalidOperation");

                    final ProductUpdateCommand cmd = ProductUpdateCommand.of(productInStateA, TransitionState.of(stateB, true));
                    final Product productInStateB = client().executeBlocking(cmd);

                    assertThat(productInStateB.getState()).isEqualTo(stateB.toReference());

                    return productInStateA;
                });
            });
        });
    }

    @Test
    public void setProductPriceCustomTypeAndsetProductPriceCustomField() {
        withUpdateableType(client(), type -> {
            withUpdateablePricedProduct(client(), product -> {
                final String priceId = getFirstPrice(product).getId();
                final UpdateAction<Product> updateAction = SetProductPriceCustomType.
                        ofTypeIdAndObjects(type.getId(), STRING_FIELD_NAME, "a value", priceId);
                final ProductUpdateCommand productUpdateCommand = ProductUpdateCommand.of(product, updateAction);
                final Product updatedProduct = client().executeBlocking(productUpdateCommand);

                final Price price = getFirstPrice(updatedProduct);
                assertThat(price.getCustom().getFieldAsString(STRING_FIELD_NAME))
                        .isEqualTo("a value");

                final Product updated2 = client().executeBlocking(ProductUpdateCommand.of(updatedProduct, SetProductPriceCustomField.ofObject(STRING_FIELD_NAME, "a new value", priceId)));
                assertThat(getFirstPrice(updated2).getCustom().getFieldAsString(STRING_FIELD_NAME))
                        .isEqualTo("a new value");
                return updated2;
            });
            return type;
        });
    }

    @Test
    public void setSku() throws Exception {
        final String oldSku = randomKey();
        withProductOfSku(oldSku, (Product product) -> {
            assertThat(product.getMasterData().getStaged().getMasterVariant().getSku()).isEqualTo(oldSku);
            assertThat(product.getMasterData().getCurrent().getMasterVariant().getSku()).isEqualTo(oldSku);

            final String newSku = randomKey();

            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, SetSku.of(MASTER_VARIANT_ID, newSku));
            final Product updatedProduct = client().executeBlocking(cmd);

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getSku())
                    .as("update action updates SKU in staged")
                    .isEqualTo(newSku);
            assertThat(updatedProduct.getMasterData().getCurrent().getMasterVariant().getSku())
                    .as("update action updates NOT directly in current")
                    .isEqualTo(oldSku)
                    .isNotEqualTo(newSku);
            return updatedProduct;
        });
    }

    private void withProductOfSku(final String sku, final Function<Product, Product> productProductFunction) {
        withUpdateableProduct(client(), builder -> {
            return builder.masterVariant(ProductVariantDraftBuilder.of(builder.getMasterVariant()).sku(sku).build());
        }, product -> {
            final ProductUpdateCommand productUpdateCommand = ProductUpdateCommand.of(product, Publish.of());
            final Product publishedProduct = client().executeBlocking(productUpdateCommand);
            return productProductFunction.apply(publishedProduct);
        });
    }

    private Price getFirstPrice(final Product product) {
        return product.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
    }

    private static void withUpdateableProductProjectionOfMultipleVariants(final BlockingSphereClient client,
                                                                          final Function<ProductProjection, Versioned<Product>> f) {
        withUpdateableProductOfMultipleVariants(client(), product -> {
            //given
            final ProductProjection productProjection =
                    client().executeBlocking(ProductProjectionByIdGet.of(product, STAGED));
            return f.apply(productProjection);
        });
    }

    private void testAddPrice(final PriceDraft expectedPrice) throws Exception {
        withUpdateableProduct(client(), product -> {
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, AddPrice.of(1, expectedPrice)));


            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            assertThat(prices).hasSize(1);
            final Price actualPrice = prices.get(0);

            assertThat(expectedPrice).isEqualTo(PriceDraft.of(actualPrice));

            return updatedProduct;
        });
    }

    private void withProductWithImages(final BlockingSphereClient client, final String url1, final String url2, final String url3, final Function<Product, Product> productProductFunction) {
        withUpdateableProduct(client, builder -> {
            List<Image> imagesList = new LinkedList<>();
            imagesList.add(Image.ofWidthAndHeight(url1, 460, 102, "commercetools logo"));
            imagesList.add(Image.ofWidthAndHeight(url2, 460, 102, "commercetools logo"));
            imagesList.add(Image.ofWidthAndHeight(url3, 460, 102, "commercetools logo"));
            final ProductVariantDraft oldMasterVariant = builder.getMasterVariant();
            final ProductVariantDraftBuilder variantDraftBuilder = ProductVariantDraftBuilder.of(oldMasterVariant);
            variantDraftBuilder.images(imagesList);
            variantDraftBuilder.sku(randomKey());
            return builder.masterVariant(variantDraftBuilder.build());
        }, productProductFunction);
    }

}