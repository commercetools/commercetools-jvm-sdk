package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectFixtures;
import io.sphere.sdk.customobjects.demo.Foo;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.queries.Sort;
import io.sphere.sdk.queries.SortDirection;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.customobjects.CustomObjectFixtures.withCustomObject;
import static io.sphere.sdk.queries.SortDirection.DESC;
import static io.sphere.sdk.test.SphereTestUtils.toIds;
import static org.fest.assertions.Assertions.assertThat;

public class CustomObjectQueryTest extends IntegrationTest {

    private static final CustomObjectQuery<Foo> CUSTOM_OBJECT_QUERY = CustomObjectQuery.of(Foo.pagedQueryResultTypeReference());

    @BeforeClass
    public static void cleanCustomObjects() throws Exception {
        CustomObjectFixtures.dropAll(client());
    }

    @Test
    public void queryAll() throws Exception {
        withCustomObject(client(), co -> {
            final PagedQueryResult<CustomObject<Foo>> result = execute(CUSTOM_OBJECT_QUERY);
            assertThat(result.getResults().stream().filter(item -> item.getId().equals(co.getId())).count())
                    .isGreaterThanOrEqualTo(1);
        });
    }

    @Test
    public void queryByContainer() throws Exception {
        withCustomObject(client(), "containerA", "key", coA ->
            withCustomObject(client(), "containerB", "key", coB -> {
                final PagedQueryResult<CustomObject<Foo>> result = execute(CUSTOM_OBJECT_QUERY.byContainer(coA.getContainer()));
                result.getResults().stream().map(x -> x.getContainer() +"/" + x.getKey() ).forEach( System.out::println);
                final List<String> resultIds = toIds(result.getResults());
                assertThat(resultIds).contains(coA.getId()).excludes(coB.getId());
            })
        );
    }

    @Test
    public void queryPureJson() throws Exception {
        withCustomObject(client(), existingCustomObject -> {
            final Sort<CustomObject<JsonNode>> sort = CustomObjectQuery.<JsonNode>model().createdAt().sort(DESC);
            final QueryDsl<CustomObject<JsonNode>> clientRequest = CustomObjectQuery.of().withSort(sort);
            final PagedQueryResult<CustomObject<JsonNode>> result = execute(clientRequest);
            assertThat(result.getResults().stream().filter(item -> item.getId().equals(existingCustomObject.getId())).count())
                    .isGreaterThanOrEqualTo(1);
            final String expected = existingCustomObject.getValue().getBar();
            final CustomObject<JsonNode> loadedCustomObject = result.head().get();
            final JsonNode jsonNode = loadedCustomObject.getValue();
            final String actual = jsonNode.get("bar").asText("it is not present");
            assertThat(actual).isEqualTo(expected);
        });
    }
}