package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.search.PagedSearchResult;
import org.junit.Test;

import java.io.IOException;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionTest {
    @Test
    public void transformProductIntoProductProjection() throws Exception {
        final Product product = getProduct();

        final ProductProjection staged = product.toProjection(ProductProjectionType.STAGED);

        assertThat(staged).overridingErrorMessage("staged is always present").isNotNull();
        assertThat(staged.getName()).isEqualTo(product.getMasterData().getStaged().getName());

        final ProductProjection current = product.toProjection(ProductProjectionType.CURRENT);
        assertThat(current).overridingErrorMessage("current can be empty").isNull();
    }

    @Test
    public void deserialization() throws Exception {
        final String jsonString = "{\"offset\":0,\"count\":1,\"total\":15,\"results\":[{\"masterVariant\":{\"id\":1,\"sku\":\"2\",\"prices\":[{\"value\":{\"currencyCode\":\"EUR\",\"centAmount\":1413}}],\"images\":[{\"url\":\"https://s3.eu-central-1.amazonaws.com/commercetools-angry-bird-demo/Red+Skywalker+Plush+Toy.jpg\",\"dimensions\":{\"w\":0,\"h\":0}}],\"attributes\":[{\"name\":\"size\",\"value\":{\"key\":\"onesize\",\"label\":\"one size\"}},{\"name\":\"color\",\"value\":{\"key\":\"multicolor\",\"label\":\"multi color\"}}]},\"id\":\"184aaadc-63f6-4e6a-95fe-22b4f001612f\",\"version\":2,\"productType\":{\"typeId\":\"product-type\",\"id\":\"371e1d3a-e553-4b76-a834-dd3745c9afa0\"},\"name\":{\"en\":\"Red Skywalker Plush Toy\"},\"description\":{\"en\":\"Red Skywalker is a committed Rebel and is learning to be a Jedi Bird warrior.\"},\"categories\":[{\"typeId\":\"category\",\"id\":\"bc67f617-79ca-4f48-bf1b-18d5dbc7b552\"}],\"slug\":{\"en\":\"Red-Skywalker-Plush-Toy\"},\"metaTitle\":{\"en\":\"Red Skywalker Plush Toy\"},\"metaKeywords\":{\"en\":\"star wars, toy, angry birds\"},\"metaDescription\":{\"en\":\"Red Skywalker is a committed Rebel and is learning to be a Jedi Bird warrior.\"},\"variants\":[],\"searchKeywords\":{},\"hasStagedChanges\":false,\"published\":true,\"taxCategory\":{\"typeId\":\"tax-category\",\"id\":\"ab3939bd-0e2d-4fbb-a640-ae8a33c4e2c9\"},\"createdAt\":\"2015-02-25T11:20:11.466Z\",\"lastModifiedAt\":\"2015-02-25T11:20:11.687Z\"}],\"facets\":{}}";
        final PagedSearchResult<ProductProjection> pagedSearchResult = SphereJsonUtils.readObject(jsonString, TypeReferenceTestImpl.of());
    }

    @Test
    public void findVariantBySku() throws Exception {
        final Product product = getProduct();

        final ProductProjection staged = product.toProjection(ProductProjectionType.STAGED);
        assertThat(staged.findVariantBySku("sku-5000")).isPresent();
        assertThat(staged.findVariantBySku("not existing")).isEmpty();
    }

    @Test
    public void findMatchingVariantsOnSearch() throws Exception {
        final ProductProjection product = getProductProjectionFromSearch();
        assertThat(product.findMatchingVariants()).extracting(v -> v.getId()).containsExactly(2, 4);
    }

    @Test
    public void findFirstMatchingVariantOnSearch() throws Exception {
        final ProductProjection product = getProductProjectionFromSearch();
        assertThat(product.findFirstMatchingVariant().get().getId()).isEqualTo(2);
    }

    @Test
    public void findMatchingVariantsOnQuery() throws Exception {
        final ProductProjection product = getProductProjectionFromQuery();
        assertThat(product.findMatchingVariants()).extracting(v -> v.getId()).isEmpty();
    }

    @Test
    public void findFirstMatchingVariantOnQuery() throws Exception {
        final ProductProjection product = getProductProjectionFromQuery();
        assertThat(product.findFirstMatchingVariant()).isEmpty();
    }

    @Test
    public void productProjectionIsAVersionedProduct() throws IOException {
        final ProductProjection productProjection = getProduct().toProjection(ProductProjectionType.STAGED);
        final Versioned<Product> versioned = productProjection;
    }

    private Product getProduct() throws IOException {
        return SphereJsonUtils.readObjectFromResource("ProductProjectionTest/product.json", Product.typeReference());
    }

    private ProductProjection getProductProjectionFromSearch() throws IOException {
        return SphereJsonUtils.readObjectFromResource("ProductProjectionTest/product-projection-from-search.json", ProductProjection.typeReference());
    }

    private ProductProjection getProductProjectionFromQuery() throws IOException {
        return SphereJsonUtils.readObjectFromResource("ProductProjectionTest/product-projection-from-query.json", ProductProjection.typeReference());
    }
}