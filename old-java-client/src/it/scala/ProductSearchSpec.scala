package sphere

import IntegrationTest.Implicits._
import io.sphere.client.ProductSort
import org.scalatest._
import io.sphere.client.shop.model.{Attribute, ProductUpdate}

class ProductSearchSpec extends WordSpec with MustMatchers {
  val client = IntegrationTestClient()

  "sphere client" must {
    import scala.collection.JavaConversions._
    def fetchProducts(sort: ProductSort) = client.products().all().sort(sort).fetch()

    "sort products asc" in {
      val searchResult = fetchProducts(ProductSort.name.asc)
      searchResult.getCount must be >= (2)
      val names = searchResult.getResults.map(_.getName(locale))
      names must be (names.sorted)
    }

    "sort products desc" in {
      val searchResult = fetchProducts(ProductSort.name.desc)
      searchResult.getCount must be >= (2)
      val names = searchResult.getResults.map(_.getName(locale))
      names must be (names.sorted.reverse)
    }
    
    "set and remove attribute" in {
      val queryResult = client.products().query().fetch()
      queryResult.getCount must be >= (1)
      val Some(product) = queryResult.getResults.headOption
      val attribute = new Attribute("custom-attribute", "value1")
      
      info("set attribute")
      val setAttribute = new ProductUpdate().setAttribute(product.getMasterVariant.getId, attribute, false)
      val attributeSet = client.products().updateProduct(product.getIdAndVersion, setAttribute).execute()
      attributeSet.getAttribute(attribute.getName) must be (attribute)

      info("remove attribute")
      val unsetAttribute = new ProductUpdate().removeAttribute(product.getMasterVariant.getId, attribute.getName, false)
      val attributeUnset = client.products().updateProduct(attributeSet.getIdAndVersion, unsetAttribute).execute()
      attributeUnset.getAttribute(attribute.getName) must be (null)
    }
  }
}