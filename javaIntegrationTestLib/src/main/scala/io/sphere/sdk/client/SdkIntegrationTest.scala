package io.sphere.sdk.client

import org.scalatest.{Suite, BeforeAndAfterAll}
import io.sphere.sdk.models.LocalizedString
import java.util.Locale
import com.google.common.base.{Function => GFunction, Supplier, Optional}
import scala.util.Try


trait SdkIntegrationTest extends BeforeAndAfterAll {
  this: Suite =>

  var client: SphereJavaClient = _

  override protected def beforeAll() {
    client = new SphereJavaClientImpl(IntegrationTestUtils.defaultConfig)
  }


  override protected def afterAll() {
    client.close()
  }

  implicit def function1ToGFunction[I, R](f: Function1[I, R]) = new GFunction[I, R] {
    override def apply(input: I): R = f(input)
  }
  implicit def string2localizedString(s: String) = LocalizedString.of(Locale.ENGLISH, s)
  implicit def optionalToOption[T](optional: Optional[T]) = optional.transform(new GFunction[T, Option[T]] {
    override def apply(input: T): Option[T] = Some(input)
  }).or(None)

  def withCleanup(cleanup: => Unit)(test: => Unit) {
    Try(cleanup)
    test
    Try(cleanup)
  }

  implicit class RichString(s: String) {
    def localized = LocalizedString.of(Locale.ENGLISH, s)
  }
}
