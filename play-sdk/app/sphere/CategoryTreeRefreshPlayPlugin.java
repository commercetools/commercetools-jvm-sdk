package sphere;

import akka.actor.Cancellable;
import play.Application;
import play.Configuration;
import play.Logger;
import play.Plugin;
import play.libs.Akka;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

/**
 * A plugin that frequently updates the cache for the category tree.
 * You need to wire it into the projectRoot/conf/play.plugins file:
 * 9000:sphere.CategoryTreeRefreshPlayPlugin
 *
 * Configuration keys for application.conf:
 * <pre>sphere.categories.refresh.enabled</pre> a boolean if the plugin is enabled, default: true
 * <pre>sphere.categories.refresh.initialDelay</pre> duration before the first category update, example: 15min
 * <pre>sphere.categories.refresh.delay</pre> duration between the starts of the category updates, example: 15min
 */
public class CategoryTreeRefreshPlayPlugin extends Plugin {
    private final Application app;
    private final Configuration config;
    private Cancellable task;
    private final Logger.ALogger logger = Logger.of(CategoryTreeRefreshPlayPlugin.class);

    public CategoryTreeRefreshPlayPlugin(Application app) {
        this.app = app;
        this.config = app.configuration().getConfig("sphere.categories.refresh");
    }

    @Override
    public void onStart() {
        logger.info("Starting category refresh plugin.");
        final FiniteDuration initialDelay = convert(Duration.create(config.getString("initialDelay")));
        final FiniteDuration delay = convert(Duration.create(config.getString("delay")));
        task = Akka.system().scheduler().schedule(initialDelay, delay, new UnitOfWork(), Akka.system().dispatcher());
    }

    @Override
    public void onStop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public boolean enabled() {
        return config.getBoolean("enabled");
    }

    private static FiniteDuration convert(final Duration duration) {
        return new FiniteDuration(duration.length(), duration.unit());
    }

    private class UnitOfWork implements Runnable {
        @Override
        public void run() {
            logger.debug("Refreshing the category tree.");
            Sphere.getInstance().categories().rebuildAsync();
        }
    }
}
