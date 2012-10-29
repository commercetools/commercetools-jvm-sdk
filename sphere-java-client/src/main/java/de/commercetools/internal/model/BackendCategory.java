package de.commercetools.internal.model;

import de.commercetools.sphere.client.model.Reference;
import de.commercetools.sphere.client.model.EmptyReference;

import java.util.ArrayList;
import java.util.List;

/** Internal representation of a category in the form the backend returns it.
 *  See {@link de.commercetools.sphere.client.shop.model.Category} for a more user friendly version. */
public class BackendCategory {
    private String id;
    private int version;
    private String name;
    private String description;
    private List<Reference<BackendCategory>> ancestors = new ArrayList<Reference<BackendCategory>>();     // initialize to prevent NPEs
    private Reference<BackendCategory> parent = EmptyReference.create("parent"); // initialize to prevent NPEs
    private List<BackendCategory> children = new ArrayList<BackendCategory>();                 // initialize to prevent NPEs

    // for JSON deserializer
    private BackendCategory() { }

    /** Unique id of this category. */
    public String getId() {
        return id;
    }
    /** Version of this category that increases when the category is changed. */
    public int getVersion() {
        return version;
    }
    /** Gets the name of this category. */
    public String getName() {
        return name;
    }
    /** Gets the description of this category. */
    public String getDescription() {
        return description;
    }
    /** Gets a reference to the parent category. */
    public Reference<BackendCategory> getParent() {
        return parent;
    }
    /** Gets references to all ancestors (parents of parents) of this category.
     *  The list is sorted from the farthest parent to the closest (the last element being the direct parent). */
    public List<Reference<BackendCategory>> getAncestors() {
        return ancestors;
    }
    /** Gets child categories of this category. */
    public List<BackendCategory> getChildren() {
        return children;
    }
}
