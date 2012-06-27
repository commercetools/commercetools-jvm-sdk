package sphere;

import play.libs.F;
import sphere.util.OAuthClient;
import sphere.util.ServiceError;
import sphere.util.Tokens;

/** Holds OAuth access tokens for accessing a project using Sphere HTTP APIs.
 *  Refreshes the access token as needed automatically. */
class ClientCredentials {

    private String tokenEndpoint;
    private String projectID;
    private String clientID;
    private String clientSecret;
    
    private String accessToken ;
    private int originalExpiresInSeconds = -1;
    private boolean hasExpiresIn = false;
    /** Time the tokens stored inside this instance were last refreshed, in System.currentTimeMillis(). */
    private long lastUpdateTime = -1L;

    /** Creates a default implementation of ClientCredentials. */
    public static ClientCredentials create(Config config) {
        return new ClientCredentials(Endpoints.tokenEndpoint(config.coreEndpoint()), config.projectID(), config.clientID(), config.clientSecret());
    }

    ClientCredentials(String tokenEndpoint, String projectID, String clientID, String clientSecret) {
        this.tokenEndpoint = tokenEndpoint;
        this.projectID = projectID;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
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

    /** Returns a new initialized instance of OAuthCredentials. Does a blocking call to the authorization server. */
    public static ClientCredentials create(String tokenEndpoint, String projectID, String clientID, String clientSecret) {
        return new ClientCredentials(tokenEndpoint, projectID, clientID, clientSecret);
    }

    /** Asynchronously refreshes the tokens contained in this ClientCredentials instance. */
    public F.Promise<Void> refreshAsync() {
        return getTokenAsync().map(new F.Function<Validation<Tokens>, Void>() {
            @Override
            public Void apply(Validation<Tokens> tokensValidation) throws Throwable {
                if (tokensValidation.isError()) {
                    throw new RuntimeException("Could not obtain credentials: " + tokensValidation.getError().getMessage());
                } else {
                    ClientCredentials.this.update(tokensValidation.getValue());
                    return null; // Void value
                }
            }
        });
    }

    /** Asynchronously gets tokens from the auth server and updates this instance in place. */
    private F.Promise<Validation<Tokens>> getTokenAsync() {
        return OAuthClient.getTokensForClient(tokenEndpoint, clientID, clientSecret, "project:" + projectID,
                new F.Function<ServiceError, Validation<Tokens>>() {
                    @Override
                    public Validation<Tokens> apply(ServiceError loginError) throws Throwable {
                        return new Validation<Tokens>(loginError);
                    }
                },
                new F.Function<Tokens, Validation<Tokens>>() {
                    @Override
                    public Validation<Tokens> apply(Tokens tokens) throws Throwable {
                        return new Validation<Tokens>(tokens);
                    }
                }
        );
    }
}
