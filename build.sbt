import scala.language.postfixOps
import scala.sys.process._

resolvers += Resolver.sonatypeRepo("snapshots")

val CatsVersion = "1.5.0"
val CirceVersion = "0.11.0"
val DoobieVersion = "0.6.0"
val EnumeratumVersion = "1.5.13"
val EnumeratumCirceVersion = "1.5.18"
val H2Version = "1.4.197"
val Http4sVersion = "0.20.0-M4"
val LogbackVersion = "1.2.3"
val ScalaCheckVersion = "1.14.0"
val ScalaTestVersion = "3.2.0-M1"
val FlywayVersion = "5.2.4"
val PureConfigVersion = "0.10.1"
val Specs2Version = "4.8.1"
val akkaVersion = "2.6.0-M8"
val testcontainersScalaVersion = "0.36.0"

// Filter out compiler flags to make the repl experience functional...
val badConsoleFlags = Seq(/**"-Xfatal-warnings", */"-Ywarn-unused:imports")
scalacOptions in(Compile, console) ~= (_.filterNot(badConsoleFlags.contains(_)))

enablePlugins(ScalafmtPlugin, JavaAppPackaging, GhpagesPlugin, MicrositesPlugin, TutPlugin, GraalVMNativeImagePlugin)

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    // format: off
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8", // Specify character encoding used by source files.
    "-explaintypes", // Explain type errors in more detail.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros", // Allow macro definition (besides implementation and application)
    "-language:higherKinds", // Allow higher-kinded types
    "-language:implicitConversions", // Allow definition of implicit functions called views
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
    //    "-Xfatal-warnings", // Fail the compilation if there are any warnings.
    "-Xfuture", // Turn on future language features.
    "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
    //    "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
    "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
    "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
    "-Xlint:option-implicit", // Option.apply used implicit view.
    "-Xlint:package-object-classes", // Class or object defined in package object.
    "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
    //    "-Xlint:unsound-match", // Pattern match may not be typesafe.
    //    "-Yno-adapted-args", // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    //    "-Ypartial-unification", // Enable partial unification in type constructor inference
    "-Ywarn-dead-code", // Warn when dead code is identified.
    "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
    //    "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
    //    "-Ywarn-infer-any", // Warn when a type argument is inferred to be `Any`.
    //    "-Ywarn-nullary-unit", // Warn when nullary methods return Unit.
    //    "-Ywarn-nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-numeric-widen", // Warn when numerics are widened.
    "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
    "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals", // Warn if a local definition is unused.
    "-Ywarn-unused:params",           // Warn if a value parameter is unused.
    "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates", // Warn if a private member is unused.
    "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
    // format: on
  ),
  libraryDependencies ++= Seq(
    "com.beachape" %% "enumeratum" % EnumeratumVersion,

    "org.specs2" %% "specs2-core" % Specs2Version % Test,
    "org.scalacheck" %% "scalacheck" % ScalaCheckVersion % Test,
    "org.scalatest" %% "scalatest" % ScalaTestVersion % Test,
    "org.scalamock" %% "scalamock" % "4.4.0" % Test
  ),
  test in assembly := {}
)

lazy val scalaExercises = (project in file("scala-exercises"))
  .settings(commonSettings: _*)
  .settings(
    organization := "com.example",
    name := "scala-exercises",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8"
  )

lazy val ninetyNine = (project in file("ninety-nine"))
  .settings(commonSettings: _*)
  .settings(
    organization := "com.example",
    name := "ninety-nine",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8"
  )

lazy val fpInScala = (project in file("fp-in-scala"))
  .settings(commonSettings: _*)
  .settings(
    organization := "com.example",
    name := "fp-in-scala",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8"
  )

lazy val trial = (project in file("trial"))
  .settings(commonSettings: _*)
  .settings(
    organization := "com.example",
    name := "trial",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8",
    libraryDependencies ++= Seq(
      "org.json4s" %% "json4s-xml" % "3.6.7",
      "com.softwaremill.sttp" %% "core" % "1.7.1",
      "com.typesafe.akka" %% "akka-http" % "10.1.10",
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "org.scalaj" %% "scalaj-http" % "2.4.2",
      "com.lihaoyi" %% "os-lib" % "0.3.0",
      "com.github.pathikrit" %% "better-files" % "3.8.0",
      "org.wvlet.airframe" %% "airframe-log" % "19.10.1",
      "com.h2database" % "h2" % "1.4.200" % Test,
      "org.scalikejdbc" %% "scalikejdbc" % "3.4.0",
      "org.scalikejdbc" %% "scalikejdbc-test" % "3.4.0" % Test
    )
  )

lazy val serverCask = (project in file("server-cask"))
  .settings(commonSettings: _*)
  .settings(
    organization := "com.example",
    name := "server-cask",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "cask" % "0.5.6",
      "com.lihaoyi" %% "scalatags" % "0.7.0"
    )
  )

lazy val realworldExampleApp = (project in file("realworld-example-app"))
  .settings(commonSettings: _*)
  .settings(
    organization := "com.example",
    name := "realworld-example-app",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "cask" % "0.5.6",
      "com.lihaoyi" %% "scalatags" % "0.7.0",
      "org.wvlet.airframe" %% "airframe-log" % "19.10.1",
      "io.scalaland" %% "chimney" % "0.4.1",
      "de.huxhorn.sulky" % "de.huxhorn.sulky.ulid" % "8.2.0",
      "org.typelevel" %% "cats-core" % "2.0.0",
      "com.github.slugify" % "slugify" % "2.4",
      "com.typesafe.akka" %% "akka-actor" % "2.6.3",
      "org.scalikejdbc" %% "scalikejdbc" % "3.4.0",
      "org.scalikejdbc" %% "scalikejdbc-async" % "0.13.+",
      "org.scalikejdbc" %% "scalikejdbc-config" % "3.4.0",
      "com.github.jasync-sql" % "jasync-mysql" % "1.0.+",
      "mysql" % "mysql-connector-java" % "8.0.18",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.h2database" % "h2" % "1.4.200" % Test,
      "org.scalikejdbc" %% "scalikejdbc-test" % "3.4.0" % Test,
      "com.dimafeng" %% "testcontainers-scala-scalatest" % testcontainersScalaVersion % Test,
      "com.dimafeng" %% "testcontainers-scala-mysql" % testcontainersScalaVersion % Test
    )
  )

lazy val nativeCompile = taskKey[Unit]("Build native image")
lazy val commandLineTool = (project in file("command-line-tool"))
  .settings(commonSettings: _*)
  .settings(
    organization := "com.example",
    name := "command-line-tool",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      "com.github.scopt" %% "scopt" % "4.0.0-RC2",
      "org.json4s" %% "json4s-xml" % "3.6.7",
      "com.softwaremill.sttp" %% "core" % "1.7.1",
      "com.lihaoyi" %% "os-lib" % "0.3.0",
      "org.wvlet.airframe" %% "airframe-log" % "19.10.1",
      "org.typelevel" %% "cats-effect" % "2.0.0"
    ),
    mainClass in assembly := Some("com.example.Main"),
    assemblyJarName in assembly := {
      val projectName = name.value
      s"$projectName-app.jar"
    },
    nativeCompile := {
      clean.value
      assembly.value
      val path = baseDirectory.value
      val jarName = (assembly / assemblyJarName).value
      s"native-image --no-server -jar $path/target/scala-2.13/$jarName" !
    }
  )