package de.commercetools.sphere.client.shop.model;

import de.commercetools.sphere.client.model.Reference;
import de.commercetools.sphere.client.model.EmptyReference;
import static de.commercetools.sphere.client.util.Ext.*;

import java.util.ArrayList;
import java.util.List;

/** Category of product in the product catalog. */
public class Category {
    private String id;
    private String version;
    private String name;
    private String description;
    private List<Reference<Category>> ancestors = new ArrayList<Reference<Category>>(); // initialize to prevent NPEs
    private Reference<Category> parent = EmptyReference.create("parent"); // initialize to prevent NPEs
    private List<Category> children = new ArrayList<Category>();

    // for JSON deserializer
    private Category() { }

    /** The URL slug of this product. */
    public String getSlug() {
        return slugify(getName());
    }

    /** Unique id of this category. */
    public String getId() {
        return id;
    }
    /** Version of this category that increases when the category is changed. */
    public String getVersion() {
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
    public Reference<Category> getParent() {
        return parent;
    }
    /** Gets references to all ancestors (parents of parents) of this category. */
    public List<Reference<Category>> getAncestors() {
        return ancestors;
    }
    /** Gets child categories of this category. */
    public List<Category> getChildren() {
        return children;
    }
}
