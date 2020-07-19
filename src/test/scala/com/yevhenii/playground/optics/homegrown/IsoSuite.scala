package com.yevhenii.playground.optics.homegrown

import cats.derived.auto.eq._
import cats.implicits._
import org.scalatestplus.scalacheck.Checkers
import org.scalacheck.ScalacheckShapeless._
import com.yevhenii.playground.Structure.Person.Raw.{Flat, Nested}
import com.yevhenii.playground.TestSuite

final class IsoSuite extends TestSuite with Checkers {
  val iso: Iso[Flat, Nested] = Iso[Flat, Nested](_.toNested)(_.toFlat)

  test("round trip one way") {
    forAll( (s: Flat) => (iso.reverseGet _).compose(iso.get).apply(s) shouldBe s)
  }

  test("round trip other way") {
    forAll( (s: Nested) => (iso.get _).compose(iso.reverseGet).apply(s) shouldBe s)
  }

  test("apply is equal to reverseGet") {
    forAll( (s: Nested) => iso(s) shouldBe iso.reverseGet(s) )
  }

  test("modify") {
    forAll( (s: Flat, f: Nested => Nested) => iso.modify(f)(s) shouldBe iso.reverse.get(f(iso.get(s))) )
  }

  test("compose modify") {
    forAll( (s: Flat, f: Nested => Nested, g: Nested => Nested) =>
      iso.modify(g compose f)(s) shouldBe iso.reverseGet((g compose f).apply(iso.get(s)))
    )
  }

  test("compose modify 2") {
    forAll( (s: Flat, f: Nested => Nested, g: Nested => Nested) =>
      iso.modify(g compose f)(s) shouldBe iso.modify(g)(iso.modify(f)(s))
    )
  }
}
