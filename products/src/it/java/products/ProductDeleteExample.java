package products;

import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductDeleteByIdCommand;
import play.libs.F;

public class ProductDeleteExample {
    private PlayJavaClient client;
    private Product product;

    public void delete() {
        final ProductDeleteByIdCommand command = new ProductDeleteByIdCommand(product);
        final F.Promise<Product> deletedProduct = client.execute(command);
    }
}
