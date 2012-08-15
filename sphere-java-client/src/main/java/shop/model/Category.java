package de.commercetools.sphere.client.shop.model;

import de.commercetools.sphere.client.model.Reference;
import static de.commercetools.sphere.client.util.Ext.*;

import java.util.ArrayList;
import java.util.List;

/** Category of product in the product catalog. */
public class Category {
    private String id;
    private String version;
    private String name;
    private String description;
    private List<Reference<Category>> ancestors;
    private Reference<Category> parent;
    private List<Category> children = new ArrayList<Category>();

    public String getReference() {
        return "category:" + this.getID();
    }

    // for JSON deserializer
    private Category() { }

    /** The URL slug of this product. */
    public String getSlug() {
        return slugify(getName());
    }

    public String getID() {
        return id;
    }
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
