package de.commercetools.sphere.client.model.products;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import de.commercetools.sphere.client.model.Money;
import de.commercetools.sphere.client.shop.model.*;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import de.commercetools.sphere.client.model.Reference;
import de.commercetools.sphere.client.model.EmptyReference;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

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
    @JsonProperty("reviewRating") private ReviewRating rating = ReviewRating.empty();

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

    /** Represents the accumulated review scores for the product. */
    public ReviewRating getReviewRating() { return rating; }

    // --------------------------------------------------------
    // Get attribute
    // --------------------------------------------------------

    /** Returns true if a custom attribute with given name is present. */
    public boolean hasAttribute(String attributeName) { return masterVariant.hasAttribute(attributeName); }

    /** Returns the value of a custom attribute. Delegates to master variant.
     *  @return Returns null if no such attribute is present. */
    public Object getAttribute(String attributeName) { return masterVariant.getAttribute(attributeName); }

    /** Returns the value of a custom string attribute. Delegates to master variant.
     *  @return Returns an empty string if no such attribute is present or if it is not a string. */
    public String getStringAttribute(String attributeName) { return masterVariant.getStringAttribute(attributeName); }

    /** Returns the value of a custom number attribute. Delegates to master variant.
     *  @return Returns 0 if no such attribute is present or if it is not an int. */
    public int getIntAttribute(String attributeName) { return masterVariant.getIntAttribute(attributeName); }

    /** Returns the value of a custom number attribute. Delegates to master variant.
     *  @return Returns 0 if no such attribute is present or if it is not a double. */
    public double getDoubleAttribute(String attributeName) { return masterVariant.getDoubleAttribute(attributeName); }

    /** Returns the value of a custom money attribute. Delegates to master variant.
     *  @return Returns null if no such attribute is present or if it is not of type Money. */
    public Money getMoneyAttribute(String attributeName) { return masterVariant.getMoneyAttribute(attributeName); }

    /** Returns the value of a custom date attribute. Delegates to master variant.
     *  @return Returns null if no such attribute is present or if it is not a LocalDate. */
    public LocalDate getDateAttribute(String attributeName) { return masterVariant.getDateAttribute(attributeName); }

    /** Returns the value of a custom time attribute. Delegates to master variant.
     *  @return Returns null if no such attribute is present or if it is not a LocalTime. */
    public LocalTime getTimeAttribute(String attributeName) { return masterVariant.getTimeAttribute(attributeName); }

    /** Returns the value of a custom DateTime attribute. Delegates to master variant.
     *  @return Returns null if no such attribute is present or if it is not a DateTime. */
    public DateTime getDateTimeAttribute(String attributeName) { return masterVariant.getDateTimeAttribute(attributeName); }

    // --------------------------------------------------------
    // Delegation to master variant
    // --------------------------------------------------------

    /** The main image for this product which is the first image in the {@link #getImageURLs()} list.
     *  Return null if this product has no images. Delegates to master variant. */
    public String getFirstImageURL() { return this.masterVariant.getFirstImageURL(); }

    /** SKU (Stock Keeping Unit identifier) of this product. SKUs are optional.
     *  Delegates to master variant. */
    public String getSKU() { return masterVariant.getSKU(); }

    /** Price of this product. Delegates to master variant. */
    public Money getPrice() { return masterVariant.getPrice(); }

    /** URLs of images attached to this product. Delegates to master variant. */
    public List<String> getImageURLs() { return masterVariant.getImageURLs(); }

    /** Custom attributes of this product. Delegates to master variant. */
    public List<Attribute> getAttributes() { return masterVariant.getAttributes(); }
}
