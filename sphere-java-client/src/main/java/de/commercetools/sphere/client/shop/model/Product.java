package de.commercetools.sphere.client.shop.model;

import java.util.*;

import de.commercetools.sphere.client.model.Money;

import de.commercetools.sphere.client.model.Reference;
import static de.commercetools.internal.util.ListUtil.*;
import net.jcip.annotations.Immutable;

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
    private final ReviewRating reviewRating;


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
        this.reviewRating = reviewRating;
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

    /** Finds a variant that has the same attributes as given variant, with the exception of specified attribute changes.
     *  This is useful e.g. for implementing a variant switcher, where you want to change values of several attributes
     *  but keep the remaining attributes fixed.
     *
     *  Returns null if no variant satisfying given conditions exists.
     *
     *  Example:
     *  <code>
     *      Variant greenVariant = p.getRelatedVariantExact(currentVariant, Arrays.asList(new Attribute("color", "green")));
     *  </code>
     *  */
    public Variant getRelatedVariantExact(Variant variant, Iterable<Attribute> desiredAttributes) {
        if (variant == null) return null;
        Map<String, Attribute> originalAttributesMap = toMap(variant.getAttributes());
        Map<String, Attribute> desiredAttributesMap = toMap(desiredAttributes);
        for (Variant v: getVariants()) {
            if (variantMatchesExactly(v, originalAttributesMap, desiredAttributesMap)) {
                return v;
            }
        }
        return null;
    }

    /** Finds a variant that has the same attributes as given variant, with the exception of one attribute being changed.
     *  This is useful e.g. for implementing a variant switcher, where you want to change the value of one attribute
     *  but keep the remaining attributes fixed.
     *
     *  Returns null if no variant satisfying given conditions exists.
     *
     *  Example:
     *  <code>
     *      Variant greenVariant = p.getRelatedVariantExact(currentVariant, new Attribute("color", "green"));
     *  </code>
     *  */
    public Variant getRelatedVariantExact(Variant variant, Attribute desiredAttribute) {
        return getRelatedVariantExact(variant, Collections.singletonList(desiredAttribute));
    }

    // TODO we might also want a method getVariantClosest(Iterable<Attribute> attributes) - not based on any variant

    /** Tries to find a variant that has the same attributes as given variant, with the exception of specified attribute changes.
     *  This is useful e.g. for implementing a variant switcher, where you want to change values of several attributes
     *  but keep the remaining attributes fixed.
     *
     *  Returns the variant closest to satisfying given conditions -
     *  this means even the original variant itself if no other variant is a better match.
     *
     *  Example:
     *  <code>
     *      Variant greenVariant = p.getRelatedVariantClosest(currentVariant, Arrays.asList(new Attribute("color", "green")));
     *  </code>
     *  */
    public Variant getRelatedVariantClosest(Variant variant, Iterable<Attribute> desiredAttributes) {
        if (variant == null) return null;
        Variant bestVariant = variant;
        Map<String, Attribute> originalAttributesMap = toMap(toList(variant.getAttributes()));
        Map<String, Attribute> desiredAttributesMap = toMap(desiredAttributes);
        double bestScore = 0.0;
        for (Variant v: getVariants()) {
            Double score = variantMatchScore(v, originalAttributesMap, desiredAttributesMap);
            if (score > bestScore) {
                bestScore = score;
                bestVariant = v;
            }
        }
        return bestVariant;
    }

    /** Tries to find a variant that has the same attributes as given variant, with the exception of one attribute being changed.
     *  This is useful e.g. for implementing a variant switcher, where you want to change the value of one attribute
     *  but keep the remaining attributes fixed.
     *
     *  Returns the variant closest to satisfying given conditions -
     *  this means even the original variant itself if no other variant is a better match.
     *
     *  Example:
     *  <code>
     *      Variant greenVariant = p.getRelatedVariantClosest(currentVariant, new Attribute("color", "green"));
     *  </code>
     *  */
    public Variant getRelatedVariantClosest(Variant variant, Attribute desiredAttribute) {
        return getRelatedVariantClosest(variant, Collections.singletonList(desiredAttribute));
    }

    /** Gets distinct values of given attribute across all variants of this product. */
    public List<Object> getAvailableAttributeValues(String attributeName) {
        Set<Object> values = new HashSet<Object>();
        for(Variant v: getVariants()) {
            for (Attribute a: v.getAttributes()) {
                if (a.getName().equals(attributeName)) {
                    values.add(a.getValue());  // Set -> duplicates skipped
                }
            }
        }
        return new ArrayList<Object>(values);
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

    /** Master variant of this product. */
    public Variant getMasterVariant() { return masterVariant;}

    /** All variants of this product including the master variant. */
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
    public String getFirstImageURL() { return this.masterVariant.getFirstImageURL(); }

    /** Returns the value of custom attribute with given name, or null if the attribute is not present.
     *  Casts the value to given type. Throws {@link ClassCastException} if the actual type of value is different.
     *  Delegates to master variant. */
    @SuppressWarnings("unchecked")
    public <T> T getAttributeAs(String name) { return masterVariant.getAttributeAs(name); }

    /** Returns the value of custom attribute with given name, or null if the attribute is not present.
     *  Delegates to master variant. */
    public Object getAttribute(String name) { return masterVariant.getAttribute(name); }

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

    /** Returns a score from 0.0 to 1.0 based on how well a given variant matches given conditions.
     *  A match on explicitly desired attributes is more valuable than similarity to the original variant. */
    private Double variantMatchScore(Variant v, Map<String, Attribute> originalAttributes, Map<String, Attribute> desiredAttributes) {
        int matchScore = 0;
        for (Attribute a: v.getAttributes()) {
            Attribute matchingOriginal = originalAttributes.get(a.getName());
            if (matchingOriginal != null && matchingOriginal.getValue().equals(a.getValue())) {
                matchScore += 0.5;
            }
            Attribute matchingDesired = desiredAttributes.get(a.getName());
            if (matchingDesired != null && matchingDesired.getValue().equals(a.getValue())) {
                matchScore += 1.0;
            }
        }
        return matchScore / (double)(originalAttributes.size() + desiredAttributes.size());
    }

    private boolean variantMatchesExactly(Variant v, Map<String, Attribute> originalAttributes, Map<String, Attribute> desiredAttributes) {
        Map<String, Attribute> exactAttributes = new HashMap<String, Attribute>(originalAttributes);
        exactAttributes.putAll(desiredAttributes);   // override
        // v must have exactly the same set of attributes as exactAttributes
        if (v.getAttributes().size() != exactAttributes.size()) {
            return false;
        }
        for (Attribute a: v.getAttributes()) {
            Attribute matching = exactAttributes.get(a.getName());
            if (matching == null || !matching.getValue().equals(a.getValue())) {
                return false;
            }
        }
        return true;
    }

    /** Copies the attributes of given variant and overrides given attributes (based on name). */
    private Map<String, Attribute> toMap(Iterable<Attribute> attributes) {
        Map<String, Attribute> map = new HashMap<String, Attribute>();
        for (Attribute a: attributes) {
            map.put(a.getName(), a);
        }
        return map;
    }

    /** Represents the accumulated review scores for the product. */
    public ReviewRating getReviewRating() {
        return reviewRating;
    }
}
