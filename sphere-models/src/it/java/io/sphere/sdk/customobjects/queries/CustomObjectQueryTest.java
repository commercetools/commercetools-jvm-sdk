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
import static org.assertj.core.api.Assertions.assertThat;

public class CustomObjectQueryTest extends IntegrationTest {

    @BeforeClass
    public static void cleanCustomObjects() throws Exception {
        CustomObjectFixtures.dropAll(client());
    }

    @Test
    public void queryWithClass() throws Exception {
        withCustomObject(client(), co -> {
            final CustomObjectQuery<Foo> customObjectQuery = CustomObjectQuery.of(Foo.class)
                    .withPredicates(o -> o.id().is(co.getId()));
            final PagedQueryResult<CustomObject<Foo>> result = client().executeBlocking(customObjectQuery);
            assertThat(result.head().get())
                    .isEqualTo(co);
        });
    }

    @SuppressWarnings("unchecked")
    @Test
    public void queryByContainer() throws Exception {
        withCustomObject(client(), "containerA", "key", customObjectA ->
            withCustomObject(client(), "containerB", "key", customObjectB -> {
                final Class<Foo> classForTheValue = Foo.class;
                final PagedQueryResult<CustomObject<Foo>> result =
                        client().executeBlocking(CustomObjectQuery.of(classForTheValue).byContainer("containerA"));
                final List<CustomObject<Foo>> results = result.getResults();
                assertThat(results)
                        .contains(customObjectA)
                        .doesNotContain(customObjectB);
            })
        );
    }

    @Test
    public void queryPureJson() throws Exception {
        withCustomObject(client(), existingCustomObject -> {
            final CustomObjectQuery<JsonNode> clientRequest = CustomObjectQuery.ofJsonNode()
                    .withSort(m -> m.createdAt().sort().desc());
            final PagedQueryResult<CustomObject<JsonNode>> result = client().executeBlocking(clientRequest);
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
        final QuerySort<CustomObject<JsonNode>> sort = CustomObjectQueryModel.<CustomObject<JsonNode>>of().createdAt().sort().desc();
        final QuerySort<CustomObject<Foo>> fooSort = CustomObjectQueryModel.<CustomObject<Foo>>of().createdAt().sort().desc();
    }
}