package io.sphere.sdk.test;


import io.sphere.sdk.expansion.ExpansionModelImplTest;
import io.sphere.sdk.models.GeoJSONTest;
import io.sphere.sdk.models.ReferenceTest;
import io.sphere.sdk.products.search.ExistsAndMissingFilterIntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.exam.spi.reactors.PerSuite;

import java.lang.reflect.Method;

@RunWith(PaxExam.class)
@ExamReactorStrategy({PerSuite.class})
public class DummyIntegrationTest extends ReferenceTest{

    @Test
    public void simpleTest() throws Exception {

        ReferenceTest instance = ReferenceTest.class.newInstance();

        for (Method method : ReferenceTest.class.getMethods()) {
            if(method.getAnnotation(Test.class)!=null)
            method.invoke(instance, new Object[]{});
        }

    }
}
