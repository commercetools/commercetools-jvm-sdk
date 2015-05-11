package io.sphere.sdk.queries;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.queries.QuerySortDirection.*;

public class StringQuerySortingModelTest {
    private final StringQuerySortingModel<Fixtures.Product> model = new StringQuerySortingModel<>(Optional.empty(), "id");

    @Test
    public void generateSimpleQueries() throws Exception {
        assertThat(model.is("foo").toSphereQuery()).isEqualTo("id=\"foo\"");
    }

    @Test
    public void generateHierarchicalQueries() throws Exception {
        final QueryModel<Fixtures.Product> parents = new QueryModelImpl<Fixtures.Product>(Optional.empty(), "x1").
                appended("x2").appended("x3").appended("x4");
        assertThat(new StringQuerySortingModel<>(Optional.of(parents), "x5").is("foo").toSphereQuery()).isEqualTo("x1(x2(x3(x4(x5=\"foo\"))))");
    }

    @Test
    public void generateSortExpression() throws Exception {
        assertThat(model.sort(ASC).toSphereSort()).isEqualTo("id asc");
    }
}
