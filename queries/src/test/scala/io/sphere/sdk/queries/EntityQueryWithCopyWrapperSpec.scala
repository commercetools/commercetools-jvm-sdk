package io.sphere.sdk.queries

import org.scalatest._
import com.google.common.collect.Lists
import CategoryDummy._
import com.google.common.base.Optional

class EntityQueryWithCopyWrapperSpec extends FunSuite with Matchers {

  val query: QueryDsl[Category, CategoryImpl, CategoryQueryModel[_]] =
    prototype.withLimit(1).withOffset(4).withSort(newSortList).withPredicate(predicate)
  val wrapped: QueryDsl[Category, CategoryImpl, CategoryQueryModel[_]] = new QueryDslWrapper[Category, CategoryImpl, CategoryQueryModel[_]] {
    override protected def delegate(): QueryDsl[Category, CategoryImpl, CategoryQueryModel[_]] = query
  }

  test("methods should be delegated"){

    def compare(member: QueryDsl[Category, CategoryImpl, CategoryQueryModel[_]] => Unit) {
      member(query) should be(member(wrapped))
    }
    compare(_.endpoint())
    compare(_.limit())
    compare(_.offset())
    compare(_.sort())
    compare(_.predicate())
    compare(_.typeReference())
    compare(_.httpRequest())
  }


  test("provide a copy method for predicates"){
    val query: QueryDsl[Category, CategoryImpl, CategoryQueryModel[_]] = wrapped.withPredicate(predicate2)
    query.predicate should be(Optional.of(predicate2))
  }

  test("provide a copy method for sort"){
    val updated = wrapped.withSort(newSortList2)
    updated.sort should be(newSortList2)
  }

  test("provide a copy method for limit"){
    val updated = wrapped.withLimit(99)
    updated.limit.get should be(99)
  }

  test( "provide a copy method for offset"){
    val updated = wrapped.withOffset(77)
    updated.offset.get should be(77)
  }
}
