package sphere.util;

import play.libs.F;

/** Holds OAuth access tokens needed to access Sphere HTTP APIs.
 *  Refreshes the access token as needed automatically. */
public class ClientCredentials {

    private String clientID = null;
    private String clientSecret = null;
    private String accessToken = null;
    private int originalExpiresInSeconds = -1;
    private boolean hasExpiresIn = false;
    /** Time the tokens stored inside this instance were last refreshed, in System.currentTimeMillis(). */
    private long lastUpdateTime = -1L;

    protected ClientCredentials(String clientID, String clientSecret) {
        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }
    
    public String accessToken() {
        return accessToken;
    }
    public boolean hasExpiresIn() {
        return hasExpiresIn;
    }
    /** Original validity of the access token in seconds. */
    public int originalExpiresInSeconds() {
        return originalExpiresInSeconds;
    }
    /** Remaining validity of the access token in seconds. */
    public long expiresInSeconds() {
        if (!hasExpiresIn()) {
            // What to do if we don't know the validity of the token?
            return 0;
        }
        return Math.max(0, originalExpiresInSeconds() - (System.currentTimeMillis() - lastUpdateTime) / 1000L);
    }

    /** Returns a new initialized instance of OAuthCredentials. Does a blocking call to the authorization server. */
    public static ClientCredentials getFromAuthorizationServer(String tokenEndpoint, String projectID, String clientID, String clientSecret) {
        ClientCredentials credentials = new ClientCredentials(clientID, clientSecret);
        Validation<Tokens> maybeTokens = credentials.getProjectToken(tokenEndpoint, projectID, clientID, clientSecret).get();
        if (maybeTokens.isError()) {
            throw new RuntimeException("Could not obtain credentials: " + maybeTokens.getError().getMessage());
        } else {
            credentials.update(maybeTokens.getValue());
        }
        return credentials;
    }
    
    private void update(Tokens tokens) {
        this.lastUpdateTime = System.currentTimeMillis();
        this.accessToken = tokens.getAccessToken();
        this.hasExpiresIn = tokens.hasExpiresIn();
        this.originalExpiresInSeconds = tokens.getExpiresIn();
    }

    /** Asynchronously gets tokens from the auth server and updates this instance in place. */
    private static F.Promise<Validation<Tokens>> getProjectToken(String tokenEndpoint, String projectID, String clientID, String clientSecret) {
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
