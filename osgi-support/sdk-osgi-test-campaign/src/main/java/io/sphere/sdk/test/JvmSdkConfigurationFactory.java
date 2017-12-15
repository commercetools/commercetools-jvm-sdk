package io.sphere.sdk.test;


import org.ops4j.pax.exam.ConfigurationFactory;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.ops4j.pax.exam.options.UrlProvisionOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * This class contains the {@link CoreOptions#options(Option...)} that will be used to set the OSGi environment for the tests
 */
public class JvmSdkConfigurationFactory implements ConfigurationFactory {

    ///Disabling annoying outputs
    static{
        System.setProperty("org.ops4j.pax.logging.DefaultServiceLog.level","WARN");
    }


    @Override
    public Option[] createConfiguration() {


        return options(
                workingDirectory("target/paxexam/"),
                cleanCaches(true),
                getBundles(),
                junitBundles(),
                //disabling noisy logs from apache http
                systemProperty("org.ops4j.pax.logging.DefaultServiceLog.level").value("INFO")
        );
    }

    private static CompositeOption getBundles() {

        List<String> dependencies = Arrays.asList(

                "com.commercetools.sdk.jvm.core.commercetools-sdk-base",
                "com.commercetools.sdk.jvm.core.sdk-http",
                "com.fasterxml.jackson.core.jackson-annotations",
                "com.fasterxml.jackson.core.jackson-core",
                "com.fasterxml.jackson.core.jackson-databind",
                "com.fasterxml.jackson.datatype.jackson-datatype-jsr310",
                "com.fasterxml.jackson.module.jackson-module-parameter-names",
                "com.neovisionaries.i18n",
                "com.google.gson",
                "com.neovisionaries.i18n",
                "org.apache.aries.spifly.dynamic.bundle",
                "org.apache.aries.util",
                "org.apache.commons.codec",
                "org.apache.commons.io",
                "org.apache.commons.lang3",
                "org.apache.commons.logging",
                "org.apache.geronimo.specs.geronimo-annotation_1.2_spec",
                "org.apache.httpcomponents.httpasyncclient",
                "org.apache.httpcomponents.httpclient",
                "org.apache.httpcomponents.httpcore",
                "org.assertj.core",
                "org.objectweb.asm",
                "org.objectweb.asm.commons",
                "org.objectweb.asm.tree",
                "org.objenesis",
                "net.bytebuddy.byte-buddy",
                "net.bytebuddy.byte-buddy-agent",
                "org.mockito.mockito-core",
                "org.ops4j.pax.logging.pax-logging-api",
                "org.ops4j.pax.logging.pax-logging-service",
                //dependencies for tests
                "ch.qos.logback.classic",
                "ch.qos.logback.core"

        );

        List<String> fragementsList = Arrays.asList(

                "com.commercetools.sdk.jvm.core.commercetools-java-client-core",
                "com.commercetools.sdk.jvm.core.sdk-http-apache-async",
                "com.commercetools.sdk.jvm.core.commercetools-java-client-apache-async",
                "com.commercetools.sdk.jvm.core.commercetools-models",
                "com.commercetools.sdk.jvm.core.commercetools-test-lib",

                //Test Fragments
                "com.commercetools.sdk.jvm.core.commercetools-tests-fragment"

        );

        return getBundlesFromNames(dependencies, fragementsList);

    }


    private static CompositeOption getBundlesFromNames(final List<String> plugins,final  List<String> fragments) {
        final Stream<UrlProvisionOption> pluginsUrlProvisionOptionsStream = Optional.ofNullable(plugins).orElseGet(ArrayList::new).stream().map(CoreOptions::linkBundle);
        final Stream<UrlProvisionOption> fragmentsUrlProvisionOptionStream = Optional.ofNullable(fragments).orElseGet(ArrayList::new).stream().map(CoreOptions::linkBundle).map(UrlProvisionOption::noStart);
        UrlProvisionOption[] urlProvisionOptions = Stream.concat(pluginsUrlProvisionOptionsStream, fragmentsUrlProvisionOptionStream).toArray(UrlProvisionOption[]::new);
        return new DefaultCompositeOption(urlProvisionOptions);
    }

}
