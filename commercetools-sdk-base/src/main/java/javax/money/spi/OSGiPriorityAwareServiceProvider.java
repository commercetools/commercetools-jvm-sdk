package javax.money.spi;

import org.javamoney.moneta.spi.PriorityAwareServiceProvider;

import javax.annotation.Priority;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class replaces the {@link PriorityAwareServiceProvider} provided since the latest
 * contains some bug that doesen't allow using it with OSGi, these bugs are mainly due to not specifying
 * the {@link ClassLoader} when using {@link ServiceLoader#load(Class, ClassLoader)}
 *
 */
public final class OSGiPriorityAwareServiceProvider implements ServiceProvider {
    /**
     * List of services loaded, per class.
     */
    private final ConcurrentHashMap<Class<?>, List<Object>> servicesLoaded = new ConcurrentHashMap<>();

    /**
     * Returns a priority value of 10.
     *
     * @return 10, overriding the default provider.
     */
    @Override
    public int getPriority() {
        return 10;
    }

    /**
     * Loads and registers services.
     *
     * @param serviceType The service type.
     * @param <T>         the concrete type.
     * @return the items found, never {@code null}.
     */
    @Override
    public <T> List<T> getServices(final Class<T> serviceType) {
        @SuppressWarnings("unchecked")
        List<T> found = (List<T>) servicesLoaded.get(serviceType);
        if (found != null) {
            return found;
        }

        return loadServices(serviceType);
    }

    public static int compareServices(final Object o1,final  Object o2) {
        int prio1 = 0;
        int prio2 = 0;
        Priority prio1Annot = o1.getClass().getAnnotation(Priority.class);
        if (prio1Annot != null) {
            prio1 = prio1Annot.value();
        }
        Priority prio2Annot = o2.getClass().getAnnotation(Priority.class);
        if (prio2Annot != null) {
            prio2 = prio2Annot.value();
        }
        if (prio1 < prio2) {
            return 1;
        }
        if (prio2 < prio1) {
            return -1;
        }
        return o2.getClass().getSimpleName().compareTo(o1.getClass().getSimpleName());
    }

    /**
     * Loads and registers services.
     *
     * @param serviceType The service type.
     * @param <T>         the concrete type.
     * @return the items found, never {@code null}.
     */
    private <T> List<T> loadServices(final Class<T> serviceType) {
        List<T> services = new ArrayList<>();
        try {
            for (T t : ServiceLoader.load(serviceType, OSGiPriorityAwareServiceProvider.class.getClassLoader())) {
                services.add(t);
            }
            services.sort(OSGiPriorityAwareServiceProvider::compareServices);
            @SuppressWarnings("unchecked")
            final List<T> previousServices = (List<T>) servicesLoaded.putIfAbsent(serviceType, (List<Object>) services);
            return Collections.unmodifiableList(previousServices != null ? previousServices : services);
        } catch (Exception e) {
            Logger.getLogger(PriorityAwareServiceProvider.class.getName()).log(Level.WARNING,
                    "Error loading services of type " + serviceType, e);
            services.sort(PriorityAwareServiceProvider::compareServices);
            return services;
        }
    }

}
