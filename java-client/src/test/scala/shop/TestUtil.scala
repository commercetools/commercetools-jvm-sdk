package io.sphere.client

import java.util

import io.sphere.client.model.{VersionedId, Money}

object TestUtil {
  /** Versioned id at version 1. */
  def v1(id: String) = VersionedId.create(id, 1)

  /** Creates a Java ArrayList with given values. */
  def lst[A](as: A*): java.util.List[A] = {
    val l = new util.ArrayList[A]()
    as.foreach(l.add(_))
    l
  }

  def eur(amount: Double) = new Money(new java.math.BigDecimal(amount), "EUR")
}
