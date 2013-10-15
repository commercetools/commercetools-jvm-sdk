import IntegrationTest.Implicits._
import io.sphere.client.ProductSort
import org.scalatest._

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
  }
}