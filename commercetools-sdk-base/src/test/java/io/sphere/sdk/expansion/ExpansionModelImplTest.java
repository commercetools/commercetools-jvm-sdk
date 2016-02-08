package io.sphere.sdk.expansion;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.*;

public class ExpansionModelImplTest {
    interface T {

    }

    @Test
    public void presentParentStringMissingPath() {
        assertThat(byStringString("parent", null)).containsOnly("parent");
    }

    @Test
    public void missingParentStringMissingPath() {
        assertThat(byStringString(null, null)).isEmpty();
    }

    @Test
    public void presentParentStringPresentPath() {
        assertThat(byStringString("parent", "path")).containsOnly("parent.path");
    }

    @Test
    public void presentParentListStringMissingPath() {
        assertThat(byStringListString(singletonList("parent"), null)).containsOnly("parent");
    }

    @Test
    public void missingParentStringListMissingPath() {
        assertThat(byStringListString(null, null)).isEmpty();
    }

    @Test
    public void missingParentStringListPresentPath() {
        assertThat(byStringListString(null, "path")).containsOnly("path");
    }

    @Test
    public void presentParentStringListPresentPath() {
        assertThat(byStringString("parent", "path")).containsOnly("parent.path");
    }

    @Test
    public void presentParentStringMissingPathStringList() {
        assertThat(byStringStringList("parent", null)).containsOnly("parent");
    }

    @Test
    public void presentEmptyParentStringListMissingPathString() {
        assertThat(byStringListString(emptyList(), "path")).containsOnly("path");
    }

    @Test
    public void missingParentStringMissingPathStringList() {
        assertThat(byStringStringList(null, null)).isEmpty();
    }

    @Test
    public void presentParentStringPresentPathStringList() {
        assertThat(byStringStringList("parent", singletonList("path"))).containsOnly("parent.path");
    }

    @Test
    public void presentParentStringPresentEmptyPathStringList() {
        assertThat(byStringStringList("parent", emptyList())).containsOnly("parent");
    }

    @Test
    public void presentParentListStringMissingPathStringList() {
        assertThat(byStringListStringList(singletonList("parent"), null)).containsOnly("parent");
    }

    @Test
    public void missingParentStringListMissingPathStringList() {
        assertThat(byStringListStringList(null, null)).isEmpty();
    }

    @Test
    public void missingParentStringListPresentPathStringList() {
        assertThat(byStringListStringList(null, singletonList("path"))).containsOnly("path");
    }

    @Test
    public void presentParentStringListPresentPathStringList() {
        assertThat(byStringStringList("parent", singletonList("path"))).containsOnly("parent.path");
    }

    @Test
    public void presentParentStringListPresent2PathStringList() {
        assertThat(byStringStringList("parent", asList("path1", "path2"))).containsOnly("parent.path1", "parent.path2");
    }

    @Test
    public void present2ParentStringListPresent2PathStringList() {
        assertThat(byStringListStringList(asList("parent1", "parent2"), asList("path1", "path2"))).containsOnly("parent1.path1", "parent1.path2", "parent2.path1", "parent2.path2");
    }

    private List<String> byStringString(final String parent, final String path) {
        return getStrings(new ExpansionModelImpl<>(parent, path));
    }

    private List<String> byStringListString(final List<String> parent, final String path) {
        return getStrings(new ExpansionModelImpl<>(parent, path));
    }

    private List<String> byStringStringList(final String parent, final List<String> path) {
        return getStrings(new ExpansionModelImpl<>(parent, path));
    }

    private List<String> byStringListStringList(final List<String> parent, final List<String> path) {
        return getStrings(new ExpansionModelImpl<>(parent, path));
    }

    private List<String> getStrings(final ExpansionModelImpl<T> expansionModel) {
        return expansionModel.expansionPaths().stream().map(e -> e.toSphereExpand()).collect(Collectors.toList());
    }
}