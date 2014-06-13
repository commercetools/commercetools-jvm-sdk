package io.sphere.sdk.queries

import com.fasterxml.jackson.core.`type`.TypeReference
import com.google.common.collect.Lists

object CategoryDummy {
  class Category
  trait CategoryImpl
  class CategoryQueryModel[_]
  val typeReference = new TypeReference[PagedQueryResult[CategoryImpl]] { }
  val prototype = new EntityQueryWithCopyImpl[Category, CategoryImpl, CategoryQueryModel[_]]("/categories", typeReference)
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
