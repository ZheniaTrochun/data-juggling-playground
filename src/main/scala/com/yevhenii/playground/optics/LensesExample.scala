package com.yevhenii.playground.optics

import com.yevhenii.playground.Structure
import com.yevhenii.playground.Structure._
import monocle.macros.GenLens
//import com.yevhenii.playground.optics.homegrown.Lens

object LensesExample extends App {
  val person = Structure.rawPerson

  def styled(in: String): String = Console.GREEN + in + Console.RESET

//  val cityNameLens = Lens[Person.Raw.Nested, String](
//    _.address.city.name)(
//    newCityName =>
//      nested =>
//        nested.copy(
//          address = nested.address.copy(
//            city = nested.address.city.copy(
//              name = newCityName
//            )
//          )
//        )
//  )

//  val streetNameLens = Lens[Person.Raw.Nested, String](
//    _.address.street.name)(
//    newStreetName =>
//      nested =>
//        nested.copy(
//          address = nested.address.copy(
//            street = nested.address.street.copy(
//              name = newStreetName
//            )
//          )
//        )
//  )

  val cityNameLens = GenLens[Person.Raw.Nested][String](_.address.city.name) // equal to: GenLens[Person.Raw.Nested].apply[String](_.address.city.name)
  val streetNameLens = GenLens[Person.Raw.Nested][String](_.address.street.name) // equal to: GenLens[Person.Raw.Nested].apply[String](_.address.street.name)

  val updatedCityPerson = cityNameLens.modify(styled)(person)
  val updatedStreetPerson = streetNameLens.modify(styled)(updatedCityPerson)

  println("-" * 100)
  println(person)
  println("-" * 50)
  println(updatedCityPerson)
  println("-" * 50)
  println(updatedStreetPerson)
  println("-" * 100)

}
