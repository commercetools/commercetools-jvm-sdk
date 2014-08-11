package io.sphere.sdk.queries

import org.scalatest._
import java.util.Optional
import io.sphere.sdk.queries.SortDirection._
import com.fasterxml.jackson.core.`type`.TypeReference
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import com.google.common.collect.Lists
import CategoryDummy._

class QueryApiSpec extends WordSpec with Matchers {
  class Product

  def fooQueryModel: QueryModel[Product] = new QueryModelImpl[Product](Optional.empty[QueryModel[Product]], "foo") {}
  def barQueryModel: QueryModel[Product] = new QueryModelImpl[Product](Optional.of(fooQueryModel), "bar") {}
  def bazQueryModel: QueryModel[Product] = new QueryModelImpl[Product](Optional.of(barQueryModel), "baz") {}

  val emptyQueryModel = new QueryModel[String] {
    override def getPathSegment: Optional[String] = Optional.empty()

    override def getParent: Optional[_ <: QueryModel[String]] = Optional.empty()
  }

  "IsInPredicate" must {
    def createIsInPredicate(values: String*) = {
      new IsInPredicate(emptyQueryModel, values.map(v => StringQuerySortingModel.escape(v)))
    }
    val predicate = createIsInPredicate("foo", "bar\"evil", "baz")
    "render the correct sphere query expression" in {
      predicate.render should be(""" in ("foo", "bar\"evil", "baz")""")
    }

    "implement toString" in {
      predicate.toString should include("foo")
    }

    "require at least one value" in {
      intercept[IllegalArgumentException](createIsInPredicate())
    }
  }

  "NotEqPredicate" must {
    "render the correct sphere query expression" in {
      new NotEqPredicate[String, String](emptyQueryModel, "xyz").render should be(""" <> "xyz"""")
    }
  }
}
