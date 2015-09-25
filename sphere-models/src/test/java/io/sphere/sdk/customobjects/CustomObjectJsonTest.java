package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.PagedQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomObjectJsonTest {
    @Test
    public void jackson() {
        final String resourcePath = "customobjects/pagedqueryresult.json";
        final PagedQueryResult<CustomObject<Foo>> old = SphereJsonUtils.readObjectFromResource(resourcePath, new TypeReference<PagedQueryResult<CustomObject<Foo>>>() { });

        final TypeReference<Foo> fooTypeReference = Foo.typeReference();
        final JavaType fooJavaType = TypeFactory.defaultInstance().constructType(fooTypeReference);
        JavaType inner = TypeFactory.defaultInstance().constructParametrizedType(CustomObject.class, CustomObject.class, fooJavaType);
        JavaType outer = TypeFactory.defaultInstance().constructParametrizedType(PagedQueryResult.class, PagedQueryResult.class, inner);

        final JavaType t = outer;
        final PagedQueryResult<CustomObject<Foo>> newO = SphereJsonUtils.readObjectFromResource(resourcePath, t);
        assertThat(old).isEqualTo(newO);
    }
}
