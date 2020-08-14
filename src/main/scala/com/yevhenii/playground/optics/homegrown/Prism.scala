package com.yevhenii.playground.optics.homegrown

sealed abstract class Prism[A, B] {

  def getOptional(a: A): Option[B]

  def reverseGet(b: B): A

  def modify(f: B => B): A => A =
    a => getOptional(a)
      .map(f)
      .map(reverseGet)
      .getOrElse(a)

  def unapply(arg: A): Option[B] = getOptional(arg)

  def apply(b: B): A = reverseGet(b)
}

object Prism {
  def apply[A, B](_getOptional: A => Option[B])(_reverseGet: B => A): Prism[A, B] = new Prism[A, B] {
    override def getOptional(a: A): Option[B] = _getOptional(a)
    override def reverseGet(b: B): A = _reverseGet(b)
  }

  def partial[A, B](aToB: PartialFunction[A, B])(_reverseGet: B => A): Prism[A, B] = new Prism[A, B] {
    override def getOptional(a: A): Option[B] = aToB.lift(a)
    override def reverseGet(b: B): A = _reverseGet(b)
  }
}
