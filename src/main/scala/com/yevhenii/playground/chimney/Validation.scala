package com.yevhenii.playground.chimney

import cats._
import cats.data.ValidatedNec
import cats.implicits._
import io.scalaland.chimney.dsl._

object Validation extends App {
  import Structure._

  def intOrError(
    field: String,
    fieldName: => String
  ): Either[String, Int] =
    Either
      .fromOption(
        field.toIntOption,
        s"${Console.RED}$fieldName must be a valid integer, which $field was not.${Console.RESET}"
      )
      .filterOrElse(
        _ > 0,
        s"${Console.RED}$fieldName must be > 0, which $field was not.${Console.RESET}"
      )

  def mapToValidatedNec[R](
    either: Either[String, Int],
    transform: Int => R
  ): ValidatedNec[String, R] =
    either
      .map(transform)
      .toValidatedNec

  def validatePerson(rawPerson: Person.Raw): ValidatedNec[String, Person] =
    Apply[ValidatedNec[String, *]]
      .map2[Person.Age, Person.HeightInCm, Person](
        mapToValidatedNec(intOrError(rawPerson.age, "age"), Person.Age),
        mapToValidatedNec(intOrError(rawPerson.heightInCm, "heightInCm"), Person.HeightInCm)
      ) { (age, height) =>
        rawPerson
          .into[Person]
          .withFieldConst(_.age, age)
          .withFieldConst(_.heightInCm, height)
          .transform
      }

  val rawCorrectPerson = Person.Raw(
    name = "Bob",
    phone = "+380 12 345 67 89",
    age = "42",
    heightInCm = "170",
    address = Address.Raw(
      city = City.Raw(
        name = "New York",
        zipCode = "10007"
      ),
      street = Street.Raw(
        name = "5th Ave",
        number = "711"
      )
    )
  )

  val rawIncorrectPerson = Person.Raw(
    name = "Bob",
    phone = "+380 12 345 67 89",
    age = "-42",
    heightInCm = "str",
    address = Address.Raw(
      city = City.Raw(
        name = "New York",
        zipCode = "10007"
      ),
      street = Street.Raw(
        name = "5th Ave",
        number = "711"
      )
    )
  )

  println("-" * 100)
  println(validatePerson(rawCorrectPerson))
  println("-" * 100)
  println(validatePerson(rawIncorrectPerson))
  println("-" * 100)
}
