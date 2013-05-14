package io.sphere.client.model.products;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Money;
import io.sphere.client.model.Reference;
import io.sphere.client.shop.model.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

/** Product in the product catalog. */
@JsonIgnoreProperties({"productType"})
public class BackendProduct {
    private String id;
    private int version;
    private String name;
    private String description;
    private String slug;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
    private Variant masterVariant;
    private List<Variant> variants = new ArrayList<Variant>();
    private List<Reference<BackendCategory>> categories = new ArrayList<Reference<BackendCategory>>(); // initialize to prevent NPEs
    private Set<Reference<Catalog>> catalogs = new HashSet<Reference<Catalog>>();
    private Reference<Catalog> catalog = EmptyReference.create("catalog");
    @JsonProperty("reviewRating") private ReviewRating rating = ReviewRating.empty();

    // for JSON deserializer
    private BackendProduct() { }

    // --------------------------------------------------------
    // Getters
    // --------------------------------------------------------

    /** Unique id of this product. An id is never empty. */
    public String getId() { return id; }

    /** Version of this product. */
    public int getVersion() { return version; }

    /** Name of this product. */
    public String getName() { return name; }

    /** Description of this product. */
    public String getDescription() { return description; }

    /** URL friendly name of this product. */
    public String getSlug() { return slug; }

    /** HTML title for product page. */
    public String getMetaTitle() { return metaTitle; }

    /** HTML meta description for product page. */
    public String getMetaDescription() { return metaDescription; }

    /** HTML meta keywords for product page. */
    public String getMetaKeywords() { return metaKeywords; }

    /** Categories this product is in. */
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

    /** Finds custom attribute with given name. Returns null if no such attribute exists. */
    public Attribute getAttribute(String attributeName) { return masterVariant.getAttribute(attributeName); }

    /** Returns the value of a custom attribute. Delegates to master variant.
     *  @return Returns null if no such attribute is present. */
    public Object get(String attributeName) { return masterVariant.get(attributeName); }

    /** Returns the value of a custom string attribute. Delegates to master variant.
     *  @return Returns an empty string if no such attribute is present or if it is not a string. */
    public String getString(String attributeName) { return masterVariant.getString(attributeName); }

    /** Returns the value of a custom number attribute. Delegates to master variant.
     *  @return Returns 0 if no such attribute is present or if it is not an int. */
    public int getInt(String attributeName) { return masterVariant.getInt(attributeName); }

    /** Returns the value of a custom number attribute. Delegates to master variant.
     *  @return Returns 0 if no such attribute is present or if it is not a double. */
    public double getDouble(String attributeName) { return masterVariant.getDouble(attributeName); }

    /** Returns the value of a custom money attribute. Delegates to master variant.
     *  @return Returns null if no such attribute is present or if it is not of type Money. */
    public Money getMoney(String attributeName) { return masterVariant.getMoney(attributeName); }

    /** Returns the value of a custom DateTime attribute. Delegates to master variant.
     *  @return Returns null if no such attribute is present or if it is not a DateTime. */
    public DateTime getDateTime(String attributeName) { return masterVariant.getDateTime(attributeName); }

    // --------------------------------------------------------
    // Delegation to master variant
    // --------------------------------------------------------

    /** The main image for this product, which is the first image in the {@link #getImages()} list.
     * Delegates to master variant.
     * @return The image or null if the master variant of this product has no images.  */
    public Image getFeaturedImage() { return this.masterVariant.getFeaturedImage(); }

    /** SKU (Stock Keeping Unit identifier) of this product. SKUs are optional.
     *  Delegates to master variant. */
    public String getSKU() { return masterVariant.getSKU(); }

    /** The first price of this product. Delegates to master variant. */
    public Price getPrice() { return masterVariant.getPrice(); }

    /** The prices of this product. Delegates to master variant. */
    public List<Price> getPrices() { return masterVariant.getPrices(); }

    /** Images attached to this product. Delegates to master variant. */
    public List<Image> getImages() { return masterVariant.getImages(); }

    /** Custom attributes of this product. Delegates to master variant. */
    public List<Attribute> getAttributes() { return masterVariant.getAttributes(); }
}
