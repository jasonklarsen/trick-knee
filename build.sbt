import AssemblyKeys._ // put this at the top of the file

assemblySettings

val name1 = "trick-knee"

val version1 = "0.1"

name := name1

version := version1

organization := "io.larsen"

scalaVersion := "2.10.3"

// Build
libraryDependencies ++= Seq("com.typesafe" % "config" % "1.0.2",
                            "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.2")
                            
// Test
libraryDependencies += "org.scalatest" % "scalatest_2.10" % "1.9.2" % "test"

jarName in assembly := name1 + "-" + version1 + ".jar"

mainClass in assembly := Some("io.larsen.trickknee.Main")
