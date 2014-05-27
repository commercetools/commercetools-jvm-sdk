package io.sphere.sdk.queries

import org.scalatest._
import com.google.common.base.Optional
import io.sphere.sdk.queries.SortDirection._
import com.fasterxml.jackson.core.`type`.TypeReference
import io.sphere.sdk.client.PagedQueryResult
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import com.google.common.collect.Lists

class QueryApiSpec extends WordSpec with Matchers {
  class Product

  def fooQueryModel: QueryModel[Product] = new QueryModelImpl[Product](Optional.absent[QueryModel[Product]], "foo") {}
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
    val stringQueryWithSoringModel = new StringQueryWithSortingModel[Product](Optional.absent(), "id")


    "generate simple queries" in {
      stringQueryWithSoringModel.is("foo").toSphereQuery should be("""id="foo"""")
    }

    "generate hierarchical queries" in {
      val parents = new QueryModelImpl[Product](Optional.absent(), "x1").
        appended("x2").appended("x3").appended("x4")
      new StringQueryWithSortingModel[Product](Optional.of(parents), "x5").is("foo").toSphereQuery should be("""x1(x2(x3(x4(x5="foo"))))""")
    }

    "generate sort expressions" in {
      stringQueryWithSoringModel.sort(ASC).toSphereSort should be("""id asc""")
    }
  }

  "EntityQueryWithCopyImpl" must {
    class Category
    trait CategoryImpl
    class CategoryQueryModel[_]
    val typeReference = new TypeReference[PagedQueryResult[CategoryImpl]] { }
    val prototype = new EntityQueryWithCopyImpl[Category, CategoryImpl, CategoryQueryModel[_]]("/categories", typeReference)
    val predicate = new PredicateBase[CategoryQueryModel[_]] {
      override def toSphereQuery: String = "foo"
    }
    val newSortList: java.util.List[Sort] = Lists.newArrayList(new Sort {
      override def toSphereSort: String = "xyz desc"
    })

    "have id sorter by default to prevent random order in paging" in {
      val sortList = prototype.sort().asScala
      sortList should have size(1)
      sortList.head.toSphereSort should be ("id asc")
    }

    "provide a copy method for predicates" in {
      prototype.predicate() should be(Optional.absent())
      val query: EntityQueryWithCopy[Category, CategoryImpl, CategoryQueryModel[_]] = prototype.withPredicate(predicate)
      query.predicate should be(Optional.of(predicate))
    }

    "provide a copy method for sort" in {
      prototype.sort.head.toSphereSort should be ("id asc")
      val updated = prototype.withSort(newSortList)
      updated.sort should be(newSortList)
    }

    "provide a copy method for limit" in {
      prototype.limit.isPresent should be(false)
      val updated = prototype.withLimit(4)
      updated.limit.get should be(4)
    }

    "provide a copy method for offset" in {
      prototype.offset.isPresent should be(false)
      val updated = prototype.withOffset(2)
      updated.offset.get should be(2)
    }
  }
}
