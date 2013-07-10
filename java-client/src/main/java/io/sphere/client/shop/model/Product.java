package io.sphere.client.shop.model;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.google.common.base.Strings;
import io.sphere.client.model.LocalizedString;
import io.sphere.client.model.Money;
import io.sphere.client.model.Reference;
import static io.sphere.internal.util.ListUtil.list;

import io.sphere.client.model.VersionedId;
import net.jcip.annotations.Immutable;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;

// created from BackendProduct using ProductConversion
/** Products are the sellable goods in an e-commerce project on Sphere.
 *
 *  <p>
 *  A product can have several variants. For example a specific piece of clothing can be available in multiple sizes
 *  and colors and each of the distinct combinations of (size, color) is represented by a variant.
 *  <p>
 *  Each product always has at least one variant - the {@link #getMasterVariant()} master variant}.
 *  All the variants, including the master variant, are available as a {@link #getVariants() variant list} which also
 *  offers some useful filtering capabilities.
 *  <p>
 *  Every product variant, including the master variant, can hold custom attributes. These are accessible via the methods
 *  {@link #getString(String) getString}, {@link #getInt(String) getInt}, {@link #getMoney(String) getMoney} etc.
 *  For example, if you know your products have a custom number attribute called {@code "volume"}, you can access the value
 *  using {@code product.getInt("volume")}. In some cases, for example when you only want to display the value and its exact
 *  type is not important, you can use {@code product.get("volume")} which returns an {@code Object}.
 *  <p>
 *  The Product class has several convenience methods to access the master variant. For example, calling {@link #getImages() getImages()}
 *  is equivalent to calling {@code getMasterVariant().getImages()}. The same applies to {@link #getPrice() getPrice},
 *  the methods for accessing custom attributes and others - see the documentation of individual methods for reference.
 *  */
@Immutable
public class Product {
    @Nonnull private final String id;
    private final int version;
    private final LocalizedString name;
    private final LocalizedString description;
    private final LocalizedString slug;
    private final LocalizedString metaTitle;
    private final LocalizedString metaDescription;
    private final LocalizedString metaKeywords;
    @Nonnull private final Variant masterVariant;
    @Nonnull private final VariantList variants;
    @Nonnull private final List<Category> categories;
    @Nonnull private final Set<Reference<Catalog>> catalogs;
    @Nonnull private final Reference<Catalog> catalog;
    @Nonnull private final ReviewRating rating;

    public Product(VersionedId idAndVersion, LocalizedString name, LocalizedString description,
                   LocalizedString slug, LocalizedString metaTitle, LocalizedString metaDescription, LocalizedString metaKeywords,
                   Variant masterVariant, List<Variant> variants, List<Category> categories,
                   Set<Reference<Catalog>> catalogs, Reference<Catalog> catalog, ReviewRating reviewRating) {
        if (idAndVersion == null) throw new NullPointerException("idAndVersion");
        if (masterVariant == null) throw new NullPointerException("masterVariant");
        if (variants == null) throw new NullPointerException("variants");
        if (categories == null) throw new NullPointerException("categories");
        if (catalogs == null) throw new NullPointerException("catalogs");
        if (catalog == null) throw new NullPointerException("catalog");
        if (reviewRating == null) throw new NullPointerException("reviewRating");
        this.id = idAndVersion.getId();
        this.version = idAndVersion.getVersion();
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

    /** The unique id. */
    @Nonnull public String getId() { return id; }

    /** The {@link #getId() id} plus version. */
    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    /** Name of this product. */
    public String getName() { return name.get(); }
    public String getName(Locale locale) { return name.get(locale); }
    /**
     * Will try the given locales to retrieve the translated name until it finds a non-empty one.
     * Otherwise returns the empty String.
     */
    public String getName(Locale... locales) { return name.get(locales); }

    /** Description of this product. */
    public String getDescription() { return description.get(); }
    public String getDescription(Locale locale) { return description.get(locale); }
    /**
     * Will try the given locales to retrieve the translated description until it finds a non-empty one.
     * Otherwise returns the empty String.
     */
    public String getDescription(Locale... locales) { return description.get(locales); }

    /** URL friendly name of this product. */
    public String getSlug() { return slug.get(); }
    public String getSlug(Locale locale) { return slug.get(locale); }

    /** HTML title for product page. */
    public String getMetaTitle() { return metaTitle.get(); }
    public String getMetaTitle(Locale locale) { return metaTitle.get(locale); }
    /**
     * Will try the given locales to retrieve the translated meta title until it finds a non-empty one.
     * Otherwise returns the empty String.
     */
    public String getMetaTitle(Locale... locales) { return metaTitle.get(locales); }

    /** HTML meta description for product page. */
    public String getMetaDescription() { return metaDescription.get(); }
    public String getMetaDescription(Locale locale) { return metaDescription.get(locale); }
    /**
     * Will try the given locales to retrieve the translated meta description until it finds a non-empty one.
     * Otherwise returns the empty String.
     */
    public String getMetaDescription(Locale... locales) { return metaDescription.get(locales); }

    /** HTML meta keywords for product page. */
    public String getMetaKeywords() { return metaKeywords.get(); }
    public String getMetaKeywords(Locale locale) { return metaKeywords.get(locale); }
    /**
     * Will try the given locales to retrieve the translated meta keywords until it finds a non-empty one.
     * Otherwise returns the empty String.
     */
    public String getMetaKeywords(Locale... locales) { return metaKeywords.get(locales); }

    /** Master variant of this product. */
    @Nonnull public Variant getMasterVariant() { return masterVariant;}

    /** All variants of this product including the master variant. */
    @Nonnull public VariantList getVariants() { return variants; }

    /** Categories this product is in. */
    @Nonnull public List<Category> getCategories() { return categories; }

    /** All catalogs this product is in. */
    @Nonnull public Set<Reference<Catalog>> getCatalogs() { return catalogs; }

    /** One of catalogs; the catalog this product "copy" is in.
    /* If set, implies that this product is not a product in the master catalog. */
    @Nonnull public Reference<Catalog> getCatalog() { return catalog; }

    /** Represents the accumulated review scores for the product. */
    @Nonnull public ReviewRating getRating() { return rating; }

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

    /** Returns the value of a custom Enum attribute. Delegates to master variant.
     *  @return Returns the empty String if no such attribute is present or if it is not an Enum. */
    public Attribute.Enum getEnum(String attributeName) { return masterVariant.getEnum(attributeName); }


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
