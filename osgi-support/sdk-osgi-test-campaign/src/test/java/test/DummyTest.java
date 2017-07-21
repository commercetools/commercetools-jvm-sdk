package test;


import io.sphere.sdk.client.QueueSphereClientDecoratorTest;
import io.sphere.sdk.client.SphereClientFactory;
import io.sphere.sdk.client.SphereHttpClientFactory;
import io.sphere.sdk.http.ApacheHttpClientAdapter;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.search.model.SortExpressionTest;
import jdk.nashorn.internal.ir.ObjectNode;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;

public class DummyTest {

//    @Test
//    public void readObjectFromJsonNode() throws Exception {
//        final ObjectNode jsonNode = SphereJsonUtils.newObjectNode();
//        jsonNode.put("de", "Hundefutter");
//        jsonNode.put("en", "dog food");
//        final LocalizedString actual =
//                SphereJsonUtils.readObject(jsonNode, LocalizedString.typeReference());
//        assertThat(actual).isEqualTo(LocalizedString.of(ENGLISH, "dog food", GERMAN, "Hundefutter"));
//        LocalizedString.getJsonCreatorName();
//    }

//
   @Test
    public void ofCentsStringAndCurrencyCode() throws Exception{
       QueueSphereClientDecoratorTest.class.newInstance().requestsOverMaxParallelAreNotProcessed();

    }

//    @Test
//    public void testInternal() throws Exception {
//        Reflections reflections = new Reflections(".*io.sphere.sdk.client.*", new MethodAnnotationsScanner());
//        Set<Method> methods = reflections.getMethodsAnnotatedWith(Test.class);
//
//        for (Method method : methods) {
//
//            Class clazz = method.getDeclaringClass();
//            try {
//                System.out.println(">>> Running  Test : " + method.getName() + " at from Class " + clazz.getCanonicalName());
//                method.invoke(clazz.newInstance());
//            } catch (InvocationTargetException e) {
////                if(method.getAnnotation(Test.class)==null)
////                    assertThat(method.getAnnotation(Test.class)).isNotNull();
////                if ((((Test)method.getAnnotations()[0]).expected()).isAssignableFrom(e.getTargetException().getClass()))
////                   continue;
////               System.err.println("Exception at " + method.getName() + " class : " + method.getDeclaringClass());
////                e.printStackTrace();
////                method.getAnnotations()[0].annotationType().getCanonicalName();
////                method.getAnnotation()
////                Arrays.stream(method.getAnnotations())
////                        .map(Annotation::annotationType)
////                        .map(Class::getCanonicalName)
////                        .filter(str -> str.equals(Test.class.getCanonicalName()))
////                        .findFirst()
//                    e.printStackTrace();
//
//            }
//        }
//
//    }

}
