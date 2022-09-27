ThisBuild / scalaVersion := "3.2.0"


lazy val bvm = (project in file("."))
  .settings(
    name := "bvm",
    Compile / scalaSource := baseDirectory.value / "src",
    Test / scalaSource    := baseDirectory.value / "test",
    libraryDependencies   += "org.typelevel" %% "cats-core" % "2.8.0",
    libraryDependencies   += "com.github.julien-truffaut" %% "monocle-core" % "3.0.0-M6",
    libraryDependencies   += "org.scalatest" %% "scalatest" % "3.2.13" % "test",
  )