package io.sphere.sdk.test;


import org.ops4j.pax.exam.ConfigurationFactory;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.ops4j.pax.exam.options.UrlProvisionOption;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.options;

public class JvmSdkConfigurationFactory implements ConfigurationFactory {


    public Option[] createConfiguration() {
        return options(
                getBundles(),
                junitBundles()
        );
    }

    private static CompositeOption getBundles() {

        List<String> dependencies = Arrays.asList(

                "com.commercetools.sdk.jvm.core.commercetools-sdk-base",
                "com.commercetools.sdk.jvm.core.sdk-http",
                "com.commercetools.sdk.jvm.core.org.javamoney-wrapper",
                "com.commercetools.sdk.jvm.core.javax.money-wrapper",
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

                //dependencies for tests
                "ch.qos.logback.classic",
                "ch.qos.logback.core",
                "slf4j.api"

        );

        List<String> fragementsList = Arrays.asList(

                "com.commercetools.sdk.jvm.core.commercetools-java-client-core",
                "com.commercetools.sdk.jvm.core.sdk-http-apache-async",
                "com.commercetools.sdk.jvm.core.commercetools-java-client-apache-async",
                "com.commercetools.sdk.jvm.core.commercetools-models",
                "com.commercetools.sdk.jvm.core.commercetools-test-lib",

                //Test Fragments
                "com.commercetools.sdk.jvm.core.commercetools-tests-fragment",
                "com.commercetools.sdk.jvm.core.commercetools-models-tests-fragment"

        );

        return getBundlesFromNames(dependencies, fragementsList);

    }


    private static CompositeOption getBundlesFromNames(final List<String> plugins,final  List<String> fragments) {

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
