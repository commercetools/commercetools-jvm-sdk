package io.sphere.client.model.products;

import com.google.common.collect.ImmutableMap;
import io.sphere.client.model.LocalizedString;
import io.sphere.client.model.Reference;
import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.VersionedId;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Internal representation of a category in the form the backend returns it.
 *  See {@link io.sphere.client.shop.model.Category} for a more user friendly version. */
public class BackendCategory {
    @Nonnull private String id = "";
    @JsonProperty("version") private int version;
    private LocalizedString name        = new LocalizedString(ImmutableMap.of(Locale.ENGLISH, ""));
    private LocalizedString description = new LocalizedString(ImmutableMap.of(Locale.ENGLISH, ""));
    @Nonnull private List<Reference<BackendCategory>> ancestors = new ArrayList<Reference<BackendCategory>>();
    @Nonnull private Reference<BackendCategory> parent = EmptyReference.create("parent");
    @Nonnull private List<BackendCategory> children = new ArrayList<BackendCategory>();

    // for JSON deserializer
    private BackendCategory() { }

    /** The unique id. */
    @Nonnull public String getId() { return id; }

    /** The {@link #getId() id} plus version. */
    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    /** Gets the name of this category. */
    public LocalizedString getName() { return name; }

    /** Gets the description of this category. */
    public LocalizedString getDescription() { return description; }

    /** Gets a reference to the parent category. */
    @Nonnull public Reference<BackendCategory> getParent() { return parent; }

    /** Gets references to all ancestors (parents of parents) of this category.
     *  The list is sorted from the farthest parent to the closest (the last element being the direct parent). */
    @Nonnull public List<Reference<BackendCategory>> getAncestors() { return ancestors; }

    /** Gets child categories of this category. */
    @Nonnull public List<BackendCategory> getChildren() { return children; }
}
