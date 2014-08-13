package example;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.client.PlayJavaClientImpl;
import play.Configuration;
import play.Play;

import java.util.HashMap;
import java.util.Map;

public class PlayJavaClientInstantiationExample {

    public void instantiate() throws Exception {
        /* sphere.project, sphere.clientId, sphere.clientSecret should be set in application.conf */
        Configuration configuration = Play.application().configuration();
        PlayJavaClient client = new PlayJavaClientImpl(configuration);
    }

    public void forIntegrationTest() throws Exception {
        Config defaultValuesFromClasspath = ConfigFactory.load();
        Map<String, Object> values = new HashMap<>();
        values.put("sphere.project", "your project key");
        values.put("sphere.clientId", "your client id");
        values.put("sphere.clientSecret", "your client secret");
        Config config = ConfigFactory.parseMap(values).withFallback(defaultValuesFromClasspath);
        PlayJavaClient client = new PlayJavaClientImpl(config);
    }
}
