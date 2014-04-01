package io.sphere.client.model.products;

import java.util.*;

import io.sphere.client.model.*;
import io.sphere.client.shop.model.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import javax.annotation.Nonnull;

/** The full product that contains both current and staged data for all catalogs. */
@JsonIgnoreProperties({"productType"})
public class BackendProduct {
    @Nonnull private String id;
    @JsonProperty("version") private int version;
    @Nonnull private Set<Reference<Catalog>> catalogs = new HashSet<Reference<Catalog>>();
    @Nonnull private ProductCatalogData masterData;
    @Nonnull private Map<String, ProductCatalogData> catalogData = new HashMap<String, ProductCatalogData>();
    private Reference<TaxCategory> taxCategory = EmptyReference.create("tax-category");
    @JsonProperty("reviewRating") private ReviewRating rating = ReviewRating.empty();

    // for JSON deserializer
    private BackendProduct() { }

    // --------------------------------------------------------
    // Getters
    // --------------------------------------------------------

    /** The unique id. */
    @Nonnull public String getId() { return id; }

    /** The {@link #getId() id} plus version. */
    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    /** The tax category of this product. */
    public Reference<TaxCategory> getTaxCategory() { return taxCategory; }

    /** All catalogs this product is in. */
    @Nonnull public Set<Reference<Catalog>> getCatalogs() { return catalogs; }

    /** Represents the accumulated review scores for the product. */
    @Nonnull public ReviewRating getReviewRating() { return rating; }

    /** The master catalog data. */
    @Nonnull public ProductCatalogData getMasterData() { return masterData; }

    /** The catalog data. */
    @Nonnull public Map<String, ProductCatalogData> getCatalogData() { return catalogData; }

    @Override
    public String toString() {
        return "BackendProduct{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", catalogs=" + catalogs +
                ", masterData=" + masterData +
                ", catalogData=" + catalogData +
                ", taxCategory=" + taxCategory +
                ", rating=" + rating +
                '}';
    }
}
