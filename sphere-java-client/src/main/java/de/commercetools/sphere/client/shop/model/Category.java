package de.commercetools.sphere.client.shop.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import de.commercetools.internal.model.BackendCategory;
import de.commercetools.internal.util.Ext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// note: This is a user friendly representation of a category,
//       built from the de.ct.internal.model.Category returned by the backend (see CategoryTree).

/** Category of {@link Product} in the product catalog. */
public class Category {
    private String id;
    private int version;
    private String name;
    private String description;
    private String slug;
    private Category parent;
    private ImmutableList<Category> children = ImmutableList.<Category>of();
    private ImmutableList<Category> pathInTree = ImmutableList.<Category>of();

    private Category(String id, int version, String name, String description) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.slug = Ext.slugify(name);   // no editable slug in the backend?
        this.description = description;
    }

    /** Unique id of this category. */
    public String getId() { return id; }

    /** Version of this category that increases when the category is changed. */
    public int getVersion() { return version; }

    /** Name of this category. */
    public String getName() { return name; }

    /** URL-friendly slug of this category. */
    public String getSlug() { return slug; }

    /** Description of this category. */
    public String getDescription() { return description; }

    /** Parent category of this category. Null if this category is one of the roots. */
    public Category getParent() { return parent; }

    /** Child categories of this category. */
    public List<Category> getChildren() { return children; }

    /** Gets the path to this category in the category tree, in the order order root -> ... -> parent -> this category. */
    public List<Category> getPathInTree() { return pathInTree; }

    // -----------------------------------------------------
    // Build tree
    // -----------------------------------------------------

    public static List<Category> buildTree(List<BackendCategory> categories) {
        if (categories == null || categories.size() == 0) {
            return new ArrayList<Category>();
        }
        List<BackendCategory> backendRoots = new ArrayList<BackendCategory>();
        Multimap<String, BackendCategory> categoriesByParent = HashMultimap.create();
        // partition categories into roots and non-roots
        for (BackendCategory c: categories) {
            if (c.getParent().isEmpty()) {
                backendRoots.add(c);
            } else {
                categoriesByParent.put(c.getParent().getId(), c);
            }
        }
        return buildTreeRecursive(null, backendRoots, new ArrayList<Category>(), categoriesByParent);
    }

    public static List<Category> buildTreeRecursive(
            Category parent,
            Collection<BackendCategory> backendChildren,
            List<Category> pathInTree,
            Multimap<String, BackendCategory> categoriesByParent)
    {
        List<Category> result = new ArrayList<Category>();
        for (BackendCategory child : backendChildren) {
            Category c = new Category(child.getId(), child.getVersion(), child.getName(), child.getDescription());
            pathInTree.add(c);
            // we need some (private) mutability, it's hard to build truly immutable object graphs with circular references
            // http://stackoverflow.com/questions/7507965/instantiating-immutable-paired-objects
            c.children = ImmutableList.copyOf(buildTreeRecursive(c, categoriesByParent.get(c.getId()), pathInTree, categoriesByParent));
            c.pathInTree = ImmutableList.copyOf(pathInTree);
            pathInTree.remove(pathInTree.size() - 1);    // c.pathInTree ends with c itself
            c.parent = parent;
            result.add(c);
        }
        return result;
    }
}