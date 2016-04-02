package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import net.jcip.annotations.NotThreadSafe;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.test.SphereTestUtils.en;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.*;

@NotThreadSafe
public class CategoryExpansionModelIntegrationTest extends IntegrationTest {

    @Test
    public void ancestors() throws Exception {
        withCategory(client(), CategoryDraftBuilder.of(en("1"), en("level1")), level1 -> {
            withCategory(client(), CategoryDraftBuilder.of(en("2"), en("level2")).parent(level1), level2 -> {
                withCategory(client(), CategoryDraftBuilder.of(en("3"), en("level3")).parent(level2), level3 -> {
                    withCategory(client(), CategoryDraftBuilder.of(en("4"), en("level4")).parent(level3), level4 -> {
                        final ExpansionPath<Category> expansionPath =
                                CategoryExpansionModel.of().ancestors().ancestors().expansionPaths().get(0);
                        final Query<Category> query = CategoryQuery.of().byId(level4.getId())
                                .withExpansionPaths(expansionPath)
                                .toQuery();
                        final PagedQueryResult<Category> queryResult = client().executeBlocking(query);
                        final Category loadedLevel4 = queryResult.head().get();
                        final List<Reference<Category>> ancestors = loadedLevel4.getAncestors();
                        final List<String> expectedAncestorIds = ancestors.stream().map(r -> r.getObj().getId()).collect(toList());
                        assertThat(expectedAncestorIds).isEqualTo(asList(level1.getId(), level2.getId(), level3.getId()));

                        final Category level3ExpandedAncestor = ancestors.get(2).getObj();
                        assertThat(level3ExpandedAncestor.getId()).isEqualTo(level3.getId());

                        assertThat(level3ExpandedAncestor.getAncestors().get(0).getObj().getId()).isEqualTo(level1.getId());
                    });
                });
            });
        });
    }

    @Test
    public void parent() throws Exception {
        withCategory(client(), CategoryDraftBuilder.of(en("1"), en("level1")), level1 -> {
            withCategory(client(), CategoryDraftBuilder.of(en("2"), en("level2")).parent(level1), level2 -> {
                final Query<Category> query = CategoryQuery.of().byId(level2.getId())
                        .withExpansionPaths(m -> m.parent())
                        .toQuery();
                final PagedQueryResult<Category> queryResult = client().executeBlocking(query);
                final Category loadedLevel2 = queryResult.head().get();
                assertThat(loadedLevel2.getParent().getObj()).isNotNull();
            });
        });
    }

    @Test
    public void ancestorsIndex() throws Exception {
        assertThat(CategoryExpansionModel.of().ancestors(1).ancestors().expansionPaths().get(0).toSphereExpand())
                .isEqualTo("ancestors[1].ancestors[*]");
    }
}