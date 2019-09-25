package io.sphere.sdk.products.commands;

import io.sphere.sdk.products.ByIdVariantIdentifier;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.test.IntegrationTest;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static io.sphere.sdk.products.ProductFixtures.withUpdateableProduct;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductImageUploadCommandIntegrationTest extends IntegrationTest {


    @Test
    public void uploadImage() {
        withUpdateableProduct(client(),  product -> {
            final ByIdVariantIdentifier identifier = product.getMasterData().getStaged().getMasterVariant().getIdentifier();
            File imageFile ;
            try{
                imageFile = File.createTempFile("ct_logo_farbe",".gif");
                imageFile.deleteOnExit();
                byte[] fileBytes = IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream("ct_logo_farbe.gif"));
                FileOutputStream outStream = new FileOutputStream(imageFile);
                outStream.write(fileBytes);
                outStream.close();
            }catch(IOException e){
                imageFile = new File("src/test/resources/ct_logo_farbe.gif");
            }

            final ProductImageUploadCommand cmd = ProductImageUploadCommand
                    .ofVariantId(imageFile, identifier)
                    .withFilename("logo.gif")
                    .withStaged(true);
            final Product updatedProduct = client().executeBlocking(cmd);
            final Image image = updatedProduct.getMasterData().getStaged().getMasterVariant().getImages().get(0);
            assertThat(image.getDimensions().getHeight()).isEqualTo(102);
            assertThat(image.getDimensions().getWidth()).isEqualTo(460);
            assertThat(image.getUrl()).contains("logo");
            return updatedProduct;
        });
    }

    @Test(expected = IllegalStateException.class)
    public void uploadInvalidImageType() {
        withUpdateableProduct(client(),  product -> {
            final ByIdVariantIdentifier identifier = product.getMasterData().getStaged().getMasterVariant().getIdentifier();
            final ProductImageUploadCommand cmd = ProductImageUploadCommand
                    .ofVariantId(new File("src/test/resources/ct_logo_farbe.bmp"), identifier)
                    .withFilename("logo.bmp")
                    .withStaged(true);
            final Product updatedProduct = client().executeBlocking(cmd);
            return updatedProduct;
        });
    }
    
    @Test
    public void uploadWithSettingContentType() {
        withUpdateableProduct(client(),  product -> {
            final ByIdVariantIdentifier identifier = product.getMasterData().getStaged().getMasterVariant().getIdentifier();
            File imageFile ;
            try{
                imageFile = File.createTempFile("ct_logo_farbe",".gif");
                imageFile.deleteOnExit();
                byte[] fileBytes = IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream("ct_logo_farbe.gif"));
                FileOutputStream outStream = new FileOutputStream(imageFile);
                outStream.write(fileBytes);
                outStream.close();
            }catch(IOException e){
                imageFile = new File("src/test/resources/ct_logo_farbe.gif");
            }

            final ProductImageUploadCommand cmd = ProductImageUploadCommand
                    .ofVariantId(imageFile, identifier)
                    .withFilename("logo.gif")
                    .withContentType("image/gif")
                    .withStaged(true);
            final Product updatedProduct = client().executeBlocking(cmd);
            final Image image = updatedProduct.getMasterData().getStaged().getMasterVariant().getImages().get(0);
            assertThat(image.getDimensions().getHeight()).isEqualTo(102);
            assertThat(image.getDimensions().getWidth()).isEqualTo(460);
            assertThat(image.getUrl()).contains("logo");
            return updatedProduct;
        });
    }
}