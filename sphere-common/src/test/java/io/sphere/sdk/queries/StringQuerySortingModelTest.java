package io.sphere.sdk.queries;

import org.junit.Test;

import static io.sphere.sdk.queries.QuerySortDirection.ASC;
import static org.assertj.core.api.Assertions.assertThat;

public class StringQuerySortingModelTest {
    private final StringQuerySortingModel<Fixtures.Product> model = new StringQuerySortingModel<>(null, "id");

    @Test
    public void generateSimpleQueries() throws Exception {
        assertThat(model.is("foo").toSphereQuery()).isEqualTo("id=\"foo\"");
    }

    @Test
    public void isGreaterThan() throws Exception {
        assertThat(model.isGreaterThan("x").toSphereQuery()).isEqualTo("id>\"x\"");
    }

    @Test
    public void isGreaterThanOrEqualTo() throws Exception {
        assertThat(model.isGreaterThanOrEqualTo("x").toSphereQuery()).isEqualTo("id>=\"x\"");
    }

    @Test
    public void isLessThan() throws Exception {
        assertThat(model.isLessThan("x").toSphereQuery()).isEqualTo("id<\"x\"");
    }

    @Test
    public void isLessThanOrEqualTo() throws Exception {
        assertThat(model.isLessThanOrEqualTo("x").toSphereQuery()).isEqualTo("id<=\"x\"");
    }

    @Test
    public void generateHierarchicalQueries() throws Exception {
        final QueryModelImpl<Fixtures.Product> parent = new QueryModelImpl<>(new QueryModelImpl<>(new QueryModelImpl<>(new QueryModelImpl<>(null, "x1"), "x2"), "x3"), "x4");
        assertThat(new StringQuerySortingModel<>(parent, "x5").is("foo").toSphereQuery()).isEqualTo("x1(x2(x3(x4(x5=\"foo\"))))");
    }

    @Test
    public void generateSortExpression() throws Exception {
        assertThat(model.sort(ASC).toSphereSort()).isEqualTo("id asc");
    }
}
