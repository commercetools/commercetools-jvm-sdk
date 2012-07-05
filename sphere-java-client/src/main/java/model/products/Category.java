package de.commercetools.sphere.client.model.products;

import static de.commercetools.sphere.client.util.Ext.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/** Category of product in the product catalog. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    private String id;
    private String version;
    private String name;
    private String description;
    private String parent;
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
    public String getParent() {
        return parent;
    }
    /** Gets child categories of this category. */
    public List<Category> getChildren() {
        return children;
    }
}
