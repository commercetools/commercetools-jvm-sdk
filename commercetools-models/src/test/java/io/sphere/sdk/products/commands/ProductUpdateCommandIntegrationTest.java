package io.sphere.sdk.products.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.*;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.productdiscounts.ExternalProductDiscountValue;
import io.sphere.sdk.productdiscounts.ProductDiscountDraft;
import io.sphere.sdk.productdiscounts.ProductDiscountPredicate;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.AttributeAccess;
import io.sphere.sdk.products.attributes.AttributeDraft;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;
import io.sphere.sdk.products.commands.updateactions.*;
import io.sphere.sdk.products.messages.ProductPriceExternalDiscountSetMessage;
import io.sphere.sdk.products.messages.ProductSlugChangedMessage;
import io.sphere.sdk.products.messages.ProductStateTransitionMessage;
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
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.utils.HighPrecisionMoneyImpl;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.annotation.Nonnull;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.productdiscounts.ProductDiscountFixtures.withProductDiscount;
import static io.sphere.sdk.products.ProductFixtures.*;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static io.sphere.sdk.states.StateFixtures.withStateByBuilder;
import static io.sphere.sdk.states.StateType.PRODUCT_STATE;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.MONEY_ATTRIBUTE_NAME;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.*;
import static java.util.Collections.*;
import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.*;

public class ProductUpdateCommandIntegrationTest extends IntegrationTest {
    public static final Random RANDOM = new Random();
    private static final String URL_1 = "http://www.commercetools.com/ct_logo_farbe_1.gif";
    private static final String URL_2 = "http://www.commercetools.com/ct_logo_farbe_2.gif";
    private static final String URL_3 = "http://www.commercetools.com/ct_logo_farbe_3.gif";
    private static final List<String> IMAGE_URLS = Arrays.asList(URL_1, URL_2, URL_3);

    @Test
    public void updateCommandPlusUpdateActions() {
        withUpdateableProduct(client(), (Product product) -> {
            assertThat(product.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);

            final Image image = createExternalImage();
            final AddExternalImage updateAction1 = AddExternalImage.of(image, MASTER_VARIANT_ID);
            final ProductUpdateCommand command = ProductUpdateCommand.of(product, updateAction1);
            assertThat(command.getUpdateActions()).hasSize(1);
            assertThat(command.getUpdateActions().get(0)).isEqualTo(updateAction1);

            final LocalizedString localizedName = en("New Name");
            final ChangeName updateAction2 = ChangeName.of(localizedName);
            final ProductUpdateCommand updatedCommand = command.plusUpdateActions(asList(updateAction2));
            assertThat(updatedCommand.getUpdateActions()).hasSize(2);
            assertThat(updatedCommand.getUpdateActions().get(1)).isEqualTo(updateAction2);

            final Product updatedProduct = client().executeBlocking(updatedCommand);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));
            assertThat(updatedProduct.getMasterData().getStaged().getName()).isEqualTo(localizedName);
            return updatedProduct;
        });
    }

    @Test
    public void updateWithProjection() {
        withUpdateableProduct(client(), (Product product) -> {
            final ProductProjection p = client().executeBlocking(ProductProjectionByIdGet.ofStaged(product));

            final LocalizedString localizedName = en("New staged name");
            final ChangeName updateAction = ChangeName.of(localizedName);
            final ProductUpdateCommand command = ProductUpdateCommand.of(p, updateAction);
            assertThat(command.getUpdateActions()).hasSize(1);
            assertThat(command.getUpdateActions().get(0)).isEqualTo(updateAction);

            final Product updatedProduct = client().executeBlocking(command);
            assertThat(updatedProduct.getMasterData().getStaged().getName()).isEqualTo(localizedName);
            return updatedProduct;
        });
    }

    @Test
    public void moveImageToPositionByVariantId() throws Exception {
        withProductWithImages(client(), IMAGE_URLS, (Product product) -> {
            final List<String> oldImageOrderUrls = product.getMasterData().getStaged().getMasterVariant().getImages()
                    .stream()
                    .map(image -> image.getUrl())
                    .collect(toList());
            assertThat(oldImageOrderUrls).containsExactlyElementsOf(IMAGE_URLS);
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final Integer position = 0;
            final List<Image> images = product.getMasterData().getStaged().getMasterVariant().getImages();
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, MoveImageToPosition.ofImageUrlAndVariantId(images.get(1).getUrl(), MASTER_VARIANT_ID, position));

            final Product updatedProduct = client().executeBlocking(cmd);

            final List<String> urls = updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()
                    .stream()
                    .map(image -> image.getUrl())
                    .collect(toList());
            assertThat(urls).containsExactly(URL_2, URL_1, URL_3);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void moveImageToPositionByVariantIdWithStaged() {
        moveImageToPositionByVariantIdWithStaged(true);
        moveImageToPositionByVariantIdWithStaged(false);
    }

    public void moveImageToPositionByVariantIdWithStaged(final Boolean staged) {
        withProductWithImages(client(), IMAGE_URLS, (Product product) -> {
            final List<String> oldImageOrderUrls = product.getMasterData().getStaged().getMasterVariant().getImages()
                    .stream()
                    .map(image -> image.getUrl())
                    .collect(toList());
            assertThat(oldImageOrderUrls).containsExactlyElementsOf(IMAGE_URLS);
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final Integer position = 0;
            final List<Image> images = product.getMasterData().getStaged().getMasterVariant().getImages();
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, MoveImageToPosition.ofImageUrlAndVariantId(images.get(1).getUrl(), MASTER_VARIANT_ID, position, staged));

            final Product updatedProduct = client().executeBlocking(cmd);

            final List<String> urls = updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()
                    .stream()
                    .map(image -> image.getUrl())
                    .collect(toList());
            assertThat(urls).containsExactly(URL_2, URL_1, URL_3);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void moveImageToPositionBySku() throws Exception {
        withProductWithImages(client(), IMAGE_URLS, (Product product) -> {
            final List<String> oldImageOrderUrls = product.getMasterData().getStaged().getMasterVariant().getImages()
                    .stream()
                    .map(image -> image.getUrl())
                    .collect(toList());
            assertThat(oldImageOrderUrls).containsExactlyElementsOf(IMAGE_URLS);
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final Integer position = 0;
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final List<Image> images = masterVariant.getImages();
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, MoveImageToPosition.ofImageUrlAndSku(images.get(1).getUrl(), masterVariant.getSku(), position));

            final Product updatedProduct = client().executeBlocking(cmd);

            final List<String> urls = updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()
                    .stream()
                    .map(image -> image.getUrl())
                    .collect(toList());
            assertThat(urls).containsExactly(URL_2, URL_1, URL_3);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void setImageLabel() throws Exception {
        withProductWithImages(client(), Arrays.asList(URL_1), (Product product) -> {
            final String changeToImageLabel = "New image label";
            final ProductUpdateCommand updateCommand = ProductUpdateCommand.of(product, SetImageLabel.of(1, URL_1, changeToImageLabel, null));

            final Product updatedProduct = client().executeBlocking(updateCommand);

            final Optional<Image> changedImage = updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()
                    .stream()
                    .findFirst();

            assertThat(changedImage).isPresent();
            final String updatedImageLabel = changedImage.get().getLabel();
            assertThat(updatedImageLabel).isEqualTo(changeToImageLabel);

            return updatedProduct;
        });
    }

    @Test
    public void moveImageToPositionBySkuWithStaged() {
        moveImageToPositionBySkuWithStaged(true);
        moveImageToPositionBySkuWithStaged(false);
    }

    public void moveImageToPositionBySkuWithStaged(final Boolean staged) {
        withProductWithImages(client(), IMAGE_URLS, (Product product) -> {
            final List<String> oldImageOrderUrls = product.getMasterData().getStaged().getMasterVariant().getImages()
                    .stream()
                    .map(image -> image.getUrl())
                    .collect(toList());
            assertThat(oldImageOrderUrls).containsExactlyElementsOf(IMAGE_URLS);
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final Integer position = 0;
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final List<Image> images = masterVariant.getImages();
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, MoveImageToPosition.ofImageUrlAndSku(images.get(1).getUrl(), masterVariant.getSku(), position, staged));

            final Product updatedProduct = client().executeBlocking(cmd);

            final List<String> urls = updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()
                    .stream()
                    .map(image -> image.getUrl())
                    .collect(toList());
            assertThat(urls).containsExactly(URL_2, URL_1, URL_3);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void publishWithPriceScope() throws Exception {
        withUpdateableProduct(client(), (Product product) -> {
            final ProductData stagedData = product.getMasterData().getStaged();
            final Product publishedProduct = client().executeBlocking(ProductUpdateCommand.of(product, Publish.of()));
            final ProductData currentData = publishedProduct.getMasterData().getCurrent();
            assertThat(stagedData.getMasterVariant().getImages()).hasSize(0);
            assertThat(stagedData.getMasterVariant().getPrices()).hasSize(0);
            assertThat(currentData).isEqualTo(stagedData);

            final PriceDraft expectedPrice = PriceDraft.of(EURO_10);
            final Image image = createExternalImage();
            final List<UpdateAction<Product>> updateActions = asList(
                    AddExternalImage.of(image, MASTER_VARIANT_ID),
                    AddPrice.of(MASTER_VARIANT_ID, expectedPrice),
                    Publish.ofScope(PublishScope.PRICES)
            );
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(publishedProduct, updateActions));
            List<Price> prices = updatedProduct.getMasterData().getCurrent().getMasterVariant().getPrices();

            //Verify published price in the current product
            assertThat(prices).hasSize(1);
            assertThat(expectedPrice).isEqualTo(PriceDraft.of(prices.get(0)));

            //Verify that the image has not been published
            assertThat(updatedProduct.getMasterData().getCurrent().getMasterVariant().getImages()).hasSize(0);

            return updatedProduct;
        });
    }

    @Test
    public void publishWithAllScope() throws Exception {
        withUpdateableProduct(client(), (Product product) -> {
            final ProductData stagedData = product.getMasterData().getStaged();
            final Product publishedProduct = client().executeBlocking(ProductUpdateCommand.of(product, Publish.of()));
            final ProductData currentData = publishedProduct.getMasterData().getCurrent();
            assertThat(stagedData.getMasterVariant().getImages()).hasSize(0);
            assertThat(stagedData.getMasterVariant().getPrices()).hasSize(0);
            assertThat(currentData).isEqualTo(stagedData);

            final PriceDraft expectedPrice = PriceDraft.of(EURO_10);
            final Image image = createExternalImage();
            final List<UpdateAction<Product>> updateActions = asList(
                    AddExternalImage.of(image, MASTER_VARIANT_ID),
                    AddPrice.of(MASTER_VARIANT_ID, expectedPrice),
                    Publish.ofScope(PublishScope.ALL)
            );
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(publishedProduct, updateActions));
            List<Price> prices = updatedProduct.getMasterData().getCurrent().getMasterVariant().getPrices();

            assertThat(prices).hasSize(1);
            assertThat(expectedPrice).isEqualTo(PriceDraft.of(prices.get(0)));
            assertThat(updatedProduct.getMasterData().getCurrent().getMasterVariant().getImages()).isEqualTo(asList(image));

            return updatedProduct;
        });
    }

    @Test
    public void addExternalImage() throws Exception {
        withUpdateableProduct(client(), (Product product) -> {
            assertThat(product.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);

            final Image image = createExternalImage();
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.of(image, MASTER_VARIANT_ID)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));
            return updatedProduct;
        });
    }

    @Test
    public void addExternalImageByVariantId() throws Exception {
        withUpdateableProduct(client(), (Product product) -> {
            assertThat(product.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final Image image = createExternalImage();
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.ofVariantId(MASTER_VARIANT_ID, image)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();
            return updatedProduct;
        });
    }

    @Test
    public void addExternalImageByVariantIdWithStaged() {
        addExternalImageByVariantIdWithStaged(true);
        addExternalImageByVariantIdWithStaged(false);
    }

    public void addExternalImageByVariantIdWithStaged(final Boolean staged) {
        withUpdateableProduct(client(), (Product product) -> {
            assertThat(product.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final Image image = createExternalImage();
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.ofVariantId(MASTER_VARIANT_ID, image, staged)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
            return updatedProduct;
        });
    }

    @Test
    public void addExternalImageBySku() throws Exception {
        withUpdateableProduct(client(), (Product product) -> {
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            assertThat(masterVariant.getImages()).hasSize(0);
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final Image image = createExternalImage();
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.ofSku(masterVariant.getSku(), image)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();
            return updatedProduct;
        });
    }

    @Test
    public void addExternalImageBySkuWithStaged() {
        addExternalImageBySkuWithStaged(true);
        addExternalImageBySkuWithStaged(false);
    }

    public void addExternalImageBySkuWithStaged(final Boolean staged) {
        withUpdateableProduct(client(), (Product product) -> {
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            assertThat(masterVariant.getImages()).hasSize(0);
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final Image image = createExternalImage();
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.ofSku(masterVariant.getSku(), image, staged)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
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
    public void addPriceWithTiers() throws Exception {
        final List<PriceTier> tiers = Arrays.asList(PriceTierBuilder.of(10, EURO_1).build(), PriceTierBuilder.of(5, EURO_5).build());
        final PriceDraft expectedPriceWithTiers = PriceDraftBuilder.of(MoneyImpl.of(123, EUR))
                .tiers(tiers)
                .build();
        withUpdateableProduct(client(), product -> {
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, AddPrice.of(1, expectedPriceWithTiers)));


            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            assertThat(prices).hasSize(1);
            final Price actualPrice = prices.get(0);

            assertThat(expectedPriceWithTiers).isEqualTo(PriceDraft.of(actualPrice));

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
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, AddPrice.ofVariantId(1, priceDraft)));

            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            assertThat(prices).hasSize(1);
            final Price actualPrice = prices.get(0);

            assertThat(priceDraft).isEqualTo(PriceDraft.of(actualPrice));
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void addPriceByVariantIdWithStaged() {
        addPriceByVariantIdWithStaged(true);
        addPriceByVariantIdWithStaged(false);
    }

    public void addPriceByVariantIdWithStaged(final Boolean staged) {
        final PriceDraft priceDraft = PriceDraft.of(MoneyImpl.of(new BigDecimal("12345"), "JPY"));
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, AddPrice.ofVariantId(1, priceDraft, staged)));

            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            assertThat(prices).hasSize(1);
            final Price actualPrice = prices.get(0);

            assertThat(priceDraft).isEqualTo(PriceDraft.of(actualPrice));
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void addPriceBySku() throws Exception {
        final PriceDraft priceDraft = PriceDraft.of(MoneyImpl.of(new BigDecimal("12345"), "JPY"));
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, AddPrice.ofSku(sku, priceDraft)));

            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            assertThat(prices).hasSize(1);
            final Price actualPrice = prices.get(0);

            assertThat(priceDraft).isEqualTo(PriceDraft.of(actualPrice));
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void addPriceBySkuWithStaged() {
        addPriceBySkuWithStaged(true);
        addPriceBySkuWithStaged(false);
    }

    public void addPriceBySkuWithStaged(final Boolean staged) {
        final PriceDraft priceDraft = PriceDraft.of(MoneyImpl.of(new BigDecimal("12345"), "JPY"));
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, AddPrice.ofSku(sku, priceDraft, staged)));

            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            assertThat(prices).hasSize(1);
            final Price actualPrice = prices.get(0);

            assertThat(priceDraft).isEqualTo(PriceDraft.of(actualPrice));
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void setPrices() throws Exception {
        final PriceDraft expectedPrice1 = PriceDraft.of(MoneyImpl.of(123, EUR));
        final PriceDraft expectedPrice2 = PriceDraft.of(MoneyImpl.of(123, EUR)).withCountry(DE);
        final List<PriceTier> tiers = Arrays.asList(PriceTierBuilder.of(10, EURO_1).build());
        final PriceDraft expectedPriceWithTiers = PriceDraftBuilder.of(MoneyImpl.of(123, EUR))
                .country(CountryCode.AT)
                .tiers(tiers)
                .build();
        final List<PriceDraft> expectedPriceList = asList(expectedPrice1, expectedPrice2, expectedPriceWithTiers);

        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, SetPrices.of(1, expectedPriceList)));

            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            List<PriceDraft> draftPricesList = prices.stream().map(PriceDraft::of).collect(toList());
            assertThat(draftPricesList).containsOnly(expectedPrice1, expectedPrice2, expectedPriceWithTiers);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void setPricesByVariantId() throws Exception {
        final PriceDraft priceDraft = PriceDraft.of(MoneyImpl.of(123, EUR));
        final PriceDraft priceDraft2 = PriceDraft.of(MoneyImpl.of(123, EUR)).withCountry(DE);
        final List<PriceDraft> expectedPriceList = asList(priceDraft, priceDraft2);

        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, SetPrices.ofVariantId(1, expectedPriceList)));

            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            List<PriceDraft> draftPricesList = prices.stream().map(PriceDraft::of).collect(toList());
            assertThat(draftPricesList).containsOnly(priceDraft, priceDraft2);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void setPricesByVariantIdWithStaged() {
        setPricesByVariantIdWithStaged(true);
        setPricesByVariantIdWithStaged(false);
    }

    private void setPricesByVariantIdWithStaged(final Boolean staged) {
        final PriceDraft priceDraft = PriceDraft.of(MoneyImpl.of(123, EUR));
        final PriceDraft priceDraft2 = PriceDraft.of(MoneyImpl.of(123, EUR)).withCountry(DE);
        final List<PriceDraft> expectedPriceList = asList(priceDraft, priceDraft2);
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, SetPrices.ofVariantId(1, expectedPriceList, staged)));
            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            List<PriceDraft> draftPricesList = prices.stream().map(PriceDraft::of).collect(toList());
            assertThat(draftPricesList).containsOnly(priceDraft, priceDraft2);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
            return updatedProduct;
        });
    }

    @Test
    public void setPricesBySku() throws Exception {
        final PriceDraft priceDraft = PriceDraft.of(MoneyImpl.of(123, EUR));
        final PriceDraft priceDraft2 = PriceDraft.of(MoneyImpl.of(123, EUR)).withCountry(DE);
        final List<PriceDraft> expectedPriceList = asList(priceDraft, priceDraft2);

        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, SetPrices.ofSku(sku, expectedPriceList)));

            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            List<PriceDraft> draftPricesList = prices.stream().map(PriceDraft::of).collect(toList());
            assertThat(draftPricesList).containsOnly(priceDraft, priceDraft2);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void setPricesBySkuWithStaged() {
        setPricesBySkuWithStaged(true);
        setPricesBySkuWithStaged(false);
    }

    public void setPricesBySkuWithStaged(final Boolean staged) {
        final PriceDraft priceDraft = PriceDraft.of(MoneyImpl.of(123, EUR));
        final PriceDraft priceDraft2 = PriceDraft.of(MoneyImpl.of(123, EUR)).withCountry(DE);
        final List<PriceDraft> expectedPriceList = asList(priceDraft, priceDraft2);

        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, SetPrices.ofSku(sku, expectedPriceList, staged)));

            final List<Price> prices = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices();
            List<PriceDraft> draftPricesList = prices.stream().map(PriceDraft::of).collect(toList());
            assertThat(draftPricesList).containsOnly(priceDraft, priceDraft2);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

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
    public void addToCategoryWithStaged() {
        addToCategoryWithStaged(true);
        addToCategoryWithStaged(false);
    }

    public void addToCategoryWithStaged(final Boolean staged) {
        withCategory(client(), category -> {
            withUpdateableProduct(client(), product -> {
                assertThat(product.getMasterData().getStaged().getCategories()).isEmpty();
                assertThat(product.getMasterData().hasStagedChanges()).isFalse();

                final String orderHint = "0.123";
                final Product productWithCategory = client()
                        .executeBlocking(ProductUpdateCommand.of(product, AddToCategory.of(category, orderHint, staged)));
                assertThat(productWithCategory.getMasterData().hasStagedChanges()).isEqualTo(staged);

                final Product publishedProductWithCategory = client().executeBlocking(ProductUpdateCommand.of(productWithCategory, Publish.of()));
                final Product productWithoutCategory = client()
                        .executeBlocking(ProductUpdateCommand.of(publishedProductWithCategory, RemoveFromCategory.of(category, staged)));
                assertThat(productWithoutCategory.getMasterData().hasStagedChanges()).isEqualTo(staged);
                assertThat(productWithoutCategory.getMasterData().getStaged().getCategories()).isEmpty();

                return productWithoutCategory;
            });
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
    public void setCategoryOrderHintWithStaged() {
        setCategoryOrderHintWithStaged(true);
        setCategoryOrderHintWithStaged(false);
    }

    public void setCategoryOrderHintWithStaged(final Boolean staged) {
        withCategory(client(), category -> {
            withUpdateableProduct(client(), product -> {
                assertThat(product.getMasterData().getStaged().getCategories()).isEmpty();

                final String orderHint = "0.123";
                final Product productWithCategory = client()
                        .executeBlocking(ProductUpdateCommand.of(product, AddToCategory.of(category, orderHint, false)));
                assertThat(productWithCategory.getMasterData().hasStagedChanges()).isFalse();

                final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(productWithCategory, SetCategoryOrderHint.of(category.getId(), "0.1234", staged)));

                final CategoryOrderHints actual = updatedProduct.getMasterData().getStaged().getCategoryOrderHints();
                assertThat(actual).isEqualTo(CategoryOrderHints.of(category.getId(), "0.1234"));
                assertThat(actual.getAsMap()).isEqualTo(Collections.singletonMap(category.getId(), "0.1234"));

                assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
                return productWithCategory;
            });
        });
    }

    @Test
    public void productUpdateByKey() throws Exception {
        final String key = randomKey();
        withUpdateableProduct(client(), builder -> builder.key(key), product -> {
            final LocalizedString newName = LocalizedString.ofEnglish("newName " + RANDOM.nextInt());
            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.ofKey(key, product.getVersion(), ChangeName.of(newName));
            final Product updatedProduct = client().executeBlocking(cmd);

            assertThat(updatedProduct.getMasterData().getStaged().getName()).isEqualTo(newName);
            assertThat(updatedProduct.getKey()).isEqualTo(key);
            return updatedProduct;
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
    public void changeNameWithStaged() {
        changeNameWithStaged(true);
        changeNameWithStaged(false);
    }

    public void changeNameWithStaged(final Boolean staged) {
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final LocalizedString newName = LocalizedString.ofEnglish("newName " + RANDOM.nextInt());
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, ChangeName.of(newName, staged)));
            assertThat(updatedProduct.getMasterData().getStaged().getName()).isEqualTo(newName);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
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
    public void changePriceToAHigherPrecision() throws Exception {
        withUpdateablePricedProduct(client(), product -> {
            final PriceDraft newHighPrecisionPrice = PriceDraft.of(HighPrecisionMoneyImpl.of(new BigDecimal("15.3669996"),EUR,7));
            final List<Price> prices = product.getMasterData().getStaged().getMasterVariant()
                    .getPrices();
            assertThat(prices.stream().anyMatch(p -> p.equals(newHighPrecisionPrice))).isFalse();

            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(product, ChangePrice.of(prices.get(0), newHighPrecisionPrice)));

            final Price actualPrice = getFirstPrice(updatedProduct);

            assertThat(PriceDraft.of(actualPrice)).isEqualTo(newHighPrecisionPrice);
            return updatedProduct;
        });
    }

    @Test
    public void changePriceAddTiers() throws Exception {
        withUpdateablePricedProduct(client(), product -> {
            final List<PriceTier> tiers = Arrays.asList(PriceTierBuilder.of(10, EURO_5).build());
            final PriceDraft newPrice = PriceDraftBuilder.of(MoneyImpl.of(234, EUR))
                    .tiers(tiers)
                    .build();
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
    public void changePriceWithStaged() {
        changePriceWithStaged(true);
        changePriceWithStaged(false);
    }

    public void changePriceWithStaged(final Boolean staged) {
        withUpdateableProduct(client(), product -> {
            final ProductUpdateCommand command = ProductUpdateCommand.of(product, AddPrice.ofVariantId(1, PriceDraft.of(MoneyImpl.of(123, EUR)), false));
            final Product productWithPrice = client().executeBlocking(command);
            assertThat(productWithPrice.getMasterData().hasStagedChanges()).isFalse();

            final PriceDraft newPrice = PriceDraft.of(MoneyImpl.of(234, EUR));
            final List<Price> prices = productWithPrice.getMasterData().getStaged().getMasterVariant()
                    .getPrices();
            assertThat(prices.stream().anyMatch(p -> p.equals(newPrice))).isFalse();

            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(productWithPrice, ChangePrice.of(prices.get(0), newPrice, staged)));

            final Price actualPrice = getFirstPrice(updatedProduct);
            assertThat(PriceDraft.of(actualPrice)).isEqualTo(newPrice);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void changeSlug() throws Exception {
        withUpdateableProduct(client(), product -> {
            final LocalizedString oldSlug = product.getMasterData().getStaged().getSlug();
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
                assertThat(message.getOldSlug()).isEqualTo(oldSlug);
            });

            return updatedProduct;
        });
    }

    @Test
    public void changeSlugWithStaged() {
        changeSlugWithStaged(true);
        changeSlugWithStaged(false);
    }

    public void changeSlugWithStaged(final Boolean staged) {
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final LocalizedString newSlug = LocalizedString.ofEnglish("new-slug-" + RANDOM.nextInt());
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, ChangeSlug.of(newSlug, staged)));
            assertThat(updatedProduct.getMasterData().getStaged().getSlug()).isEqualTo(newSlug);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
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
        final Image image = createExternalImage();
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
        final Image image = createExternalImage();
        withUpdateableProduct(client(), product -> {
            final Product productWithImage = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.ofVariantId(MASTER_VARIANT_ID, image)));
            assertThat(productWithImage.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(productWithImage, RemoveImage.ofVariantId(MASTER_VARIANT_ID, image)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);
            return updatedProduct;
        });
    }

    @Test
    public void removeImageByVariantIdWithStaged() {
        removeImageByVariantIdWithStaged(true);
        removeImageByVariantIdWithStaged(false);
    }

    public void removeImageByVariantIdWithStaged(final Boolean staged) {
        final Image image = createExternalImage();
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final Product productWithImage = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.ofVariantId(MASTER_VARIANT_ID, image, staged)));
            assertThat(productWithImage.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));
            assertThat(productWithImage.getMasterData().hasStagedChanges()).isEqualTo(staged);

            final Product publishedProduct = client().executeBlocking(ProductUpdateCommand.of(productWithImage, Publish.of()));
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(publishedProduct, RemoveImage.ofVariantId(MASTER_VARIANT_ID, image, staged)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
            return updatedProduct;
        });
    }

    @Test
    public void removeImageBySku() throws Exception {
        final Image image = createExternalImage();
        withUpdateableProduct(client(), product -> {
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final Product productWithImage = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.ofSku(sku, image)));
            assertThat(productWithImage.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(productWithImage, RemoveImage.ofSku(sku, image)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);
            return updatedProduct;
        });
    }

    @Test
    public void removeImageBySkuWithStaged() {
        removeImageBySkuWithStaged(true);
        removeImageBySkuWithStaged(false);
    }

    public void removeImageBySkuWithStaged(final Boolean staged) {
        final Image image = createExternalImage();
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final Product productWithImage = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.ofSku(sku, image, staged)));
            assertThat(productWithImage.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(asList(image));
            assertThat(productWithImage.getMasterData().hasStagedChanges()).isEqualTo(staged);

            final Product publishedProduct = client().executeBlocking(ProductUpdateCommand.of(productWithImage, Publish.of()));
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(publishedProduct, RemoveImage.ofSku(sku, image, staged)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
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
    public void removePriceWithStaged() {
        removePriceWithStaged(true);
        removePriceWithStaged(false);
    }

    public void removePriceWithStaged(final Boolean staged) {
        withUpdateableProduct(client(), product -> {
            final ProductUpdateCommand command = ProductUpdateCommand.of(product, AddPrice.ofVariantId(1, PriceDraft.of(MoneyImpl.of(123, EUR)), false));
            final Product productWithPrice = client().executeBlocking(command);
            assertThat(productWithPrice.getMasterData().hasStagedChanges()).isFalse();

            final Price oldPrice = getFirstPrice(productWithPrice);

            final Product updatedProduct = client()
                    .executeBlocking(ProductUpdateCommand.of(productWithPrice, RemovePrice.of(oldPrice, staged)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant()
                    .getPrices().stream().anyMatch(p -> p.equals(oldPrice))).isFalse();
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

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
    public void setDescriptionWithStaged() {
        setDescriptionWithStaged(true);
        setDescriptionWithStaged(false);
    }

    public void setDescriptionWithStaged(final Boolean staged) {
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final LocalizedString newDescription = LocalizedString.ofEnglish("new description " + RANDOM.nextInt());
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, SetDescription.of(newDescription, staged));
            final Product updatedProduct = client().executeBlocking(cmd);
            assertThat(updatedProduct.getMasterData().getStaged().getDescription()).isEqualTo(newDescription);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
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
                    .of(ENGLISH, "commercetools&#8482; is the first Platform-as-a-Service solution for eCommerce.");
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
                    .of(ENGLISH, "commercetools&#8482; - Next generation eCommerce");
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
                    "commercetools&#8482; - Next generation eCommerce",
                    "commercetools&#8482; is the first and leading Platform-as-a-Service solution for eCommerce.",
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
                    "commercetools&#8482; - Next generation eCommerce",
                    "commercetools&#8482; is the first and leading Platform-as-a-Service solution for eCommerce.",
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
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

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
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            final SetAttributeInAllVariants unsetAction = SetAttributeInAllVariants.ofUnsetAttribute(moneyAttribute);
            final Product productWithoutMoney = client().executeBlocking(ProductUpdateCommand.of(updatedProduct, unsetAction));

            assertThat(productWithoutMoney.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).isEmpty();

            return productWithoutMoney;
        });
    }

    @Test
    public void setAttributeInAllVariantsWithStaged() {
        setAttributeInAllVariantsWithStaged(true);
        setAttributeInAllVariantsWithStaged(false);
    }

    public void setAttributeInAllVariantsWithStaged(final Boolean staged) {
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

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

            final SetAttributeInAllVariants moneyUpdate = SetAttributeInAllVariants.of(moneyAttribute, newValueForMoney, staged);
            final SetAttributeInAllVariants localizedEnumUpdate = SetAttributeInAllVariants.of(colorAttribute, newValueForColor, staged);

            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, asList(moneyUpdate, localizedEnumUpdate)));
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(moneyAttribute)).contains(newValueForMoney);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().findAttribute(colorAttribute)).contains(newValueForColor);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
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
    public void revertStagedVariantChanges() throws Exception {
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
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final PriceDraft price = PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
            final List<PriceDraft> prices = asList(price);
            final List<AttributeDraft> attributeValues = asList(moneyAttributeValue, colorAttributeValue, sizeValue);
            final ProductUpdateCommand addVariantCommand =
                    ProductUpdateCommand.of(product, AddVariant.of(attributeValues, prices, randomKey(), false));

            final Product productWithVariant = client().executeBlocking(addVariantCommand);
            final ProductVariant variant = productWithVariant.getMasterData().getStaged().getVariants().get(0);
            assertThat(productWithVariant.getMasterData().hasStagedChanges()).isFalse();

            final Product productWithoutVariant = client().executeBlocking(ProductUpdateCommand.of(productWithVariant, RemoveVariant.ofVariantId(variant.getId())));
            assertThat(productWithoutVariant.getMasterData().getStaged().getVariants()).isEmpty();
            assertThat(productWithoutVariant.getMasterData().hasStagedChanges()).isTrue();

            final Product revertedProduct = client().executeBlocking(ProductUpdateCommand.of(productWithoutVariant,RevertStagedVariantChanges.of(variant.getId())));
            assertThat(revertedProduct.getMasterData().getStaged().getVariants()).isNotEmpty();
            assertThat(revertedProduct.getMasterData().hasStagedChanges()).isFalse();


            return productWithoutVariant;
        });
    }

    @Test
    public void setTaxCategory() throws Exception {
        TaxCategoryFixtures.withTransientTaxCategory(client(), taxCategory ->
                withUpdateableProduct(client(), product -> {
                    assertThat(product.getTaxCategory()).isNotEqualTo(taxCategory);
                    final ProductUpdateCommand command = ProductUpdateCommand.of(product, SetTaxCategory.of(taxCategory.toResourceIdentifier()));
                    final Product updatedProduct = client().executeBlocking(command);
                    assertThat(updatedProduct.getTaxCategory()).isEqualTo(taxCategory.toReference());
                    return updatedProduct;
                })
        );
    }

    @Test
    public void setSearchKeywords() throws Exception {
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final SearchKeywords searchKeywords = SearchKeywords.of(Locale.ENGLISH, asList(SearchKeyword.of("Raider", CustomSuggestTokenizer.of(singletonList("Twix")))));
            final ProductUpdateCommand command = ProductUpdateCommand.of(product, SetSearchKeywords.of(searchKeywords));
            final Product updatedProduct = client().executeBlocking(command);

            final SearchKeywords actualKeywords = updatedProduct.getMasterData().getStaged().getSearchKeywords();
            assertThat(actualKeywords).isEqualTo(searchKeywords);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();
            return updatedProduct;
        });
    }

    @Test
    public void setSearchKeywordsWithStaged() {
        setSearchKeywordsWithStaged(true);
        setSearchKeywordsWithStaged(false);
    }

    public void setSearchKeywordsWithStaged(final Boolean staged) {
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final SearchKeywords searchKeywords = SearchKeywords.of(Locale.ENGLISH, asList(SearchKeyword.of("Raider", CustomSuggestTokenizer.of(singletonList("Twix")))));
            final ProductUpdateCommand command = ProductUpdateCommand.of(product, SetSearchKeywords.of(searchKeywords, staged));
            final Product updatedProduct = client().executeBlocking(command);

            final SearchKeywords actualKeywords = updatedProduct.getMasterData().getStaged().getSearchKeywords();
            assertThat(actualKeywords).isEqualTo(searchKeywords);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
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
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final PriceDraft price = PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
            final List<PriceDraft> prices = asList(price);
            final List<AttributeDraft> attributeValues = asList(moneyAttributeValue, colorAttributeValue, sizeValue);
            final String sku = randomKey();
            final String key = randomKey();
            final Image image = Image.of("url", ImageDimensions.of(3, 5));

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
            product = client().executeBlocking(ProductUpdateCommand.of(product, AddAsset.ofSku(product.getMasterData().getStaged().getMasterVariant().getSku(), assetDraft)));

            final List<Asset> assets = product.getMasterData().getStaged().getMasterVariant().getAssets();
            assertThat(assets).hasSize(1);

            final AddVariant updateAction = AddVariant.of(attributeValues, prices, sku)
                    .withKey(key)
                    .withImages(singletonList(image))
                    .withAssets(assets);
            final ProductUpdateCommand addVariantCommand =
                    ProductUpdateCommand.of(product, updateAction);

            final Product productWithVariant = client().executeBlocking(addVariantCommand);
            final ProductVariant variant = productWithVariant.getMasterData().getStaged().getVariants().get(0);
            assertThat(productWithVariant.getMasterData().hasStagedChanges()).isTrue();
            assertThat(variant.getId()).isEqualTo(2);
            assertThat(variant.findAttribute(moneyAttribute).get()).isEqualTo(EURO_10);
            assertThat(variant.findAttribute(colorAttribute).get()).isEqualTo(color);
            assertThat(variant.findAttribute(sizeAttribute).get()).isEqualTo(Sizes.M);
            assertThat(variant.getSku()).isEqualTo(sku);
            assertThat(variant.getKey()).isEqualTo(key);
            assertThat(variant.getImages()).containsExactly(image);
            assertThat(variant.getAssets().get(0).getKey()).isEqualTo(assets.get(0).getKey());

            final Product productWithoutVariant = client().executeBlocking(ProductUpdateCommand.of(productWithVariant, RemoveVariant.of(variant)));
            assertThat(productWithoutVariant.getMasterData().getStaged().getVariants()).isEmpty();

            final List<AssetDraft> assetDrafts = product.getMasterData().getStaged().getMasterVariant().getAssets().stream().map(a -> AssetDraftBuilder.of(a).build()).collect(toList());
            assertThat(assets).hasSize(1);

            final AddVariant updateAction2 = AddVariant.of(attributeValues, prices, sku)
                                                      .withKey(key)
                                                      .withImages(singletonList(image))
                                                      .withAssetDrafts(assetDrafts);
            final ProductUpdateCommand addVariantCommand2 =
                    ProductUpdateCommand.of(productWithoutVariant, updateAction2);

            final Product productWithVariant2 = client().executeBlocking(addVariantCommand2);
            final ProductVariant variant2 = productWithVariant2.getMasterData().getStaged().getVariants().get(0);
            assertThat(productWithVariant2.getMasterData().hasStagedChanges()).isTrue();
            assertThat(variant2.getId()).isEqualTo(3);
            assertThat(variant2.findAttribute(moneyAttribute).get()).isEqualTo(EURO_10);
            assertThat(variant2.findAttribute(colorAttribute).get()).isEqualTo(color);
            assertThat(variant2.findAttribute(sizeAttribute).get()).isEqualTo(Sizes.M);
            assertThat(variant2.getSku()).isEqualTo(sku);
            assertThat(variant2.getKey()).isEqualTo(key);
            assertThat(variant2.getImages()).containsExactly(image);
            assertThat(variant2.getAssets().get(0).getKey()).isEqualTo(assets.get(0).getKey());

            return productWithVariant2;
        });

    }

    @Test
    public void addVariantWithStaged() {
        addVariantWithStaged(true);
        addVariantWithStaged(false);
    }

    private void addVariantWithStaged(final Boolean staged) {
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
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final PriceDraft price = PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
            final List<PriceDraft> prices = asList(price);
            final List<AttributeDraft> attributeValues = asList(moneyAttributeValue, colorAttributeValue, sizeValue);
            final String sku = randomKey();
            final String key = randomKey();
            final Image image = Image.of("url", ImageDimensions.of(3, 5));
            final AddVariant updateAction = AddVariant.of(attributeValues, prices, sku, staged)
                    .withKey(key)
                    .withImages(singletonList(image));
            final ProductUpdateCommand addVariantCommand =
                    ProductUpdateCommand.of(product, updateAction);

            final Product productWithVariant = client().executeBlocking(addVariantCommand);
            final ProductVariant variant = productWithVariant.getMasterData().getStaged().getVariants().get(0);
            assertThat(productWithVariant.getMasterData().hasStagedChanges()).isEqualTo(staged);
            assertThat(variant.getId()).isEqualTo(2);
            assertThat(variant.findAttribute(moneyAttribute).get()).isEqualTo(EURO_10);
            assertThat(variant.findAttribute(colorAttribute).get()).isEqualTo(color);
            assertThat(variant.findAttribute(sizeAttribute).get()).isEqualTo(Sizes.M);
            assertThat(variant.getSku()).isEqualTo(sku);
            assertThat(variant.getKey()).isEqualTo(key);
            assertThat(variant.getImages()).containsExactly(image);

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
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final PriceDraft price = PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
            final List<PriceDraft> prices = asList(price);
            final List<AttributeDraft> attributeValues = asList(moneyAttributeValue, colorAttributeValue, sizeValue);
            final ProductUpdateCommand addVariantCommand =
                    ProductUpdateCommand.of(product, AddVariant.of(attributeValues, prices, randomKey(), false));

            final Product productWithVariant = client().executeBlocking(addVariantCommand);
            final ProductVariant variant = productWithVariant.getMasterData().getStaged().getVariants().get(0);
            assertThat(productWithVariant.getMasterData().hasStagedChanges()).isFalse();

            final Product productWithoutVariant = client().executeBlocking(ProductUpdateCommand.of(productWithVariant, RemoveVariant.ofVariantId(variant.getId())));
            assertThat(productWithoutVariant.getMasterData().getStaged().getVariants()).isEmpty();
            assertThat(productWithoutVariant.getMasterData().hasStagedChanges()).isTrue();

            return productWithoutVariant;
        });
    }

    @Test
    public void removeVariantByIdWithStaged() {
        removeVariantByIdWithStaged(true);
        removeVariantByIdWithStaged(false);
    }

    private void removeVariantByIdWithStaged(final Boolean staged) {
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
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final PriceDraft price = PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
            final List<PriceDraft> prices = asList(price);
            final List<AttributeDraft> attributeValues = asList(moneyAttributeValue, colorAttributeValue, sizeValue);
            final ProductUpdateCommand addVariantCommand =
                    ProductUpdateCommand.of(product, AddVariant.of(attributeValues, prices, randomKey(), false));

            final Product productWithVariant = client().executeBlocking(addVariantCommand);
            final ProductVariant variant = productWithVariant.getMasterData().getStaged().getVariants().get(0);
            assertThat(productWithVariant.getMasterData().hasStagedChanges()).isFalse();

            final Product productWithoutVariant = client().executeBlocking(ProductUpdateCommand.of(productWithVariant, RemoveVariant.ofVariantId(variant.getId(), staged)));
            assertThat(productWithoutVariant.getMasterData().getStaged().getVariants()).isEmpty();
            assertThat(productWithoutVariant.getMasterData().hasStagedChanges()).isEqualTo(staged);

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
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final PriceDraft price = PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
            final List<PriceDraft> prices = asList(price);
            final List<AttributeDraft> attributeValues = asList(moneyAttributeValue, colorAttributeValue, sizeValue);
            final ProductUpdateCommand addVariantCommand =
                    ProductUpdateCommand.of(product, AddVariant.of(attributeValues, prices, randomKey(), false));

            final Product productWithVariant = client().executeBlocking(addVariantCommand);
            final ProductVariant variant = productWithVariant.getMasterData().getStaged().getVariants().get(0);
            assertThat(productWithVariant.getMasterData().hasStagedChanges()).isFalse();

            final Product productWithoutVariant = client().executeBlocking(ProductUpdateCommand.of(productWithVariant, RemoveVariant.ofSku(variant.getSku())));
            assertThat(productWithoutVariant.getMasterData().getStaged().getVariants()).isEmpty();
            assertThat(productWithoutVariant.getMasterData().hasStagedChanges()).isTrue();

            return productWithoutVariant;
        });
    }

    @Test
    public void removeVariantBySkuWithStaged() {
        removeVariantBySkuWithStaged(true);
        removeVariantBySkuWithStaged(false);
    }

    private void removeVariantBySkuWithStaged(final Boolean staged) {
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
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final PriceDraft price = PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
            final List<PriceDraft> prices = asList(price);
            final List<AttributeDraft> attributeValues = asList(moneyAttributeValue, colorAttributeValue, sizeValue);
            final ProductUpdateCommand addVariantCommand =
                    ProductUpdateCommand.of(product, AddVariant.of(attributeValues, prices, randomKey(), false));

            final Product productWithVariant = client().executeBlocking(addVariantCommand);
            final ProductVariant variant = productWithVariant.getMasterData().getStaged().getVariants().get(0);
            assertThat(productWithVariant.getMasterData().hasStagedChanges()).isFalse();

            final Product productWithoutVariant = client().executeBlocking(ProductUpdateCommand.of(productWithVariant, RemoveVariant.ofSku(variant.getSku(), staged)));
            assertThat(productWithoutVariant.getMasterData().getStaged().getVariants()).isEmpty();
            assertThat(productWithoutVariant.getMasterData().hasStagedChanges()).isEqualTo(staged);

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
    public void changeMasterVariantWithVariantIdWithStaged() {
        changeMasterVariantWithVariantIdWithStaged(true);
        changeMasterVariantWithVariantIdWithStaged(false);
    }

    private void changeMasterVariantWithVariantIdWithStaged(final Boolean staged) {
        withUpdateableProductOfMultipleVariants(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final ProductVariant variantSupposedToBeMaster = product.getMasterData().getStaged().getAllVariants().get(2);
            final Integer variantId = variantSupposedToBeMaster.getId();

            final ChangeMasterVariant updateAction = ChangeMasterVariant.ofVariantId(variantId, staged);
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, updateAction));

            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getId()).isEqualTo(variantId);

            return updatedProduct;
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

    @Test
    public void changeMasterVariantWithSkuWithStaged() {
        changeMasterVariantWithSkuWithStaged(true);
        changeMasterVariantWithSkuWithStaged(false);
    }

    private void changeMasterVariantWithSkuWithStaged(final Boolean staged) {
        withUpdateableProductOfMultipleVariants(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final int originalMasterVariantId = product.getMasterData().getStaged().getMasterVariant().getId();
            final ProductVariant variantSupposedToBeMaster = product.getMasterData().getStaged().getAllVariants().get(2);
            final String sku = variantSupposedToBeMaster.getSku();

            final ChangeMasterVariant updateAction = ChangeMasterVariant.ofSku(sku, staged);
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, updateAction));

            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getSku()).isEqualTo(sku);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getId())
                    .as("variant IDs don't change in reordering")
                    .isNotEqualTo(originalMasterVariantId)
                    .isEqualTo(variantSupposedToBeMaster.getId());

            return updatedProduct;
        });
    }

    @Test
    public void transitionState() {
        withStateByBuilder(client(), builder -> builder.type(PRODUCT_STATE), state -> {
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
    public void setProductPriceCustomTypeAndsetProductPriceCustomFieldWithEmptySet() {
        Map<String, JsonNode> map = new HashMap<>();
        ArrayNode arrayNode = new ArrayNode(new JsonNodeFactory(true));
        arrayNode.add("some value");
        map.put(STRING_SET_FIELD_NAME, arrayNode);
        withUpdateableType(client(), type -> {
            withUpdateablePricedProduct(client(), product -> {
                final String priceId = getFirstPrice(product).getId();
                final UpdateAction<Product> updateAction = SetProductPriceCustomType.
                        ofTypeIdAndJson(type.getId(), map, priceId);
                final ProductUpdateCommand productUpdateCommand = ProductUpdateCommand.of(product, updateAction);
                final Product updatedProduct = client().executeBlocking(productUpdateCommand);

                final Price price = getFirstPrice(updatedProduct);
                assertThat(price.getCustom().getFieldAsStringSet(STRING_SET_FIELD_NAME))
                        .contains("some value");

                arrayNode.removeAll();
                final Product updated2 = client().executeBlocking(ProductUpdateCommand.of(updatedProduct,
                        SetProductPriceCustomField.ofObject(STRING_SET_FIELD_NAME, arrayNode, priceId)));
                assertThat(getFirstPrice(updated2).getCustom().getFieldAsStringSet(STRING_SET_FIELD_NAME))
                        .isEmpty();
                return updated2;
            });
            return type;
        });
    }

    @Test
    public void setProductPriceCustomTypeAndsetProductPriceCustomFieldWithStaged() {
        setProductPriceCustomTypeAndsetProductPriceCustomFieldWithStaged(true);
        setProductPriceCustomTypeAndsetProductPriceCustomFieldWithStaged(false);
    }

    public void setProductPriceCustomTypeAndsetProductPriceCustomFieldWithStaged(final Boolean staged) {
        withUpdateableType(client(), type -> {
            withUpdateableProduct(client(), product -> {

                final ProductUpdateCommand command = ProductUpdateCommand.of(product, AddPrice.ofVariantId(1, PriceDraft.of(MoneyImpl.of(123, EUR)), false));
                final Product productWithPrice = client().executeBlocking(command);
                assertThat(productWithPrice.getMasterData().hasStagedChanges()).isFalse();

                final String priceId = getFirstPrice(productWithPrice).getId();
                final UpdateAction<Product> updateAction = SetProductPriceCustomType.
                        ofTypeIdAndObjects(type.getId(), STRING_FIELD_NAME, "a value", priceId, staged);
                final ProductUpdateCommand productUpdateCommand = ProductUpdateCommand.of(productWithPrice, updateAction);
                final Product updatedProduct = client().executeBlocking(productUpdateCommand);

                final Price price = getFirstPrice(updatedProduct);
                assertThat(price.getCustom().getFieldAsString(STRING_FIELD_NAME))
                        .isEqualTo("a value");
                assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

                final Product updated2 = client().executeBlocking(ProductUpdateCommand.of(updatedProduct, SetProductPriceCustomField.ofObject(STRING_FIELD_NAME, "a new value", priceId, staged)));
                assertThat(getFirstPrice(updated2).getCustom().getFieldAsString(STRING_FIELD_NAME))
                        .isEqualTo("a new value");
                assertThat(updated2.getMasterData().hasStagedChanges()).isEqualTo(staged);
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
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

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
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();
            return updatedProduct;
        });
    }

    @Test
    public void setSkuWithStaged() {
        setSkuWithStaged(true);
        setSkuWithStaged(false);
    }

    public void setSkuWithStaged(final Boolean staged) {
        final String oldSku = randomKey();
        withProductOfSku(oldSku, (Product product) -> {
            assertThat(product.getMasterData().getStaged().getMasterVariant().getSku()).isEqualTo(oldSku);
            assertThat(product.getMasterData().getCurrent().getMasterVariant().getSku()).isEqualTo(oldSku);
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();

            final String newSku = randomKey();
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, SetSku.of(MASTER_VARIANT_ID, newSku, staged));
            final Product updatedProduct = client().executeBlocking(cmd);

            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
            return updatedProduct;
        });
    }

    @Test
    public void setKey() throws Exception {
        final String key = randomKey();
        withProduct(client(), (Product product) -> {
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, SetKey.of(key)));
            assertThat(updatedProduct.getKey()).isEqualTo(key);
        });
    }

    @Test
    public void setProductVariantKeyByVariantId() throws Exception {
        final String key = randomKey();
        withProduct(client(), (Product product) -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final Integer variantId = product.getMasterData().getStaged().getMasterVariant().getId();
            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetProductVariantKey.ofKeyAndVariantId(key, variantId));
            final Product updatedProduct = client().executeBlocking(cmd);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getKey()).isEqualTo(key);
            assertThat(updatedProduct.getKey()).isNotEqualTo(key);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();
        });
    }

    @Test
    public void setProductVariantKeyByVariantIdWithStaged() {
        setProductVariantKeyByVariantIdWithStaged(true);
        setProductVariantKeyByVariantIdWithStaged(false);
    }

    public void setProductVariantKeyByVariantIdWithStaged(final Boolean staged) {
        final String key = randomKey();
        withProduct(client(), (Product product) -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final Integer variantId = product.getMasterData().getStaged().getMasterVariant().getId();
            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetProductVariantKey.ofKeyAndVariantId(key, variantId, staged));
            final Product updatedProduct = client().executeBlocking(cmd);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getKey()).isEqualTo(key);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
        });
    }

    @Test
    public void setProductVariantKeyBySku() throws Exception {
        final String key = randomKey();
        withProduct(client(), (Product product) -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetProductVariantKey.ofKeyAndSku(key, sku));
            final Product updatedProduct = client().executeBlocking(cmd);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getKey()).isEqualTo(key);
            assertThat(updatedProduct.getKey()).isNotEqualTo(key);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();
        });
    }

    @Test
    public void setProductVariantKeyBySkuWithStaged() {
        setProductVariantKeyBySkuWithStaged(true);
        setProductVariantKeyBySkuWithStaged(false);
    }

    public void setProductVariantKeyBySkuWithStaged(final Boolean staged) {
        final String key = randomKey();
        withProduct(client(), (Product product) -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetProductVariantKey.ofKeyAndSku(key, sku, staged));
            final Product updatedProduct = client().executeBlocking(cmd);
            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getKey()).isEqualTo(key);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
        });
    }

    @Test
    public void addAssetByVariantId() {
        withProduct(client(), (Product product) -> {
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
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, AddAsset.ofVariantId(MASTER_VARIANT_ID, assetDraft)));

            final List<Asset> assets = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets();
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
    public void addAssetBySku() {
        withProduct(client(), (Product product) -> {
            final AssetSource assetSource = AssetSourceBuilder.ofUri("https://commercetools.com/binaries/content/gallery/commercetoolswebsite/homepage/cases/rewe.jpg")
                    .build();
            final LocalizedString name = LocalizedString.ofEnglish("REWE show case");
            final AssetDraft assetDraft = AssetDraftBuilder.of(singletonList(assetSource), name)
                    .build();
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, AddAsset.ofSku(sku, assetDraft).withPosition(0).withStaged(false));
            final Product updatedProduct = client().executeBlocking(cmd);

            final List<Asset> assets = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets();
            assertThat(assets).hasSize(1);
            final Asset asset = assets.get(0);
            assertThat(asset.getName()).isEqualTo(name);
            assertThat(asset.getSources()).hasSize(1);
            final AssetSource source = asset.getSources().get(0);
            assertThat(source.getUri()).isEqualTo("https://commercetools.com/binaries/content/gallery/commercetoolswebsite/homepage/cases/rewe.jpg");
        });
    }

    @Test
    public void removeAssetByVariantId() {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final List<Asset> originalAssets = product.getMasterData().getStaged().getMasterVariant().getAssets();
            final Asset assetToRemove = originalAssets.get(0);
            final String assetId = assetToRemove.getId();
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, RemoveAsset.ofVariantId(MASTER_VARIANT_ID, assetId)));

            final List<Asset> assets = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets();
            assertThat(assets).hasSize(originalAssets.size() - 1);
            assertThat(assets).allMatch(asset -> !asset.getId().equals(assetId));
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void removeAssetByVariantIdWithStaged() {
        removeAssetByVariantIdWithStaged(true);
        removeAssetByVariantIdWithStaged(false);
    }

    public void removeAssetByVariantIdWithStaged(final Boolean staged) {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final List<Asset> originalAssets = product.getMasterData().getStaged().getMasterVariant().getAssets();
            final Asset assetToRemove = originalAssets.get(0);
            final String assetId = assetToRemove.getId();
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, RemoveAsset.ofVariantId(MASTER_VARIANT_ID, assetId, staged)));

            final List<Asset> assets = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets();
            assertThat(assets).hasSize(originalAssets.size() - 1);
            assertThat(assets).allMatch(asset -> !asset.getId().equals(assetId));
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void removeAssetBySku() {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();
            final String sku = masterVariant.getSku();
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, RemoveAsset.ofSku(sku, assetId)));

            final List<Asset> assets = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets();
            assertThat(assets).allMatch(asset -> !asset.getId().equals(assetId));
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void removeAssetBySkuWithStaged() {
        removeAssetBySkuWithStaged(true);
        removeAssetBySkuWithStaged(false);
    }

    public void removeAssetBySkuWithStaged(final Boolean staged) {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();
            final String sku = masterVariant.getSku();
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, RemoveAsset.ofSku(sku, assetId, staged)));

            final List<Asset> assets = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets();
            assertThat(assets).allMatch(asset -> !asset.getId().equals(assetId));
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void changeAssetOrderByVariantId() {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final List<Asset> originalAssets = product.getMasterData().getStaged().getMasterVariant().getAssets();

            final List<String> newAssetOrder =
                    new LinkedList<>(originalAssets.stream().map(Asset::getId).collect(toList()));
            Collections.reverse(newAssetOrder);

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, ChangeAssetOrder.ofVariantId(MASTER_VARIANT_ID, newAssetOrder));
            final Product updatedProduct = client().executeBlocking(cmd);

            final List<Asset> assets = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets();
            assertThat(assets).extracting(Asset::getId).isEqualTo(newAssetOrder);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void changeAssetOrderByVariantIdWithStaged() {
        changeAssetOrderByVariantIdWithStaged(true);
        changeAssetOrderByVariantIdWithStaged(false);
    }

    public void changeAssetOrderByVariantIdWithStaged(final Boolean staged) {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final List<Asset> originalAssets = product.getMasterData().getStaged().getMasterVariant().getAssets();

            final List<String> newAssetOrder =
                    new LinkedList<>(originalAssets.stream().map(Asset::getId).collect(toList()));
            Collections.reverse(newAssetOrder);

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, ChangeAssetOrder.ofVariantId(MASTER_VARIANT_ID, newAssetOrder, staged));
            final Product updatedProduct = client().executeBlocking(cmd);

            final List<Asset> assets = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets();
            assertThat(assets).extracting(Asset::getId).isEqualTo(newAssetOrder);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void changeAssetOrderBySku() {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final List<Asset> originalAssets = masterVariant.getAssets();

            final List<String> newAssetOrder =
                    new LinkedList<>(originalAssets.stream().map(Asset::getId).collect(toList()));
            Collections.reverse(newAssetOrder);

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, ChangeAssetOrder.ofSku(masterVariant.getSku(), newAssetOrder));
            final Product updatedProduct = client().executeBlocking(cmd);

            final List<Asset> assets = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets();
            assertThat(assets).extracting(Asset::getId).isEqualTo(newAssetOrder);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void changeAssetOrderBySkuWithStaged() {
        changeAssetOrderBySkuWithStaged(true);
        changeAssetOrderBySkuWithStaged(false);
    }

    public void changeAssetOrderBySkuWithStaged(final Boolean staged) {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final List<Asset> originalAssets = masterVariant.getAssets();

            final List<String> newAssetOrder =
                    new LinkedList<>(originalAssets.stream().map(Asset::getId).collect(toList()));
            Collections.reverse(newAssetOrder);

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, ChangeAssetOrder.ofSku(masterVariant.getSku(), newAssetOrder, staged));
            final Product updatedProduct = client().executeBlocking(cmd);

            final List<Asset> assets = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets();
            assertThat(assets).extracting(Asset::getId).isEqualTo(newAssetOrder);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void changeAssetNameByVariantId() throws Exception {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final LocalizedString newName = LocalizedString.ofEnglish("new name");
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, ChangeAssetName.ofVariantId(masterVariant.getId(), assetId, newName));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Asset updatedAsset = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0);
            assertThat(updatedAsset.getName()).isEqualTo(newName);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void changeAssetNameByVariantIdWithStaged() {
        changeAssetNameByVariantIdWithStaged(true);
        changeAssetNameByVariantIdWithStaged(false);
    }

    public void changeAssetNameByVariantIdWithStaged(final Boolean staged) {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final LocalizedString newName = LocalizedString.ofEnglish("new name");
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, ChangeAssetName.ofVariantId(masterVariant.getId(), assetId, newName, staged));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Asset updatedAsset = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0);
            assertThat(updatedAsset.getName()).isEqualTo(newName);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
            return updatedProduct;
        });
    }

    @Test
    public void changeAssetNameBySku() throws Exception {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final LocalizedString newName = LocalizedString.ofEnglish("new name");
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, ChangeAssetName.ofSku(masterVariant.getSku(), assetId, newName));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Asset updatedAsset = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0);
            assertThat(updatedAsset.getName()).isEqualTo(newName);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void changeAssetNameBySkuWithStaged() {
        changeAssetNameBySkuWithStaged(true);
        changeAssetNameBySkuWithStaged(false);
    }

    public void changeAssetNameBySkuWithStaged(final Boolean staged) {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final LocalizedString newName = LocalizedString.ofEnglish("new name");
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, ChangeAssetName.ofSku(masterVariant.getSku(), assetId, newName, staged));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Asset updatedAsset = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0);
            assertThat(updatedAsset.getName()).isEqualTo(newName);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void changeAssetNameByVariantIdAndAssetKey() {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final LocalizedString newName = LocalizedString.ofEnglish("new name");
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetKey = masterVariant.getAssets().get(0).getKey();

            final ProductUpdateCommand cmd =
                ProductUpdateCommand.of(product, ChangeAssetName.ofAssetKeyAndVariantId(masterVariant.getId(), assetKey, newName));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Asset updatedAsset = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0);
            assertThat(updatedAsset.getName()).isEqualTo(newName);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void changeAssetNameByVariantIdAndAssetKeyWithStaged() {
        changeAssetNameByVariantIdAndAssetKeyWithStaged(true);
        changeAssetNameByVariantIdAndAssetKeyWithStaged(false);
    }

    public void changeAssetNameByVariantIdAndAssetKeyWithStaged(@Nonnull final Boolean staged) {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final LocalizedString newName = LocalizedString.ofEnglish("new name");
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetKey = masterVariant.getAssets().get(0).getKey();

            final ProductUpdateCommand cmd =
                ProductUpdateCommand.of(product, ChangeAssetName.ofAssetKeyAndVariantId(masterVariant.getId(), assetKey, newName, staged));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Asset updatedAsset = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0);
            assertThat(updatedAsset.getName()).isEqualTo(newName);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void setAssetDescriptionByVariantId() throws Exception {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final LocalizedString newDescription = LocalizedString.ofEnglish("new description");
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetAssetDescription.ofVariantId(masterVariant.getId(), assetId, newDescription));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Asset updatedAsset = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0);
            assertThat(updatedAsset.getDescription()).isEqualTo(newDescription);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void setAssetDescriptionByVariantIdWithStaged() {
        setAssetDescriptionByVariantIdWithStaged(true);
        setAssetDescriptionByVariantIdWithStaged(false);
    }

    public void setAssetDescriptionByVariantIdWithStaged(final Boolean staged) {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final LocalizedString newDescription = LocalizedString.ofEnglish("new description");
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetAssetDescription.ofVariantId(masterVariant.getId(), assetId, newDescription, staged));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Asset updatedAsset = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0);
            assertThat(updatedAsset.getDescription()).isEqualTo(newDescription);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void setAssetDescriptionBySku() throws Exception {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final LocalizedString newDescription = LocalizedString.ofEnglish("new description");
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetAssetDescription.ofSku(masterVariant.getSku(), assetId, newDescription));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Asset updatedAsset = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0);
            assertThat(updatedAsset.getDescription()).isEqualTo(newDescription);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void setAssetDescriptionBySkuWithStaged() {
        setAssetDescriptionBySkuWithStaged(true);
        setAssetDescriptionBySkuWithStaged(false);
    }

    public void setAssetDescriptionBySkuWithStaged(final Boolean staged) {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final LocalizedString newDescription = LocalizedString.ofEnglish("new description");
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetAssetDescription.ofSku(masterVariant.getSku(), assetId, newDescription, staged));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Asset updatedAsset = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0);
            assertThat(updatedAsset.getDescription()).isEqualTo(newDescription);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void setAssetTagsByVariantId() throws Exception {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final Set<String> newTags = new HashSet<>(asList("tag1", "tag2"));
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetAssetTags.ofVariantId(masterVariant.getId(), assetId, newTags));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Asset updatedAsset = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0);
            assertThat(updatedAsset.getTags()).isEqualTo(newTags);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

            return updatedProduct;
        });
    }

    @Test
    public void setAssetTagsByVariantIdWithStaged() {
        setAssetTagsByVariantIdWithStaged(true);
        setAssetTagsByVariantIdWithStaged(false);
    }

    public void setAssetTagsByVariantIdWithStaged(final Boolean staged) {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final Set<String> newTags = new HashSet<>(asList("tag1", "tag2"));
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetAssetTags.ofVariantId(masterVariant.getId(), assetId, newTags, staged));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Asset updatedAsset = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0);
            assertThat(updatedAsset.getTags()).isEqualTo(newTags);
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

            return updatedProduct;
        });
    }

    @Test
    public void setAssetTagsBySku() throws Exception {
        withProductHavingAssets(client(), product -> {
            final Set<String> newTags = new HashSet<>(asList("tag1", "tag2"));
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetAssetTags.ofSku(masterVariant.getSku(), assetId, newTags));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Asset updatedAsset = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0);
            assertThat(updatedAsset.getTags()).isEqualTo(newTags);

            return updatedProduct;
        });
    }


    @Test
    public void setAssetSourcesByVariantId() throws Exception {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final AssetSource assetSource = AssetSourceBuilder.ofUri("https://docs.commercetools.com/assets/img/CT-logo.svg")
                    .key("commercetools-logo")
                    .contentType("image/svg+xml")
                    .build();
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetAssetSources.ofVariantId(masterVariant.getId(), assetId, Collections.singletonList(assetSource)));
            final Product updatedProduct = client().executeBlocking(cmd);

            AssetSource source = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0).getSources().get(0);
            assertThat(source.getUri()).isEqualTo("https://docs.commercetools.com/assets/img/CT-logo.svg");
            assertThat(source.getKey()).isEqualTo("commercetools-logo");
            assertThat(source.getContentType()).isEqualTo("image/svg+xml");
            return updatedProduct;
        });
    }

    @Test
    public void setAssetSourcesBySku() throws Exception {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final AssetSource assetSource = AssetSourceBuilder.ofUri("https://docs.commercetools.com/assets/img/CT-logo.svg")
                    .key("commercetools-logo")
                    .contentType("image/svg+xml")
                    .build();
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetAssetSources.ofSku(masterVariant.getSku(), assetId, Collections.singletonList(assetSource)));
            final Product updatedProduct = client().executeBlocking(cmd);

            AssetSource source = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0).getSources().get(0);
            assertThat(source.getUri()).isEqualTo("https://docs.commercetools.com/assets/img/CT-logo.svg");
            assertThat(source.getKey()).isEqualTo("commercetools-logo");
            assertThat(source.getContentType()).isEqualTo("image/svg+xml");
            return updatedProduct;
        });
    }


    @Test
    public void setAssetSourcesByVariantIdWithStaged() {
        setAssetSourcesByVariantIdWithStaged(true);
        setAssetSourcesByVariantIdWithStaged(false);
    }

    public void setAssetSourcesByVariantIdWithStaged(final Boolean staged) {
        withProductHavingAssets(client(), product -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final AssetSource assetSource = AssetSourceBuilder.ofUri("https://docs.commercetools.com/assets/img/CT-logo.svg")
                    .key("commercetools-logo")
                    .contentType("image/svg+xml")
                    .build();
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final String assetId = masterVariant.getAssets().get(0).getId();

            final ProductUpdateCommand cmd =
                    ProductUpdateCommand.of(product, SetAssetSources.ofVariantId(masterVariant.getId(), assetId, Collections.singletonList(assetSource), staged));
            final Product updatedProduct = client().executeBlocking(cmd);

            AssetSource source = updatedProduct.getMasterData().getStaged().getMasterVariant().getAssets().get(0).getSources().get(0);
            assertThat(source.getUri()).isEqualTo("https://docs.commercetools.com/assets/img/CT-logo.svg");
            assertThat(source.getKey()).isEqualTo("commercetools-logo");
            assertThat(source.getContentType()).isEqualTo("image/svg+xml");
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
            return updatedProduct;
        });
    }

    @Test
    public void assetCustomTypeByVariantId() throws Exception {
        withUpdateableType(client(), (Type type) -> {
            withProductHavingAssets(client(), product -> {
                assertThat(product.getMasterData().hasStagedChanges()).isFalse();
                final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                final Asset assetWithoutCustomType = masterVariant.getAssets().get(0);
                final String assetId = assetWithoutCustomType.getId();

                final String firstFieldValue = "commercetools";
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type)
                        .addObject(STRING_FIELD_NAME, firstFieldValue)
                        .build();
                final Integer variantId = masterVariant.getId();
                final ProductUpdateCommand cmd = ProductUpdateCommand.of(product,
                        SetAssetCustomType.ofVariantId(variantId, assetId, customFieldsDraft));
                final Product updatedProductWithCustomTypeInAssets = client().executeBlocking(cmd);

                final String actualFieldValue = updatedProductWithCustomTypeInAssets.getMasterData()
                        .getStaged().getMasterVariant()
                        .getAssets().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME);
                assertThat(actualFieldValue).isEqualTo(firstFieldValue);

                final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(updatedProductWithCustomTypeInAssets,
                        SetAssetCustomField.ofVariantId(variantId, assetId, STRING_FIELD_NAME, "new")));

                assertThat(updatedProduct.getMasterData()
                        .getStaged().getMasterVariant()
                        .getAssets().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME))
                        .isEqualTo("new");
                assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

                return updatedProduct;
            });
            return type;
        });
    }

    @Test
    public void assetCustomTypeByVariantIdWithStaged() {
        assetCustomTypeByVariantIdWithStaged(true);
        assetCustomTypeByVariantIdWithStaged(false);
    }

    public void assetCustomTypeByVariantIdWithStaged(final Boolean staged) {
        withUpdateableType(client(), (Type type) -> {
            withProductHavingAssets(client(), product -> {
                assertThat(product.getMasterData().hasStagedChanges()).isFalse();
                final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                final Asset assetWithoutCustomType = masterVariant.getAssets().get(0);
                final String assetId = assetWithoutCustomType.getId();

                final String firstFieldValue = "commercetools";
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type)
                        .addObject(STRING_FIELD_NAME, firstFieldValue)
                        .build();
                final Integer variantId = masterVariant.getId();
                final ProductUpdateCommand cmd = ProductUpdateCommand.of(product,
                        SetAssetCustomType.ofVariantId(variantId, assetId, customFieldsDraft, staged));
                final Product updatedProductWithCustomTypeInAssets = client().executeBlocking(cmd);

                final String actualFieldValue = updatedProductWithCustomTypeInAssets.getMasterData()
                        .getStaged().getMasterVariant()
                        .getAssets().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME);
                assertThat(actualFieldValue).isEqualTo(firstFieldValue);
                assertThat(updatedProductWithCustomTypeInAssets.getMasterData().hasStagedChanges()).isEqualTo(staged);

                final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(updatedProductWithCustomTypeInAssets,
                        SetAssetCustomField.ofVariantId(variantId, assetId, STRING_FIELD_NAME, "new", staged)));

                assertThat(updatedProduct.getMasterData()
                        .getStaged().getMasterVariant()
                        .getAssets().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME))
                        .isEqualTo("new");
                assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

                return updatedProduct;
            });
            return type;
        });
    }

    @Test
    public void assetCustomTypeBySku() throws Exception {
        withUpdateableType(client(), (Type type) -> {
            withProductHavingAssets(client(), product -> {
                assertThat(product.getMasterData().hasStagedChanges()).isFalse();
                final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                final Asset assetWithoutCustomType = masterVariant.getAssets().get(0);
                final String assetId = assetWithoutCustomType.getId();

                final String firstFieldValue = "commercetools";
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type)
                        .addObject(STRING_FIELD_NAME, firstFieldValue)
                        .build();
                final String sku = masterVariant.getSku();
                final ProductUpdateCommand cmd = ProductUpdateCommand.of(product,
                        SetAssetCustomType.ofSku(sku, assetId, customFieldsDraft));
                final Product updatedProductWithCustomTypeInAssets = client().executeBlocking(cmd);

                final String actualFieldValue = updatedProductWithCustomTypeInAssets.getMasterData()
                        .getStaged().getMasterVariant()
                        .getAssets().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME);
                assertThat(actualFieldValue).isEqualTo(firstFieldValue);

                final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(updatedProductWithCustomTypeInAssets,
                        SetAssetCustomField.ofSku(sku, assetId, STRING_FIELD_NAME, "new")));

                assertThat(updatedProduct.getMasterData()
                        .getStaged().getMasterVariant()
                        .getAssets().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME))
                        .isEqualTo("new");
                assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

                return updatedProduct;
            });
            return type;
        });
    }

    @Test
    public void assetCustomTypeBySkuWithStaged() {
        assetCustomTypeBySkuWithStaged(true);
        assetCustomTypeBySkuWithStaged(false);
    }

    public void assetCustomTypeBySkuWithStaged(final Boolean staged) {
        withUpdateableType(client(), (Type type) -> {
            withProductHavingAssets(client(), product -> {
                assertThat(product.getMasterData().hasStagedChanges()).isFalse();
                final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                final Asset assetWithoutCustomType = masterVariant.getAssets().get(0);
                final String assetId = assetWithoutCustomType.getId();

                final String firstFieldValue = "commercetools";
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type)
                        .addObject(STRING_FIELD_NAME, firstFieldValue)
                        .build();
                final String sku = masterVariant.getSku();
                final ProductUpdateCommand cmd = ProductUpdateCommand.of(product,
                        SetAssetCustomType.ofSku(sku, assetId, customFieldsDraft, staged));
                final Product updatedProductWithCustomTypeInAssets = client().executeBlocking(cmd);

                final String actualFieldValue = updatedProductWithCustomTypeInAssets.getMasterData()
                        .getStaged().getMasterVariant()
                        .getAssets().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME);
                assertThat(actualFieldValue).isEqualTo(firstFieldValue);
                assertThat(updatedProductWithCustomTypeInAssets.getMasterData().hasStagedChanges()).isEqualTo(staged);

                final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(updatedProductWithCustomTypeInAssets,
                        SetAssetCustomField.ofSku(sku, assetId, STRING_FIELD_NAME, "new", staged)));

                assertThat(updatedProduct.getMasterData()
                        .getStaged().getMasterVariant()
                        .getAssets().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME))
                        .isEqualTo("new");
                assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

                return updatedProduct;
            });
            return type;
        });
    }

    @Test
    public void setAssetCustomTypeByVariantIdAndAssetKey() {
        withUpdateableType(client(), (Type type) -> {
            withProductHavingAssets(client(), product -> {
                assertThat(product.getMasterData().hasStagedChanges()).isFalse();
                final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                final Asset assetWithoutCustomType = masterVariant.getAssets().get(0);
                final String assetKey = assetWithoutCustomType.getKey();

                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).build();

                final ProductUpdateCommand cmd = ProductUpdateCommand.of(product,
                    SetAssetCustomType.ofVariantIdAndAssetKey(masterVariant.getId(), assetKey, customFieldsDraft));
                final Product updatedProductWithCustomTypeInAssets = client().executeBlocking(cmd);

                final Asset updatedAsset = updatedProductWithCustomTypeInAssets.getMasterData().getStaged()
                                                                               .getMasterVariant().getAssets().get(0);
                assertThat(updatedAsset.getCustom()).isNotNull();
                assertThat(updatedAsset.getCustom().getType().getId()).isEqualTo(type.getId());

                return updatedProductWithCustomTypeInAssets;
            });
            return type;
        });
    }

    @Test
    public void setAssetCustomTypeByVariantSkuAndAssetKey() {
        withUpdateableType(client(), (Type type) -> {
            withProductHavingAssets(client(), product -> {
                assertThat(product.getMasterData().hasStagedChanges()).isFalse();
                final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                final Asset assetWithoutCustomType = masterVariant.getAssets().get(0);
                final String assetKey = assetWithoutCustomType.getKey();

                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).build();

                final ProductUpdateCommand cmd = ProductUpdateCommand.of(product,
                        SetAssetCustomType.ofSkuAndAssetKey(masterVariant.getSku(), assetKey, customFieldsDraft));
                final Product updatedProductWithCustomTypeInAssets = client().executeBlocking(cmd);

                final Asset updatedAsset = updatedProductWithCustomTypeInAssets.getMasterData().getStaged()
                        .getMasterVariant().getAssets().get(0);
                assertThat(updatedAsset.getCustom()).isNotNull();
                assertThat(updatedAsset.getCustom().getType().getId()).isEqualTo(type.getId());

                return updatedProductWithCustomTypeInAssets;
            });
            return type;
        });
    }
    @Test
    public void setAssetCustomTypeByVariantIdAndAssetKeyWithStaged() {
        setAssetCustomTypeByVariantIdAndAssetKeyWithStaged(true);
        setAssetCustomTypeByVariantIdAndAssetKeyWithStaged(false);
    }

    public void setAssetCustomTypeByVariantIdAndAssetKeyWithStaged(@Nonnull final Boolean staged) {
        withUpdateableType(client(), (Type type) -> {
            withProductHavingAssets(client(), product -> {
                assertThat(product.getMasterData().hasStagedChanges()).isFalse();
                final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                final Asset assetWithoutCustomType = masterVariant.getAssets().get(0);
                final String assetKey = assetWithoutCustomType.getKey();

                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).build();

                final ProductUpdateCommand cmd = ProductUpdateCommand.of(product,
                    SetAssetCustomType.ofVariantIdAndAssetKey(masterVariant.getId(), assetKey, customFieldsDraft, staged));
                final Product updatedProductWithCustomTypeInAssets = client().executeBlocking(cmd);

                final Asset updatedAsset = updatedProductWithCustomTypeInAssets.getMasterData().getStaged()
                                                                               .getMasterVariant().getAssets().get(0);
                assertThat(updatedAsset.getCustom()).isNotNull();
                assertThat(updatedAsset.getCustom().getType().getId()).isEqualTo(type.getId());
                assertThat(updatedProductWithCustomTypeInAssets.getMasterData().hasStagedChanges()).isEqualTo(staged);

                return updatedProductWithCustomTypeInAssets;
            });
            return type;
        });
    }

    @Test
    public void setAssetCustomFieldByVariantIdAndAssetKey() {
        withUpdateableType(client(), (Type type) -> {
            withProductHavingAssets(client(), product -> {
                assertThat(product.getMasterData().hasStagedChanges()).isFalse();
                final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                final Asset assetWithoutCustomType = masterVariant.getAssets().get(0);
                final String assetKey = assetWithoutCustomType.getKey();

                final String firstFieldValue = "commercetools";
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type)
                                                                                    .addObject(STRING_FIELD_NAME, firstFieldValue)
                                                                                    .build();
                // Set custom type with staged false to publish the change right away, since this test is only for
                // SetAssetCustomField and not SetAssetCustomType.
                final ProductUpdateCommand cmd = ProductUpdateCommand.of(product,
                    SetAssetCustomType.ofVariantIdAndAssetKey(masterVariant.getId(), assetKey, customFieldsDraft, false));
                final Product updatedProductWithCustomTypeInAssets = client().executeBlocking(cmd);

                final String actualFieldValue = updatedProductWithCustomTypeInAssets.getMasterData()
                                                                                    .getStaged().getMasterVariant()
                                                                                    .getAssets().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME);
                assertThat(actualFieldValue).isEqualTo(firstFieldValue);

                final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(updatedProductWithCustomTypeInAssets,
                    SetAssetCustomField.ofVariantIdAndAssetKey(masterVariant.getId(), assetKey, STRING_FIELD_NAME, "new")));

                assertThat(updatedProduct.getMasterData()
                                         .getStaged().getMasterVariant()
                                         .getAssets().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME))
                    .isEqualTo("new");
                assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();

                return updatedProduct;
            });
            return type;
        });
    }

    @Test
    public void setAssetCustomFieldByVariantIdAndAssetKeyWithStaged() {
        setAssetCustomFieldByVariantIdAndAssetKeyWithStaged(true);
        setAssetCustomFieldByVariantIdAndAssetKeyWithStaged(false);
    }

    public void setAssetCustomFieldByVariantIdAndAssetKeyWithStaged(@Nonnull final Boolean staged) {
        withUpdateableType(client(), (Type type) -> {
            withProductHavingAssets(client(), product -> {
                assertThat(product.getMasterData().hasStagedChanges()).isFalse();
                final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                final Asset assetWithoutCustomType = masterVariant.getAssets().get(0);
                final String assetKey = assetWithoutCustomType.getKey();

                final String firstFieldValue = "commercetools";
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type)
                                                                                    .addObject(STRING_FIELD_NAME, firstFieldValue)
                                                                                    .build();
                // Set custom type with staged false to publish the change right away, since this test is only for
                // SetAssetCustomField and not SetAssetCustomType.
                final ProductUpdateCommand cmd = ProductUpdateCommand.of(product,
                    SetAssetCustomType.ofVariantIdAndAssetKey(masterVariant.getId(), assetKey, customFieldsDraft, false));
                final Product updatedProductWithCustomTypeInAssets = client().executeBlocking(cmd);

                final String actualFieldValue = updatedProductWithCustomTypeInAssets.getMasterData()
                                                                                    .getStaged().getMasterVariant()
                                                                                    .getAssets().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME);
                assertThat(actualFieldValue).isEqualTo(firstFieldValue);

                final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(updatedProductWithCustomTypeInAssets,
                    SetAssetCustomField.ofVariantIdAndAssetKey(masterVariant.getId(), assetKey, STRING_FIELD_NAME, "new", staged)));

                assertThat(updatedProduct.getMasterData()
                                         .getStaged().getMasterVariant()
                                         .getAssets().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME))
                    .isEqualTo("new");
                assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);

                return updatedProduct;
            });
            return type;
        });
    }

    @Test
    public void setAttributeWithObjects() {
        withProduct(client(), (Product product) -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product,
                    SetAttribute.ofSku(sku, "size", "M"),
                    SetAttribute.ofVariantId(MASTER_VARIANT_ID, "color", "red")
            ));
            final ProductVariant masterVariant = updatedProduct.getMasterData().getStaged().getMasterVariant();
            assertThat(masterVariant.getAttribute("size").getValueAsEnumValue()).isEqualTo(EnumValue.of("M", "M"));
            assertThat(masterVariant.getAttribute("color").getValueAsLocalizedEnumValue().getKey()).isEqualTo("red");
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isTrue();
        });
    }

    @Test
    public void setAttributeWithObjectsWithStaged() {
        setAttributeWithObjectsWithStaged(true);
        setAttributeWithObjectsWithStaged(false);
    }

    public void setAttributeWithObjectsWithStaged(final Boolean staged) {
        withProduct(client(), (Product product) -> {
            assertThat(product.getMasterData().hasStagedChanges()).isFalse();
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, asList(
                    SetAttribute.ofSku(sku, "size", "M", staged),
                    SetAttribute.ofVariantId(MASTER_VARIANT_ID, "color", "red", staged)
            )));
            final ProductVariant masterVariant = updatedProduct.getMasterData().getStaged().getMasterVariant();
            assertThat(masterVariant.getAttribute("size").getValueAsEnumValue()).isEqualTo(EnumValue.of("M", "M"));
            assertThat(masterVariant.getAttribute("color").getValueAsLocalizedEnumValue().getKey()).isEqualTo("red");
            assertThat(updatedProduct.getMasterData().hasStagedChanges()).isEqualTo(staged);
        });
    }

    @Test
    public void setDiscountedPrice() {
        final ProductDiscountPredicate predicate = ProductDiscountPredicate.of("1 = 1");//can be used for all products
        final ProductDiscountDraft productDiscountDraft = ProductDiscountDraft.of(randomSlug(), randomKey(), randomSlug(),
                predicate, ExternalProductDiscountValue.of(), randomSortOrder(), true);
        //don't forget that one product discount can be used for multiple products
        withProductDiscount(client(), productDiscountDraft, externalProductDiscount -> {
            withUpdateablePricedProduct(client(), product -> {
                final Price originalPrice = product.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
                assertThat(originalPrice.getDiscounted()).isNull();

                final String priceId = originalPrice.getId();
                final SetDiscountedPrice action =
                        SetDiscountedPrice.of(priceId, DiscountedPrice.of(EURO_5, externalProductDiscount.toReference()));
                final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, action));
                final Price updatedPrice = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
                assertThat(updatedPrice.getValue()).isEqualTo(originalPrice.getValue());
                assertThat(updatedPrice.getDiscounted().getValue()).isEqualTo(EURO_5);
                assertThat(updatedPrice.getDiscounted().getDiscount()).isEqualTo(externalProductDiscount.toReference());

                assertEventually(() -> {
                    final Query<ProductPriceExternalDiscountSetMessage> query = MessageQuery.of()
                            .withPredicates(m -> m.resource().is(product))
                            .forMessageType(ProductPriceExternalDiscountSetMessage.MESSAGE_HINT);
                    final List<ProductPriceExternalDiscountSetMessage> results =
                            client().executeBlocking(query).getResults();
                    assertThat(results).hasSize(1);
                    final ProductPriceExternalDiscountSetMessage message = results.get(0);
                    assertThat(message.getPriceId()).isEqualTo(priceId);
                });
                return updatedProduct;
            });
        });
    }

    @Test
    public void setDiscountedPriceWithStaged() {
        setDiscountedPriceWithStaged(true);
        setDiscountedPriceWithStaged(false);
    }

    public void setDiscountedPriceWithStaged(final Boolean staged) {
        final ProductDiscountPredicate predicate = ProductDiscountPredicate.of("1 = 1");//can be used for all products
        final ProductDiscountDraft productDiscountDraft = ProductDiscountDraft.of(randomSlug(), randomSlug(),
                predicate, ExternalProductDiscountValue.of(), randomSortOrder(), true);
        //don't forget that one product discount can be used for multiple products
        withProductDiscount(client(), productDiscountDraft, externalProductDiscount -> {
            withProductOfPrices(client(), singletonList(PriceDraft.of(EURO_40)), product -> {
                final Product publishedProduct = client().executeBlocking(ProductUpdateCommand.of(product, Publish.of()));
                final Price originalPrice = publishedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
                assertThat(originalPrice.getDiscounted()).isNull();

                final String priceId = originalPrice.getId();
                final SetDiscountedPrice action =
                        SetDiscountedPrice.of(priceId, DiscountedPrice.of(EURO_5, externalProductDiscount.toReference()), staged);
                final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(publishedProduct, action));
                final Price stagedPrice = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
                assertThat(stagedPrice.getValue()).isEqualTo(EURO_40);
                assertThat(stagedPrice.getDiscounted().getValue()).isEqualTo(EURO_5);
                assertThat(stagedPrice.getDiscounted().getDiscount()).isEqualTo(externalProductDiscount.toReference());

                final Price currentPrice = updatedProduct.getMasterData().getCurrent().getMasterVariant().getPrices().get(0);
                if (staged) {
                    assertThat(stagedPrice).isNotEqualTo(currentPrice);
                } else {
                    assertThat(stagedPrice).isEqualTo(currentPrice);
                }
            });
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
        withUpdateableProductOfMultipleVariants(client, product -> {
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

    private void withProductWithImages(final BlockingSphereClient client, final List<String> imageUrls, final Function<Product, Product> productProductFunction) {
        withUpdateableProduct(client, builder -> {
            final List<Image> imagesList = new LinkedList<>();
            for (final String imageUrl : imageUrls) {
                imagesList.add(Image.ofWidthAndHeight(imageUrl, 460, 102, "commercetools logo"));
            }
            final ProductVariantDraft oldMasterVariant = builder.getMasterVariant();
            final ProductVariantDraftBuilder variantDraftBuilder = ProductVariantDraftBuilder.of(oldMasterVariant);
            variantDraftBuilder.images(imagesList);
            variantDraftBuilder.sku(randomKey());
            return builder.masterVariant(variantDraftBuilder.build());
        }, productProductFunction);
    }

}
