package io.sphere.client.shop;

import com.google.common.collect.ComparisonChain;
import io.sphere.client.shop.model.Category;

import java.util.Comparator;

public class CategoryByNameComparator implements Comparator<Category> {

    @Override
    public int compare(Category cat1, Category cat2) {
        return ComparisonChain.start().compare(cat1.getName(), cat2.getName()).result();
    }
}
