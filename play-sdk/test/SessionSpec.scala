package sphere

import java.util.{UUID, HashMap}

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import play.mvc.Http
import sphere.testobjects.{TestCustomer, TestCart}

class SessionSpec extends WordSpec with MustMatchers {

  val emptyHttpSession = new Http.Session(new HashMap[String,String]())
  val testCartId = UUID.randomUUID().toString
  val testCart = TestCart(testCartId, 1)
  val testCustomerId = UUID.randomUUID().toString
  val testCustomer = TestCustomer(testCustomerId, 2)

  "putCart" must {
    "add cart id, cart version and total quantity to session" in {
      val session = new Session(emptyHttpSession)
      session.putCart(testCart)
      val idVer = session.getCartId()
      idVer.getId() must be (testCartId)
      idVer.getVersion() must be (1)
      session.getCartTotalQuantity must be (0)
    }
  }

  "clearCart" must {
    "remove cart id, cart version and total quantity from session" in {
      val session = new Session(emptyHttpSession)
      session.putCart(testCart)
      session.clearCart()
      val idVer = session.getCartId()
      idVer must be (null)
      session.getCartTotalQuantity must be (null)
    }
  }

  "putCustomerIdAndVersion" must {
    "add customer id and customer version to session" in {
      val session = new Session(emptyHttpSession)
      session.putCustomer(testCustomer)
      val idVer = session.getCustomerId()
      idVer.getId() must be (testCustomerId)
      idVer.getVersion() must be (2)
    }
  }

  "clearCustomer" must {
    "remove customer id and customer version from session" in {
      val session = new Session(emptyHttpSession)
      session.putCustomer(testCustomer)
      session.clearCustomer()
      session.getCustomerId() must be (null)
    }
  }
}
