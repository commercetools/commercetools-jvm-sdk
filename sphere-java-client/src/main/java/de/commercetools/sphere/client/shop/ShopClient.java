package de.commercetools.sphere.client.shop;

import de.commercetools.internal.Categories;
import de.commercetools.sphere.client.SphereClient;
import net.jcip.annotations.*;

@Immutable
@ThreadSafe
final public class ShopClient implements SphereClient {

    private final ShopClientConfig config;
    private final Products         productService;
    private final CategoryTree     categoryTree;
    private final Carts            cartService;
    private final Orders           orderService;
    private final CustomerService  customerService;

    public ShopClient(ShopClientConfig config,
                      Products products, CategoryTree categoryTree, Carts carts, Orders orders, CustomerService customerService) {
        this.config = config;
        this.productService = products;
        this.categoryTree = categoryTree;
        this.cartService = carts;
        this.orderService = orders;
        this.customerService = customerService;
    }

    @Override public ShopClientConfig getConfig() { return this.config; }

    /** Provides access to shop's products. */
    public Products products() { return productService; }

    /** Provides access to shop's categories. */
    public CategoryTree categories() { return categoryTree; }

    /** Provides access to shop's shopping carts. */
    public Carts carts() { return cartService; }

    /** Provides access to shop's orders. */
    public Orders orders() { return orderService; }

    /** Provides access to shop's customers. */
    public CustomerService customers() { return customerService; }
}
