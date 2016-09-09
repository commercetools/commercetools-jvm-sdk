package io.sphere.sdk.jsonnodes.queries;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonNodeQueryIntegrationTest extends IntegrationTest {
    @Test
    public void dsl() {
        CategoryFixtures.withCategoryAndParentCategory(client(), (cat, parent) -> {
            final JsonNodeQuery query = JsonNodeQuery.of("/categories")
                    .withExpansionPaths(m -> m.paths("parent"))
                    .plusExpansionPaths(m -> m.paths("ancestors[*]"))
                    .plusPredicates(m -> m.predicate("id=\"" + cat.getId() + "\""))
                    .plusPredicates(m -> m.id().is(cat.getId()));
            final PagedQueryResult<JsonNode> res = client().executeBlocking(query);
            assertThat(res.getResults()).hasSize(1);
            final JsonNode jsonNode = res.getResults().get(0);
            assertThat(jsonNode.get("id").asText()).isEqualTo(cat.getId());
            assertThat(jsonNode.get("parent").get("obj").get("id").asText()).isEqualTo(parent.getId());
            assertThat(jsonNode.get("ancestors").get(0).get("obj").get("id").asText()).isEqualTo(parent.getId());
        });
    }

    @Test
    public void allowToUseHttpQueryParameters() {
        ProductFixtures.withProduct(client(), (Product product) -> {
            final JsonNodeQuery query = JsonNodeQuery.of("/product-projections?staged=true")
                    .plusPredicates(m -> m.id().is(product.getId()));
            final String path = query.httpRequestIntent().getPath();
            assertThat(path).startsWith("/product-projections?staged=true&where=id%3D%22");
            final List<JsonNode> results = client().executeBlocking(query).getResults();
            assertThat(results).hasSize(1);
            assertThat(results.get(0).get("id").asText()).isEqualTo(product.getId());
        });
    }
}