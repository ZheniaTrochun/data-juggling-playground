import sbt._

object Dependencies {
  case object com {
    case object github {
      case object alexarchambault {
        val `scalacheck-shapeless_1.14` =
          "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.5"
      }

      case object `julien-truffaut` {
        val MonocleVersion = "2.0.0"

        val `monocle-core` =
          "com.github.julien-truffaut" %% "monocle-core" % MonocleVersion

        val `monocle-macro` =
          "com.github.julien-truffaut" %% "monocle-macro" % MonocleVersion

        val `monocle-law` =
          "com.github.julien-truffaut" %% "monocle-law" % MonocleVersion
      }
    }

    case object olegpy {
      val `better-monadic-for` =
        "com.olegpy" %% "better-monadic-for" % "0.3.1"
    }
  }

  case object org {
    case object augustjune {
      val `context-applied` =
        "org.augustjune" %% "context-applied" % "0.1.4"
    }

    case object scalacheck {
      val scalacheck =
        "org.scalacheck" %% "scalacheck" % "1.14.3"
    }

    case object scalatest {
      val scalatest =
        "org.scalatest" %% "scalatest" % "3.2.0"
    }

    case object scalatestplus {
      val `scalacheck-1-14` =
        "org.scalatestplus" %% "scalacheck-1-14" % "3.2.0.0"
    }

    case object typelevel {
      val `discipline-scalatest` =
        "org.typelevel" %% "discipline-scalatest" % "1.0.1"

      val `kind-projector` =
        "org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full

      val `cats-core` =
        "org.typelevel" %% "cats-core" % "2.1.1"

      val kittens =
        "org.typelevel" %% "kittens" % "2.1.0"
    }
  }
  
  case object scalaland {
    val chimney = 
      "io.scalaland" %% "chimney" % "0.5.2"
  }
}
