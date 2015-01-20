package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.commands.ChannelCreateCommand;
import io.sphere.sdk.channels.commands.ChannelDeleteByIdCommand;
import io.sphere.sdk.channels.queries.ChannelFetchByKey;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteByIdCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.*;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.products.queries.ProductQueryModel;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteByIdCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.suppliers.SimpleCottonTShirtProductDraftSupplier;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.models.LocalizedStrings.ofEnglishLocale;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.products.ProductProjectionType.*;
import static io.sphere.sdk.products.ProductUpdateScope.STAGED_AND_CURRENT;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.*;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Sizes;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SphereInternalLogger.getLogger;
import static java.util.Locale.ENGLISH;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static io.sphere.sdk.test.ReferenceAssert.assertThat;

public class ProductCrudIntegrationTest extends QueryIntegrationTest<Product> {
    public static final Random RANDOM = new Random();
    public static final int MASTER_VARIANT_ID = 1;
    private static ProductType productType;
    private final static String productTypeName = "t-shirt-" + ProductCrudIntegrationTest.class.getName();

    @BeforeClass
    public static void prepare() throws Exception {
        PagedQueryResult<ProductType> queryResult = execute(ProductTypeQuery.of().byName(productTypeName));
        queryResult.getResults().forEach(pt -> deleteProductsAndProductType(pt));
        productType = execute(ProductTypeCreateCommand.of(new TShirtProductTypeDraftSupplier(productTypeName).get()));
    }

    @AfterClass
    public static void cleanUp() {
        deleteProductsAndProductType(productType);
        productType = null;
    }

    @Override
    protected ClientRequest<Product> deleteCommand(final Product item) {
        return ProductDeleteByIdCommand.of(item);
    }

    @Override
    protected ClientRequest<Product> newCreateCommandForName(final String name) {
        return ProductCreateCommand.of(new SimpleCottonTShirtProductDraftSupplier(productType, name).get());
    }

    @Override
    protected String extractName(final Product instance) {
        return instance.getMasterData().getStaged().getName().get(ENGLISH).get();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryRequestForQueryAll() {
        return ProductQuery.of();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryObjectForName(final String name) {
        return ProductQuery.of().withPredicate(ProductQuery.model().masterData().current().name().lang(ENGLISH).is(name));
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryObjectForNames(final List<String> names) {
        return ProductQuery.of().withPredicate(ProductQuery.model().masterData().current().name().lang(ENGLISH).isOneOf(names));
    }

    @Test
    public void changeNameUpdateAction() throws Exception {
        final Product product = createInBackendByName("oldName");

        final LocalizedStrings newName = ofEnglishLocale("newName " + RANDOM.nextInt());
        final Product updatedProduct = execute(ProductUpdateCommand.of(product, ChangeName.of(newName, STAGED_AND_CURRENT)));

        assertThat(updatedProduct.getMasterData().getStaged().getName()).isEqualTo(newName);
    }

    @Test
    public void setDescriptionUpdateAction() throws Exception {
        final Product product = createInBackendByName("demo for set description");

        final LocalizedStrings newDescription = ofEnglishLocale("new description " + RANDOM.nextInt());
        final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, SetDescription.of(newDescription, STAGED_AND_CURRENT));
        final Product updatedProduct = execute(cmd);

        assertThat(updatedProduct.getMasterData().getStaged().getDescription()).isPresentAs(newDescription);
    }

    @Test
    public void addExternalImageUpdateAction() throws Exception {
        final Product product = createInBackendByName("demo for adding external image");
        assertThat(product.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);

        final Image image = Image.ofWidthAndHeight("http://www.commercetools.com/assets/img/ct_logo_farbe.gif", 460, 102, "commercetools logo");
        final Product updatedProduct = execute(ProductUpdateCommand.of(product, AddExternalImage.of(image, MASTER_VARIANT_ID, STAGED_AND_CURRENT)));

        assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).containsExactly(image);
    }

    @Test
    public void changeSlugUpdateAction() throws Exception {
        final Product product = createInBackendByName("demo for setting slug");

        final LocalizedStrings newSlug = ofEnglishLocale("new-slug-" + RANDOM.nextInt());
        final Product updatedProduct = execute(ProductUpdateCommand.of(product, ChangeSlug.of(newSlug, STAGED_AND_CURRENT)));

        assertThat(updatedProduct.getMasterData().getStaged().getSlug()).isEqualTo(newSlug);
    }

    @Test
    public void setMetaAttributesUpdateAction() throws Exception {
        final Product product = createInBackendByName("demo for setMetaAttributesUpdateAction");

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
    }

    @Test
    public void addPriceUpdateAction() throws Exception {
        final Product product = createInBackendByName("demo for addPriceUpdateAction");

        final Price expectedPrice = Price.of(MoneyImpl.of(123, EUR));
        final Product updatedProduct = client()
                .execute(ProductUpdateCommand.of(product, AddPrice.of(1, expectedPrice, STAGED_AND_CURRENT)));

        final Price actualPrice = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
        assertThat(actualPrice).isEqualTo(expectedPrice);
    }

    @Test
    public void changePriceUpdateAction() throws Exception {
        final Product product = preparePricedProduct("demo for changePriceUpdateAction");

        final Price newPrice = Price.of(MoneyImpl.of(234, EUR));
        assertThat(product.getMasterData().getStaged().getMasterVariant()
                .getPrices().stream().anyMatch(p -> p.equals(newPrice))).isFalse();

        final Product updatedProduct = client()
                .execute(ProductUpdateCommand.of(product, ChangePrice.of(MASTER_VARIANT_ID, newPrice, STAGED_AND_CURRENT)));

        final Price actualPrice = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
        assertThat(actualPrice).isEqualTo(newPrice);
    }

    @Test
    public void removePriceUpdateAction() throws Exception {
        final Product product = preparePricedProduct("demo for removePriceUpdateAction");

        final Price oldPrice = product.getMasterData().getStaged().getMasterVariant().getPrices().get(0);

        final Product updatedProduct = client()
                .execute(ProductUpdateCommand.of(product, RemovePrice.of(MASTER_VARIANT_ID, oldPrice, STAGED_AND_CURRENT)));

        assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant()
                .getPrices().stream().anyMatch(p -> p.equals(oldPrice))).isFalse();
    }

    @Test
    public void addToCategoryUpdateAction() throws Exception {
        withProductAndCategory((final Product product, final Category category) -> {

            assertThat(product.getMasterData().getStaged().getCategories()).isEmpty();

            final Product updatedProduct = client()
                    .execute(ProductUpdateCommand.of(product, AddToCategory.of(category, STAGED_AND_CURRENT)));

            assertThat(updatedProduct.getMasterData().getStaged().getCategories().get(0)).references(category);
        });
    }

    @Test
    public void assignPricesToMasterVariantAccordingToAChannel() throws Exception {
        final String channelKey = "assignPricesToMasterVariantAccordingToAChannel";
        cleanUpChannelByKey(channelKey);
        final Product product = createInBackendByName("assignPricesToMasterVariantAccordingToAChannel");
        final Channel channel = execute(ChannelCreateCommand.of(ChannelDraft.of(channelKey)));
        final Price price = Price.of(MoneyImpl.of(523, EUR)).withChannel(channel);
        final Product updatedProduct = execute(ProductUpdateCommand.of(product, AddPrice.of(MASTER_VARIANT_ID, price, STAGED_AND_CURRENT)));
        assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0).getChannel()).isPresentAs(channel.toReference());
        execute(ProductUpdateCommand.of(updatedProduct, RemovePrice.of(MASTER_VARIANT_ID, price, STAGED_AND_CURRENT)));
        cleanUpChannelByKey(channelKey);
    }

    @Test
    public void testPublishAndUnpublish() throws Exception {
        final Product product = createInBackendByName("testPublishAndUnPublish");
        assertThat(product.getMasterData().isPublished()).isFalse();

        final Product publishedProduct = execute(ProductUpdateCommand.of(product, Publish.of()));
        assertThat(publishedProduct.getMasterData().isPublished()).isTrue();

        final Product unpublishedProduct = execute(ProductUpdateCommand.of(publishedProduct, Unpublish.of()));
        assertThat(unpublishedProduct.getMasterData().isPublished()).isFalse();
    }

    @Test
    public void queryBySku() {
        final String sku = "sku2000";
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .sku(sku)
                .plusAttribute(Sizes.ATTRIBUTE.valueOf(Sizes.S))
                .plusAttribute(Colors.ATTRIBUTE.valueOf(Colors.GREEN)).build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, en("foo"), en("foo-slug"), masterVariant).build();
        execute(ProductCreateCommand.of(productDraft));
        final PagedQueryResult<Product> result = execute(ProductQuery.of().bySku(sku, STAGED));
        assertThat(result.getResults()).hasSize(1);
        assertThat(result.getResults().get(0).getMasterData().getStaged().getMasterVariant().getSku()).isPresentAs(sku);
        assertThat(result.getResults().get(0).getMasterData().getStaged().getMasterVariant().getAttribute(Colors.ATTRIBUTE)).isPresentAs(Colors.GREEN);
        assertThat(result.getResults().get(0).getMasterData().getStaged().getMasterVariant().getAttribute(Sizes.ATTRIBUTE)).isPresentAs(Sizes.S);
    }

    public void cleanUpChannelByKey(final String channelKey) {
        execute(ChannelFetchByKey.of(channelKey)).ifPresent(channel -> execute(ChannelDeleteByIdCommand.of(channel)));
    }

    private void withProductAndCategory(final BiConsumer<Product, Category> consumer) {
        final Consumer<Category> consumer1 = category -> {
            final Consumer<Product> user = product -> consumer.accept(product, category);
            withProduct(client(), "withProductAndCategory", user);
        };
        withCategory(client(), consumer1);
    }

    private Product preparePricedProduct(final String name) {
        final Product product = createInBackendByName(name);
        final Price expectedPrice = Price.of(MoneyImpl.of(123, EUR));
        return execute(ProductUpdateCommand.of(product, AddPrice.of(1, expectedPrice, STAGED_AND_CURRENT)));
    }

    public static void deleteProductsAndProductType(final ProductType productType) {
        if (productType != null) {
            ProductQueryModel productQueryModelProductQueryModel = ProductQuery.model();
            Predicate<Product> ofProductType = productQueryModelProductQueryModel.productType().is(productType);
            QueryDsl<Product> productsOfProductTypeQuery = ProductQuery.of().withPredicate(ofProductType);
            List<Product> products = execute(productsOfProductTypeQuery).getResults();
            products.forEach(
                    product -> execute(ProductDeleteByIdCommand.of(product))
            );
            deleteProductType(productType);
        }
    }

    static void deleteProductType(ProductType productType) {

        try {
            execute(ProductTypeDeleteByIdCommand.of(productType));
        } catch (Exception e) {
            getLogger("test.fixtures").debug(() -> "no product type to delete");
        }
    }
}
