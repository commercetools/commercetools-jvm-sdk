package sphere;

import java.util.Currency;
import java.util.Locale;

import com.google.common.base.Joiner;
import io.sphere.client.SphereClientException;
import io.sphere.client.shop.SphereClientConfig;
import io.sphere.internal.ChaosMode;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.model.Cart;
import io.sphere.internal.util.Util;
import static io.sphere.client.shop.ConfigurationKeys.*;

/** Internal configuration of the Sphere SDK.
 *  Use {@link #root()} to get the configured object. */
class SphereConfig implements Config {
    private static final String configPrefix = "sphere";
    private final play.Configuration sphereConfig;

    private static final SphereConfig instance = new SphereConfig(play.Configuration.root());
    public static SphereConfig root() { return instance; }

    /** The configuration for a {@link io.sphere.client.shop.SphereClient}. */
    public SphereClientConfig createSphereClientConfig() {
        String localeString = sphereConfig.getString(DEFAULT_LOCALE, "en");
        Locale locale = Util.fromLanguageTag(localeString);
        return new SphereClientConfig.Builder(project(), clientId(), clientSecret(), locale)
            .setCoreHttpServiceUrl(coreEndpoint())
            .setAuthHttpServiceUrl(authEndpoint())
            .setApiMode(apiMode())
            .build();
    }

    /** Creates a new instance of config. */
    SphereConfig(play.Configuration playConfig) {
        this.sphereConfig = playConfig.getConfig(configPrefix);
    }

    // -----------------------------
    // Config values
    // -----------------------------

    /** Main Sphere API endpoint. Configured as 'sphere.api'. */
    public String coreEndpoint() { return getStringOrThrow(CORE_URL); }

    /** Sphere authorization service endpoint. */
    public String authEndpoint() { return getStringOrThrow(AUTH_URL); }

    /** Name of your project. Configured as 'sphere.project'. */
    public String project() {
        String value = getStringOrThrow(PROJECT);
        try {
            SphereClientConfig.validateProjectKey(value);
            return value;
        } catch (SphereClientException e) {
            throw sphereConfig.reportError(PROJECT, e.getMessage(), e);
        }
    }

    /** Id of your project, generated in the developer center. Configured as 'sphere.clientId'. */
    public String clientId() { return getStringOrThrow(CLIENT_ID); }

    /** Authorization key for your project, generated in the developer center. Configured as 'sphere.clientSecret'. */
    public String clientSecret() { return getStringOrThrow(CLIENT_SECRET); }

    /** Level of simulated API errors, to test how your client handles them. */
    public int chaosLevel() {
       Integer value = sphereConfig.getInt(CHAOS_LEVEL);
       return value == null ? 0 : Math.min(Math.max(value, ChaosMode.minLevel), ChaosMode.maxLevel);
    }

    /** Specifies whether {@linkplain ApiMode staging or live} data is accessed by the shop client. */
    public ApiMode apiMode() {
        String value = sphereConfig.getString(PRODUCTS_API_MODE);
        if (value == null) return ApiMode.Published;
        if (value.toLowerCase().equals("published")) return ApiMode.Published;
        if (value.toLowerCase().equals("staged")) return ApiMode.Staged;
        throw sphereConfig.reportError(PRODUCTS_API_MODE, "'" + configPrefix + "." + PRODUCTS_API_MODE + "' must be \"published\" or \"staged\". Was \"" + value + "\".", null);
    }

    /** The inventory mode of the shopping cart. */
    public Cart.InventoryMode cartInventoryMode() {
        String value = sphereConfig.getString(CART_INVENTORY_MODE);
        if (value == null) return Cart.InventoryMode.None;
        try {
            return Cart.InventoryMode.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw sphereConfig.reportError(CART_INVENTORY_MODE, "Invalid value for cart.inventoryMode: '" + value + "'. " +
                    "Valid values are: " + Joiner.on(", ").join(Cart.InventoryMode.values()), e);
        }
    }

    /** Currency used for shopping carts. */
    public Currency cartCurrency() {
        String currencyCode = getStringOrThrow(CART_CURRENCY);
        try {
            return Currency.getInstance(currencyCode);
        } catch (Exception e) {
            throw sphereConfig.reportError(CART_CURRENCY, "'" + configPrefix + "." + currencyCode + "' is not a valid ISO 4217 currency code.", e);
        }
    }

    // -----------------------------
    // Helpers
    // -----------------------------

    /** Converts a null value returned by Play Configuration into an exception.
     *
     *  It's better to fail fast rather than pass around a null and crashing later. */
    private String getStringOrThrow(final String key) {
        String value = sphereConfig.getString(key);
        if (value == null) {
            throw sphereConfig.reportError(key, "Path '" + configPrefix + "." + key + "' not found in configuration.", null);
        } else if (value.equals("")) {
            throw sphereConfig.reportError(key, "Path '" + configPrefix + "." + key + "' can't be empty in configuration.", null);
        } else {
            return value;
        }
    }
}
