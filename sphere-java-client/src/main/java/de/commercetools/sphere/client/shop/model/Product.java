package de.commercetools.sphere.client.shop.model;

import java.util.List;
import java.util.Set;

import de.commercetools.sphere.client.model.Money;

import de.commercetools.sphere.client.model.Reference;
import net.jcip.annotations.Immutable;

// created from BackendProduct using ModelConversion
/**
 *  Product in the product catalog.
 */
@Immutable
public class Product {
    private final String id;
    private final int version;
    private final String name;
    private final String description;
    private final Reference<Vendor> vendor;
    private final String slug;
    private final String metaTitle;
    private final String metaDescription;
    private final String metaKeywords;
    private final int quantityAtHand;
    private final Variant masterVariant;
    private final List<Variant> variants;
    private final List<Category> categories;
    private final Set<Reference<Catalog>> catalogs;
    private final Reference<Catalog> catalog;
    private final ReviewRating reviewRating;

    public Product(String id,
                   int version,
                   String name,
                   String description,
                   Reference<Vendor> vendor,
                   String slug,
                   String metaTitle,
                   String metaDescription,
                   String metaKeywords,
                   int quantityAtHand,
                   Variant masterVariant,
                   List<Variant> variants,
                   List<Category> categories,
                   Set<Reference<Catalog>> catalogs,
                   Reference<Catalog> catalog,
                   ReviewRating reviewRating) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.description = description;
        this.vendor = vendor;
        this.slug = slug;
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
        this.quantityAtHand = quantityAtHand;
        this.masterVariant = masterVariant;
        this.variants = variants;
        this.categories = categories;
        this.catalogs = catalogs;
        this.catalog = catalog;
        this.reviewRating = reviewRating;
    }

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
    public List<Category> getCategories() { return categories; }

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

    /** Represents the accumulated review scores for the product. */
    public ReviewRating getReviewRating() {
        return reviewRating;
    }
}
