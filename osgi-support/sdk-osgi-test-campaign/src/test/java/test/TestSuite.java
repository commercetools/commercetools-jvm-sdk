package test;

import org.junit.Test;
import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.ClassnameFilters;
import org.junit.extensions.cpsuite.ClasspathSuite.SuiteTypes;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.options.FrameworkPropertyOption;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.exam.spi.reactors.PerSuite;
import org.ops4j.pax.tinybundles.core.TinyBundle;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.extensions.cpsuite.SuiteType.TEST_CLASSES;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.junit.extensions.cpsuite.ClasspathSuite.*;
import static org.ops4j.pax.tinybundles.core.TinyBundles.*;

/**
 * This is a test suite case that reruns all the tests but in an OSGI container
 */
@RunWith(PaxExam.class)

@ExamReactorStrategy(PerClass.class)
@IncludeJars(true)
public class TestSuite {

    @Configuration
    public Option[] configure() throws IOException {
        return options(
                getBundles(),
                junitBundles()

        );
    }


    private static CompositeOption getBundles() {

        List<String> dependencies = Arrays.asList(

                "com.commercetools.sdk.jvm.core.commercetools-sdk-base",
                "com.commercetools.sdk.jvm.core.org.javamoney-wrapper",
                "com.commercetools.sdk.jvm.core.javax.money-wrapper",
                "com.commercetools.sdk.jvm.core.sdk-http",
                //"com.commercetools.sdk.jvm.core.commercetools-test-lib",
                // "com.commercetools.sdk.jvm.core.commercetools-unbandled-osgi-dependencies",
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
                "org.jsr-305",
                "org.objectweb.asm",
                "org.objectweb.asm.commons",
                "org.objectweb.asm.tree",
                "osgi.core",

//                "io.netty.buffer",
//                "io.netty.codec",
//
//                "io.netty.codec-http",
//                "io.netty.common",
//
//                "io.netty.handler",
//                "io.netty.transport",
//
//                "io.netty.transport-native-epoll",


                //Some unnecessary  just to make sure tests work
                "org.reflections",
                "com.google.guava",
                "javassist"

        );

        List<String> fragementsList = Arrays.asList(

                "com.commercetools.sdk.jvm.core.commercetools-java-client-core",
                "com.commercetools.sdk.jvm.core.sdk-http-apache-async",
                "com.commercetools.sdk.jvm.core.commercetools-models",
                "com.commercetools.sdk.jvm.core.commercetools-java-client-apache-async",
//                //Test Fragments
                "com.commercetools.sdk.jvm.core.sdk-http-tests-fragment",
                "com.commercetools.sdk.jvm.core.commercetools-sdk-base-tests-fragment",
//                "com.commercetools.sdk.jvm.core.commercetools-models-tests-fragment",
                "com.commercetools.sdk.jvm.core.commercetools-java-client-core-tests-fragment",
                "com.commercetools.sdk.jvm.core.commercetools-java-client-apache-async-tests-fragment"

        );


        return OSGITestUtils.getBundlesFromNames(dependencies, fragementsList);


    }


}
