package de.commercetools.sphere.client.model.products;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import de.commercetools.sphere.client.model.Money;
import de.commercetools.sphere.client.shop.model.Attribute;
import de.commercetools.sphere.client.shop.model.Catalog;
import de.commercetools.sphere.client.shop.model.Variant;
import de.commercetools.sphere.client.shop.model.Vendor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import de.commercetools.sphere.client.model.Reference;
import de.commercetools.sphere.client.model.EmptyReference;

/**
 *  Product in the product catalog.
 */
@JsonIgnoreProperties({"productType"})
public class BackendProduct {
    private String id;
    private int version;
    private String name;
    private String description;
    private Reference<Vendor> vendor = EmptyReference.create("vendor"); // initialize to prevent NPEs
    private String slug;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
    private int quantityAtHand;
    private Variant masterVariant;
    private List<Variant> variants = new ArrayList<Variant>();
    private List<Reference<BackendCategory>> categories = new ArrayList<Reference<BackendCategory>>(); // initialize to prevent NPEs
    private Set<Reference<Catalog>> catalogs = new HashSet<Reference<Catalog>>();
    private Reference<Catalog> catalog = EmptyReference.create("catalog");

    // for JSON deserializer
    private BackendProduct() { }

    /** Returns the variant with given SKU or this product itself, or null if such variant does not exist. */
    public Variant getVariantBySKU(String sku) {
        if (this.masterVariant == null) {
            return null;    // shouldn't really happen
        }
        if (this.masterVariant.getSKU().equals(sku))
            return this.masterVariant;
        for (Variant v: variants) {
            if (v.getSKU().equals(sku)) return v;
        }
        return null;
    }

    // --------------------------------------------------------
    // Getters
    // --------------------------------------------------------

    /** Unique id of this product. An id is never empty. */
    public String getId() { return id; }

    /** Version of this product that increases when the product is changed. */
    public int getVersion() { return version; }

    /** Name of this product. */
    public String getName() { return name; }

    /** Description of this product. */
    public String getDescription() { return description; }

    /** Vendor of this product.  */
    public Reference<Vendor> getVendor() { return vendor; }

    /** URL friendly name of this product. */
    public String getSlug() { return slug; }

    /** HTML title for product page. */
    public String getMetaTitle() { return metaTitle; }

    /** HTML meta description for product page. */
    public String getMetaDescription() { return metaDescription; }

    /** HTML meta keywords for product page. */
    public String getMetaKeywords() { return metaKeywords; }

    /** Current available stock quantity for this product. */
    public int getQuantityAtHand() { return quantityAtHand; }

    /** Categories this product is assigned to. */
    public List<Reference<BackendCategory>> getCategories() { return categories; }

    /** Master (or 'default') variant of this product. */
    public Variant getMasterVariant() { return masterVariant;}

    /** Other variants of this product besides the master variant. */
    public List<Variant> getVariants() { return variants; }

    /** All catalogs this product is in. */
    public Set<Reference<Catalog>> getCatalogs() { return catalogs; }

    /** One of catalogs; the catalog this product "copy" is in.
    /* If set, implies that this product is not a product in the master catalog. */
    public Reference<Catalog> getCatalog() { return catalog; }

    // --------------------------------------------------------
    // Delegation to master variant
    // --------------------------------------------------------

    /** The main image for this product which is the first image in the {@link #getImageURLs()} list.
     *  Return null if this product has no images. Delegates to master variant. */
    public String getFirstImageURL() {
        return this.masterVariant.getFirstImageURL();
    }

    /** Returns the value of custom attribute with given name, or null if the attribute is not present.
     *  Casts the value to given type. Throws {@link ClassCastException} if the actual type of value is different.
     *  Delegates to master variant. */
    @SuppressWarnings("unchecked")
    public <T> T getAttributeAs(String name) {
        return masterVariant.<T>getAttributeAs(name);
    }

    /** Returns the value of custom attribute with given name, or null if the attribute is not present.
     *  Delegates to master variant. */
    public Object getAttribute(String name) {
        return masterVariant.getAttribute(name);
    }

    /** SKU (Stock Keeping Unit identifier) of this product. SKUs are optional.
     *  Delegates to master variant. */
    public String getSKU() {
        return masterVariant.getSKU();
    }
    /** Price of this product. Delegates to master variant. */
    public Money getPrice() {
        return masterVariant.getPrice();
    }
    /** URLs of images attached to this product. Delegates to master variant. */
    public List<String> getImageURLs() {
        return masterVariant.getImageURLs();
    }
    /** Custom attributes of this product. Delegates to master variant. */
    public List<Attribute> getAttributes() {
        return masterVariant.getAttributes();
    }
}
