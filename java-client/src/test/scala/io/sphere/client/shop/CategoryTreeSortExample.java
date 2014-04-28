package io.sphere.client.shop;

import io.sphere.client.shop.model.Category;
import org.junit.Test;

import java.util.List;

public class CategoryTreeSortExample {
    private CategoryTree categoryTree;

    @Test
    public void sort() {
        final List<Category> sortedRoots = categoryTree.getRoots(new CategoryByNameComparator());
    }
}
