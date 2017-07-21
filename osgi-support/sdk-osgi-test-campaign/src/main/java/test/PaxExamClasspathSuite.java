
package test;

import org.junit.Test;
import org.junit.extensions.cpsuite.ClassesFinder;
import org.junit.extensions.cpsuite.ClassesFinderFactory;
import org.junit.extensions.cpsuite.ClasspathFinderFactory;
import org.junit.extensions.cpsuite.SuiteType;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeElementsScanner;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.extensions.cpsuite.ClasspathSuite.*;

public class PaxExamClasspathSuite extends Suite {

    private static final boolean DEFAULT_INCLUDE_JARS = false;
    private static final SuiteType[] DEFAULT_SUITE_TYPES = new SuiteType[]{SuiteType.TEST_CLASSES};
    private static final Class<?>[] DEFAULT_BASE_TYPES = new Class<?>[]{Object.class};
    private static final Class<?>[] DEFAULT_EXCLUDED_BASES_TYPES = new Class<?>[0];
    private static final String[] DEFAULT_CLASSNAME_FILTERS = new String[0];
    private static final String DEFAULT_CLASSPATH_PROPERTY = "java.class.path";
    private final Class<?> suiteClass;

    /**
     * Used by JUnit
     */
    public PaxExamClasspathSuite(Class<?> suiteClass, RunnerBuilder builder) throws InitializationError, InstantiationException, IllegalAccessException {
        this(suiteClass, builder, new ClasspathFinderFactory());
    }

    /**
     * For testing purposes only
     */
    public PaxExamClasspathSuite(Class<?> suiteClass, RunnerBuilder builder, ClassesFinderFactory factory) throws InitializationError, InstantiationException, IllegalAccessException {
        super(suiteClass, setPaxExamRunnerForTestSuitClasses(getSortedTestclasses(createFinder(suiteClass, factory)), suiteClass));
        this.suiteClass = suiteClass;
    }

    private static List<Runner> setPaxExamRunnerForTestSuitClasses(final Class<?>[] classes, Class testSuiteClass) throws InitializationError, InstantiationException, IllegalAccessException {
        List<Runner> runners = new ArrayList<>();
        for (Class clazz : classes) {
            runners.add(new CustomProbeRunner(clazz, testSuiteClass));
        }
        return runners;
    }


    private static ClassesFinder createFinder(Class<?> suiteClass, ClassesFinderFactory finderFactory) {
        return finderFactory.create(getSearchInJars(suiteClass), getClassnameFilters(suiteClass), getSuiteTypes(suiteClass),
                getBaseTypes(suiteClass), getExcludedBaseTypes(suiteClass), getClasspathProperty(suiteClass));
    }

    private static Class<?>[] getSortedTestclasses(ClassesFinder finder) {
        List<Class<?>> testclasses = finder.find();
        Collections.sort(testclasses, getClassComparator());
        return testclasses.toArray(new Class[testclasses.size()]);
    }

    private static Comparator<Class<?>> getClassComparator() {
        return new Comparator<Class<?>>() {
            public int compare(Class<?> o1, Class<?> o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
    }

    private static String[] getClassnameFilters(Class<?> suiteClass) {
        ClassnameFilters filtersAnnotation = suiteClass.getAnnotation(ClassnameFilters.class);
        if (filtersAnnotation == null) {
            return DEFAULT_CLASSNAME_FILTERS;
        }
        return filtersAnnotation.value();
    }

    private static boolean getSearchInJars(Class<?> suiteClass) {
        IncludeJars includeJarsAnnotation = suiteClass.getAnnotation(IncludeJars.class);
        if (includeJarsAnnotation == null) {
            return DEFAULT_INCLUDE_JARS;
        }
        return includeJarsAnnotation.value();
    }

    private static SuiteType[] getSuiteTypes(Class<?> suiteClass) {
        SuiteTypes suiteTypesAnnotation = suiteClass.getAnnotation(SuiteTypes.class);
        if (suiteTypesAnnotation == null) {
            return DEFAULT_SUITE_TYPES;
        }
        return suiteTypesAnnotation.value();
    }

    private static Class<?>[] getBaseTypes(Class<?> suiteClass) {
        BaseTypeFilter baseTypeAnnotation = suiteClass.getAnnotation(BaseTypeFilter.class);
        if (baseTypeAnnotation == null) {
            return DEFAULT_BASE_TYPES;
        }
        return baseTypeAnnotation.value();
    }

    private static Class<?>[] getExcludedBaseTypes(Class<?> suiteClass) {
        ExcludeBaseTypeFilter excludeBaseTypeAnnotation = suiteClass.getAnnotation(ExcludeBaseTypeFilter.class);
        if (excludeBaseTypeAnnotation == null) {
            return DEFAULT_EXCLUDED_BASES_TYPES;
        }
        return excludeBaseTypeAnnotation.value();
    }

    private static String getClasspathProperty(Class<?> suiteClass) {
        ClasspathProperty cpPropertyAnnotation = suiteClass.getAnnotation(ClasspathProperty.class);
        if (cpPropertyAnnotation == null) {
            return DEFAULT_CLASSPATH_PROPERTY;
        }
        return cpPropertyAnnotation.value();
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            runBeforeMethods();
        } catch (Exception e) {
            notifier.fireTestFailure(new Failure(getDescription(), e));
            return;
        }
        super.run(notifier);
    }

    private void runBeforeMethods() throws Exception {
        for (Method each : suiteClass.getMethods()) {
            if (each.isAnnotationPresent(BeforeSuite.class)) {
                if (isPublicStaticVoid(each)) {
                    each.invoke(null, new Object[0]);
                }
            }
        }
    }

    private boolean isPublicStaticVoid(Method method) {
        return method.getReturnType() == void.class && method.getParameterTypes().length == 0
                && (method.getModifiers() & Modifier.STATIC) != 0;
    }
}
