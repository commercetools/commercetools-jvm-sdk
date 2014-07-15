package io.sphere.sdk.client

import org.scalatest.{Suite, BeforeAndAfterAll}
import io.sphere.sdk.models.LocalizedString
import java.util.{Optional, Locale}
import com.google.common.base.{Function => GFunction}
import scala.util.Try


trait SdkIntegrationTest extends BeforeAndAfterAll {
  this: Suite =>

  var client: TestClient = _

  implicit val locale = Locale.ENGLISH

  override protected def beforeAll() {
    client = new TestClient(new JavaClientImpl(IntegrationTestUtils.defaultConfig))
  }


  override protected def afterAll() {
    client.close()
  }

  implicit def function1ToGFunction[I, R](f: Function1[I, R]) = new GFunction[I, R] {
    override def apply(input: I): R = f(input)
  }
  implicit def string2localizedString(s: String) = LocalizedString.of(Locale.ENGLISH, s)
  implicit def optionalToOption[T](optional: Optional[T]): Option[T] = if(optional.isPresent) Some(optional.get) else None

  def withCleanup(cleanup: => Unit)(test: => Unit) {
    Try(cleanup)
    test
    Try(cleanup)
  }

  implicit class RichString(s: String) {
    def localized = LocalizedString.of(Locale.ENGLISH, s)
  }
}
