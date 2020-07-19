import Dependencies._

ThisBuild / organization := "com.yevhenii"
ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.0.1-SNAPSHOT"

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:_",
  "-unchecked",
  "-Wunused:_",
  "-Xfatal-warnings",
  "-Ymacro-annotations"
)

lazy val `data-juggling-playground` =
  project
    .in(file("."))
    .settings(name := "data-juggling-playground")
    .settings(commonSettings: _*)
    .settings(dependencies: _*)

lazy val commonSettings = Seq(
  addCompilerPlugin(org.augustjune.`context-applied`),
  addCompilerPlugin(org.typelevel.`kind-projector`),
  Compile / console / scalacOptions --= Seq(
    "-Wunused:_",
    "-Xfatal-warnings"
  ),
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value
)

lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
    scalaland.chimney,
    org.typelevel.`cats-core`,
    com.github.`julien-truffaut`.`monocle-core`,
    com.github.`julien-truffaut`.`monocle-macro`
  ),
  libraryDependencies ++= Seq(
    com.github.alexarchambault.`scalacheck-shapeless_1.14`,
    com.github.`julien-truffaut`.`monocle-law`,
    org.scalacheck.scalacheck,
    org.scalatest.scalatest,
    org.scalatestplus.`scalacheck-1-14`,
    org.typelevel.`discipline-scalatest`,
    org.typelevel.kittens
  ).map(_ % Test),
  dependencyOverrides ++= Seq(
    org.scalatest.scalatest
  )
)
