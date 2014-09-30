package io.sphere.sdk.products;

import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.NewCategoryBuilder;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.NewChannel;
import io.sphere.sdk.channels.commands.ChannelCreateCommand;
import io.sphere.sdk.channels.commands.ChannelDeleteByIdCommand;
import io.sphere.sdk.channels.queries.FetchChannelByKey;
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
import io.sphere.sdk.suppliers.SimpleCottonTShirtNewProductSupplier;
import io.sphere.sdk.suppliers.TShirtNewProductTypeSupplier;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.sphere.sdk.models.LocalizedString.ofEnglishLocale;
import static io.sphere.sdk.products.ProductProjectionType.*;
import static io.sphere.sdk.suppliers.TShirtNewProductTypeSupplier.*;
import static io.sphere.sdk.suppliers.TShirtNewProductTypeSupplier.Sizes;
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
        PagedQueryResult<ProductType> queryResult = client().execute(new ProductTypeQuery().byName(productTypeName));
        queryResult.getResults().forEach(pt -> deleteProductsAndProductType(pt));
        productType = client().execute(new ProductTypeCreateCommand(new TShirtNewProductTypeSupplier(productTypeName).get()));
    }

    @AfterClass
    public static void cleanUp() {
        deleteProductsAndProductType(productType);
        productType = null;
    }

    @Override
    protected ClientRequest<Product> deleteCommand(final Product item) {
        return new ProductDeleteByIdCommand(item);
    }

    @Override
    protected ClientRequest<Product> newCreateCommandForName(final String name) {
        return new ProductCreateCommand(new SimpleCottonTShirtNewProductSupplier(productType, name).get());
    }

    @Override
    protected String extractName(final Product instance) {
        return instance.getMasterData().getStaged().getName().get(ENGLISH).get();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryRequestForQueryAll() {
        return new ProductQuery();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryObjectForName(final String name) {
        return new ProductQuery().withPredicate(ProductQuery.model().masterData().current().name().lang(ENGLISH).is(name));
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryObjectForNames(final List<String> names) {
        return new ProductQuery().withPredicate(ProductQuery.model().masterData().current().name().lang(ENGLISH).isOneOf(names));
    }

    @Test
    public void changeNameUpdateAction() throws Exception {
        final Product product = createInBackendByName("oldName");

        final LocalizedString newName = ofEnglishLocale("newName " + RANDOM.nextInt());
        final Product updatedProduct = client().execute(new ProductUpdateCommand(product, ChangeName.of(newName)));

        assertThat(updatedProduct.getMasterData().getStaged().getName()).isEqualTo(newName);
    }

    @Test
    public void setDescriptionUpdateAction() throws Exception {
        final Product product = createInBackendByName("demo for set description");

        final LocalizedString newDescription = ofEnglishLocale("new description " + RANDOM.nextInt());
        final ProductUpdateCommand cmd = new ProductUpdateCommand(product, SetDescription.of(newDescription));
        final Product updatedProduct = client().execute(cmd);

        assertThat(updatedProduct.getMasterData().getStaged().getDescription()).isPresentAs(newDescription);
    }


    @Test
    public void changeSlugUpdateAction() throws Exception {
        final Product product = createInBackendByName("demo for setting slug");

        final LocalizedString newSlug = ofEnglishLocale("new-slug-" + RANDOM.nextInt());
        final Product updatedProduct = client().execute(new ProductUpdateCommand(product, ChangeSlug.of(newSlug)));

        assertThat(updatedProduct.getMasterData().getStaged().getSlug()).isEqualTo(newSlug);
    }

    @Test
    public void setMetaAttributesUpdateAction() throws Exception {
        final Product product = createInBackendByName("demo for setMetaAttributesUpdateAction");

        final MetaAttributes metaAttributes = MetaAttributes.of(ENGLISH,
                "commercetools SPHERE.IO&#8482; - Next generation eCommerce",
                "SPHERE.IO&#8482; is the first and leading Platform-as-a-Service solution for eCommerce.",
                "Platform-as-a-Service, e-commerce, http, api, tool");
        final Product updatedProduct = client()
                .execute(new ProductUpdateCommand(product, SetMetaAttributes.of(metaAttributes)));

        final ProductData productData = updatedProduct.getMasterData().getStaged();
        assertThat(productData.getMetaTitle()).isEqualTo(metaAttributes.getMetaTitle());
        assertThat(productData.getMetaDescription()).isEqualTo(metaAttributes.getMetaDescription());
        assertThat(productData.getMetaKeywords()).isEqualTo(metaAttributes.getMetaKeywords());
    }

    @Test
    public void addPriceUpdateAction() throws Exception {
        final Product product = createInBackendByName("demo for addPriceUpdateAction");

        final Price expectedPrice = Price.of(Money.fromCents(123, "EUR"));
        final Product updatedProduct = client()
                .execute(new ProductUpdateCommand(product, AddPrice.of(1, expectedPrice)));

        final Price actualPrice = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
        assertThat(actualPrice).isEqualTo(expectedPrice);
    }

    @Test
    public void changePriceUpdateAction() throws Exception {
        final Product product = preparePricedProduct("demo for changePriceUpdateAction");

        final Price newPrice = Price.of(Money.fromCents(234, "EUR"));
        assertThat(product.getMasterData().getStaged().getMasterVariant()
                .getPrices().stream().anyMatch(p -> p.equals(newPrice))).isFalse();

        final Product updatedProduct = client()
                .execute(new ProductUpdateCommand(product, ChangePrice.of(MASTER_VARIANT_ID, newPrice)));

        final Price actualPrice = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
        assertThat(actualPrice).isEqualTo(newPrice);
    }

    @Test
    public void removePriceUpdateAction() throws Exception {
        final Product product = preparePricedProduct("demo for removePriceUpdateAction");

        final Price oldPrice = product.getMasterData().getStaged().getMasterVariant().getPrices().get(0);

        final Product updatedProduct = client()
                .execute(new ProductUpdateCommand(product, RemovePrice.of(MASTER_VARIANT_ID, oldPrice)));

        assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant()
                .getPrices().stream().anyMatch(p -> p.equals(oldPrice))).isFalse();
    }

    @Test
    public void addToCategoryUpdateAction() throws Exception {
        withProductAndCategory((final Product product, final Category category) -> {

            assertThat(product.getMasterData().getStaged().getCategories()).isEmpty();

            final Product updatedProduct = client()
                    .execute(new ProductUpdateCommand(product, AddToCategory.of(category)));

            assertThat(updatedProduct.getMasterData().getStaged().getCategories().get(0)).references(category);
        });
    }

    @Test
    public void assignPricesToMasterVariantAccordingToAChannel() throws Exception {
        final String channelKey = "assignPricesToMasterVariantAccordingToAChannel";
        cleanUpChannelByKey(channelKey);
        final Product product = createInBackendByName("assignPricesToMasterVariantAccordingToAChannel");
        final Channel channel = client().execute(new ChannelCreateCommand(NewChannel.of(channelKey)));
        final Price price = Price.of(Money.fromCents(523, "EUR")).withChannel(channel);
        final Product updatedProduct = client().execute(new ProductUpdateCommand(product, AddPrice.of(MASTER_VARIANT_ID, price)));
        assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0).getChannel()).isPresentAs(channel.toReference());
        client().execute(new ProductUpdateCommand(updatedProduct, RemovePrice.of(MASTER_VARIANT_ID, price)));
        cleanUpChannelByKey(channelKey);
    }

    @Test
    public void testPublishAndUnpublish() throws Exception {
        final Product product = createInBackendByName("testPublishAndUnPublish");
        assertThat(product.getMasterData().isPublished()).isFalse();

        final Product publishedProduct = client().execute(new ProductUpdateCommand(product, Publish.of()));
        assertThat(publishedProduct.getMasterData().isPublished()).isTrue();

        final Product unpublishedProduct = client().execute(new ProductUpdateCommand(publishedProduct, Unpublish.of()));
        assertThat(unpublishedProduct.getMasterData().isPublished()).isFalse();
    }

    @Test
    public void queryBySku() {
        final String sku = "sku2000";
        final NewProductVariant masterVariant = NewProductVariantBuilder.of()
                .sku(sku)
                .plusAttribute(Sizes.ATTRIBUTE.valueOf(Sizes.S))
                .plusAttribute(Colors.ATTRIBUTE.valueOf(Colors.GREEN)).build();
        final NewProduct newProduct = NewProductBuilder.of(productType, en("foo"), en("foo-slug"), masterVariant).build();
        client().execute(new ProductCreateCommand(newProduct));
        final PagedQueryResult<Product> result = client().execute(new ProductQuery().bySku(sku, STAGED));
        assertThat(result.getResults()).hasSize(1);
        assertThat(result.getResults().get(0).getMasterData().getStaged().getMasterVariant().getSku()).isPresentAs(sku);
        assertThat(result.getResults().get(0).getMasterData().getStaged().getMasterVariant().getAttribute(Colors.ATTRIBUTE)).isPresentAs(Colors.GREEN);
        assertThat(result.getResults().get(0).getMasterData().getStaged().getMasterVariant().getAttribute(Sizes.ATTRIBUTE)).isPresentAs(Sizes.S);
    }

    public void cleanUpChannelByKey(final String channelKey) {
        client().execute(new FetchChannelByKey(channelKey)).ifPresent(channel -> client().execute(new ChannelDeleteByIdCommand(channel)));
    }

    private void withProductAndCategory(final BiConsumer<Product, Category> consumer) {
        withCategory(category -> {
            ProductReferenceExpansionTest.withProduct(client(), "withProductAndCategory", product -> consumer.accept(product, category));
        });
    }

    static void withCategory(final Consumer<Category> consumer) {
        final NewCategoryBuilder catSupplier = NewCategoryBuilder.of(en("1"), en("level1"));
        CategoryFixtures.withCategory(client(), catSupplier, consumer);
    }


    private Product preparePricedProduct(final String name) {
        final Product product = createInBackendByName(name);
        final Price expectedPrice = Price.of(Money.fromCents(123, "EUR"));
        return client().execute(new ProductUpdateCommand(product, AddPrice.of(1, expectedPrice)));
    }

    public static void deleteProductsAndProductType(final ProductType productType) {
        if (productType != null) {
            ProductQueryModel productQueryModelProductQueryModel = ProductQuery.model();
            Predicate<Product> ofProductType = productQueryModelProductQueryModel.productType().is(productType);
            QueryDsl<Product> productsOfProductTypeQuery = new ProductQuery().withPredicate(ofProductType);
            List<Product> products = client().execute(productsOfProductTypeQuery).getResults();
            products.forEach(
                    product -> client().execute(new ProductDeleteByIdCommand(product))
            );
            deleteProductType(productType);
        }
    }

    static void deleteProductType(ProductType productType) {

        try {
            client().execute(new ProductTypeDeleteByIdCommand(productType));
        } catch (Exception e) {
            getLogger("test.fixtures").debug(() -> "no product type to delete");
        }
    }
}
