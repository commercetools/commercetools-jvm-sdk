package io.sphere.client.shop;

import io.sphere.client.shop.model.Category;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/** All categories in the project, represented as an in-memory tree. */
public interface CategoryTree {
    /** Root categories (the ones that have no parent). */
    List<Category> getRoots();
    /** Root categories (the ones that have no parent) sorted by comparator for the root level. */
    List<Category> getRoots(Comparator<Category> comparator);
    /** Finds a category by id using a in-memory HashMap lookup. Returns null if no category with given id exists. */
    Category getById(String id);
    /** Finds a category by the slug of the default locale using a in-memory HashMap lookup. Returns null if no category with given slug exists. */
    Category getBySlug(String slug);
    /** Finds a category by the slug and a specific locale using a in-memory HashMap lookup. Returns null if no category with given slug exists. */
    Category getBySlug(String slug, Locale locale);
    /** All categories as a flat list, sorted by name. */
    List<Category> getAsFlatList();
    /** Rebuilds the category cache for category tree implementations. */
    void rebuildAsync();
}
