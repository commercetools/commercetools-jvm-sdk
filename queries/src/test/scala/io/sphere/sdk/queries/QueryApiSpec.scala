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

  "SphereSort" must {
    "SphereSort with single element query model" in {
      new SphereSort(fooQueryModel, ASC).toSphereSort should be("foo asc")
    }

    "SphereSort with 2 elements query model hierarchy" in {
      new SphereSort(barQueryModel, ASC).toSphereSort should be("foo.bar asc")
    }

    "SphereSort with 3 elements query model hierarchy" in {
      new SphereSort(bazQueryModel, DESC).toSphereSort should be("foo.bar.baz desc")
    }
  }

  "PredicateBase" must {
    val predicate1 = new PredicateBase(){
      override def toSphereQuery: String = "masterData(current(slug(en=\"xyz-42\")"
    }

    val predicate2 = new PredicateBase(){
      override def toSphereQuery: String = "tags contains all (\"a\", \"b\", \"c\")"
    }


    "connect predicates via or" in {
      (predicate1 or predicate2).toSphereQuery should be("masterData(current(slug(en=\"xyz-42\") or tags contains all (\"a\", \"b\", \"c\")")
    }

    "connect predicates via and" in {
      (predicate1 and predicate2).toSphereQuery should be("masterData(current(slug(en=\"xyz-42\") and tags contains all (\"a\", \"b\", \"c\")")
    }
  }

  "StringQueryWithSoringModel" must {
    val stringQueryWithSoringModel = new StringQuerySortingModel[Product](Optional.empty(), "id")


    "generate simple queries" in {
      stringQueryWithSoringModel.is("foo").toSphereQuery should be("""id="foo"""")
    }

    "generate hierarchical queries" in {
      val parents = new QueryModelImpl[Product](Optional.empty(), "x1").
        appended("x2").appended("x3").appended("x4")
      new StringQuerySortingModel[Product](Optional.of(parents), "x5").is("foo").toSphereQuery should be("""x1(x2(x3(x4(x5="foo"))))""")
    }

    "generate sort expressions" in {
      stringQueryWithSoringModel.sort(ASC).toSphereSort should be("""id asc""")
    }
  }
  val emptyQueryModel = new QueryModel[String] {
    override def getPathSegment: Optional[String] = Optional.empty()

    override def getParent: Optional[_ <: QueryModel[String]] = Optional.empty()
  }

  "IsInPredicate" must {
    def createIsInPredicate(values: String*) = {
      new IsInPredicate(emptyQueryModel, values.map(v => StringQueryModel.escape(v)))
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
