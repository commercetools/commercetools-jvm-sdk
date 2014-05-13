package io.sphere.client.shop.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import io.sphere.client.model.ReferenceId;
import io.sphere.client.model.VersionedId;

public class TaxCategory {
    private static final String typeId = "tax-category";
    @Nonnull private String id;
    private int version;
    @Nonnull private String name;
    @Nonnull private String description = "";
    @Nonnull private List<TaxRate> rates = new ArrayList<TaxRate>();

    // for JSON deserializer
    protected TaxCategory() {}

    /** The unique id. */
    @Nonnull public String getId() { return id; }

    /** The {@link #getId() id} plus version. */
    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    /** The name of the tax category. */
    @Nonnull public String getName() { return name; }

    /** The description of the tax category. */
    @Nonnull public String getDescription() { return description; }

    /** The tax rates. */
    @Nonnull public List<TaxRate> getRates() { return rates; }

    /** The ReferenceId for this tax category. */
    @Nonnull public ReferenceId<TaxCategory> getReferenceId() { return reference(id); }


    @Override
    public String toString() {
        return "TaxCategory{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rates=" + rates +
                '}';
    }

    public static ReferenceId<TaxCategory> reference(String id) {
        return ReferenceId.create(typeId, id);
    }
}
