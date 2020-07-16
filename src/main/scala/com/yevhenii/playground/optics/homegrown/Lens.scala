package com.yevhenii.playground.optics.homegrown

sealed abstract class Lens[S, A] {
  def get: S => A

  def set: A => S => S

  final def modify(f: A => A): S => S =
    source => set(f(get(source)))(source)
}

object Lens {
  def apply[S, A](
    _get: S => A)(
    _set: A => S => S
  ): Lens[S, A] =
    new Lens[S, A] {
      override def get: S => A = _get
      override def set: A => S => S = _set
    }
}