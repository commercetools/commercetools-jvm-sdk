package sphere;

import play.libs.F;
import sphere.util.OAuthClient;
import sphere.util.ServiceError;
import sphere.util.Tokens;

/** Holds OAuth access tokens for accessing protected Sphere HTTP API endpoints.
 *  Refreshes the access token as needed automatically. */
// TODO auto refreshing
class ClientCredentialsImpl implements ClientCredentials {

    private String tokenEndpoint;
    private String projectID;
    private String clientID;
    private String clientSecret;

    private OAuthClient oauthClient;
    
    private String accessToken ;
    private int originalExpiresInSeconds = -1;
    private boolean hasExpiresIn = false;
    /** Time the tokens stored inside this instance were last refreshed, in System.currentTimeMillis(). */
    private long lastUpdateTime = -1L;

    /** Creates a default implementation of ClientCredentials. */
    public static ClientCredentialsImpl create(Config config, OAuthClient oauthClient) {
        return new ClientCredentialsImpl(Endpoints.tokenEndpoint(config.authEndpoint()), config.projectID(), config.clientID(), config.clientSecret(), oauthClient);
    }

    ClientCredentialsImpl(String tokenEndpoint, String projectID, String clientID, String clientSecret, OAuthClient oauthClient) {
        this.tokenEndpoint = tokenEndpoint;
        this.projectID = projectID;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        this.oauthClient  = oauthClient;
    }

    public synchronized String accessToken() {
        return accessToken;
    }
    public synchronized boolean hasExpiresIn() {
        return hasExpiresIn;
    }
    /** Original validity of the access token in seconds. */
    public synchronized int originalExpiresInSeconds() {
        return originalExpiresInSeconds;
    }
    /** Remaining validity of the access token in seconds. */
    public synchronized long expiresInSeconds() {
        if (!hasExpiresIn()) {
            // What to do if we don't know the validity of the token?
            return 0;
        }
        return Math.max(0, originalExpiresInSeconds() - (System.currentTimeMillis() - lastUpdateTime) / 1000L);
    }

    private synchronized void update(Tokens tokens) {
        this.lastUpdateTime = System.currentTimeMillis();
        this.accessToken = tokens.getAccessToken();
        this.hasExpiresIn = tokens.hasExpiresIn();
        this.originalExpiresInSeconds = tokens.getExpiresIn();
    }

    /** Asynchronously refreshes the tokens contained in this ClientCredentials instance. */
    public F.Promise<Validation<Void>> refreshAsync() {
        return getTokenAsync().map(new F.Function<Validation<Tokens>, Validation<Void>>() {
            @Override
            public Validation<Void> apply(Validation<Tokens> tokensValidation) throws Throwable {
                if (tokensValidation.isError()) {
                    String message = "Could not obtain credentials: " + tokensValidation.getError().getMessage();
                    sphere.Log.error(message);
                    return Validation.<Void>failure(tokensValidation.getError());
                } else {
                    ClientCredentialsImpl.this.update(tokensValidation.getValue());
                    return Validation.<Void>success(null);
                }
            }
        });
    }

    /** Asynchronously gets tokens from the auth server and updates this instance in place. */
    private F.Promise<Validation<Tokens>> getTokenAsync() {
        return oauthClient.getTokensForClient(tokenEndpoint, clientID, clientSecret, "project:" + projectID,
            new F.Function<ServiceError, Validation<Tokens>>() {
                @Override
                public Validation<Tokens> apply(ServiceError error) throws Throwable {
                    return Validation.<Tokens>failure(error);
                }
            },
            new F.Function<Tokens, Validation<Tokens>>() {
                @Override
                public Validation<Tokens> apply(Tokens tokens) throws Throwable {
                    return Validation.<Tokens>success(tokens);
                }
            }
        );
    }
}
