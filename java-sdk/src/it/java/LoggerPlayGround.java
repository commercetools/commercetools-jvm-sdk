import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

public class LoggerPlayGround extends IntegrationTest {
    @Test
    public void testLogger() throws Exception {
        client().execute(new CategoryQuery());
    }
}
