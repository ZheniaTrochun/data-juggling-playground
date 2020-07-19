package com.yevhenii
package playground

import cats.derived.auto.eq._
import cats.implicits._

import monocle.Iso
import monocle.law.discipline.IsoTests

import org.scalacheck.Prop
import org.scalacheck.ScalacheckShapeless._

import org.scalatestplus.scalacheck._

final class IsoSuite extends TestSuite with Checkers {
  test("iso") {
    import com.yevhenii.playground.Structure.Person.Raw._

    val iso: Iso[Nested, Flat] = Iso[Nested, Flat](_.toFlat)(_.toNested)

    val tests: IsoTests.RuleSet = IsoTests(iso)

    val properties: Seq[Prop] = tests.all.properties.map(_._2).to(Seq)

    properties.foreach(check(_))
  }
}
