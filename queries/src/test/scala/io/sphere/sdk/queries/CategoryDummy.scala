package io.sphere.sdk.queries

import com.fasterxml.jackson.core.`type`.TypeReference
import com.google.common.collect.Lists

object CategoryDummy {
  trait Category
  class CategoryImpl extends Category
  class CategoryQueryModel[_]
  val typeReference = new TypeReference[PagedQueryResult[CategoryImpl]] { }
  val prototype = new QueryDslImpl[Category, CategoryImpl, CategoryQueryModel[_]]("/categories",
    new TypeReference[PagedQueryResult[CategoryImpl]] {})
  val predicate = new PredicateBase[CategoryQueryModel[_]] {
    override def toSphereQuery: String = "foo"
  }
  val predicate2 = new PredicateBase[CategoryQueryModel[_]] {
    override def toSphereQuery: String = "bar"
  }
  val newSortList: java.util.List[Sort] = Lists.newArrayList(new Sort {
    override def toSphereSort: String = "xyz desc"
  })
  val newSortList2: java.util.List[Sort] = Lists.newArrayList(new Sort {
    override def toSphereSort: String = "uvw desc"
  })
}
