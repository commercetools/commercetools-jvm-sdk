package de.commercetools.sphere.client.shop.model;

import java.util.*;

import de.commercetools.sphere.client.model.Money;

import de.commercetools.sphere.client.model.Reference;
import static de.commercetools.internal.util.ListUtil.*;
import net.jcip.annotations.Immutable;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import javax.annotation.Nonnull;

// created from BackendProduct using ModelConversion
/** Product in the product catalog. */
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
    private final ReviewRating rating;

    public Product(String id, int version, String name, String description,
                   Reference<Vendor> vendor, String slug, String metaTitle, String metaDescription, String metaKeywords,
                   int quantityAtHand, Variant masterVariant, List<Variant> variants, List<Category> categories,
                   Set<Reference<Catalog>> catalogs, Reference<Catalog> catalog, ReviewRating reviewRating) {
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
        this.variants = list(masterVariant, variants);
        this.categories = categories;
        this.catalogs = catalogs;
        this.catalog = catalog;
        this.rating = reviewRating;
    }

    /** Returns the variant with given SKU or this product itself, or null if such variant does not exist. */
    public Variant getVariantBySKU(String sku) {
        if (this.masterVariant == null) {
            return null;    // shouldn't happen
        }
        if (this.masterVariant.getSKU().equals(sku))
            return this.masterVariant;
        for (Variant v: variants) {
            if (v.getSKU().equals(sku)) return v;
        }
        return null;
    }

    /** Finds first variant that satisfies all given attribute values.
     *
     *  @param desiredAttribute Attribute that the returned variant must have.
     *
     *  Example:
     *
     *  If you want to implement a variant switcher that changes color but maintains selected size:
     *  <code>
     *      Variant greenVariant = p.getVariant(new Attribute("color", "green"), currentVariant.getAttribute("size"));
     *  </code>
     *
     *  If you want to implement a variant switcher for colors of current product:
     *  <code>
     *      for (Attribute color: product.getAvailableVariantAttributes("color")) {
     *          Variant variant = p.getVariant(color);  // returns first variant for color
     *      }
     *  </code>
     *
     *  @return The variant or null if no such variant exists.
     *  */
    public Variant getVariant(Attribute desiredAttribute, Attribute... desiredAttributes) {
        return getVariant(list(desiredAttribute, desiredAttributes));
    }

    /** Finds first variant that satisfies all given attribute values.
     *
     *  @param desiredAttributes Attributes that the returned variant must have.
     *
     *  Example:
     *
     *  If you want to implement a variant switcher that changes color but maintains selected size:
     *  <code>
     *      Variant greenVariant = p.getVariant(new Attribute("color", "green"), currentVariant.getAttribute("size"));
     *  </code>
     *
     *  If you want to implement a variant switcher for colors of current product:
     *  <code>
     *      for (Attribute color: product.getAvailableVariantAttributes("color")) {
     *          Variant variant = p.getVariant(color);  // returns first variant for color
     *      }
     *  </code>
     *
     *  @return The variant or null if no such variant exists.
     *  */
    public Variant getVariant(@Nonnull Iterable<Attribute> desiredAttributes) {
        Map<String, Attribute> desiredAttributesMap = toMap(desiredAttributes);
        for (Variant v: this.getVariants()) {
            int matchCount = 0;
            for (Attribute a: v.getAttributes()) {
                Attribute desiredAttribute = desiredAttributesMap.get(a.getName());
                if (desiredAttribute != null && (desiredAttribute.getValue().equals(a.getValue()))) {
                   matchCount++;
                }
            }
            if (matchCount == desiredAttributesMap.size()) {
                // has all desiredAttributes
                return v;
            }
        }
        return null;
    }

    /** Gets distinct values of given attribute across all variants of this product. */
    public List<Attribute> getAvailableVariantAttributes(String attributeName) {
        List<Attribute> attributes = new ArrayList<Attribute>();
        Set<Object> seen = new HashSet<Object>();
        for(Variant v: getVariants()) {
            for (Attribute a: v.getAttributes()) {
                if (a.getName().equals(attributeName) && !seen.contains(a.getValue())) {
                    attributes.add(a);
                    seen.add(a.getValue());
                }
            }
        }
        return attributes;
    }

    // --------------------------------------------------------
    // Getters
    // --------------------------------------------------------

    /** Unique id of this product. An id is never empty. */
    public String getId() { return id; }

    /** Version of this product that increases when the product is modified. */
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

    /** Master variant of this product. */
    public Variant getMasterVariant() { return masterVariant;}

    /** All variants of this product including the master variant. */
    public List<Variant> getVariants() { return variants; }

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
    public Object getValue(String attributeName) { return masterVariant.get(attributeName); }

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

    /** Returns the value of a custom date attribute. Delegates to master variant.
     *  @return Returns null if no such attribute is present or if it is not a LocalDate. */
    public LocalDate getDate(String attributeName) { return masterVariant.getDate(attributeName); }

    /** Returns the value of a custom time attribute. Delegates to master variant.
     *  @return Returns null if no such attribute is present or if it is not a LocalTime. */
    public LocalTime getTime(String attributeName) { return masterVariant.getTime(attributeName); }

    /** Returns the value of a custom DateTime attribute. Delegates to master variant.
     *  @return Returns null if no such attribute is present or if it is not a DateTime. */
    public DateTime getDateTime(String attributeName) { return masterVariant.getDateTime(attributeName); }

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

    /** Price of this product. Delegates to master variant. */
    public Money getPrice() { return masterVariant.getPrice(); }

    /** URLs of images attached to this product. Delegates to master variant. */
    public List<String> getImageURLs() { return masterVariant.getImageURLs(); }

    /** Custom attributes of this product. Delegates to master variant. */
    public List<Attribute> getAttributes() { return masterVariant.getAttributes(); }

    // --------------------------------------------------------
    // Helpers
    // --------------------------------------------------------

    /** Copies the attributes of given variant and overrides given attributes (based on name). */
    private Map<String, Attribute> toMap(Iterable<Attribute> attributes) {
        Map<String, Attribute> map = new HashMap<String, Attribute>();
        for (Attribute a: attributes) {
            if (a != null) {
                map.put(a.getName(), a);
            }
        }
        return map;
    }
}
