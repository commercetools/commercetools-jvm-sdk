package io.sphere.sdk.products;

import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ImageTest {

    @Test
    public void testOfWidthAndHeight() throws Exception {
        final Image image = Image.ofWidthAndHeight("http://domain.tld/image.png", 400, 300, "foo");
        assertThat(image.getUrl()).isEqualTo("http://domain.tld/image.png");
        assertThat(image.getDimensions()).isEqualTo(ImageDimensions.ofWidthAndHeight(400, 300));
        assertThat(image.getLabel()).isEqualTo("foo");
    }

    @Test
    public void deserialization() throws Exception {
        final String jsonString = "{\"url\":\"https://s3.eu-central-1.amazonaws.com/commercetools-angry-bird-demo/Red+Skywalker+Plush+Toy.jpg\",\"dimensions\":{\"w\":1,\"h\":4}}";
        final Image image = SphereJsonUtils.readObject(jsonString, Image.typeReference());
        assertThat(image.getUrl()).isEqualTo("https://s3.eu-central-1.amazonaws.com/commercetools-angry-bird-demo/Red+Skywalker+Plush+Toy.jpg");
        assertThat(image.getHeight()).isEqualTo(4);
        assertThat(image.getWidth()).isEqualTo(1);
        assertThat(image.getLabel()).isNull();
    }
}