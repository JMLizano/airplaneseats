import com.typesafe.sbt.packager.docker._


name := "AirplaneSeats"
version := "1.0"
scalaVersion := "2.12.2"

libraryDependencies +=  "org.scalatest" %% "scalatest" % "3.0.1" % "test"

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)

mainClass in Compile := Some("airplaneSeats.SeatsArrangement")
dockerBaseImage := "openjdk:jre-alpine"
mappings in Universal += file("test.txt") -> "test.txt"
mappings in Universal += file("test_large_plane.txt") -> "test_large_plane.txt"
mappings in Universal += file("test_oversubscribed.txt") -> "test_oversubscribed.txt"
mappings in Universal += file("test_split_group.txt") -> "test_split_group.txt"
