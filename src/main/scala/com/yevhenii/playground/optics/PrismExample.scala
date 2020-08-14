package com.yevhenii.playground.optics

import monocle.Prism
import monocle.macros.{GenIso, GenPrism}

object PrismExample extends App {

  sealed abstract class TrafficLight extends Product with Serializable

  object TrafficLight {
    final case class Red(opacity: Double) extends TrafficLight {
      override def toString: String =
        Console.RED + s"Red($opacity)" + Console.RESET
    }

    final case class Yellow(opacity: Double) extends TrafficLight {
      override def toString: String =
        Console.YELLOW + s"Yellow($opacity)" + Console.RESET
    }

    final case class Green(opacity: Double) extends TrafficLight {
      override def toString: String =
        Console.GREEN + s"Green($opacity)" + Console.RESET
    }
  }

  import com.yevhenii.playground.optics.PrismExample.TrafficLight._

  val greenPrism1: Prism[TrafficLight, Green] =
    Prism[TrafficLight, Green] {
      case green @ Green(_) => Some(green)
      case _ => None
    } (identity)

  // Same as above
  val greenPrism2: Prism[TrafficLight, Green] =
    Prism.partial[TrafficLight, Green] {
      case green @ Green(_) => green
    } (identity)

  val greenPrism3: Prism[TrafficLight, Green] =
    GenPrism[TrafficLight, Green]

  Seq(greenPrism1, greenPrism2, greenPrism3) foreach { prism =>
    println(prism.getOption(Red(0.1)))
    println(prism.getOption(Yellow(0.2)))
    println(prism.getOption(Green(0.3)))
  }

  val typicalPrism1: Prism[TrafficLight, Double] =
    Prism.partial[TrafficLight, Double] {
      case Green(opacity) => opacity
    } (Green.apply)

  val typicalPrism2: Prism[TrafficLight, Double] =
    GenPrism[TrafficLight, Green]
      .composeIso(GenIso[Green, Double])

  println("=" * 100)

  Seq(typicalPrism1, typicalPrism2) foreach { prism =>
    println(prism.getOption(Red(0.1)))
    println(prism.getOption(Yellow(0.2)))
    println(prism.getOption(Green(0.3)))
  }

  println("=" * 100)
  Seq(typicalPrism1, typicalPrism2) foreach { prism =>
    println(prism.modify(_ + 1)(Red(0.1)))
    println(prism.modify(_ + 1)(Yellow(0.2)))
    println(prism.modify(_ + 1)(Green(0.3)))
  }

  val doubleIntPrism: Prism[Double, Int] =
    Prism[Double, Int](double =>
      if (double % 1 == 0) Some(double.toInt)
      else None
    )(_.toDouble)

  val greenIntPrism: Prism[TrafficLight, Int] =
    GenPrism[TrafficLight, Green]
      .composeIso(GenIso[Green, Double])
      .composePrism(doubleIntPrism)

  println("=" * 100)
  // should change only the last one
  Seq(Red(0.1), Yellow(0.2), Green(0.3), Green(1))
    .map(greenIntPrism.modify(_ + 1))
    .foreach(println)

  println("=" * 100)

  // Prism is an EXTRACTOR
  Red(0.1) match {
    case greenIntPrism(value) => println(value)
    case x => println(s"$x did NOT matched the prism")
  }
  Green(0.2) match {
    case greenIntPrism(value) => println(value)
    case x => println(s"$x did NOT matched the prism")
  }
  Green(1) match {
    case x @ greenIntPrism(value) => println(s"$x matched the prism, value=$value")
    case x => println(s"$x did NOT matched the prism")
  }

  println("=" * 100)

  // Prism is an CONSTRUCTOR
  println(greenIntPrism(2))
}
