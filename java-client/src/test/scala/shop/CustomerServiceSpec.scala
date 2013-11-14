package io.sphere.client
package shop

import model._
import io.sphere.internal.command._
import io.sphere.internal.request._
import io.sphere.internal.request.QueryRequestImpl
import io.sphere.internal.command.CustomerCommands._
import io.sphere.internal.command.CartCommands.CartUpdateAction
import io.sphere.client.TestUtil._
import io.sphere.client.exceptions.{InvalidCredentialsException, EmailAlreadyInUseException, InvalidPasswordException}

import com.neovisionaries.i18n.CountryCode._
import org.scalatest._

class CustomerServiceSpec extends WordSpec with MustMatchers {

  import JsonResponses._

  val sphere = MockSphereClient.create(customersResponse = FakeResponse(customerJson))
  val sphereTokenClient = MockSphereClient.create(customersResponse = FakeResponse(tokenJson))

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
    val shopClient = MockSphereClient.create(customersResponse = FakeResponse("{}"))
    shopClient.customers.query.fetch.getCount must be(0)
  }

  "Get customer byId" in {
    val req = sphere.customers.byId(customerId)
    asImpl(req).getRequestHolder.getUrl must be ("/customers/" + customerId)
    val customer = req.fetch()
    customer.get.getId must be(customerId)
  }

  "Get customer byToken" in {
    val req = sphere.customers.byToken("token1")
    asImpl(req).getRequestHolder.getUrl must be ("/customers/?token=token1")
    val customer = req.fetch()
    customer.get.getId must be(customerId)
  }

  "Create customer" in {
    val customerShopClient = MockSphereClient.create(customersResponse = FakeResponse(loginResultJson))
    val req = customerShopClient.customers.signUp("em@ail.com", "secret", new CustomerName("sir", "hans", "don", "wurst"), cartId)
      .asInstanceOf[CommandRequestImpl[SignInResult]]
    req.getRequestHolder.getUrl must be("/customers")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreateCustomer]
    cmd.getEmail must be ("em@ail.com")
    cmd.getPassword must be ("secret")
    cmd.getTitle must be ("sir")
    cmd.getFirstName must be ("hans")
    cmd.getMiddleName must be ("don")
    cmd.getLastName must be ("wurst")
    val result: SignInResult = req.execute()
    result.getCustomer.getId must be(customerId)
  }

  "Create customer with anonymous cart" in {
    val customerShopClient = MockSphereClient.create(customersResponse = FakeResponse(loginResultJson))
    val req = customerShopClient.customers.signUp("em@ail.com", "secret", new CustomerName("sir", "hans", "don", "wurst"), cartId)
      .asInstanceOf[CommandRequestImpl[SignInResult]]
    req.getRequestHolder.getUrl must be("/customers")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreateCustomerWithCart]
    cmd.getEmail must be ("em@ail.com")
    cmd.getPassword must be ("secret")
    cmd.getTitle must be ("sir")
    cmd.getFirstName must be ("hans")
    cmd.getMiddleName must be ("don")
    cmd.getLastName must be ("wurst")
    cmd.getAnonymousCartId must be (cartId)
    val result: SignInResult = req.execute()
    result.getCustomer.getId must be(customerId)
    result.getCart.getId must be(cartId)
  }

  "Login" in {
    val client = MockSphereClient.create(customersResponse = FakeResponse(loginResultJson))
    val req = client.customers().signIn("em@ail.com", "secret")
      .asInstanceOf[CommandRequestImpl[SignInResult]]
    req.getRequestHolder.getUrl must be("/login")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.SignIn]
    cmd.getEmail must be ("em@ail.com")
    cmd.getPassword must be ("secret")

    val result: SignInResult = req.execute()
    result.getCart.getId must be(cartId)
    result.getCustomer.getId must be(customerId)
  }
  
  "Login with anonymous cart" in {
    val client = MockSphereClient.create(customersResponse = FakeResponse(loginResultJson))
    val req = client.customers().signIn("em@ail.com", "secret", cartId)
      .asInstanceOf[CommandRequestImpl[SignInResult]]
    req.getRequestHolder.getUrl must be("/login")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.SignInWithCart]
    cmd.getEmail must be ("em@ail.com")
    cmd.getPassword must be ("secret")
    cmd.getAnonymousCartId must be (cartId)

    val result: SignInResult = req.execute()
    result.getCart.getId must be(cartId)
    result.getCustomer.getId must be(customerId)
  }

  "Change password" in {
    val req = sphere.customers.changePassword(v1(customerId), "old", "new").asInstanceOf[CommandRequestImpl[Customer]]
    req.getRequestHolder.getUrl must be("/customers/password")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.ChangePassword]
    checkIdAndVersion(cmd)
    cmd.getCurrentPassword must be ("old")
    cmd.getNewPassword must be ("new")
    val customer: Customer = req.execute()
    customer.getId must be(customerId)
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
    val req = asImpl(sphere.customers.update(v1(customerId), update))
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
    val req = sphereTokenClient.customers.createPasswordResetToken("em@ail.com")
      .asInstanceOf[CommandRequestImpl[CustomerToken]]
    req.getRequestHolder.getUrl must be("/customers/password-token")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreatePasswordResetToken]
    cmd.getEmail must be ("em@ail.com")
    val token: CustomerToken = req.execute()
    token.getValue must be (tokenValue)
  }

  "Reset password" in {
    val req = asImpl(sphere.customers.resetPassword(v1(customerId), "token1", "newpass"))
    req.getRequestHolder.getUrl must be("/customers/password/reset")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.ResetCustomerPassword]
    checkIdAndVersion(cmd)
    cmd.getTokenValue must be ("token1")
    cmd.getNewPassword must be ("newpass")
    val customer: Customer = req.execute()
    customer.getId must be(customerId)
  }

  "Create email verification token" in {
    val req = sphereTokenClient.customers.createEmailVerificationToken(v1(customerId), 10)
      .asInstanceOf[CommandRequestImpl[CustomerToken]]
    req.getRequestHolder.getUrl must be("/customers/email-token")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.CreateEmailVerificationToken]
    cmd.getTTLMinutes must be (10)
    val token: CustomerToken = req.execute()
    token.getValue must be (tokenValue)
  }

  "Verify email" in {
    val req = asImpl(sphere.customers.confirmEmail(v1(customerId), "token1"))
    req.getRequestHolder.getUrl must be("/customers/email/confirm")
    val cmd = req.getCommand.asInstanceOf[CustomerCommands.VerifyCustomerEmail]
    checkIdAndVersion(cmd)
    cmd.getTokenValue must be ("token1")
    val customer: Customer = req.execute()
    customer.getId must be(customerId)
  }
  
  // -----------------------
  // Error handling
  // -----------------------
  
  import JsonErrors._

  "change password" must {
    "handle InvalidPassword" in {
      val sphere = MockSphereClient.create(customersResponse = FakeResponse(invalidPassword, 400))
      intercept[InvalidPasswordException] {
        sphere.customers().changePassword(v1("customer"), "current", "fresh").execute()
      }
    }
  }

  "sign-up" must {
    "handle EmailAlreadyInUseException" in {
      val sphere = MockSphereClient.create(customersResponse = FakeResponse(emailAlreadyTaken, 400))
      intercept[EmailAlreadyInUseException] {
        sphere.customers().signUp("fresh@example.com", "So fresh!", CustomerName.parse("Fresh")).execute()
      }
    }
    "handle EmailAlreadyInUseException when signing up with cart" in {
      val sphere = MockSphereClient.create(customersResponse = FakeResponse(emailAlreadyTaken, 400))
      intercept[EmailAlreadyInUseException] {
        sphere.customers().signUp("fresh@example.com", "So fresh!", CustomerName.parse("Fresh"), "someCart").execute()
      }
    }
  }

  "sign-in" must {
    "handle InvalidPasswordException" in {
      val sphere = MockSphereClient.create(customersResponse = FakeResponse(invalidCredentials, 400))
      intercept[InvalidCredentialsException] {
        sphere.customers().signIn("fresh@example.com", "So fresh!").execute()
      }
    }
    "handle InvalidPasswordException when signing in with cart" in {
      val sphere = MockSphereClient.create(customersResponse = FakeResponse(invalidCredentials, 400))
      intercept[InvalidCredentialsException] {
        sphere.customers().signIn("fresh@example.com", "So fresh!", "someCart").execute()
      }
    }
  }
}
