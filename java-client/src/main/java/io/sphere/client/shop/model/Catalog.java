package io.sphere.client.shop.model;

import io.sphere.client.model.VersionedId;
import io.sphere.client.model.products.BackendCategory;
import io.sphere.client.model.Reference;
import io.sphere.client.model.EmptyReference;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nonnull;

/** A Catalog is a grouping of products from the master catalog for a certain
 * purpose or under a certain theme.
 *
 * <p>Catalogs are used to organize workflows within the lifecycle of a shop (e.g. staging
 * and online catalogs for different seasons) as well as to organize multiple published
 * catalogs, e.g. distinguished by locale/country (e.g. shop.com, shop.de, ...).
 *
 * <p>Catalogs exist separately from the master catalog. The master catalog is an
 * implicit concept and is comprised of all Products in a Project. Thus, the
 * master catalog cannot be published and consequently a Project must have at
 * least one Catalog in order to publish its Products. */
public class Catalog {
    @Nonnull private String id;
    @JsonProperty("version") private int version;
    private String name;
    private String description;
    private Reference<BackendCategory> rootCategory = EmptyReference.create("rootCategory");

    // for JSON deserializer
    private Catalog() { }

    /** The unique id. */
    @Nonnull public String getId() { return id; }

    /** The {@link #getId() id} plus version. */
    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    /** Display name of this catalog. */
    public String getName() { return name; }

    /** Description of this catalog. */
    public String getDescription() { return description; }

    /** Root of the category tree of this catalog. */
    public Reference<BackendCategory> getRootCategory() { return rootCategory; }
}
