package sphere;

import java.util.Currency;

import io.sphere.internal.ChaosMode;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.ShopClientConfig;
import io.sphere.client.shop.model.Cart;
import io.sphere.internal.Defaults;

/** Internal configuration of the Sphere SDK.
 *  Use {@link #root()} to get the configured object. */
class SphereConfig implements Config {
    private static class Keys {
        private static final String core              = "sphere.core";
        private static final String auth              = "sphere.auth";
        private static final String project           = "sphere.project";
        private static final String clientId          = "sphere.clientId";
        private static final String clientSecret      = "sphere.clientSecret";
        private static final String apiMode           = "sphere.api.mode";
        private static final String cartCurrency      = "sphere.cartCurrency";
        private static final String cartInventoryMode = "sphere.cartInventoryMode";
        private static final String chaosLevel        = "sphere.chaosLevel";
    }

    private final play.Configuration playConfig;

    private static final SphereConfig instance = new SphereConfig(play.Configuration.root());
    public static SphereConfig root() { return instance; }

    /** The configuration for a {@link io.sphere.client.shop.ShopClient}. */
    public ShopClientConfig createShopClientConfig() {
        return new ShopClientConfig.Builder(project(), clientId(), clientSecret())
            .setCoreHttpServiceUrl(coreEndpoint())
            .setAuthHttpServiceUrl(authEndpoint())
            .setApiMode(apiMode())
            .build();
    }

    /** Creates a new instance of config. */
    SphereConfig(play.Configuration playConfig) {
        this.playConfig = playConfig;
    }

    // -----------------------------
    // Config values
    // -----------------------------

    /** Main Sphere API endpoint. Configured as 'sphere.api'. */
    public String coreEndpoint() { return getStringOrThrow(Keys.core); }

    /** Sphere authorization service endpoint. */
    public String authEndpoint() { return getStringOrThrow(Keys.auth); }

    /** Name of your project. Configured as 'sphere.project'. */
    public String project() { return getStringOrThrow(Keys.project); }

    /** Id of your project, generated in the developer center. Configured as 'sphere.clientId'. */
    public String clientId() { return getStringOrThrow(Keys.clientId); }

    /** Authorization key for your project, generated in the developer center. Configured as 'sphere.clientSecret'. */
    public String clientSecret() { return getStringOrThrow(Keys.clientSecret); }

    /** Level of simulated API errors, to test how your client handles them. */
    public int chaosLevel() {
       Integer value = playConfig.getInt(Keys.chaosLevel);
       return value == null ? 0 : Math.min(Math.max(value, ChaosMode.minLevel), ChaosMode.maxLevel);
    }

    /** Specifies whether {@linkplain ApiMode staging or live} data is accessed by the shop client. */
    public ApiMode apiMode() {
        String value = playConfig.getString(Keys.apiMode);
        if (value == null) return Defaults.apiMode;
        if (value.toLowerCase().equals("live")) return ApiMode.Published;
        if (value.toLowerCase().equals("staging")) return ApiMode.Staged;
        throw playConfig.reportError(Keys.apiMode, "'" + Keys.apiMode + "' must be \"live\" or \"staging\". Was \"" + value + "\".", null);
    }

    /** The inventory mode of the shopping cart. */
    public Cart.InventoryMode cartInventoryMode() {
        return Cart.InventoryMode.valueOf(getStringOrThrow(Keys.cartInventoryMode));
    }

    /** Currency used for shopping carts. */
    public Currency cartCurrency() {
        String currencyCode = getStringOrThrow(Keys.cartCurrency);
        try {
            return Currency.getInstance(currencyCode);
        } catch (Exception e) {
            throw playConfig.reportError(Keys.cartCurrency, "Not a valid ISO 4217 currency code: " + currencyCode, e);
        }
    }

    // -----------------------------
    // Helpers
    // -----------------------------

    /** Converts a null value returned by Play Configuration into an exception.
     *
     *  It's better to fail fast rather than pass around a null and crashing later. */
    private String getStringOrThrow(String key) {
        String value = playConfig.getString(key);
        if (value == null) {
            throw playConfig.reportError(key, "Path '" + key + "' not found in configuration.", null);
        } else if (value.equals("")) {
            throw playConfig.reportError(key, "Path '" + key + "' can't be empty in configuration.", null);
        } else {
            return value;
        }
    }
}
