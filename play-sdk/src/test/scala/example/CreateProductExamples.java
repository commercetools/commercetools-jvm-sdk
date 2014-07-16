package example;

import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.products.NewProduct;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductCreateCommand;
import play.libs.F;

public class CreateProductExamples {
    PlayJavaClient client;
    NewProduct newProduct;

    public void createWithClient() {
        ProductCreateCommand command = new ProductCreateCommand(newProduct);
        F.Promise<Product> result = client.execute(command);
    }
}
