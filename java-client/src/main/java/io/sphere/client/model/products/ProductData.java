package io.sphere.client.model.products;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import io.sphere.client.model.LocalizedString;
import io.sphere.client.model.Reference;
import io.sphere.client.shop.model.Variant;
import org.codehaus.jackson.annotate.JsonProperty;


/** ProductData is a part of for the BackendProduct/ProductCatalogData. */
public class ProductData {

    private LocalizedString name;
    private LocalizedString description;
    @Nonnull private List<Reference<BackendCategory>> categories = new ArrayList<Reference<BackendCategory>>(); // initialize to prevent NPEs
    private LocalizedString slug;
    private LocalizedString metaTitle;
    private LocalizedString metaDescription;
    private LocalizedString metaKeywords;
    @Nonnull private Variant masterVariant;
    @Nonnull private List<Variant> variants = new ArrayList<Variant>();

    @JsonProperty("hasStagedChanges") private boolean hasStagedChanges;
    @JsonProperty("published") private boolean published;
    
    // for JSON deserializer
    private ProductData() { }

    /** Name of this product data. */
    public LocalizedString getName() { return name; }

    /** Description of this product data. */
    public LocalizedString getDescription() { return description; }

    /** URL friendly name of this product data. */
    public LocalizedString getSlug() { return slug; }

    /** HTML title for product page. */
    public LocalizedString getMetaTitle() { return metaTitle; }

    /** HTML meta description for product page. */
    public LocalizedString getMetaDescription() { return metaDescription; }

    /** HTML meta keywords for product page. */
    public LocalizedString getMetaKeywords() { return metaKeywords; }

    /** Categories this product data is in. */
    @Nonnull public List<Reference<BackendCategory>> getCategories() { return categories; }

    /** Master (or 'default') variant of this product data. */
    @Nonnull public Variant getMasterVariant() { return masterVariant; }

    /** Other variants of this product data besides the master variant. */
    @Nonnull public List<Variant> getVariants() { return variants; }

    /** @return true if this product data contains changes that have not been published. */
    public boolean hasStagedChanges() { return hasStagedChanges; }

    /** @return true if the product data has been published. */
    public boolean isPublished() { return published; }

    @Override public String toString() {
        return "ProductData{" +
                "name=" + name +
                ", description=" + description +
                ", categories=" + categories +
                ", slug=" + slug +
                ", metaTitle=" + metaTitle +
                ", metaDescription=" + metaDescription +
                ", metaKeywords=" + metaKeywords +
                ", masterVariant=" + masterVariant +
                ", variants=" + variants +
                ", hasStagedChanges=" + hasStagedChanges +
                ", published=" + published +
                '}';
    }
}
