package javax.money.spi;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.logging.Logger;

/**
 * This class was overriding from the javamoney library in order to alter the classloader behavior, though it might not be signaled as used by the ide,
 * it will replace the one provided from the library after the compilation, as long as the canonical class names are kept identical.
 */
public final class Bootstrap {

    private static volatile ServiceProvider serviceProviderDelegate;

    private static final Object LOCK = new Object();

    private Bootstrap() {
    }

    private static ServiceProvider loadDefaultServiceProvider() {
        try {
            for (ServiceProvider sp : ServiceLoader.load(ServiceProvider.class,ServiceProvider.class.getClassLoader())) {
                return sp;
            }
        } catch (Exception e) {
            Logger.getLogger(Bootstrap.class.getName()).info("No ServiceProvider loaded, using default.");
        }
        return new DefaultServiceProvider();
    }

    public static ServiceProvider init(ServiceProvider serviceProvider) {
        Objects.requireNonNull(serviceProvider);
        synchronized (LOCK) {
            if (Objects.isNull(Bootstrap.serviceProviderDelegate)) {
                Bootstrap.serviceProviderDelegate = serviceProvider;
                Logger.getLogger(Bootstrap.class.getName())
                        .info("Money Bootstrap: new ServiceProvider set: " + serviceProvider.getClass().getName());
                return null;
            } else {
                ServiceProvider prevProvider = Bootstrap.serviceProviderDelegate;
                Bootstrap.serviceProviderDelegate = serviceProvider;
                Logger.getLogger(Bootstrap.class.getName())
                        .warning("Money Bootstrap: ServiceProvider replaced: " + serviceProvider.getClass().getName());
                return prevProvider;
            }
        }
    }

    static ServiceProvider getServiceProvider() {
        if (Objects.isNull(serviceProviderDelegate)) {
            synchronized (LOCK) {
                if (Objects.isNull(serviceProviderDelegate)) {
                    serviceProviderDelegate = loadDefaultServiceProvider();
                }
            }
        }
        return serviceProviderDelegate;
    }

    public static <T> Collection<T> getServices(Class<T> serviceType) {
        return getServiceProvider().getServices(serviceType);
    }

    public static <T> T getService(Class<T> serviceType) {
        List<T> services = getServiceProvider().getServices(serviceType);
		return services
				.stream()
                .sorted((o1, o2) -> o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName()))
                .findFirst()
                .orElse(null);
    }

}
