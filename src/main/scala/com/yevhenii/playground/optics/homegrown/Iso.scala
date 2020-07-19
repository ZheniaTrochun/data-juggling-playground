package com.yevhenii.playground
package optics.homegrown

sealed abstract class Iso[A, B] {
  def get(in: A): B

  def reverse: Iso[B, A]

  final def reverseGet(in: B): A = reverse.get(in)

  final def apply(in: B): A = reverseGet(in)

  final def modify(f: B => B): A => A =
    s => reverseGet(f(get(s)))
}

object Iso {
  def apply[A, B](aToB: A => B)(bToA: B => A): Iso[A, B] = new Iso[A, B] {
    override def get(in: A): B = aToB(in)

    override def reverse: Iso[B, A] = Iso(bToA)(aToB)
  }
}
