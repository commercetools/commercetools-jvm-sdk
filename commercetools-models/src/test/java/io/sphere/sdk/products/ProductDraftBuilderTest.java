package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.search.SearchKeyword;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.states.State;
import io.sphere.sdk.taxcategories.TaxCategory;
import org.junit.Test;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.IntStream;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductDraftBuilderTest {

    @Test
    public void createsWithAllVariants() {
        final List<ProductVariantDraft> allVariants = IntStream.range(0, 9)
                .mapToObj(i -> ProductVariantDraftBuilder.of().sku("sku-" + i).build())
                .collect(toList());
        final Reference<ProductType> productType = ProductType.referenceOfId("product-type-id");
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), allVariants).build();
        assertThat(productDraft.getMasterVariant().getSku()).isEqualTo("sku-0");
        assertThat(productDraft.getVariants().get(3).getSku()).isEqualTo("sku-4");
    }

    @Test
    public void createsFromProductDraft() throws Exception {
        final LocalizedString name = randomSlug();
        final LocalizedString slug = randomSlug();
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of().sku("master-variant-sku").build();
        final LocalizedString description = LocalizedString.ofEnglish(randomString());
        final LocalizedString metaTitle = LocalizedString.ofEnglish(randomString());
        final LocalizedString metaDescription = LocalizedString.ofEnglish(randomString());
        final LocalizedString metaKeywords = LocalizedString.ofEnglish(randomString());
        final Set<ResourceIdentifier<Category>> categories = asSet(Category.referenceOfId("category-id"));
        final Reference<ProductType> productType = ProductType.referenceOfId("product-type-id");
        final Reference<TaxCategory> taxCategory = TaxCategory.referenceOfId("tax-category-id");
        final SearchKeywords searchKeywords = SearchKeywords.of(Locale.ENGLISH, singletonList(SearchKeyword.of(randomString())));
        final Reference<State> state = State.referenceOfId("state-reference-id");
        final CategoryOrderHints categoryOrderHints = CategoryOrderHints.of("category-id", "5");
        final String key = randomKey();
        final ProductDraftBuilder productDraftBuilder = ProductDraftBuilder.of(productType, name, slug, masterVariant)
                .description(description)
                .metaTitle(metaTitle)
                .metaDescription(metaDescription)
                .metaKeywords(metaKeywords)
                .categories(categories)
                .taxCategory(taxCategory)
                .searchKeywords(searchKeywords)
                .state(state)
                .categoryOrderHints(categoryOrderHints)
                .publish(true)
                .key(key);
        final ProductDraft productDraft = productDraftBuilder.build();
        assertThat(productDraft.getName()).isEqualTo(name);
        assertThat(productDraft.getSlug()).isEqualTo(slug);
        assertThat(productDraft.getProductType()).isEqualTo(productType);
        assertThat(productDraft.getMasterVariant().getSku()).isEqualTo("master-variant-sku");
        assertThat(productDraft.getVariants()).isEmpty();
        assertThat(productDraft.getDescription()).isEqualTo(description);
        assertThat(productDraft.getMetaTitle()).isEqualTo(metaTitle);
        assertThat(productDraft.getMetaDescription()).isEqualTo(metaDescription);
        assertThat(productDraft.getMetaKeywords()).isEqualTo(metaKeywords);
        assertThat(productDraft.getCategories()).isEqualTo(categories);
        assertThat(productDraft.getTaxCategory()).isEqualTo(taxCategory);
        assertThat(productDraft.getSearchKeywords()).isEqualTo(searchKeywords);
        assertThat(productDraft.getState()).isEqualTo(state);
        assertThat(productDraft.getCategoryOrderHints()).isEqualTo(categoryOrderHints);
        assertThat(productDraft.isPublish()).isTrue();
        assertThat(productDraft.getKey()).isEqualTo(key);
        assertThat(ProductDraftBuilder.of(productDraft)).isEqualTo(productDraftBuilder);
    }
}