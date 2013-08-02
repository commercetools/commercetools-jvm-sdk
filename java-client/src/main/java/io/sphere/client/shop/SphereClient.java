package io.sphere.client.shop;

import io.sphere.client.customobjects.CustomObjectService;
import io.sphere.client.Endpoints;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.oauth.ClientCredentials;
import io.sphere.client.oauth.OAuthClient;
import io.sphere.internal.*;
import io.sphere.internal.oauth.SphereClientCredentials;
import io.sphere.internal.request.BasicRequestFactoryImpl;
import io.sphere.internal.request.ProductRequestFactoryImpl;
import io.sphere.internal.request.RequestFactory;
import io.sphere.internal.request.RequestFactoryImpl;
import io.sphere.internal.util.Log;
import com.ning.http.client.AsyncHttpClient;
import net.jcip.annotations.Immutable;
import java.util.Locale;

/** The main access point to Sphere HTTP APIs.
 *  It is essentially just a configured set of services. */
@Immutable
final public class SphereClient {
    private final SphereClientConfig config;
    private final AsyncHttpClient    httpClient;
    private final ClientCredentials  clientCredentials;
    private final ProductService        productService;
    private final CategoryTree          categoryTree;
    private final CartService           cartService;
    private final OrderService          orderService;
    private final CustomerService       customerService;
    private final CommentService        commentService;
    private final ReviewService         reviewService;
    private final InventoryService      inventoryService;
    private final ShippingMethodService shippingMethodService;
    private final TaxCategoryService    taxCategoryService;
    private final CustomObjectService   customObjectService;

    /** Creates an instance of SphereClient.
     *
     * All dependencies are configurable. This allows for supplying alternate implementations,
     * for example stubs for testing. */
    public SphereClient(SphereClientConfig config,
                        AsyncHttpClient httpClient,
                        ClientCredentials clientCredentials,
                        ProductService productService,
                        CategoryTree categoryTree,
                        CartService cartService,
                        OrderService orderService,
                        CustomerService customerService,
                        CommentService commentService,
                        ReviewService reviewService,
                        InventoryService inventoryService,
                        ShippingMethodService shippingMethodService,
                        TaxCategoryService taxCategoryService,
                        CustomObjectService customObjectService) {
        this.config            = config;
        this.httpClient        = httpClient;
        this.clientCredentials = clientCredentials;
        this.productService        =   productService;
        this.categoryTree          = categoryTree;
        this.cartService           = cartService;
        this.orderService          = orderService;
        this.customerService       = customerService;
        this.commentService        = commentService;
        this.reviewService         = reviewService;
        this.inventoryService      = inventoryService;
        this.shippingMethodService = shippingMethodService;
        this.taxCategoryService    = taxCategoryService;
        this.customObjectService = customObjectService;
    }

    /** Creates an instance of SphereClient. */
    public static SphereClient create(SphereClientConfig config) {
        final AsyncHttpClient httpClient = new AsyncHttpClient();
        ProjectEndpoints projectEndpoints = Endpoints.forProject(
                config.getCoreHttpServiceUrl(),
                config.getProjectKey());
        ClientCredentials clientCredentials = SphereClientCredentials.createAndBeginRefreshInBackground(
                config,
                new OAuthClient(httpClient));
        RequestFactory requestFactory = new RequestFactoryImpl(new BasicRequestFactoryImpl(
                httpClient,
                clientCredentials));
        CategoryTree categoryTree = CategoryTreeImpl.createAndBeginBuildInBackground(
                new CategoriesImpl(requestFactory, projectEndpoints), config.getDefaultLocale());
        return new SphereClient(
            config,
            httpClient,
            clientCredentials,
            new ProductServiceImpl(
                    new ProductRequestFactoryImpl(requestFactory, categoryTree), config.getApiMode(), projectEndpoints,
                                                  config.getDefaultLocale()),
            categoryTree,
            new CartServiceImpl(requestFactory, projectEndpoints),
            new OrderServiceImpl(requestFactory, projectEndpoints),
            new CustomerServiceImpl(requestFactory, projectEndpoints),
            new CommentServiceImpl(requestFactory, projectEndpoints),
            new ReviewServiceImpl(requestFactory, projectEndpoints),
            new InventoryServiceImpl(requestFactory, projectEndpoints),
            new ShippingMethodServiceImpl(requestFactory, projectEndpoints),
            new TaxCategoryServiceImpl(requestFactory, projectEndpoints),
            new CustomObjectServiceImpl(requestFactory, projectEndpoints)
        );
    }

    /** Closes HTTP connections and shuts down internal thread pools.
     *
     * <p>You should call this method right before your application exits, otherwise background threads created by the
     * SphereClient might prevent the process from terminating. */
    public void shutdown() {
        Log.info("Shutting down SphereClient.");
        if (httpClient != null) httpClient.close();
        if (clientCredentials instanceof SphereClientCredentials) ((SphereClientCredentials)clientCredentials).shutdown();
        if (categoryTree instanceof CategoryTreeImpl) ((CategoryTreeImpl)categoryTree).shutdown();
    }

    /** Configuration of the client. */
    public SphereClientConfig getConfig() { return this.config; }

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

    /** Provides access to shop's shipping methods. */
    public ShippingMethodService shippingMethods() { return shippingMethodService; }

    /** Provides access to shop's tax categories. */
    public TaxCategoryService getTaxCategoryService() { return taxCategoryService; }

    /** Provides access to custom objects. */
    public CustomObjectService customObjects() { return customObjectService; }
}
