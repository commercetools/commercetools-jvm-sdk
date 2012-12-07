package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.SphereClient;
import net.jcip.annotations.*;

@Immutable
@ThreadSafe
final public class ShopClient implements SphereClient {
    private final ShopClientConfig config;
    private final ProductService   productService;
    private final CategoryTree     categoryTree;
    private final CartService      cartService;
    private final OrderService     orderService;
    private final CustomerService  customerService;
    private final CommentService   commentService;
    private final ReviewService    reviewService;

    public ShopClient(ShopClientConfig config,
                      ProductService   productService,
                      CategoryTree     categoryTree,
                      CartService      cartService,
                      OrderService     orderService,
                      CustomerService  customerService,
                      CommentService   commentService,
                      ReviewService    reviewService) {
        this.config          = config;
        this.productService  = productService;
        this.categoryTree    = categoryTree;
        this.cartService     = cartService;
        this.orderService    = orderService;
        this.customerService = customerService;
        this.commentService  = commentService;
        this.reviewService   = reviewService;
    }

    @Override public ShopClientConfig getConfig() { return this.config; }

    /** Provides access to shop's products. */
    public ProductService products() { return productService; }

    /** Provides access to shop's categories. */
    public CategoryTree categories() { return categoryTree; }

    /** Provides access to shop's shopping carts. */
    public CartService carts() { return cartService; }

    /** Provides access to shop's orders. */
    public OrderService orders() { return orderService; }

    /** Provides access to shop's customers. */
    public CustomerService customers() { return customerService; }

    /** Provides access to shop's comments. */
    public CommentService comments() { return commentService; }

    /** Provides access to shop's reviews. */
    public ReviewService reviews() { return reviewService; }
}
