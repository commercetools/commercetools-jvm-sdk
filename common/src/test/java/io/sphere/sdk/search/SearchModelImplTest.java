package io.sphere.sdk.search;

import org.junit.Test;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.*;

public class SearchModelImplTest {

    @Test
    public void hasMatchingPath() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(Optional.empty(), "variants").appended("price").appended("centAmount");
        assertThat(model.hasPath(asList("variants", "price", "centAmount"))).isTrue();
        assertThat(model.hasPath(asList("hello", "world"))).isFalse();
    }

    @Test
    public void parentWithEmptyPath() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(Optional.empty(), Optional.<String>empty()).appended("price").appended("centAmount");
        assertThat(model.hasPath(asList("price", "centAmount"))).isTrue();
    }

    @Test
    public void buildsSegmentPath() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(Optional.empty(), "variants").appended("price").appended("centAmount");
        assertThat(model.buildPath()).containsExactly("variants", "price", "centAmount");
    }
}