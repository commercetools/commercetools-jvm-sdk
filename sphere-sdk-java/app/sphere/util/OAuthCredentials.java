package sphere.util;

import play.libs.F;

/** Holds OAuth access tokens needed to access Sphere HTTP APIs.
 *  Refreshes the access token as needed automatically. */
public class OAuthCredentials {

    private String accessToken = null;
    private String refreshToken = null;
    private int originalExpiresInSeconds = -1;
    private boolean hasExpiresIn = false;
    private LoginError error = null;
    /** Time the tokens stored inside this instance were last refreshed, in System.currentTimeMillis(). */
    private long lastRefreshTime = -1L;
    private boolean hasBeenInitialized = false;

    protected OAuthCredentials() {}
    
    protected boolean hasBeenInitialized() {
        return hasBeenInitialized;
    }

    protected void throwIfInvalid() {
        if (!hasBeenInitialized()) {
            throw new IllegalStateException("Credentials are still being initialized. Try again in a while.");
        } else {
          if (error != null)
              throw new IllegalStateException("Could not obtain credentials: " + error.getMessage());
        }
    }

    public String accessToken() {
        throwIfInvalid();
        return accessToken;
    }
    public String refreshToken() {
        throwIfInvalid();
        return refreshToken;
    }
    public boolean hasExpiresIn() {
        throwIfInvalid();
        return hasExpiresIn;
    }
    /** Original validity of the access token in seconds. */
    public int originalExpiresInSeconds() {
        throwIfInvalid();
        return originalExpiresInSeconds;
    }
    /** Remaining validity of the access token in seconds. */
    public long expiresInSecond() {
        throwIfInvalid();
        if (!hasExpiresIn()) {
            // What to do if we don't know the validity of the token?
            return 0;
        }
        return Math.max(0, originalExpiresInSeconds() - (System.currentTimeMillis() - lastRefreshTime) / 1000L);
    }

    /** Returns a new instance of OAuthCredentials and starts its initialization
     *  (obtaining OAuth tokens from the authorization server) in the background. */
    public static OAuthCredentials initClientCredentialsAsync(String tokenEndpoint, String clientID, String clientSecret) {
        OAuthCredentials credentials = new OAuthCredentials();
        credentials.doInitClientCredentialsAsync(tokenEndpoint, clientID, clientSecret);
        return credentials;
    }

    /** Asynchronously gets tokens from the auth server and updates this instance in place. */
    protected void doInitClientCredentialsAsync(String tokenEndpoint, String clientID, String clientSecret) {
        OAuthClient.getTokensForClient(tokenEndpoint, clientID, clientSecret,
            new F.Function<LoginError, Void>() {
                @Override
                public Void apply(LoginError loginError) throws Throwable {
                    OAuthCredentials self = OAuthCredentials.this;
                    self.error = loginError;
                    self.hasBeenInitialized = true;
                    return null;
                }
            },
            new F.Function<Tokens, Void>() {
                @Override
                public Void apply(Tokens tokens) throws Throwable {
                    OAuthCredentials self = OAuthCredentials.this;
                    self.accessToken = tokens.getAccessToken();
                    self.refreshToken = tokens.getRefreshToken();
                    self.originalExpiresInSeconds = tokens.getExpiresIn();
                    self.hasExpiresIn = tokens.hasExpiresIn();
                    self.lastRefreshTime = System.currentTimeMillis();
                    self.hasBeenInitialized = true;
                    return null;
                }
            }
        );
    }
}
