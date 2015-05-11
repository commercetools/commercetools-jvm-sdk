package io.sphere.sdk.products;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.commands.ChannelCreateCommand;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.*;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.suppliers.SimpleCottonTShirtProductDraftSupplier;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.products.ProductProjectionType.*;
import static io.sphere.sdk.products.ProductUpdateScope.STAGED_AND_CURRENT;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.*;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Sizes;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SphereInternalLogger.getLogger;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.channels.ChannelFixtures.*;
import static io.sphere.sdk.products.ProductFixtures.*;

public class ProductCrudIntegrationTest extends QueryIntegrationTest<Product> {
    public static final Random RANDOM = new Random();
    private static ProductType productType;
    private final static String productTypeName = "t-shirt-" + ProductCrudIntegrationTest.class.getName();

    @BeforeClass
    public static void prepare() throws Exception {
        PagedQueryResult<ProductType> queryResult = execute(ProductTypeQuery.of().byName(productTypeName));
        queryResult.getResults().forEach(pt -> deleteProductsAndProductType(client(), pt));
        productType = execute(ProductTypeCreateCommand.of(new TShirtProductTypeDraftSupplier(productTypeName).get()));
    }

    @AfterClass
    public static void cleanUp() {
        deleteProductsAndProductType(client(), productType);
        productType = null;
    }

    @Override
    protected SphereRequest<Product> deleteCommand(final Product item) {
        return ProductDeleteCommand.of(item);
    }

    @Override
    protected SphereRequest<Product> newCreateCommandForName(final String name) {
        return ProductCreateCommand.of(new SimpleCottonTShirtProductDraftSupplier(productType, name).get());
    }

    @Override
    protected String extractName(final Product instance) {
        return instance.getMasterData().getStaged().getName().get(ENGLISH).get();
    }

    @Override
    protected SphereRequest<PagedQueryResult<Product>> queryRequestForQueryAll() {
        return ProductQuery.of();
    }

    @Override
    protected SphereRequest<PagedQueryResult<Product>> queryObjectForName(final String name) {
        return ProductQuery.of().withPredicate(ProductQuery.model().masterData().current().name().lang(ENGLISH).is(name));
    }

    @Override
    protected SphereRequest<PagedQueryResult<Product>> queryObjectForNames(final List<String> names) {
        return ProductQuery.of().withPredicate(ProductQuery.model().masterData().current().name().lang(ENGLISH).isOneOf(names));
    }

    @Test
    public void assignPricesToMasterVariantAccordingToAChannel() throws Exception {
        final String channelKey = "assignPricesToMasterVariantAccordingToAChannel";
        cleanUpChannelByKey(client(), channelKey);
        final Product product = createInBackendByName("assignPricesToMasterVariantAccordingToAChannel");
        final Channel channel = execute(ChannelCreateCommand.of(ChannelDraft.of(channelKey)));
        final Price price = Price.of(MoneyImpl.of(523, EUR)).withChannel(channel);
        final Product updatedProduct = execute(ProductUpdateCommand.of(product, AddPrice.of(MASTER_VARIANT_ID, price, STAGED_AND_CURRENT)));
        assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0).getChannel()).contains(channel.toReference());
        execute(ProductUpdateCommand.of(updatedProduct, RemovePrice.of(MASTER_VARIANT_ID, price, STAGED_AND_CURRENT)));
        cleanUpChannelByKey(client(), channelKey);
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
        assertThat(result.getResults().get(0).getMasterData().getStaged().getMasterVariant().getSku()).contains(sku);
        assertThat(result.getResults().get(0).getMasterData().getStaged().getMasterVariant().getAttribute(Colors.ATTRIBUTE)).contains(Colors.GREEN);
        assertThat(result.getResults().get(0).getMasterData().getStaged().getMasterVariant().getAttribute(Sizes.ATTRIBUTE)).contains(Sizes.S);
    }
}
