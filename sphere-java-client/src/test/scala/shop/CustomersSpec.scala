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

class CustomersSpec extends WordSpec with MustMatchers {

  val customerId = "764c4d25-5d04-4999-8a73-0cf8570f7601"
  val customerJson =
    """{
          "type":"Customer",
          "id":"%s",
          "version":0,
          "email":"em@ail.com",
          "firstName":"hans",
          "lastName":"wurst",
          "password":"p75aPGdoBK62KSHuWcoWrw==$LMnb/9st6JhKFS0gBMx/zOBV3MVY+cbC2qBFR7aeutg=",
          "middleName":"the horrible",
          "title":"sir",
          "shippingAddresses":[]
       }""".format(customerId)

  val tokenValue = "uJ58PwYmpuw0MU4bEEViJRhd6cvVHrhqs8vQKZVj"
  val tokenJson =
    """{
          "id":"10b0a46c-27ed-4d19-a2b0-1497b86fac39",
          "customerId":"c8a2e4f6-f22d-4826-b3bb-48561089fc93",
          "createdAt":"2012-10-29T15:13:23.669Z",
          "expiresAt":"2012-10-29T15:18:23.669Z",
          "value":"%s"
       }""".format(tokenValue)

  val customerShopClient = Mocks.mockShopClient(customerJson)
  val customerTokenShopClient = Mocks.mockShopClient(tokenJson)

  val testAddress = new Address("Alexanderplatz")

  // downcast to be able to test some request properties which are not public for shop developers
  private def asImpl(req: QueryRequest[Customer]) = req.asInstanceOf[QueryRequestImpl[Customer]]
  private def asImpl(req: CommandRequest[Customer]) = req.asInstanceOf[CommandRequestImpl[Customer]]

  private def checkIdAndVersion(cmd: CommandBase): Unit = {
    cmd.getId() must be (customerId)
    cmd.getVersion() must be (1)
  }

  "Get all customers" in {
    val shopClient = Mocks.mockShopClient("{}")
    shopClient.customers.all().fetch.getCount must be(0)
  }

  "Get customer byId" in {
    val req = customerShopClient.customers.byId(customerId)
    asImpl(req).getRawUrl must be ("/customers/" + customerId)
    val customer = req.fetch()
    customer.getId() must be(customerId)
  }

  "Get customer byToken" in {
    val req = customerShopClient.customers.byToken("tokken")
    asImpl(req).getRawUrl must be ("/customers/by-token?token=tokken")
    val customer = req.fetch()
    customer.getId() must be(customerId)
  }

  "Create customer" in {
    val req = asImpl(customerShopClient.customers.signup("em@ail.com", "secret", "hans", "wurst", "don", "sir"))
    req.getRawUrl must be("/customers")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreateCustomer]
    cmd.getEmail must be ("em@ail.com")
    cmd.getPassword must be ("secret")
    cmd.getFirstName must be ("hans")
    cmd.getLastName must be ("wurst")
    cmd.getMiddleName must be ("don")
    cmd.getTitle must be ("sir")
    val customer: Customer = req.execute()
    customer.getId() must be(customerId)
  }

  "Login" in {
    val req = asImpl(customerShopClient.customers.login("em@ail.com", "secret"))
    req.getRawUrl must be("/customers/authenticated?email=" + Util.encodeUrl("em@ail.com") + "&password=secret")
    val customer: Customer = req.fetch()
    customer.getId() must be(customerId)
  }

  "Change password" in {
    val req = asImpl(customerShopClient.customers.changePassword(customerId, 1, "old", "new"))
    req.getRawUrl must be("/customers/password")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.ChangePassword]
    checkIdAndVersion(cmd)
    cmd.getCurrentPassword must be ("old")
    cmd.getNewPassword must be ("new")
    val customer: Customer = req.execute()
    customer.getId() must be(customerId)
  }

  "Change shipping address" in {
    val req = asImpl(customerShopClient.customers.changeShippingAddress(customerId, 1, 0, testAddress))
    req.getRawUrl must be("/customers/shipping-addresses/change")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.ChangeShippingAddress]
    checkIdAndVersion(cmd)
    cmd.getAddress.getFullAddress must be (testAddress.getFullAddress)
    cmd.getAddressIndex must be (0)
    val customer: Customer = req.execute()
    customer.getId() must be(customerId)
  }

  "Remove shipping address" in {
    val req = asImpl(customerShopClient.customers.removeShippingAddress(customerId, 1, 0))
    req.getRawUrl must be("/customers/shipping-addresses/remove")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.RemoveShippingAddress]
    checkIdAndVersion(cmd)
    cmd.getAddressIndex must be (0)
    val customer: Customer = req.execute()
    customer.getId() must be(customerId)
  }

  "Set default shipping address" in {
    val req = asImpl(customerShopClient.customers.setDefaultShippingAddress(customerId, 1, 0))
    req.getRawUrl must be("/customers/shipping-addresses/default")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.SetDefaultShippingAddress]
    checkIdAndVersion(cmd)
    cmd.getAddressIndex must be (0)
    val customer: Customer = req.execute()
    customer.getId() must be(customerId)
  }

  "Update" in {
    val update = new CustomerUpdate();
    update.setEmail("new@mail.com")
    update.setName(new Name("updatedFirst", "updatedLast"))
    update.addShippingAddress(new Address("Alex"))
    update.addShippingAddress(new Address("Zoo"))
    val req = asImpl(customerShopClient.customers.updateCustomer(customerId, 1, update))
    req.getRawUrl must be("/customers/update")
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
    customer.getId() must be(customerId)
  }

  "Create password reset token" in {
    val req = customerTokenShopClient.customers.createPasswordResetToken("em@ail.com")
      .asInstanceOf[CommandRequestImpl[CustomerToken]]
    req.getRawUrl must be("/customers/password-token")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreatePasswordResetToken]
    cmd.getEmail must be ("em@ail.com")
    val token: CustomerToken = req.execute()
    token.getValue must be (tokenValue)
  }

  "Reset password" in {
    val req = asImpl(customerShopClient.customers.resetPassword(customerId, 1, "tokken", "newpass"))
    req.getRawUrl must be("/customers/password/reset")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.ResetCustomerPassword]
    checkIdAndVersion(cmd)
    cmd.getTokenValue must be ("tokken")
    cmd.getNewPassword must be ("newpass")
    val customer: Customer = req.execute()
    customer.getId() must be(customerId)
  }

  "Create email verification token" in {
    val req = customerTokenShopClient.customers.createEmailVerificationToken(customerId, 1, 10)
      .asInstanceOf[CommandRequestImpl[CustomerToken]]
    req.getRawUrl must be("/customers/email-token")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreateEmailVerificationToken]
    cmd.getTTLMinutes must be (10)
    val token: CustomerToken = req.execute()
    token.getValue must be (tokenValue)
  }

  "Verify email" in {
    val req = asImpl(customerShopClient.customers.verifyEmail(customerId, 1, "tokken"))
    req.getRawUrl must be("/customers/email/verify")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.VerifyCustomerEmail]
    checkIdAndVersion(cmd)
    cmd.getTokenValue must be ("tokken")
    val customer: Customer = req.execute()
    customer.getId() must be(customerId)
  }
}
