package test;

import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.ops4j.pax.exam.options.UrlProvisionOption;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class
 *
 */
public interface OSGITestUtils {

    static CompositeOption getBundlesFromLinkDir(final String path) {
        final File file = new File(path);
        final String extension = ".link";
        final List<String> urlProvisionOptions = Arrays.stream(file.listFiles())
                .filter(File::isFile)
                .map(File::getName)
                .filter(name -> name.endsWith(extension))
                .map(name -> name.substring(0, name.lastIndexOf(extension)))
                .collect(Collectors.toList());
        urlProvisionOptions.stream().forEach(name -> System.out.println(",\"" + name + "\""));
        return getBundlesFromNames(Arrays.asList(), urlProvisionOptions);
    }

    static CompositeOption getBundlesFromNames(final List<String> plugins, List<String> fragments) {


        final Stream<UrlProvisionOption> pluginsUrlProvisionOptionsStream = plugins == null ? Stream.empty() : plugins.stream()
                .map(CoreOptions::linkBundle);

        final Stream<UrlProvisionOption> fragmentsUrlProvisionOptionStream = fragments == null ? Stream.empty() : fragments.stream()
                .map(CoreOptions::linkBundle)
                .map(UrlProvisionOption::noStart);

        UrlProvisionOption[] urlProvisionOptions = Stream.concat(pluginsUrlProvisionOptionsStream, fragmentsUrlProvisionOptionStream)
                .toArray(UrlProvisionOption[]::new);

        return new DefaultCompositeOption(urlProvisionOptions);
    }

}
