package io.sphere.sdk.search;

import org.junit.Test;
import java.util.Optional;

import static org.fest.assertions.Assertions.*;

public class SphereSearchSortTest {

    @Test
    public void buildsSortExpression() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(Optional.empty(), "variants").appended("attributes").appended("size");
        String expression = new SphereSearchSort<>(model, SearchSortDirection.ASC).toSphereSort();
        assertThat(expression).isEqualTo("variants.attributes.size asc");
    }

    @Test
    public void buildsSortExpressionWithAppendedParameter() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(Optional.empty(), "variants").appended("attributes").appended("size");
        String expression = new SphereSearchSort<>(model, SearchSortDirection.DESC_MIN).toSphereSort();
        assertThat(expression).isEqualTo("variants.attributes.size desc.min");
    }
}