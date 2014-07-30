import io.sphere.sdk.categories.Category;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

public class LoggerPlayGround extends IntegrationTest {
    @Test
    public void testLogger() throws Exception {
        client().execute(Category.query());
    }
}
