package sphere


import org.scalatest._
import scala.collection.JavaConversions._
import io.sphere.client.exceptions.SphereBackendException
import io.sphere.client.shop.model.Product

class QueryApiSpec extends WordSpec with MustMatchers {
  val client = IntegrationTestClient()

  "sphere client" must {
    def queryByName(name: String) = client.products.query.where("name(en=\"" + name + "\")")

    "enable queries for products" in {
      val allProducts = client.products.all.fetch.getResults
      assertTwoDifferentProductsPresent(allProducts)
      val name = allProducts(0).getName

      val expectedResult = allProducts.filter(_.getName == name).map(_.getId).toSet
      val actualResult = queryByName(name).fetch.getResults.map(_.getId).toSet
      actualResult must be(expectedResult)
      actualResult.contains(allProducts(0).getId) must be(true)
      actualResult.size must be < (allProducts.size())
    }

    "not crash by search with null values" in {
      queryByName(null).fetch.getResults.size must be (0)
    }

    "give a proper error message if query string is invalid" in {
      intercept[SphereBackendException](
        client.products.query.where("name())))").fetch
      ).getMessage must include ("Malformed parameter: where:")
    }

    "enable queries with sort for products" in {
      val allProducts = client.products.all.fetch.getResults
      assertTwoDifferentProductsPresent(allProducts)

      val expectedResultAsc = allProducts.sortBy(_.getName).map(_.getId)
      val actualResultAsc = client.products.query.sort("name.en asc").fetch.getResults.map(_.getId)
      val actualResultDesc = client.products.query.sort("name.en desc").fetch.getResults.map(_.getId)
      actualResultAsc must be(expectedResultAsc)
      actualResultDesc must be(expectedResultAsc.reverse)
    }
  }

  def assertTwoDifferentProductsPresent(allProducts: java.util.List[Product]) {
    withClue("need at least 2 categories with different names") {
      allProducts.size must be >= (2)
      allProducts(0).getName must not be allProducts(1).getName
    }
  }
}
