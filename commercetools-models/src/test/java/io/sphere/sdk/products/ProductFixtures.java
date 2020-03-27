package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.ConcurrentModificationException;
import io.sphere.sdk.client.SphereClientUtils;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.InventoryEntryDraft;
import io.sphere.sdk.inventory.commands.InventoryEntryCreateCommand;
import io.sphere.sdk.inventory.commands.InventoryEntryDeleteCommand;
import io.sphere.sdk.models.*;
import io.sphere.sdk.productdiscounts.commands.ProductDiscountDeleteCommand;
import io.sphere.sdk.productdiscounts.queries.ProductDiscountQuery;
import io.sphere.sdk.products.attributes.AttributeDraft;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.*;
import io.sphere.sdk.products.queries.ProductByIdGet;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.products.queries.ProductQueryModel;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeFixtures;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.search.SearchKeyword;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.search.tokenizer.CustomSuggestTokenizer;
import io.sphere.sdk.search.tokenizer.WhiteSpaceSuggestTokenizer;
import io.sphere.sdk.suppliers.SimpleCottonTShirtProductDraftSupplier;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;
import io.sphere.sdk.utils.MoneyImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.*;
import java.util.stream.Stream;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.producttypes.ProductTypeFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class ProductFixtures {
    private static final Logger logger = LoggerFactory.getLogger(ProductFixtures.class);
    public static final PriceDraft PRICE = PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
    private static final int MASTER_VARIANT_ID = 1;

    public static void withUpdateableProduct(final BlockingSphereClient client,
                                             final UnaryOperator<ProductDraftBuilder> builderOp,
                                             final Function<Product, Product> f) {
        withEmptyProductType(client, productType -> {
            final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of().build();
            final ProductDraftBuilder builder = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), masterVariant);
            final ProductDraft productDraft = builderOp.apply(builder).build();
            final Product product = client.executeBlocking(ProductCreateCommand.of(productDraft));
            Product productToDelete = f.apply(product);
            if (productToDelete.getMasterData().isPublished()) {
                productToDelete = client.executeBlocking(ProductUpdateCommand.of(productToDelete, Unpublish.of()));
            }
            delete(client, productToDelete);
        });
    }

    public static void withUpdateableProductOfMultipleVariants(final BlockingSphereClient client,
                                             final Function<Product, Versioned<Product>> f) {
        withProductType(client, randomKey(), productType -> {
            final AttributeDraft greenColor = AttributeDraft.of(TShirtProductTypeDraftSupplier.Colors.ATTRIBUTE, TShirtProductTypeDraftSupplier.Colors.GREEN);
            final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                    .attributes(AttributeDraft.of(TShirtProductTypeDraftSupplier.Sizes.ATTRIBUTE, TShirtProductTypeDraftSupplier.Sizes.M),
                            greenColor)
                    .sku(randomKey())
                    .build();
            final List<ProductVariantDraft> variants = Stream.of(TShirtProductTypeDraftSupplier.Sizes.S, TShirtProductTypeDraftSupplier.Sizes.X)
                    .map(size -> AttributeDraft.of(TShirtProductTypeDraftSupplier.Sizes.ATTRIBUTE, size))
                    .map(attrDraft -> ProductVariantDraftBuilder.of()
                            .attributes(attrDraft, greenColor)
                            .sku(randomKey())
                            .build())
                    .collect(toList());

            final ProductDraftBuilder builder = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), masterVariant)
                    .variants(variants);
            final ProductDraft productDraft = builder.build();
            final Product product = client.executeBlocking(ProductCreateCommand.of(productDraft));
            Versioned<Product> productToDelete = f.apply(product);
            client.executeBlocking(ProductDeleteCommand.of(productToDelete));
        });
    }

    public static void withUpdateableProduct(final BlockingSphereClient client, final Function<Product, Product> f) {
        withUpdateableProduct(client, randomString(), f);
    }

    public static void withProduct(final BlockingSphereClient client, final Consumer<Product> productConsumer) {
        withProduct(client, randomString(), productConsumer);
    }

    public static void withTaxedProduct(final BlockingSphereClient client, final Consumer<Product> user) {
        TaxCategoryFixtures.withTransientTaxCategory(client, taxCategory ->
                        withProduct(client, randomString(), product -> {
                            final Product productWithTaxes = client.executeBlocking(createSetTaxesCommand(taxCategory, product));
                            user.accept(productWithTaxes);
                        })
        );
    }

    public static Product referenceableProduct(final BlockingSphereClient client) {
        final ProductType productType = ProductTypeFixtures.defaultProductType(client);
        final ProductVariantDraft variantDraft = ProductVariantDraftBuilder.of().price(PRICE).build();
        final String slugEn = "referenceable-product-2";
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, en("referenceable product"), en(slugEn), variantDraft).build();
        return client.executeBlocking(ProductQuery.of().bySlug(ProductProjectionType.STAGED, ENGLISH, slugEn)).head()
                .orElseGet(() -> client.executeBlocking(ProductCreateCommand.of(productDraft)));
    }

    private static ProductUpdateCommand createSetTaxesCommand(final TaxCategory taxCategory, final Product product) {
        return ProductUpdateCommand.of(product, asList(AddPrice.of(MASTER_VARIANT_ID, PRICE), SetTaxCategory.of(taxCategory.toResourceIdentifier()), Publish.of()));
    }

    public static void withUpdateableProduct(final BlockingSphereClient client, final String testName, final Function<Product, Product> f) {
        withProductType(client, randomString(), productType -> {
            withUpdateableProduct(client, new SimpleCottonTShirtProductDraftSupplier(productType, "foo" + testName + "-2"), f);
        });
    }

    public static void withProduct(final BlockingSphereClient client, final String testName, final Consumer<Product> consumer) {
        withUpdateableProduct(client, testName, consumerToFunction(consumer));
    }

    public static void withUpdateableProduct(final BlockingSphereClient client, final Supplier<? extends ProductDraft> creator, final Function<Product, Product> user) {
        final ProductDraft productDraft = creator.get();
        final String slug = englishSlugOf(productDraft);
        final PagedQueryResult<Product> pagedQueryResult = client.executeBlocking(ProductQuery.of().bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, slug));
        delete(client, pagedQueryResult.getResults());
        final Product product = client.executeBlocking(ProductCreateCommand.of(productDraft));
        final Product possiblyUpdateProduct = user.apply(product);
        delete(client, possiblyUpdateProduct);
    }

    public static void withProduct(final BlockingSphereClient client, final UnaryOperator<ProductDraftBuilder> builderMapper, final Consumer<Product> productConsumer) {
        withAttributesProductType(client, productType -> {
            final ProductDraftBuilder builder = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), ProductVariantDraftBuilder.of().build());
            final ProductDraftBuilder updatedBuilder = builderMapper.apply(builder);
            final Product product = client.executeBlocking(ProductCreateCommand.of(updatedBuilder.build()));
            productConsumer.accept(product);
            client.executeBlocking(ProductDeleteCommand.of(product));
        });
    }

    public static void withProduct(final BlockingSphereClient client, final Supplier<? extends ProductDraft> creator, final Consumer<Product> user) {
        withUpdateableProduct(client, creator, consumerToFunction(user));
    }

    public static void withProductType(final BlockingSphereClient client, final String productTypeName, final Consumer<ProductType> user) {
        ProductTypeFixtures.withProductType(client, new TShirtProductTypeDraftSupplier(productTypeName), user);
    }

    public static void delete(final BlockingSphereClient client, final List<Product> products) {
        products.forEach(product -> delete(client, product));
    }

    public static void delete(final BlockingSphereClient client, final Product product) {
        final Optional<Product> freshLoadedProduct = Optional.ofNullable(client.executeBlocking(ProductByIdGet.of(product.getId())));
        freshLoadedProduct.ifPresent(loadedProduct -> {
            final boolean isPublished = loadedProduct.getMasterData().isPublished();
            final Product unPublishedProduct;
            if (isPublished) {
                unPublishedProduct = unpublishWithRetry(client, loadedProduct, 5);
            } else {
                unPublishedProduct = loadedProduct;
            }
            deleteWithRetry(client, unPublishedProduct, 5);
        });
    }

    private static Product unpublishWithRetry(final BlockingSphereClient client, final Versioned<Product> product, final int ttl) {
        if (ttl > 0) {
            try {
                return client.executeBlocking(ProductUpdateCommand.of(product, Unpublish.of()));
            } catch(final ConcurrentModificationException e) {
                final Versioned<Product> versioned = Versioned.of(product.getId(), e.getCurrentVersion());
                return unpublishWithRetry(client, versioned, ttl - 1);
            }
        } else {
            throw new RuntimeException("cannot unpublish product due to too much concurrent updates, product: " + product);
        }
    }

    private static void deleteWithRetry(final BlockingSphereClient client, final Versioned<Product> unPublishedProduct, final int ttl) {
        if (ttl > 0) {
            try {
                client.executeBlocking(ProductDeleteCommand.of(unPublishedProduct));
            } catch(final ConcurrentModificationException e) {
                final Versioned<Product> versioned = Versioned.of(unPublishedProduct.getId(), e.getCurrentVersion());
                deleteWithRetry(client, versioned, ttl - 1);
            }
        } else {
            throw new RuntimeException("cannot delete product due to too much concurrent updates, product: " + unPublishedProduct);
        }
    }

    public static void withUpdateablePricedProduct(final BlockingSphereClient client, final Function<Product, Product> f) {
        withUpdateablePricedProduct(client, PriceDraft.of(MoneyImpl.of(123, EUR)), f);
    }

    public static void withUpdateablePricedProduct(final BlockingSphereClient client, final PriceDraft expectedPrice, final Function<Product, Product> f) {
        withUpdateableProduct(client, product -> {
            final ProductUpdateCommand command = ProductUpdateCommand.of(product, AddPrice.of(1, expectedPrice));
            return f.apply(client.executeBlocking(command));
        });
    }

    public static void deleteProductsProductTypeAndProductDiscounts(final BlockingSphereClient client, final ProductType productType) {
        client.executeBlocking(ProductDiscountQuery.of().withLimit(500L)).getResults()
                .forEach(discount -> client.executeBlocking(ProductDiscountDeleteCommand.of(discount)));

        if (productType != null) {
            QueryPredicate<Product> ofProductType = ProductQueryModel.of().productType().is(productType);
            ProductQuery productsOfProductTypeQuery = ProductQuery.of().withPredicates(ofProductType).withLimit(500L);
            do {
                final List<Product> products = client.executeBlocking(productsOfProductTypeQuery).getResults();
                final List<Product> unpublishedProducts = products.stream().map(
                        product -> {
                            if (product.getMasterData().isPublished()) {
                                return client.executeBlocking(ProductUpdateCommand.of(product, Unpublish.of()));
                            } else {
                                return product;
                            }
                        }
                ).collect(toList());

                final List<CompletionStage<Product>> stages = new LinkedList<>();

                unpublishedProducts.forEach(
                        product -> {
                            final CompletionStage<Product> completionStage = client.execute(ProductDeleteCommand.of(product));
                            stages.add(completionStage);
                        }
                );
                stages.forEach(stage -> SphereClientUtils.blockingWait(stage, 30, TimeUnit.SECONDS));

                deleteProductType(client, productType);
            } while (client.executeBlocking(productsOfProductTypeQuery).getCount() > 0);
        }
    }

    public static void withProductAndUnconnectedCategory(final BlockingSphereClient client, final BiConsumer<Product, Category> consumer) {
        final Consumer<Category> consumer1 = category -> {
            final Consumer<Product> user = product -> consumer.accept(product, category);
            withProduct(client, "withProductAndCategory", user);
        };
        withCategory(client, consumer1);
    }

    public static void withProductInCategory(final BlockingSphereClient client, final BiConsumer<Product, Category> consumer) {
        withCategory(client, category -> {
            final Consumer<Product> user = product -> consumer.accept(product, category);
            withProduct(client, "withProductAndCategory", product -> {
                final Product productWithCategory = client.executeBlocking(ProductUpdateCommand.of(product, AddToCategory.of(category)));
                consumer.accept(productWithCategory, category);
            });
        });
    }

    public static void withProductWithProductReference(final BlockingSphereClient client, final BiConsumer<Product, Product> consumer) {
        withProduct(client, referencedProduct -> {
            final ProductType productType = productReferenceProductType(client);
            final ProductVariantDraft productVariantDraft =
                    ProductVariantDraftBuilder.of().attributes(AttributeDraft.of("productreference", referencedProduct.toReference())).build();
            final ProductDraft productDraft = ProductDraftBuilder.of(productType, en("product reference name 1"), randomSlug(), productVariantDraft).build();
            final Product product = client.executeBlocking(ProductCreateCommand.of(productDraft));
            consumer.accept(product, referencedProduct);
            client.executeBlocking(ProductDeleteCommand.of(product));
        });
    }

    public static void withSuggestProduct(final BlockingSphereClient client, final Consumer<Product> consumer) {
        withEmptyProductType(client, randomKey(), productType -> {
            final SearchKeywords searchKeywords = SearchKeywords.of(
                    Locale.ENGLISH, asList(SearchKeyword.of("Multi tool"), SearchKeyword.of("Swiss Army Knife", WhiteSpaceSuggestTokenizer.of())),
                    Locale.GERMAN, singletonList(SearchKeyword.of("Schweizer Messer", CustomSuggestTokenizer.of(asList("schweizer messer", "offiziersmesser", "sackmesser"))))
            );

            final ProductDraftBuilder productDraftBuilder = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), ProductVariantDraftBuilder.of().build())
                    .searchKeywords(searchKeywords);
            withProduct(client, productDraftBuilder, consumer);
        });
    }

    public static void creatingProduct(final BlockingSphereClient client, final UnaryOperator<ProductDraftBuilder> builderMapper, final UnaryOperator<Product> op) {
        withProductType(client, randomKey(), productType -> {
            final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of().build();
            final ProductDraftBuilder builder = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), masterVariant);
            final ProductDraft productDraft = builderMapper.apply(builder).build();
            final Product product = client.executeBlocking(ProductCreateCommand.of(productDraft));
            final Product updatedProduct = op.apply(product);
            client.executeBlocking(ProductDeleteCommand.of(updatedProduct));
        });
    }

    public static void deleteProductsAndProductTypes(final BlockingSphereClient client) {
        final List<ProductType> productTypes = client.executeBlocking(ProductTypeQuery.of().withLimit(500L)).getResults();
        productTypes.forEach(productType -> deleteProductsProductTypeAndProductDiscounts(client, productType));
    }


    public static void withProductOfStock(final BlockingSphereClient client, final int availableQuantity, final Consumer<Product> productConsumer) {
        withProductOfStockAndChannel(client, availableQuantity, null, productConsumer);
    }

    public static void withProductOfStockAndChannel(final BlockingSphereClient client, final int availableQuantity, @Nullable final Referenceable<Channel> channelReferenceable, final Consumer<Product> productConsumer) {
        final Reference<Channel> channelReference = Optional.ofNullable(channelReferenceable).map(Referenceable::toReference).orElse(null);
        ProductFixtures.withProduct(client, product -> {
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final InventoryEntry inventoryEntry = client.executeBlocking(InventoryEntryCreateCommand.of(InventoryEntryDraft.of(sku, availableQuantity, null, null, channelReference)));
            productConsumer.accept(product);
            client.executeBlocking(InventoryEntryDeleteCommand.of(inventoryEntry));
        });
    }

    public static void withProductOfRestockableInDaysAndChannel(final BlockingSphereClient client, final int restockableInDays, @Nullable final Referenceable<Channel> channelReferenceable, final Consumer<Product> productConsumer) {
        final Reference<Channel> channelReference = Optional.ofNullable(channelReferenceable).map(Referenceable::toReference).orElse(null);
        ProductFixtures.withProduct(client, product -> {
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final InventoryEntry inventoryEntry = client.executeBlocking(InventoryEntryCreateCommand.of(InventoryEntryDraft.of(sku, 5, null, restockableInDays, channelReference)));
            productConsumer.accept(product);
            client.executeBlocking(InventoryEntryDeleteCommand.of(inventoryEntry));
        });
    }

    public static void withProductOfPrices(final BlockingSphereClient client, final List<PriceDraft> priceDrafts, final Consumer<Product> productConsumer) {
        withEmptyProductType(client, randomKey(), productType -> {
            final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                    .prices(priceDrafts)
                    .build();
            final ProductDraft productDraft = ProductDraftBuilder.of(productType,  randomSlug(),  randomSlug(), masterVariant)
                    .publish(true)
                    .build();
            ProductFixtures.withProduct(client, () -> productDraft, productConsumer);
        });
    }

    public static void withProductHavingAssets(final BlockingSphereClient client, final UnaryOperator<Product> op) {
        withUpdateableProduct(client, productDraftBuilder -> {
            final ProductVariantDraft masterVariant = productDraftBuilder.getMasterVariant();
            final ProductVariantDraft variantDraft = ProductVariantDraftBuilder.of(masterVariant)
                    .assets(asList(getAssetDraft1(), getAssetDraft2()))
                    .sku(randomKey())
                    .build();
            return productDraftBuilder.masterVariant(variantDraft);
        }, op);
    }
    public static void withProductHavingImages(final BlockingSphereClient client, final UnaryOperator<Product> op) {
        withUpdateableProduct(client, productDraftBuilder -> {
            final ProductVariantDraft masterVariant = productDraftBuilder.getMasterVariant();
            final ProductVariantDraft variantDraft = ProductVariantDraftBuilder.of(masterVariant)
                    .images(createExternalImage())
                    .sku(randomKey())
                    .build();
            return productDraftBuilder.masterVariant(variantDraft);
        }, op);
    }

    private static AssetDraft getAssetDraft1() {
        final AssetSource assetSource1 = AssetSourceBuilder.ofUri("https://commercetools.com/binaries/content/gallery/commercetoolswebsite/homepage/cases/rewe.jpg")
                .key(randomKey())
                .contentType("image/jpg")
                .dimensionsOfWidthAndHeight(1934, 1115)
                .build();
        final LocalizedString name = LocalizedString.ofEnglish("REWE show case");
        final LocalizedString description = LocalizedString.ofEnglish("screenshot of the REWE webshop on a mobile and a notebook");
        return AssetDraftBuilder.of(singletonList(assetSource1), name)
                                .key("asset1Key")
                                .description(description)
                                .tags("desktop-sized", "jpg-format", "REWE", "awesome")
                                .build();
    }

    private static AssetDraft getAssetDraft2() {
        final AssetSource assetSource1 = AssetSourceBuilder.ofUri("https://docs.commercetools.com/assets/img/CT-logo.svg")
                .key(randomKey())
                .contentType("image/svg+xml")
                .build();
        final LocalizedString name = LocalizedString.ofEnglish("commercetools logo");
        return AssetDraftBuilder.of(singletonList(assetSource1), name)
                                .key("asset2Key")
                                .tags("desktop-sized", "svg-format", "commercetools", "awesome")
                                .build();
    }

    public static Image createExternalImage() {
        return Image.ofWidthAndHeight("https://www.commercetools.com/assets/img/ct_logo_farbe.gif", 460, 102, "commercetools logo");
    }
}
