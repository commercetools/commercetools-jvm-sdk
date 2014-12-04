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
      <li>{@link io.sphere.sdk.carts.Cart} - A shopping cart holds product variants and can be turned into an order. Each cart either belongs to a registered customer or it is an anonymous cart.</li>
      <li>{@link io.sphere.sdk.customers.Customer} - A customer is a person purchasing products. Carts, Orders, Comments and Reviews can be associated to a customer.</li>
      <li>{@link io.sphere.sdk.customergroups.CustomerGroup} - A Customer can be a member of several customer groups (e.g. reseller, gold member). Special prices can be assigned to specific products based on a customer group.</li>
      <li>{@link io.sphere.sdk.orders.Order} - An order is the final state of a cart, usually created after a checkout process has been completed.</li>
  </ul>

  <br>

  {@doc.gen list clientrequests}
 */
public final class SphereResources {
    private SphereResources() {
    }
}
