package io.sphere.sdk.search.model;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;

public class SearchModelTest {

    @Test
    public void hasMatchingPath() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>("variants").appended("price").appended("centAmount");
        assertThat(model.hasPath(asList("variants", "price", "centAmount"))).isTrue();
        assertThat(model.hasPath(asList("hello", "world"))).isFalse();
    }

    @Test
    public void parentWithEmptyPath() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>("price").appended(null).appended("centAmount");
        assertThat(model.hasPath(asList("price", "centAmount"))).isTrue();
    }

    @Test
    public void buildsSegmentPath() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>("variants").appended("price").appended("centAmount");
        assertThat(model.attributePath()).isEqualTo("variants.price.centAmount");
    }
}