package io.sphere.sdk.queries

import com.fasterxml.jackson.core.`type`.TypeReference
import com.google.common.collect.Lists

object CategoryDummy {
  trait Category
  class CategoryImpl extends Category
  class CategoryQueryModel[_]
  val typeReference = new TypeReference[PagedQueryResult[CategoryImpl]] { }
  val prototype = new QueryDslImpl[Category]("/categories",
    new TypeReference[PagedQueryResult[Category]] {})
  val predicate = new PredicateBase[Category] {
    override def toSphereQuery: String = "foo"
  }
  val predicate2 = new PredicateBase[Category] {
    override def toSphereQuery: String = "bar"
  }
  val newSortList = Lists.newArrayList(Sort.of[Category]("xyz desc"))
  val newSortList2 = Lists.newArrayList(Sort.of[Category]("uvw desc"))
}
