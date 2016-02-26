package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.customobjects.CustomObjectFixtures;
import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import io.sphere.sdk.customobjects.demo.Foo;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.AbstractIterableAssert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.customobjects.CustomObjectFixtures.withCustomObject;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomObjectQueryIntegrationTest extends IntegrationTest {

    @BeforeClass
    public static void cleanCustomObjects() throws Exception {
        CustomObjectFixtures.dropAll(client());
    }

    @Test
    public void queryByContainerAndKey() {
        withCustomObject(client(), co -> {
            final CustomObjectQuery<Foo> customObjectQuery = CustomObjectQuery.of(Foo.class)
                    .withPredicates(m -> m.container().is(co.getContainer()).and(m.key().is(co.getKey())));
            final PagedQueryResult<CustomObject<Foo>> result = client().executeBlocking(customObjectQuery);
            assertThat(result.head().get())
                    .isEqualTo(co);
        });
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

    @Test
    public void queryByPlainStringValue() {
        final String value = "hello";
        checkSimpleQuery(value, String.class,
                m -> m.value().ofValue().ofString().is(value),
                jsonNode -> assertThat(jsonNode.textValue()).isEqualTo(value));
    }

    @Test
    public void queryByPlainIntegerValue() {
        final int value = 1;
        checkSimpleQuery(value, Integer.class,
                m -> m.value().ofValue().ofInteger().is(value),
                jsonNode -> assertThat(jsonNode.asInt()).isEqualTo(value));
    }

    @Test
    public void queryByPlainLongValue() {
        final long value = 2L;
        checkSimpleQuery(value, Long.class,
                m -> m.value().ofValue().ofLong().is(value),
                jsonNode -> assertThat(jsonNode.asLong()).isEqualTo(value));
    }

    @Test
    public void queryByPlainBooleanValue() {
        final boolean value = true;
        checkSimpleQuery(value, Boolean.class,
                m -> m.value().ofValue().ofBoolean().is(value),
                jsonNode -> assertThat(jsonNode.asBoolean()).isEqualTo(value));
    }

    private <T> void checkSimpleQuery(final T value, final Class<T> valueClass,
                                      final Function<CustomObjectQueryModel<CustomObject<JsonNode>>, QueryPredicate<CustomObject<JsonNode>>> predicateFunction,
                                      final Consumer<JsonNode> assertion) {
        final CustomObjectDraft<T> draft = CustomObjectDraft.ofUnversionedUpsert("CustomObjectQueryIntegrationTest", "queryByAsValue", value, valueClass);
        client().executeBlocking(CustomObjectUpsertCommand.of(draft));
        final CustomObjectQuery<JsonNode> query = CustomObjectQuery.ofJsonNode()
                .plusPredicates(m -> predicateFunction.apply(m))
                .plusPredicates(m -> m.container().is("CustomObjectQueryIntegrationTest"))
                .plusPredicates(m -> m.key().is("queryByAsValue"))
                ;
        final List<CustomObject<JsonNode>> results = client().executeBlocking(query).getResults();
        assertThat(results).hasSize(1);
        assertion.accept(results.get(0).getValue());
    }

    @Test
    public void queryByValueAsObject() {
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("s", "s value");
        final ObjectNode sub = mapper.createObjectNode();
        sub.put("sub-number", 5);
        sub.put("sub-s", "sub s value");
        sub.set("sub-nullable", sub.nullNode());
        sub.put("sub-boolean", true);
        final ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.add("foo");
        arrayNode.add("bar");
        arrayNode.add("baz");
        sub.set("arrno", arrayNode);
        rootNode.set("sub", sub);

        final CustomObjectDraft<JsonNode> draft = CustomObjectDraft.ofUnversionedUpsert("CustomObjectQueryIntegrationTest", "queryByValueAsObject", rootNode);
        client().executeBlocking(CustomObjectUpsertCommand.of(draft));

        final CustomObjectQuery<JsonNode> query = CustomObjectQuery.ofJsonNode()
                .plusPredicates(m -> m.value().ofObject().ofString("s").is("s value"))
                .plusPredicates(m -> m.value().ofObject().ofObject("sub").ofString("sub-s").is("sub s value"))
                .plusPredicates(m -> m.value().ofObject().ofObject("sub").ofString("sub-nullable").isNotPresent())
                .plusPredicates(m -> m.value().ofObject().ofObject("sub").ofBoolean("sub-boolean").is(true))
                .plusPredicates(m -> m.value().ofObject().ofObject("sub").ofInteger("sub-number").is(5))
                .plusPredicates(m -> m.value().ofObject().ofObject("sub").ofLong("sub-number").is(5L))
                .plusPredicates(m -> m.value().ofObject().ofObject("sub").ofStringCollection("arrno").containsAll(asList("foo", "bar")))
                .plusPredicates(m -> m.container().is("CustomObjectQueryIntegrationTest"))
                .plusPredicates(m -> m.key().is("queryByValueAsObject"))
                ;
        final PagedQueryResult<CustomObject<JsonNode>> queryResult = client().executeBlocking(query);
        assertThat(queryResult.head()).isPresent();
    }

    public void demoModelTypeParameter() {
        final QuerySort<CustomObject<JsonNode>> sort = CustomObjectQueryModel.<CustomObject<JsonNode>>of().createdAt().sort().desc();
        final QuerySort<CustomObject<Foo>> fooSort = CustomObjectQueryModel.<CustomObject<Foo>>of().createdAt().sort().desc();
    }
}