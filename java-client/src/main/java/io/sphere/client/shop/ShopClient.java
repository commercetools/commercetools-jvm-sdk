package io.sphere.client.shop;

import com.ning.http.client.AsyncHttpClient;
import io.sphere.internal.*;
import io.sphere.internal.oauth.ShopClientCredentials;
import io.sphere.internal.request.BasicRequestFactoryImpl;
import io.sphere.internal.request.ProductRequestFactoryImpl;
import io.sphere.internal.request.RequestFactory;
import io.sphere.internal.request.RequestFactoryImpl;
import io.sphere.client.Endpoints;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.oauth.OAuthClient;
import net.jcip.annotations.Immutable;

/** ShopClient is the main access point to Sphere HTTP APIs.
 *  It is essentially just a configured set of services. */
@Immutable
final public class ShopClient {
    private final ShopClientConfig config;
    private final ProductService   productService;
    private final CategoryTree     categoryTree;
    private final CartService      cartService;
    private final OrderService     orderService;
    private final CustomerService  customerService;
    private final CommentService   commentService;
    private final ReviewService    reviewService;
    private final InventoryService inventoryService;

    /** Creates an instance of ShopClient.
     *
     * All dependencies are configurable. This allows for supplying alternate implementations,
     * for example stubs for testing. */
    public ShopClient(ShopClientConfig config,
                      ProductService   productService,
                      CategoryTree     categoryTree,
                      CartService      cartService,
                      OrderService     orderService,
                      CustomerService  customerService,
                      CommentService   commentService,
                      ReviewService    reviewService,
                      InventoryService inventoryService) {
        this.config           = config;
        this.productService   = productService;
        this.categoryTree     = categoryTree;
        this.cartService      = cartService;
        this.orderService     = orderService;
        this.customerService  = customerService;
        this.commentService   = commentService;
        this.reviewService    = reviewService;
        this.inventoryService = inventoryService;
    }

    /** Creates an instance of ShopClient. */
    public static ShopClient create(ShopClientConfig config) {
        final AsyncHttpClient httpClient = new AsyncHttpClient();
        ProjectEndpoints projectEndpoints = Endpoints.forProject(
                config.getCoreHttpServiceUrl(),
                config.getProjectKey());
        RequestFactory requestFactory = new RequestFactoryImpl(new BasicRequestFactoryImpl(
                httpClient,
                ShopClientCredentials.createAndBeginRefreshInBackground(
                        config,
                        new OAuthClient(httpClient))));
        CategoryTree categoryTree = CategoryTreeImpl.createAndBeginBuildInBackground(
                new CategoriesImpl(requestFactory, projectEndpoints));
        return new ShopClient(
            config,
            new ProductServiceImpl(
                    new ProductRequestFactoryImpl(requestFactory, categoryTree), config.getApiMode(), projectEndpoints),
            categoryTree,
            new CartServiceImpl(requestFactory, projectEndpoints),
            new OrderServiceImpl(requestFactory, projectEndpoints),
            new CustomerServiceImpl(requestFactory, projectEndpoints),
            new CommentServiceImpl(requestFactory, projectEndpoints),
            new ReviewServiceImpl(requestFactory, projectEndpoints),
            new InventoryServiceImpl(requestFactory, projectEndpoints));
    }

    /** Configuration of the client. */
    public ShopClientConfig getConfig() { return this.config; }

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

    /** Provides access to shop's product inventory. */
    public InventoryService inventory() { return inventoryService; }
}
