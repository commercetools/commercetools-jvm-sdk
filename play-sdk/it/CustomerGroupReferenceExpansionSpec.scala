package sphere

import io.sphere.client.shop.model.Customer
import org.scalatest._
import SphereIntegrationTest._
import scala.collection.JavaConversions._

class CustomerGroupReferenceExpansionSpec extends WordSpec with MustMatchers {
  lazy val client = IntegrationTestClient()
  lazy val service = client.customers()

  def getCustomer: Customer = {
    //TODO we need a possibility to filter by customer group
    service.query.expand("customerGroup").pageSize(1000).fetch.getResults.filter(_.getCustomerGroup.isExpanded).head
  }
  
  
  "sphere java client" must {
    "expand the customer group of a customer" in {
      val customer = getCustomer
      customer.getCustomerGroup.get.getName must not be (null)
    }
  }

  "sphere play client" must {
    "expand the customer group of a customer" in {
      withSphere(Option(getCustomer)) { sphere =>
        val customer = sphere.currentCustomer.fetch
        val name = customer.getCustomerGroup.get.getName
        name must not be (null)
      }
    }
  }
}
