package com.yevhenii.playground.chimney

import io.scalaland.chimney.dsl._

object Colours extends App {
  sealed abstract class Colour extends Product with Serializable

  object Colour {
    case object Red extends Colour
    case object Green extends Colour
    case object Blue extends Colour
  }

  sealed abstract class Channel extends Product with Serializable

  object Channel {
    case object Alpha extends Channel
    case object Red extends Channel
    case object Green extends Channel
    case object Blue extends Channel
  }

  val colour: Colour =
    Colour.Red

  val channel: Channel =
    colour.transformInto[Channel]

  println(colour)
  println(channel)
  println("-" * 100)

  val colourFromChannel =
    channel
      .into[Colour]
      .withCoproductInstance((_: Channel.Alpha.type) => Colour.Blue)
      .transform

  println(colourFromChannel)
}