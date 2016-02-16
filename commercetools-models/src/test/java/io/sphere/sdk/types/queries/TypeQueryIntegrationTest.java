package io.sphere.sdk.types.queries;

import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.Type;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeQueryIntegrationTest extends IntegrationTest {

    public static final List<String> RESOURCE_TYPE_IDS = asList(Category.resourceTypeId(), CustomLineItem.resourceTypeId());

    @Test
    public void queryByContainsAllResourceTypeIds() {
        checkByQuery(m -> m.resourceTypeIds().containsAll(RESOURCE_TYPE_IDS));
    }

    @Test
    public void queryByContainsAnyResourceTypeIds() {
        checkByQuery(m -> m.resourceTypeIds().containsAny(RESOURCE_TYPE_IDS));
    }

    @Test
    public void queryByFieldDefinition() {
        checkByQuery(m -> m.fieldDefinitions().name().is(STRING_FIELD_NAME)
                .and(m.fieldDefinitions().type().name().is("String")));
    }

    private void checkByQuery(final Function<TypeQueryModel, QueryPredicate<Type>> typeQueryModelQueryPredicateFunction) {
        withUpdateableType(client(), type -> {
            final TypeQuery sphereRequest = TypeQuery.of()
                    .withPredicates(typeQueryModelQueryPredicateFunction)
                    .plusPredicates(m -> m.id().is(type.getId()));
            final PagedQueryResult<Type> pagedQueryResult = client().executeBlocking(sphereRequest);
             assertThat(pagedQueryResult.head()).contains(type);
             return type;
         });
    }
}