package sphere

import io.sphere.client.shop.model.{Customer, CustomerToken}
import org.scalatest._
import io.sphere.internal.request.CommandRequestImpl


class PasswordResetSpec extends WordSpec with MustMatchers {
  lazy val client = IntegrationTestClient()
  lazy val service = client.customers()
  lazy val email = getCustomer.getEmail
  val minutesPerDay = 24 * 60
  def getCustomer: Customer = service.query.fetch.getResults.get(0)

  "sphere client" must {
    "have a default token expiring method" in {
      val req = service.createPasswordResetToken(email).asInstanceOf[CommandRequestImpl[CustomerToken]]
      val token: CustomerToken = req.execute()
      token.getCreatedAt.isBefore(token.getExpiresAt) must be (true)
      token.getValue.size must be >= 5
    }

    "set custom password token expire time" in {
      val req = service.createPasswordResetToken(email, minutesPerDay).asInstanceOf[CommandRequestImpl[CustomerToken]]
      val token: CustomerToken = req.execute()
      token.getCreatedAt.plusDays(1) must be (token.getExpiresAt)
      token.getValue.size must be >= 5
    }
  }
}
