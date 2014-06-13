package io.sphere.sdk.queries

import org.scalatest._
import io.sphere.sdk.queries.CategoryDummy._
import com.google.common.base.Optional
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import io.sphere.sdk.utils.UrlUtils

class EntityQueryWithCopyImplSpec extends FunSuite with Matchers {

  implicit def StringToPredicate[T](s: String) = new PredicateBase[T](){
    override def toSphereQuery: String = s
  }

  implicit class RichString(s: String) {
    def urlEncoded: String = UrlUtils.urlEncode(s)
    def toSort = new Sort {
      override def toSphereSort: String = s
    }
  }

  val namePredicate = """name(en="myCategory")"""

  test("path without any parameters"){
    prototype.httpRequest.getPath should be ("/categories?sort=id+asc")
  }

  test("path with query"){
    prototype.withPredicate(namePredicate).httpRequest.getPath should be ("/categories?where=name%28en%3D%22myCategory%22%29&sort=id+asc")
  }

  test("path with explicit empty sort"){
    prototype.withSort(Nil).httpRequest.getPath should be ("/categories")
  }

  test("path with sort"){
    prototype.withSort(List("name.en desc").map(_.toSort)).httpRequest.getPath should be ("/categories?sort=name.en+desc")
  }

  test("path with two sorts"){
    prototype.withSort(List("name.en desc", "id asc").map(_.toSort)).httpRequest.getPath should be ("/categories?sort=name.en+desc&sort=id+asc")
  }

  test("path with predicate, sort, limit, offset"){
    val query = prototype.withSort(List("name.en desc").map(_.toSort)).withPredicate(namePredicate).withOffset(400).withLimit(25)
    query.httpRequest.getPath should be ("/categories?where=name%28en%3D%22myCategory%22%29&sort=name.en+desc&limit=25&offset=400")
  }

  test("have id sorter by default to prevent random order in paging"){
    val sortList = prototype.sort().asScala
    sortList should have size(1)
    sortList.head.toSphereSort should be ("id asc")
  }

    test("provide a copy method for predicates"){
    prototype.predicate() should be(Optional.absent())
    val query: QueryDsl[Category, CategoryImpl, CategoryQueryModel[_]] = prototype.withPredicate(predicate)
    query.predicate should be(Optional.of(predicate))
  }

    test("provide a copy method for sort"){
    prototype.sort.head.toSphereSort should be ("id asc")
    val updated = prototype.withSort(newSortList)
    updated.sort should be(newSortList)
  }

    test("provide a copy method for limit"){
    prototype.limit.isPresent should be(false)
    val updated = prototype.withLimit(4)
    updated.limit.get should be(4)
  }

    test("provide a copy method for offset"){
    prototype.offset.isPresent should be(false)
    val updated = prototype.withOffset(2)
    updated.offset.get should be(2)
  }
}
