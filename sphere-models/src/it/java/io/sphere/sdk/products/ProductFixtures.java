package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.TestClient;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.*;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.producttypes.ProductTypeFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class ProductFixtures {
    public static final Price PRICE = Price.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
    private static final int MASTER_VARIANT_ID = 1;

    public static void withUpdateableProduct(final TestClient client, final Function<Product, Product> f) {
        withUpdateableProduct(client, randomString(), f);
    }

    public static void withProduct(final TestClient client, final Consumer<Product> user) {
        withProduct(client, randomString(), user);
    }

    public static void withTaxedProduct(final TestClient client, final Consumer<Product> user) {
        TaxCategoryFixtures.withTransientTaxCategory(client, taxCategory ->
                        withProduct(client, randomString(), product -> {
                            final Product productWithTaxes = client.execute(createSetTaxesCommand(taxCategory, product));
                            user.accept(productWithTaxes);
                        })
        );
    }

    public static Product referenceableProduct(final TestClient client) {
        final ProductType productType = ProductTypeFixtures.defaultProductType(client);
        final ProductVariantDraft variantDraft = ProductVariantDraftBuilder.of().price(PRICE).build();
        final String slugEn = "referenceable-product-2";
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, en("referenceable product"), en(slugEn), variantDraft).build();
        return client.execute(ProductQuery.of().bySlug(ProductProjectionType.STAGED, ENGLISH, slugEn)).head()
                .orElseGet(() -> client.execute(ProductCreateCommand.of(productDraft)));
    }

    private static ProductUpdateCommand createSetTaxesCommand(final TaxCategory taxCategory, final Product product) {
        return ProductUpdateCommand.of(product, asList(AddPrice.of(MASTER_VARIANT_ID, PRICE), SetTaxCategory.of(taxCategory), Publish.of()));
    }

    public static void withUpdateableProduct(final TestClient client, final String testName, final Function<Product, Product> f) {
        withProductType(client, randomString(), productType -> {
            withUpdateableProduct(client, new SimpleCottonTShirtProductDraftSupplier(productType, "foo" + testName + "-2"), f);
        });
    }

    public static void withProduct(final TestClient client, final String testName, final Consumer<Product> consumer) {
        withUpdateableProduct(client, testName, consumerToFunction(consumer));
    }

    public static void withUpdateableProduct(final TestClient client, final Supplier<ProductDraft> creator, final Function<Product, Product> user) {
        final ProductDraft productDraft = creator.get();
        final String slug = englishSlugOf(productDraft);
        final PagedQueryResult<Product> pagedQueryResult = client.execute(ProductQuery.of().bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, slug));
        delete(client, pagedQueryResult.getResults());
        final Product product = client.execute(ProductCreateCommand.of(productDraft));
        final Product possiblyUpdateProduct = user.apply(product);
        delete(client, possiblyUpdateProduct);
    }

    public static void withProduct(final TestClient client, final Supplier<ProductDraft> creator, final Consumer<Product> user) {
        withUpdateableProduct(client, creator, consumerToFunction(user));
    }

    public static void withProductType(final TestClient client, final String productTypeName, final Consumer<ProductType> user) {
        ProductTypeFixtures.withProductType(client, new TShirtProductTypeDraftSupplier(productTypeName), user);
    }

    public static void delete(final TestClient client, final List<Product> products) {
        products.forEach(product -> delete(client, product));
    }

    public static void delete(final TestClient client, final Product product) {
        final Optional<Product> freshLoadedProduct = Optional.ofNullable(client.execute(ProductByIdGet.of(product.getId())));
        freshLoadedProduct.ifPresent(loadedProduct -> {
            final boolean isPublished = loadedProduct.getMasterData().isPublished();
            final Product unPublishedProduct;
            if (isPublished) {
                unPublishedProduct = client.execute(ProductUpdateCommand.of(loadedProduct, asList(Unpublish.of())));
            } else {
                unPublishedProduct = loadedProduct;
            }
            client.execute(ProductDeleteCommand.of(unPublishedProduct));
        });
    }

    public static void withUpdateablePricedProduct(final TestClient client, final Function<Product, Product> f) {
        withUpdateablePricedProduct(client, Price.of(MoneyImpl.of(123, EUR)), f);
    }

    public static void withUpdateablePricedProduct(final TestClient client, final Price expectedPrice, final Function<Product, Product> f) {
        withUpdateableProduct(client, product -> {
            final ProductUpdateCommand command = ProductUpdateCommand.of(product, AddPrice.of(1, expectedPrice));
            return f.apply(client.execute(command));
        });
    }

    public static void deleteProductsAndProductType(final TestClient client, final ProductType productType) {
        if (productType != null) {
            QueryPredicate<Product> ofProductType = ProductQueryModel.of().productType().is(productType);
            ProductQuery productsOfProductTypeQuery = ProductQuery.of().withPredicates(ofProductType);
            List<Product> products = client.execute(productsOfProductTypeQuery).getResults();
            products.forEach(
                    product -> client.execute(ProductDeleteCommand.of(product))
            );
            deleteProductType(client, productType);
        }
    }

    public static void withProductAndUnconnectedCategory(final TestClient client, final BiConsumer<Product, Category> consumer) {
        final Consumer<Category> consumer1 = category -> {
            final Consumer<Product> user = product -> consumer.accept(product, category);
            withProduct(client, "withProductAndCategory", user);
        };
        withCategory(client, consumer1);
    }

    public static void withProductInCategory(final TestClient client, final BiConsumer<Product, Category> consumer) {
        withCategory(client, category -> {
            final Consumer<Product> user = product -> consumer.accept(product, category);
            withProduct(client, "withProductAndCategory", product -> {
                final Product productWithCategory = client.execute(ProductUpdateCommand.of(product, AddToCategory.of(category)));
                consumer.accept(productWithCategory, category);
            });
        });
    }

    public static void withProductWithProductReference(final TestClient client, final BiConsumer<Product, Product> consumer) {
        withProduct(client, referencedProduct -> {
            final ProductType productType = productReferenceProductType(client);
            final ProductVariantDraft productVariantDraft =
                    ProductVariantDraftBuilder.of().attributes(AttributeDraft.of("productreference", referencedProduct.toReference())).build();
            final ProductDraft productDraft = ProductDraftBuilder.of(productType, en("product reference name 1"), randomSlug(), productVariantDraft).build();
            final Product product = client.execute(ProductCreateCommand.of(productDraft));
            consumer.accept(product, referencedProduct);
            client.execute(ProductDeleteCommand.of(product));
        });
    }

    public static void withSuggestProduct(final TestClient client, final Consumer<Product> consumer) {
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
}
