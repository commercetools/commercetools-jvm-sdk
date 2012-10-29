package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.shop.model.Category;

import java.util.List;

/** All categories in the project represented as a tree with helper methods. */
public interface CategoryTree {
    /** Root categories (the ones that have no parent).*/
    List<Category> getRoots();
    /** Finds a category by id using a in-memory HashMap lookup. Returns null if no category with given id exists. */
    Category getById(String id);
    /** Finds a category by slug using a in-memory HashMap lookup. Returns null if no category with given slug exists. */
    Category getBySlug(String slug);
    /** All categories as a flat list, sorted by name. */
    List<Category> getAsFlatList();
}
