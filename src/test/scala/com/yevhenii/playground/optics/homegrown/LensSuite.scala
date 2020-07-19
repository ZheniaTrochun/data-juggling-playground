package com.yevhenii.playground.optics.homegrown

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

  test("TODO") {
    // todo
  }
}
