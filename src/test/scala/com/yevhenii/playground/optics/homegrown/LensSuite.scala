package com.yevhenii.playground.optics.homegrown

import cats.derived.auto.eq._
import cats.implicits._
import org.scalatestplus.scalacheck.Checkers
import org.scalacheck.ScalacheckShapeless._
import com.yevhenii.playground.Structure.Person.Raw.Nested
import com.yevhenii.playground.TestSuite

class LensSuite extends TestSuite {

  val lens: Lens[Nested, String] = Lens[Nested, String](
    _.address.street.name)(
    newStreetName =>
      nested =>
        nested.copy(
          address = nested.address.copy(
            street = nested.address.street.copy(
              name = newStreetName
            )
          )
        )
  )

  test("setGet: get the same value that was set") {
    forAll( (a: String, nested: Nested) => lens.get(lens.set(a)(nested)) shouldBe a )
  }

  test("getSet: set the same value that was gat should change nothing") {
    forAll( (nested: Nested) => lens.set(lens.get(nested))(nested) shouldBe nested )
  }
}
