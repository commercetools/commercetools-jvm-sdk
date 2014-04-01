package io.sphere.client.model.products;

import org.codehaus.jackson.annotate.JsonProperty;

/** ProductData is a part of for the BackendProduct. */
public class ProductCatalogData {
    private ProductData current;
    private ProductData staged;
    @JsonProperty("published") private boolean published;

    // for JSON deserializer
    private ProductCatalogData() { }

    /** The current product data. */
    public ProductData getCurrent() { return current; }

    /** The staged product data. */
    public ProductData getStaged() { return staged; }

    /** @return true if this product catalog data has been published. */
    public boolean isPublished() { return published; }

    @Override public String toString() {
        return "ProductCatalogData{" +
                "current=" + current +
                ", staged=" + staged +
                ", published=" + published +
                '}';
    }
}
