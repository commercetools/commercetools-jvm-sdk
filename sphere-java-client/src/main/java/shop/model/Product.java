package de.commercetools.sphere.client.shop.model;

import de.commercetools.sphere.client.model.Reference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 *  Product in the product catalog.
 *
 *  The Product itself is a {@link Variant}. Products that only exist in one variant
 *  are represented by a single Product instance where {@link #getVariants()} is empty.
 */
@JsonIgnoreProperties({"productType"})
public class Product extends Variant {
    private String id;
    private String version;
    private String name;
    private String description;
    private Reference<Vendor> vendor = Reference.empty("vendor"); // initialize to prevent NPEs
    private String slug;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
    private int quantityAtHand;
    private List<Reference<Category>> categories = new ArrayList<Reference<Category>>(); // initialize to prevent NPEs
    private List<Variant> variants = new ArrayList<Variant>();
    private Set<Reference<Catalog>> catalogs = new HashSet<Reference<Catalog>>();
    private Reference<Catalog> catalog = Reference.empty("catalog");

    // for JSON deserializer
    private Product() { }

    /** Returns the variant with given SKU or this product itself, or null if such variant does not exist. */
    public Variant getVariantBySKU(String sku) {
        if (this.getSKU().equals(sku))
            return this;
        for (Variant v: variants) {
            if (v.getSKU().equals(sku)) return v;
        }
        return null;
    }

    /** Unique id of this product. */
    public String getId() {
        return id;
    }
    /** Version of this product that increases when the product is changed. */
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
    /** Vendor of this product.  */
    public Reference<Vendor> getVendor() {
        return vendor;
    }
    /** URL friendly name of this product. */
    public String getSlug() {
        return slug;
    }
    /** HTML title for product page. */
    public String getMetaTitle() {
        return metaTitle;
    }
    /** HTML meta description for product page. */
    public String getMetaDescription() {
        return metaDescription;
    }
    /** HTML meta keywords for product page. */
    public String getMetaKeywords() {
        return metaKeywords;
    }
    /** Current available stock quantity for this product. */
    public int getQuantityAtHand() {
        return quantityAtHand;
    }
    /** Categories this product is assigned to. */
    public List<Reference<Category>> getCategories() {
        return categories;
    }
    /** Variants of this product. */
    public List<Variant> getVariants() {
        return variants;
    }
    /** All catalogs this product is in. */
    public Set<Reference<Catalog>> getCatalogs() {
        return catalogs;
    }
    /** One of catalogs; the catalog this product "copy" is in.
    /* If set, implies that this product is not a product in the master catalog. */
    public Reference<Catalog> getCatalog() {
        return catalog;
    }
}
