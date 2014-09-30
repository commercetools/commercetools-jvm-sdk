package io.sphere.sdk.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;


public class ListUtilsTest {

    @Test
    public void testListOf() throws Exception {
        final List<Integer> integers = Arrays.asList(1, 2, 3);
        final List<Integer> combined = ListUtils.listOf(integers, 4);
        assertThat(combined).isEqualTo(Arrays.asList(1, 2, 3, 4));
    }
}