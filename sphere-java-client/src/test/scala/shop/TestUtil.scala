package de.commercetools.sphere.client

import java.util

import de.commercetools.sphere.client.model.Money

object TestUtil {
  /** Creates a Java ArrayList with given values. */
  def lst[A](as: A*): java.util.List[A] = {
    val l = new util.ArrayList[A]()
    as.foreach(l.add(_))
    l
  }

  def eur(amount: Double) = new Money(new java.math.BigDecimal(amount), "EUR")
}
