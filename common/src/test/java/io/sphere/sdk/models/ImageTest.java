package io.sphere.sdk.models;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class ImageTest {

    @Test
    public void testOfWidthAndHeight() throws Exception {
        final Image image = Image.ofWidthAndHeight("http://domain.tld/image.png", 400, 300, "foo");
        assertThat(image.getUrl()).isEqualTo("http://domain.tld/image.png");
        assertThat(image.getDimensions()).isEqualTo(ImageDimensions.of(400, 300));
        assertThat(image.getLabel().get()).isEqualTo("foo");
    }
}