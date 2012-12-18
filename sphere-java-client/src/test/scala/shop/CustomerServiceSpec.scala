package de.commercetools.sphere.client
package shop

import de.commercetools.internal.command._
import de.commercetools.internal.request._
import de.commercetools.internal.request.QueryRequestImpl
import model._
import de.commercetools.internal.util.Util
import de.commercetools.internal.command.CustomerCommands._

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import com.google.common.base.Optional

class CustomerServiceSpec extends WordSpec with MustMatchers {

  import JsonTestObjects._

  val customerShopClient = MockShopClient.create(customersResponse = FakeResponse(customerJson))
  val customerTokenShopClient = MockShopClient.create(customersResponse = FakeResponse(tokenJson))

  val testAddress = new Address("Alexanderplatz")

  // downcast to be able to test some request properties which are not public for shop developers
  private def asImpl(req: FetchRequest[Customer]) = req.asInstanceOf[FetchRequestImpl[Customer]]
  private def asImpl(req: QueryRequest[Customer]) = req.asInstanceOf[QueryRequestImpl[Customer]]
  private def asImpl(req: CommandRequest[Customer]) = req.asInstanceOf[CommandRequestImpl[Customer]]

  private def checkIdAndVersion(cmd: CommandBase) {
    cmd.getId must be (customerId)
    cmd.getVersion must be (1)
  }

  "Get all customers" in {
    val shopClient = MockShopClient.create(customersResponse = FakeResponse("{}"))
    shopClient.customers.all().fetch.getCount must be(0)
  }

  "Get customer byId" in {
    val req = customerShopClient.customers.byId(customerId)
    asImpl(req).getUrl must be ("/customers/" + customerId)
    val customer = req.fetch()
    customer.get.getId must be(customerId)
  }

  "Get customer byToken" in {
    val req = customerShopClient.customers.byToken("tokken")
    asImpl(req).getUrl must be ("/customers/by-token?token=tokken")
    val customer = req.fetch()
    customer.get.getId must be(customerId)
  }

  "Create customer" in {
    val req = asImpl(customerShopClient.customers.signup("em@ail.com", "secret", "hans", "wurst", "don", "sir"))
    req.getUrl must be("/customers")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreateCustomer]
    cmd.getEmail must be ("em@ail.com")
    cmd.getPassword must be ("secret")
    cmd.getFirstName must be ("hans")
    cmd.getLastName must be ("wurst")
    cmd.getMiddleName must be ("don")
    cmd.getTitle must be ("sir")
    val customer: Customer = req.execute()
    customer.getId must be(customerId)
  }

  "Create customer with anonymous cart" in {
    val customerShopClient = MockShopClient.create(customersResponse = FakeResponse(loginResultJson))
    val req = customerShopClient.customers.signupWithCart("em@ail.com", "secret", "hans", "wurst", "don", "sir", cartId, 1)
      .asInstanceOf[CommandRequestImpl[AuthenticationResult]]
    req.getUrl must be("/customers/with-cart")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreateCustomerWithCart]
    cmd.getEmail must be ("em@ail.com")
    cmd.getPassword must be ("secret")
    cmd.getFirstName must be ("hans")
    cmd.getLastName must be ("wurst")
    cmd.getMiddleName must be ("don")
    cmd.getTitle must be ("sir")
    cmd.getCartId must be (cartId)
    cmd.getCartVersion must be (1)
    val result: AuthenticationResult = req.execute()
    result.getCustomer.getId must be(customerId)
    result.getCart.getId must be(cartId)
  }

  "Login" in {
    val customerShopClient = MockShopClient.create(customersResponse = FakeResponse(loginResultJson))
    val req = customerShopClient.customers.byCredentials("em@ail.com", "secret")
      .asInstanceOf[FetchRequestWithErrorHandling[AuthenticationResult]]
    req.getUrl must be("/customers/authenticated?email=" + Util.encodeUrl("em@ail.com") + "&password=secret")
    val result: Optional[AuthenticationResult] = req.fetch()
    result.get.getCustomer.getId must be(customerId)
    result.get.getCart.getId must be(cartId)
  }

  "Change password" in {
    val req = asImpl(customerShopClient.customers.changePassword(customerId, 1, "old", "new"))
    req.getUrl must be("/customers/password")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.ChangePassword]
    checkIdAndVersion(cmd)
    cmd.getCurrentPassword must be ("old")
    cmd.getNewPassword must be ("new")
    val customer: Customer = req.execute()
    customer.getId must be(customerId)
  }

  "Change shipping address" in {
    val req = asImpl(customerShopClient.customers.changeShippingAddress(customerId, 1, 0, testAddress))
    req.getUrl must be("/customers/shipping-addresses/change")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.ChangeShippingAddress]
    checkIdAndVersion(cmd)
    cmd.getAddress.getFullAddress must be (testAddress.getFullAddress)
    cmd.getAddressIndex must be (0)
    val customer: Customer = req.execute()
    customer.getId() must be(customerId)
  }

  "Remove shipping address" in {
    val req = asImpl(customerShopClient.customers.removeShippingAddress(customerId, 1, 0))
    req.getUrl must be("/customers/shipping-addresses/remove")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.RemoveShippingAddress]
    checkIdAndVersion(cmd)
    cmd.getAddressIndex must be (0)
    val customer: Customer = req.execute()
    customer.getId must be(customerId)
  }

  "Set default shipping address" in {
    val req = asImpl(customerShopClient.customers.setDefaultShippingAddress(customerId, 1, 0))
    req.getUrl must be("/customers/shipping-addresses/default")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.SetDefaultShippingAddress]
    checkIdAndVersion(cmd)
    cmd.getAddressIndex must be (0)
    val customer: Customer = req.execute()
    customer.getId must be(customerId)
  }

  "Update" in {
    val update = new CustomerUpdate()
    update.setEmail("new@mail.com")
    update.setName(new CustomerName("updatedFirst", "updatedLast"))
    update.addShippingAddress(new Address("Alex"))
    update.addShippingAddress(new Address("Zoo"))
    val req = asImpl(customerShopClient.customers.updateCustomer(customerId, 1, update))
    req.getUrl must be("/customers/update")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.UpdateCustomer]
    checkIdAndVersion(cmd)
    val actions = scala.collection.JavaConversions.asScalaBuffer((cmd.getActions)).toList
    actions.length must be (4)
    actions.collect({ case a: ChangeName => a})
      .count(cn => cn.getFirstName == "updatedFirst" && cn.getLastName == "updatedLast") must be (1)
    actions.collect({ case a: ChangeEmail => a}).count(_.getEmail == "new@mail.com") must be (1)
    actions.collect({ case a: AddShippingAddress => a}).count(_.getAddress.getFullAddress == "Alex") must be (1)
    actions.collect({ case a: AddShippingAddress => a}).count(_.getAddress.getFullAddress == "Zoo") must be (1)
    val customer: Customer = req.execute()
    customer.getId must be(customerId)
  }

  "Create password reset token" in {
    val req = customerTokenShopClient.customers.createPasswordResetToken("em@ail.com")
      .asInstanceOf[CommandRequestImpl[CustomerToken]]
    req.getUrl must be("/customers/password-token")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreatePasswordResetToken]
    cmd.getEmail must be ("em@ail.com")
    val token: CustomerToken = req.execute()
    token.getValue must be (tokenValue)
  }

  "Reset password" in {
    val req = asImpl(customerShopClient.customers.resetPassword(customerId, 1, "tokken", "newpass"))
    req.getUrl must be("/customers/password/reset")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.ResetCustomerPassword]
    checkIdAndVersion(cmd)
    cmd.getTokenValue must be ("tokken")
    cmd.getNewPassword must be ("newpass")
    val customer: Customer = req.execute()
    customer.getId must be(customerId)
  }

  "Create email verification token" in {
    val req = customerTokenShopClient.customers.createEmailVerificationToken(customerId, 1, 10)
      .asInstanceOf[CommandRequestImpl[CustomerToken]]
    req.getUrl must be("/customers/email-token")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreateEmailVerificationToken]
    cmd.getTTLMinutes must be (10)
    val token: CustomerToken = req.execute()
    token.getValue must be (tokenValue)
  }

  "Verify email" in {
    val req = asImpl(customerShopClient.customers.verifyEmail(customerId, 1, "tokken"))
    req.getUrl must be("/customers/email/verify")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.VerifyCustomerEmail]
    checkIdAndVersion(cmd)
    cmd.getTokenValue must be ("tokken")
    val customer: Customer = req.execute()
    customer.getId must be(customerId)
  }
}
