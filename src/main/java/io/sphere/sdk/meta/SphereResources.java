package io.sphere.sdk.meta;

/**
  <ul>
      <li>{@link io.sphere.sdk.products.Product} - Products are the sellable goods in an e-commerce project on SPHERE.IO.</li>
      <li>{@link io.sphere.sdk.products.ProductProjection} - A projected representation of a product shows the product with its current or staged data.</li>
      <li>{@link io.sphere.sdk.categories.Category} - Categories are used to organize products in a hierarchical structure.</li>
      <li>{@link io.sphere.sdk.taxcategories.TaxCategory} - Tax Categories define how products are to be taxed in different countries.</li>
      <li>{@link io.sphere.sdk.channels.Channel} - Channels represent a source or destination of different entities.</li>
      <li>{@link io.sphere.sdk.producttypes.ProductType} - Product types are used to describe common characteristics, most importantly common custom attributes, of many concrete products.</li>
      <li>{@link io.sphere.sdk.productdiscounts.ProductDiscount} - Product discounts are used to change certain product prices.</li>
  </ul>

  <br>

  {@doc.gen list clientrequests}
 */
public final class SphereResources {
    private SphereResources() {
    }
}
