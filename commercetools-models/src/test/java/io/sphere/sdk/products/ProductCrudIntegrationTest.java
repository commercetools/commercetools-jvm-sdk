package io.sphere.sdk.products;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.commands.ChannelCreateCommand;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.AddPrice;
import io.sphere.sdk.products.commands.updateactions.RemovePrice;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.suppliers.SimpleCottonTShirtProductDraftSupplier;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import io.sphere.sdk.utils.SphereInternalUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;

import static io.sphere.sdk.channels.ChannelFixtures.cleanUpChannelByKey;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductFixtures.deleteProductsProductTypeAndProductDiscounts;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Colors;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Sizes;
import static io.sphere.sdk.test.SphereTestUtils.MASTER_VARIANT_ID;
import static io.sphere.sdk.test.SphereTestUtils.en;
import static io.sphere.sdk.utils.SphereInternalLogger.getLogger;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductCrudIntegrationTest extends IntegrationTest {
    private static ProductType productType;
    private final static String productTypeName = "t-shirt-" + ProductCrudIntegrationTest.class.getName();

    @BeforeClass
    public static void prepare() throws Exception {
        PagedQueryResult<ProductType> queryResult = client().executeBlocking(ProductTypeQuery.of().byName(productTypeName));
        queryResult.getResults().forEach(pt -> deleteProductsProductTypeAndProductDiscounts(client(), pt));
        productType = client().executeBlocking(ProductTypeCreateCommand.of(new TShirtProductTypeDraftSupplier(productTypeName).get()));
    }

    @AfterClass
    public static void cleanUp() {
        deleteProductsProductTypeAndProductDiscounts(client(), productType);
        productType = null;
    }

    protected SphereRequest<Product> deleteCommand(final Product item) {
        return ProductDeleteCommand.of(item);
    }

    protected SphereRequest<Product> newCreateCommandForName(final String name) {
        return ProductCreateCommand.of(new SimpleCottonTShirtProductDraftSupplier(productType, name).get());
    }

    protected String extractName(final Product instance) {
        return instance.getMasterData().getStaged().getName().find(ENGLISH).orElse("NOTPRESENTNAME");
    }

    protected SphereRequest<PagedQueryResult<Product>> queryRequestForQueryAll() {
        return ProductQuery.of();
    }

    protected SphereRequest<PagedQueryResult<Product>> queryObjectForName(final String name) {
        return ProductQuery.of().withPredicates(m -> m.masterData().current().name().lang(ENGLISH).is(name));
    }

    protected SphereRequest<PagedQueryResult<Product>> queryObjectForNames(final List<String> names) {
        return ProductQuery.of().withPredicates(m -> m.masterData().current().name().lang(ENGLISH).isIn(names));
    }

    @Test
    public void assignPricesToMasterVariantAccordingToAChannel() throws Exception {
        final String channelKey = "assignPricesToMasterVariantAccordingToAChannel";
        cleanUpChannelByKey(client(), channelKey);
        final Product product = createInBackendByName("assignPricesToMasterVariantAccordingToAChannel");
        final Channel channel = client().executeBlocking(ChannelCreateCommand.of(ChannelDraft.of(channelKey)));
        final PriceDraft price = PriceDraft.of(MoneyImpl.of(523, EUR)).withChannel(channel);
        final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, AddPrice.of(MASTER_VARIANT_ID, price)));
        final Price readPrice = updatedProduct.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
        assertThat(readPrice.getChannel()).isEqualTo(channel.toReference());
        client().executeBlocking(ProductUpdateCommand.of(updatedProduct, RemovePrice.of(readPrice)));
        cleanUpChannelByKey(client(), channelKey);
    }


    @Test
    public void queryBySku() {
        final String sku = "sku2000";
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .sku(sku)
                .attributes(Sizes.ATTRIBUTE.draftOf(Sizes.S), Colors.ATTRIBUTE.draftOf(Colors.GREEN))
                .build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, en("foo"), en("foo-slug"), masterVariant).build();
        client().executeBlocking(ProductCreateCommand.of(productDraft));
        final PagedQueryResult<Product> result = client().executeBlocking(ProductQuery.of().bySku(sku, STAGED));
        assertThat(result.getResults()).hasSize(1);
        assertThat(result.getResults().get(0).getMasterData().getStaged().getMasterVariant().getSku()).contains(sku);
        assertThat(result.getResults().get(0).getMasterData().getStaged().getMasterVariant().findAttribute(Colors.ATTRIBUTE)).contains(Colors.GREEN);
        assertThat(result.getResults().get(0).getMasterData().getStaged().getMasterVariant().findAttribute(Sizes.ATTRIBUTE)).contains(Sizes.S);
    }

    @Test
    public void queryByNameScenario() {
        assertModelsNotPresent();
        final List<Product> instances = createInBackendByName(modelNames());
        final List<String> actualNames = instances.stream().map(o -> extractName(o)).
                filter(name -> modelNames().contains(name)).sorted().collect(toList());
        final StringJoiner joiner = new StringJoiner(", ");
        modelNames().forEach(item -> joiner.add(item.toString()));
        assertThat(actualNames).
                overridingErrorMessage(String.format("The test requires instances with the names %s.", "[" + joiner.toString() + "]")).
                isEqualTo(modelNames());
        final String nameToFind = modelNames().get(1);
        final List<Product> results = client().executeBlocking(queryObjectForName(nameToFind)).getResults();
        assertThat(results).hasSize(1);
        assertThat(getNames(results)).isEqualTo(asList(nameToFind));
        assertModelsNotPresent();
    }

    /**
     * Removes all items with the name in {@code names}
     * Should not throw exceptions if the elements are not existing.
     * @param names the names of the items to delete
     */
    protected void cleanUpByName(final List<String> names){
        client().executeBlocking(queryObjectForNames(names)).getResults().forEach(item -> delete(item));
    }

    protected void delete(Product item) {
        try {
            client().executeBlocking(deleteCommand(item));
        } catch (final Exception e) {
            getLogger("test.fixtures").warn(() -> String.format("tried to delete %s but an Exception occurred: %s", item, e.toString()));
        }
    }

    protected List<Product> createInBackendByName(final List<String> names) {
        return names.stream().map(name -> client().executeBlocking(newCreateCommandForName(name))).collect(toList());
    }

    protected io.sphere.sdk.products.Product createInBackendByName(final String name) {
        return createInBackendByName(asList(name)).get(0);
    }

    private String sluggedClassName() {
        final String className = this.getClass().toString();
        return SphereInternalUtils.slugify(className);
    }

    protected List<String> modelNames(){
        return IntStream.of(1, 2, 3).mapToObj(i -> sluggedClassName() + i).collect(toList());
    }

    protected List<String> getNames(final List<Product> elements) {
        return elements.stream().map(o -> extractName(o)).collect(toList());
    }

    private void assertModelsNotPresent() {
        cleanUpByName(modelNames());
        assertThat(getNames(client().executeBlocking(queryRequestForQueryAll()).getResults())).overridingErrorMessage("the instances with the names " + modelNames() + " should not be present.").doesNotContainAnyElementsOf(modelNames());
    }
}
