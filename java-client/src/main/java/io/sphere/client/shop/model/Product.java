package io.sphere.client.shop.model;

import java.util.List;
import java.util.Set;

import io.sphere.client.model.Money;
import io.sphere.client.model.Reference;
import static io.sphere.internal.util.ListUtil.list;

import net.jcip.annotations.Immutable;
import org.joda.time.DateTime;

// created from BackendProduct using ModelConversion
/** Product in the product catalog. */
@Immutable
public class Product {
    private final String id;
    private final int version;
    private final String name;
    private final String description;
    private final String slug;
    private final String metaTitle;
    private final String metaDescription;
    private final String metaKeywords;
    private final Variant masterVariant;
    private final VariantList variants;
    private final List<Category> categories;
    private final Set<Reference<Catalog>> catalogs;
    private final Reference<Catalog> catalog;
    private final ReviewRating rating;

    public Product(String id, int version, String name, String description,
                   String slug, String metaTitle, String metaDescription, String metaKeywords,
                   Variant masterVariant, List<Variant> variants, List<Category> categories,
                   Set<Reference<Catalog>> catalogs, Reference<Catalog> catalog, ReviewRating reviewRating) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
        this.masterVariant = masterVariant;
        this.variants = new VariantList(list(masterVariant, variants));
        this.categories = categories;
        this.catalogs = catalogs;
        this.catalog = catalog;
        this.rating = reviewRating;
    }

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
    public List<Category> getCategories() { return categories; }

    /** Master variant of this product. */
    public Variant getMasterVariant() { return masterVariant;}

    /** All variants of this product including the master variant. */
    public VariantList getVariants() { return variants; }

    /** All catalogs this product is in. */
    public Set<Reference<Catalog>> getCatalogs() { return catalogs; }

    /** One of catalogs; the catalog this product "copy" is in.
    /* If set, implies that this product is not a product in the master catalog. */
    public Reference<Catalog> getCatalog() { return catalog; }

    /** Represents the accumulated review scores for the product. */
    public ReviewRating getRating() { return rating; }

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
