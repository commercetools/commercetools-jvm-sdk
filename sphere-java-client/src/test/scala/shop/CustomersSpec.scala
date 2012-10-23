package de.commercetools.sphere.client
package shop

import de.commercetools.internal.{CustomerCommands, CommandBase, CommandRequestBuilderImpl, RequestBuilderImpl}
import de.commercetools.sphere.client.shop.model.Customer
import de.commercetools.sphere.client.util.CommandRequestBuilder

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

class CustomersSpec extends WordSpec with MustMatchers {

  val customerId = "764c4d25-5d04-4999-8a73-0cf8570f7601"
  val customerJson =
    """{
        "type":"Consumer",
        "id":"%s",
        "version":0,
        "email":"em@ail.com",
        "firstName":"hans",
        "lastName":"wurst",
        "password":"p75aPGdoBK62KSHuWcoWrw==$LMnb/9st6JhKFS0gBMx/zOBV3MVY+cbC2qBFR7aeutg=",
        "middleName":"the horrible",
        "title":"sir",
        "shippingAddresses":[]}""".format(customerId)

  val customerShopClient = Mocks.mockShopClient(customerJson)

  // downcast to be able to test some request properties which are not public for shop developers
  private def asImpl(reqBuilder: RequestBuilder[Customer]) = reqBuilder.asInstanceOf[RequestBuilderImpl[Customer]]
  private def asImpl(reqBuilder: CommandRequestBuilder[Customer]) = reqBuilder.asInstanceOf[CommandRequestBuilderImpl[Customer]]

  private def checkIdAndVersion(cmd: CommandBase): Unit = {
    cmd.getId() must be (customerId)
    cmd.getVersion() must be (1)
  }

  "Get all customers" in {
    val shopClient = Mocks.mockShopClient("{}")
    shopClient.customers.all().fetch.getCount must be(0)
  }

  "Get customer byId" in {
    val reqBuilder = customerShopClient.customers.byId(customerId)
    asImpl(reqBuilder).getRawUrl must be ("/consumers/" + customerId)
    val customer = reqBuilder.fetch()
    customer.getId() must be(customerId)
  }

  "Create customer" in {
    val reqBuilder = asImpl(customerShopClient.customers.signup("em@ail.com", "secret", "hans", "wurst", "don", "sir"))
    reqBuilder.getRawUrl must be("/consumers")
    val cmd = reqBuilder.getCommand.asInstanceOf[CustomerCommands.CreateCustomer]
    cmd.getEmail must be ("em@ail.com")
    cmd.getPassword must be ("secret")
    cmd.getFirstName must be ("hans")
    cmd.getLastName must be ("wurst")
    cmd.getMiddleName must be ("don")
    cmd.getTitle must be ("sir")
    val customer: Customer = reqBuilder.execute()
    customer.getId() must be(customerId)
  }

  "Login" in {
    val reqBuilder = asImpl(customerShopClient.customers.login("em@ail.com", "secret"))
    reqBuilder.getRawUrl must be("/consumers/authenticated?email=em@ail.com&password=secret")
    val customer: Customer = reqBuilder.fetch()
    customer.getId() must be(customerId)
  }
}
