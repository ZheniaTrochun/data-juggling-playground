package com.yevhenii.playground.chimney

import java.time.LocalDate
import java.util.UUID

import io.scalaland.chimney.dsl._

object Persons extends App {

  final case class Name(value: String) extends AnyVal
  final case class Person(name: String, age: Int)
  final case class Customer(id: UUID, name: String, age: Int)

  val bob = Person("Bob", 44)

  val customerBob =
    bob.into[Customer]
      .withFieldComputed(_.id, _ => UUID.randomUUID())
      .transform

  println(customerBob)
  println("-" * 100)

  final case class CustomerWithBirthYear(id: UUID, name: String, birthYear: Int)

  val currYear = LocalDate.now().getYear

  val customerBobWithBirthYear =
    bob.into[CustomerWithBirthYear]
      .withFieldComputed(_.id, _ => UUID.randomUUID())
      .withFieldComputed(_.birthYear, person => currYear - person.age)
      .transform

  println(customerBobWithBirthYear)
  println("-" * 100)

  final case class CustomerWithoutId(name: String, age: Int)

  val customerBobWithoutId =
    bob.transformInto[CustomerWithoutId]

  println(customerBobWithoutId)
  println("-" * 100)

  final case class CustomerWithRenamedField(username: String, age: Int)

  val customerBobWithRenamedField =
    bob.into[CustomerWithRenamedField]
      .withFieldRenamed(_.name, _.username)
      .transform

  println(customerBobWithRenamedField)
  println("-" * 100)

  final case class CustomerWithName(name: Name, age: Int, email: String = "a@b.c")

  val customerBobWithName =
    bob.transformInto[CustomerWithName]

  println(customerBobWithName)
  println("-" * 100)

  final case class PersonWithEmail(name: String, age: Int, email: String)
  final case class CustomerWithEmail(name: Name, age: Int, email: String)

  val alice = PersonWithEmail("Alice", 25, "alice@g.c")

  val customerAliceWithEmail =
    alice.transformInto[CustomerWithEmail]

  println(customerAliceWithEmail)
  println("-" * 100)

  final case class Patch(name: String, email: String)

  val brucePatch = Patch("Bruce", "bruce@g.c")
  val bruce = alice.patchUsing(brucePatch)

  println(alice)
  println(bruce)
  println("-" * 100)
}
