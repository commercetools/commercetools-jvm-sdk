package de.commercetools.sphere.client.model.products;

import static de.commercetools.sphere.client.util.Ext.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  Product in the product catalog.
 *
 *  The Product itself is a {@link Variant}. Products that only exist in one variant
 *  are represented by a single Product instance where {@link #getVariants()} is empty.
 *  */
public class Product extends Variant {
    private String id;
    private String version;
    private String name;
    private String description;
    private String definition;
    private int quantityAtHand;
    private List<String> categories = new ArrayList<String>();
    private List<Variant> variants = new ArrayList<Variant>();

    // for JSON deserializer
    private Product() { }
    
    /** The URL slug of this product. */
    public String getSlug() {
        return slugify(getName());
    }

    /** Returns the variant with given SKU, or null if such variant does not exist. */
    public Variant getVariantBySKU(String sku) {
        for (Variant v: variants) {
            if (v.getSKU().equals(sku)) return v;
        }
        return null;
    }

    /** Id of this product. */
    public String getID() {
        return id;
    }
    /** Version (modification revision) of this product. */
    public String getVersion() {
        return version;
    }
    /** Name of this product. */
    public String getName() {
        return name;
    }
    /** Description of this product. */
    public String getDescription() {
        return description;
    }
    /** ProductDefinition of this product. */
    public String getDefinition() {
        return definition;
    }
    /** Current available stock quantity for this product. */
    public int getQuantityAtHand() {
        return quantityAtHand;
    }
    /** Categories this product is assigned to. */
    public List<String> getCategories() {
        return categories;
    }
    /** Variants of this product. */
    public List<Variant> getVariants() {
        return variants;
    }
}
