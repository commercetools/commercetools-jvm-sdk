package sphere


import org.scalatest._
import scala.collection.JavaConversions._
import io.sphere.client.exceptions.SphereBackendException

class QueryApiSpec extends WordSpec with MustMatchers {
  val client = IntegrationTestClient()

  "sphere client" must {
    def getProductsByName(name: String) = client.products.query.where("name(en=\"" + name + "\")").fetch.getResults

    "enable queries for products" in {
      val allProducts = client.products.all.fetch.getResults

      withClue("need at least 2 categories with different names") {
        allProducts.size must be >= (2)
        allProducts(0).getName must not be allProducts(1).getName
      }
      val name = allProducts(0).getName

      val expectedResult = allProducts.filter(_.getName == name).map(_.getId).toSet
      val actualResult = getProductsByName(name).map(_.getId).toSet
      actualResult must be(expectedResult)
      actualResult.contains(allProducts(0).getId) must be(true)
      actualResult.size must be < (allProducts.size())
    }

    "not crash by search with null values" in {
      getProductsByName(null).size must be (0)
    }

    "give a proper error message if query string is invalid" in {
      intercept[SphereBackendException](
        client.products.query.where("name())))").fetch
      ).getMessage must include ("Malformed parameter: where:")
    }
  }
}
