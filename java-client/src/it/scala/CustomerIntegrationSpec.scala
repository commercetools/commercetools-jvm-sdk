package sphere

import io.sphere.client.shop.model._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import TestData._
import org.scalatest._
import io.sphere.client.shop.SphereClient


class CustomerIntegrationSpec extends WordSpec with MustMatchers {
  implicit lazy val client: SphereClient = IntegrationTestClient()

  "sphere client" must {
     "add a customer address" in {
       val customer = newCustomer
       customer.getAddresses.asScala must be(List())
       val updatedCustomer = client.customers().update(customer.getIdAndVersion, new CustomerUpdate().addAddress(GermanAddress)).execute()
       updatedCustomer.getAddresses.asScala.toList(0).getCountry must be(GermanAddress.getCountry)
     }
    
     "change one customer address of may" in {
       val customer = client.customers().update(newCustomer.getIdAndVersion, new CustomerUpdate().addAddress(GermanAddress).addAddress(FranceAddress)).execute()
       customer.getAddresses.map(_.getCountry).toSet must be(Set(GermanAddress.getCountry, FranceAddress.getCountry))

       println("customer.getAddresses.map(_.getId)")
       println(customer.getAddresses.toList.map(_.getId).toString)

       val addressInFrance = customer.getAddresses.asScala.filter(_.getCountry == FranceAddress.getCountry).head
       val updatedCustomer = client.customers().update(customer.getIdAndVersion, new CustomerUpdate().changeAddress(addressInFrance.getId, BelgianAddress)).execute()
       updatedCustomer.getAddresses.map(_.getCountry).toSet must be(Set(GermanAddress.getCountry, BelgianAddress.getCountry))
     }
  }
}