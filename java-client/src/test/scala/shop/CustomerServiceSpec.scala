package io.sphere.client
package shop

import model._
import io.sphere.internal.command._
import io.sphere.internal.request._
import io.sphere.internal.request.QueryRequestImpl
import io.sphere.internal.util.Util
import io.sphere.internal.command.CustomerCommands._
import io.sphere.internal.command.CartCommands.CartUpdateAction

import com.google.common.base.Optional
import com.neovisionaries.i18n.CountryCode._
import org.scalatest.matchers.MustMatchers
import org.scalatest.WordSpec

class CustomerServiceSpec extends WordSpec with MustMatchers {

  import JsonTestObjects._

  val customerShopClient = MockShopClient.create(customersResponse = FakeResponse(customerJson))
  val customerTokenShopClient = MockShopClient.create(customersResponse = FakeResponse(tokenJson))

  val testAddress = new Address(DE)

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
    asImpl(req).getRequestHolder.getUrl must be ("/customers/" + customerId)
    val customer = req.fetch()
    customer.get.getId must be(customerId)
  }

  "Get customer byToken" in {
    val req = customerShopClient.customers.byToken("tokken")
    asImpl(req).getRequestHolder.getUrl must be ("/customers/by-token?token=tokken")
    val customer = req.fetch()
    customer.get.getId must be(customerId)
  }

  "Create customer" in {
    val req = asImpl(customerShopClient.customers.signup("em@ail.com", "secret", new CustomerName("sir", "hans", "don", "wurst")))
    req.getRequestHolder.getUrl must be("/customers")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreateCustomer]
    cmd.getEmail must be ("em@ail.com")
    cmd.getPassword must be ("secret")
    cmd.getTitle must be ("sir")
    cmd.getFirstName must be ("hans")
    cmd.getMiddleName must be ("don")
    cmd.getLastName must be ("wurst")
    val customer: Customer = req.execute()
    customer.getId must be(customerId)
  }

  "Create customer with anonymous cart" in {
    val customerShopClient = MockShopClient.create(customersResponse = FakeResponse(loginResultJson))
    val req = customerShopClient.customers.signupWithCart("em@ail.com", "secret", new CustomerName("sir", "hans", "don", "wurst"), cartId, 1)
      .asInstanceOf[CommandRequestImpl[AuthenticatedCustomerResult]]
    req.getRequestHolder.getUrl must be("/customers/with-cart")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreateCustomerWithCart]
    cmd.getEmail must be ("em@ail.com")
    cmd.getPassword must be ("secret")
    cmd.getTitle must be ("sir")
    cmd.getFirstName must be ("hans")
    cmd.getMiddleName must be ("don")
    cmd.getLastName must be ("wurst")
    cmd.getCartId must be (cartId)
    cmd.getCartVersion must be (1)
    val result: AuthenticatedCustomerResult = req.execute()
    result.getCustomer.getId must be(customerId)
    result.getCart.getId must be(cartId)
  }

  "Login" in {
    val customerShopClient = MockShopClient.create(customersResponse = FakeResponse(loginResultJson))
    val req = customerShopClient.customers.byCredentials("em@ail.com", "secret")
      .asInstanceOf[FetchRequestWithErrorHandling[AuthenticatedCustomerResult]]
    req.getRequestHolder.getUrl must be("/customers/authenticated?email=" + Util.urlEncode("em@ail.com") + "&password=secret")
    val result: Optional[AuthenticatedCustomerResult] = req.fetch()
    result.get.getCustomer.getId must be(customerId)
    result.get.getCart.getId must be(cartId)
  }

  "Change password" in {
    val req = customerShopClient.customers.changePassword(customerId, 1, "old", "new").asInstanceOf[CommandRequestWithErrorHandling[Customer]]
    req.getRequestHolder.getUrl must be("/customers/password")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.ChangePassword]
    checkIdAndVersion(cmd)
    cmd.getCurrentPassword must be ("old")
    cmd.getNewPassword must be ("new")
    val customer: Optional[Customer] = req.execute()
    customer.isPresent must be (true)
    customer.get.getId must be(customerId)
  }

  "Update" in {
    val update = new CustomerUpdate()
    update.setEmail("new@mail.com")
    update.setName(new CustomerName("updatedFirst", "updatedLast"))
    update.addAddress(new Address(FR))
    update.addAddress(new Address(CA))
    update.changeAddress("changeIndex", new Address(DE))
    update.removeAddress("removeIndex")
    update.setDefaultShippingAddress("defaultShippingIndex")
    update.setDefaultBillingAddress("defaultBillingIndex")
    update.clearDefaultShippingAddress()
    update.clearDefaultBillingAddress()
    val req = asImpl(customerShopClient.customers.update(customerId, 1, update))
    req.getRequestHolder.getUrl must be("/customers/" + customerId)
    val cmd = req.getCommand.asInstanceOf[UpdateCommand[CartUpdateAction]]
    cmd.getVersion must be (1)
    val actions = scala.collection.JavaConversions.asScalaBuffer((cmd.getActions)).toList
    actions.length must be (10)
    actions(0).asInstanceOf[ChangeEmail].getEmail must be ("new@mail.com")
    actions(1).asInstanceOf[ChangeName].getFirstName must be ("updatedFirst")
    actions(1).asInstanceOf[ChangeName].getLastName must be ("updatedLast")
    actions(2).asInstanceOf[AddAddress].getAddress.getCountry must be (FR)
    actions(3).asInstanceOf[AddAddress].getAddress.getCountry must be (CA)
    actions(4).asInstanceOf[ChangeAddress].getAddress.getCountry must be (DE)
    actions(4).asInstanceOf[ChangeAddress].getAddressId must be ("changeIndex")
    actions(5).asInstanceOf[RemoveAddress].getAddressId must be ("removeIndex")
    actions(6).asInstanceOf[SetDefaultShippingAddress].getAddressId must be ("defaultShippingIndex")
    actions(7).asInstanceOf[SetDefaultBillingAddress].getAddressId must be ("defaultBillingIndex")
    actions(8).asInstanceOf[SetDefaultShippingAddress].getAddressId must be (null)
    actions(9).asInstanceOf[SetDefaultBillingAddress].getAddressId must be (null)
    val customer: Customer = req.execute()
    customer.getId must be(customerId)
  }

  "Create password reset token" in {
    val req = customerTokenShopClient.customers.createPasswordResetToken("em@ail.com")
      .asInstanceOf[CommandRequestImpl[CustomerToken]]
    req.getRequestHolder.getUrl must be("/customers/password-token")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreatePasswordResetToken]
    cmd.getEmail must be ("em@ail.com")
    val token: CustomerToken = req.execute()
    token.getValue must be (tokenValue)
  }

  "Reset password" in {
    val req = asImpl(customerShopClient.customers.resetPassword(customerId, 1, "tokken", "newpass"))
    req.getRequestHolder.getUrl must be("/customers/password/reset")
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
    req.getRequestHolder.getUrl must be("/customers/email-token")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreateEmailVerificationToken]
    cmd.getTTLMinutes must be (10)
    val token: CustomerToken = req.execute()
    token.getValue must be (tokenValue)
  }

  "Verify email" in {
    val req = asImpl(customerShopClient.customers.confirmEmail(customerId, 1, "tokken"))
    req.getRequestHolder.getUrl must be("/customers/email/confirm")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.VerifyCustomerEmail]
    checkIdAndVersion(cmd)
    cmd.getTokenValue must be ("tokken")
    val customer: Customer = req.execute()
    customer.getId must be(customerId)
  }
}
