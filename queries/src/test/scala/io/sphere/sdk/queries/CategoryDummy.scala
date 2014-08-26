package io.sphere.sdk.queries

import java.util.Arrays.asList
import com.fasterxml.jackson.core.`type`.TypeReference

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
  val newSortList = asList(Sort.of[Category]("xyz desc"))
  val newSortList2 = asList(Sort.of[Category]("uvw desc"))
}
