package io.sphere.sdk.test;

import io.sphere.sdk.client.SphereAuthConfig;
import io.sphere.sdk.client.SphereAuthConfigBuilder;
import io.sphere.sdk.client.SphereProjectScope;
import io.sphere.sdk.retry.AsyncRetrySupervisor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.ops4j.pax.exam.options.UrlProvisionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * This is a demo test that shows a minimum configuration for use in production in an OSGi setup
 */
@RunWith(PaxExam.class)
public class DemoOSGiMinimalConfigTest {

    @Configuration
    public Option[] createConfiguration() {
        return options(
                workingDirectory("target/paxexam/"),
                cleanCaches(true),
                getBundles(),
                junitBundles()
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
                "org.objectweb.asm",
                "org.objectweb.asm.commons",
                "org.objectweb.asm.tree"
        );

        List<String> fragementsList = Arrays.asList(

                "com.commercetools.sdk.jvm.core.commercetools-java-client-core",
                "com.commercetools.sdk.jvm.core.sdk-http-apache-async",
                "com.commercetools.sdk.jvm.core.commercetools-java-client-apache-async",
                "com.commercetools.sdk.jvm.core.commercetools-models"

        );

        return getBundlesFromNames(dependencies, fragementsList);

    }

    @Test
    public void test(){

        final Logger logger = LoggerFactory.getLogger(AsyncRetrySupervisor.class);
        final SphereAuthConfig config = SphereAuthConfigBuilder
                .ofKeyIdSecret("projectKey", "clientId", "clientSecret")
                .scopes(asList(SphereProjectScope.MANAGE_CUSTOMERS, SphereProjectScope.VIEW_ORDERS))
                .build();
        logger.info(config.toString());

    }


    private static CompositeOption getBundlesFromNames(final List<String> plugins,final  List<String> fragments) {
        final Stream<UrlProvisionOption> pluginsUrlProvisionOptionsStream = Optional.ofNullable(plugins).orElseGet(ArrayList::new).stream().map(CoreOptions::linkBundle);
        final Stream<UrlProvisionOption> fragmentsUrlProvisionOptionStream = Optional.ofNullable(fragments).orElseGet(ArrayList::new).stream().map(CoreOptions::linkBundle).map(UrlProvisionOption::noStart);
        UrlProvisionOption[] urlProvisionOptions = Stream.concat(pluginsUrlProvisionOptionsStream, fragmentsUrlProvisionOptionStream).toArray(UrlProvisionOption[]::new);
        return new DefaultCompositeOption(urlProvisionOptions);
    }

}
