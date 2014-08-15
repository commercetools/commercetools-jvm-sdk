package example;

import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.commands.Command;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.SetTaxCategory;
import io.sphere.sdk.taxcategories.TaxCategory;
import play.libs.F;

import java.util.Arrays;

public class ProductUpdateExample {
    private PlayJavaClient client;

    public void taxCategory() throws Exception {
        Product product = getSomeHowAProduct();
        TaxCategory taxCategory = getSomeHowATaxCategory();
        Command<Product> updateCommand = new ProductUpdateCommand(product, Arrays.asList(SetTaxCategory.to(taxCategory)));
        F.Promise<Product> updatedProduct = client.execute(updateCommand);
    }

    private TaxCategory getSomeHowATaxCategory() {
        return null;
    }

    private Product getSomeHowAProduct() {
        return null;
    }
}