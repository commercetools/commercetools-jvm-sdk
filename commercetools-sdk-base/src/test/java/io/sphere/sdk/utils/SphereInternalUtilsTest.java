package io.sphere.sdk.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class SphereInternalUtilsTest {

    @Test
    public void testListOfOneListAndElement() throws Exception {
        final List<Integer> integers = Arrays.asList(1, 2, 3);
        final List<Integer> combined = SphereInternalUtils.listOf(integers, 4);
        assertThat(combined).isEqualTo(Arrays.asList(1, 2, 3, 4));
    }

    @Test
    public void testListOfTwoLists() throws Exception {
        final List<Integer> firstList = Arrays.asList(1, 2, 3);
        final List<Integer> secondList = Arrays.asList(4, 5, 6);
        final List<Integer> combined = SphereInternalUtils.listOf(firstList, secondList);
        assertThat(combined).isEqualTo(Arrays.asList(1, 2, 3, 4, 5, 6));
    }
}