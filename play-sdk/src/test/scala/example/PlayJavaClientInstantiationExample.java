package example;

import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.client.PlayJavaClientImpl;
import play.Configuration;
import play.Play;

public class PlayJavaClientInstantiationExample {
    public void instantiate() throws Exception {
        /* sphere.project, sphere.clientId, sphere.clientSecret should be set in application.conf */
        final Configuration configuration = Play.application().configuration();
        final PlayJavaClient client = new PlayJavaClientImpl(configuration);
    }
}
