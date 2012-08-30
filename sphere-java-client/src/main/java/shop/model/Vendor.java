package de.commercetools.sphere.client.shop.model;

import de.commercetools.sphere.client.model.Reference;
import java.util.ArrayList;
import java.util.List;

/** Vendor of a {@link Product}, e.g. Nike. */
public class Vendor {
    private String id;
    private String version;
    private String name;
    private String description;
    private List<String> imageURLs = new ArrayList<String>();

    // for JSON deserializer
    private Vendor() { }

    /** Unique id of this vendor. */
    public String getId() {
        return id;
    }
    /** Version of this vendor that increases when the vendor is changed. */
    public String getVersion() {
        return version;
    }
    /** Name of this vendor. */
    public String getName() {
        return name;
    }
    /** Description of this vendor. */
    public String getDescription() {
        return description;
    }
    /** URLs of images attached to this vendor */
    public List<String> getImageURLs() {
        return imageURLs;
    }
}
