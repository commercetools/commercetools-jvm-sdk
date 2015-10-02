package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectFixtures;
import io.sphere.sdk.customobjects.demo.Foo;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.customobjects.CustomObjectFixtures.withCustomObject;
import static io.sphere.sdk.queries.QuerySortDirection.DESC;
import static io.sphere.sdk.test.SphereTestUtils.toIds;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomObjectQueryTest extends IntegrationTest {

    private static final CustomObjectQuery<Foo> CUSTOM_OBJECT_QUERY = CustomObjectQuery.of(Foo.class);

    @BeforeClass
    public static void cleanCustomObjects() throws Exception {
        CustomObjectFixtures.dropAll(client());
    }

    @Test
    public void queryAll() throws Exception {
        withCustomObject(client(), co -> {
            final PagedQueryResult<CustomObject<Foo>> result = execute(CUSTOM_OBJECT_QUERY);
            assertThat(result.getResults().stream().filter(item -> item.hasSameIdAs(co)).count())
                    .isGreaterThanOrEqualTo(1);
        });
    }

    @Test
    public void queryWithClass() throws Exception {
        withCustomObject(client(), co -> {
            final PagedQueryResult<CustomObject<Foo>> result = execute(CustomObjectQuery.of(Foo.class).withPredicates(o -> o.id().is(co.getId())));
            assertThat(result.head().get())
                    .isEqualTo(co);
        });
    }

    @Test
    public void queryByContainer() throws Exception {
        withCustomObject(client(), "containerA", "key", coA ->
            withCustomObject(client(), "containerB", "key", coB -> {
                final PagedQueryResult<CustomObject<Foo>> result = execute(CUSTOM_OBJECT_QUERY.byContainer(coA.getContainer()));
                final List<String> resultIds = toIds(result.getResults());
                assertThat(resultIds).contains(coA.getId()).doesNotContain(coB.getId());
            })
        );
    }

    @Test
    public void queryPureJson() throws Exception {
        withCustomObject(client(), existingCustomObject -> {
            final CustomObjectQuery<JsonNode> clientRequest = CustomObjectQuery.ofJsonNode()
                    .withSort(m -> m.createdAt().sort().desc());
            final PagedQueryResult<CustomObject<JsonNode>> result = execute(clientRequest);
            assertThat(result.getResults().stream().filter(item -> item.hasSameIdAs(existingCustomObject)).count())
                    .isGreaterThanOrEqualTo(1);
            final String expected = existingCustomObject.getValue().getBar();
            final CustomObject<JsonNode> loadedCustomObject = result.head().get();
            final JsonNode jsonNode = loadedCustomObject.getValue();
            final String actual = jsonNode.get("bar").asText("it is not present");
            assertThat(actual).isEqualTo(expected);
        });
    }

    public void demoModelTypeParameter() {
        final QuerySort<CustomObject<JsonNode>> sort = CustomObjectQueryModel.<CustomObject<JsonNode>>of().createdAt().sort(DESC);
        final QuerySort<CustomObject<Foo>> fooSort = CustomObjectQueryModel.<CustomObject<Foo>>of().createdAt().sort(DESC);
    }
}