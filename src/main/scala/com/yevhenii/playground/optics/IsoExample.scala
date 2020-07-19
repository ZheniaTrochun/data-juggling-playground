package com.yevhenii.playground.optics

import com.yevhenii.playground.Structure
import com.yevhenii.playground.Structure.Person.Raw.{Flat, Nested}
import com.yevhenii.playground.Structure._
import com.yevhenii.playground.chimney.ValidationExample
import monocle._
import monocle.macros._
import monocle.macros.syntax.lens._

object IsoExample extends App {

  final case class Dog(name: String)
  final case class Owner(name: String, pet: Dog)

  type A = (Int, String, Dog)
  type B = (Int, (String, Dog))
  type C = (Int, Owner)

  val isoAB: Iso[A, B] = Iso[A, B] {
    case (int, string, dog) => (int, (string, dog))
  } {
    case (int, (string, dog)) => (int, string, dog)
  }

  val isoBC: Iso[B, C] = Iso[B, C] {
    case (int, (string, dog)) => (int, Owner(string, dog))
  } {
    case (int, Owner(string, dog)) => (int, (string, dog))
  }

  val isoAC: Iso[A, C] = isoAB.composeIso(isoBC)

  val isoCA: Iso[C, A] = isoAC.reverse

  println("-" * 100)
  println(isoAC.get((27, "Bob", Dog("Snoopy"))))

  val isoIsALens: Lens[A, C] = isoAC.asLens

  val fields: Iso[Owner, (String, Dog)] = GenIso.fields[Owner]

  val owner1 = Owner("Bob", Dog("Snoopy"))
  val tuple: (String, Dog) = fields.get(owner1)

  val owner2 = fields.reverse.get(tuple)
  val owner3 = fields.reverseGet(tuple)
  val owner4 = fields(tuple)

  println("-" * 100)
  println(owner1)
  println(tuple)
  println(owner2)
  println(owner3)
  println(owner4)
  println("-" * 100)

  final case class Name(value: String) extends AnyVal

  val isoName: Iso[Name, String] = GenIso[Name, String]
  val isoNameReversed: Iso[String, Name] = GenIso[Name, String].reverse // GenIso[String, Name] - this does not work

  println(isoName.get(Name("Bob")))
  println(isoName.reverse.get("Bob"))

  /**
   * isoName.reverse.get("Bob") equal to isoName("Bob")
   */

  println(isoName("Bob")) // factory for names
  println("-" * 100)

  // ------------------------------------------------------------------------

  val nested: Person.Raw.Nested = Structure.rawPerson

  ValidationExample
    .validatePerson(nested)
    .map( person =>
      // totally equal:
//      GenLens[Person](_.age)
//        .composeIso(GenIso[Person.Age, Int])
//        .modify(age => age + 10)(person)

      person
        .lens(_.age)
        .composeIso(GenIso[Person.Age, Int])
        .modify(age => age + 10)
    )
    .foreach(println)

  println("-" * 100)

  val isoNestedFlat: Iso[Nested, Flat] = Iso[Nested, Flat](_.toFlat)(_.toNested)
}
