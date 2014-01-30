package sphere

import IntegrationTest.Implicits._
import io.sphere.client.ProductSort
import io.sphere.client.shop.model._
import collection.JavaConversions._
import collection.JavaConverters._
import org.scalatest._
import java.util.Locale

class Uservoice223Spec extends WordSpec with MustMatchers {
  val client = IntegrationTestClient()

  "sphere client" must {
    "not throw null pointer exceptions if optional standard fields are not filled" in {
      val product: Product = client.products.query.where( """name(en="almost-empty")""").fetch.getResults.head
      //should not throw null pointer exceptions
      product.getName
      product.getDescription
      product.getSlug
      product.getMetaTitle(Locale.TAIWAN)
      product.getMetaDescription
      product.getMetaKeywords
      product.getRating
    }
  }
}
