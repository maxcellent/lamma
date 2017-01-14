organization := "io.lamma"

name := "lamma"

version := "2.3.1"

scalaVersion := "2.12.1"

crossScalaVersions := Seq("2.10.6", "2.11.7", "2.12.1")

javaOptions := Seq("-Xmx1g")

javacOptions := Seq("-Xlint:unchecked", "-Xlint:deprecation", "-source", "1.5", "-target", "1.5")

scalacOptions := Seq("-feature", "-deprecation", "-language:postfixOps", "-language:implicitConversions")

libraryDependencies += "junit" % "junit" % "4.12" % "test"

libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"  // we need to have this lib in order to run junit with sbt test

libraryDependencies += "com.google.guava" % "guava" % "20.0" % "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

// ========== demo + sample source directories =========
unmanagedSourceDirectories in Test += baseDirectory.value / "src/sample/java"

unmanagedSourceDirectories in Test += baseDirectory.value / "src/sample/scala"

// ============ publish / release related ================

publishMavenStyle := true

publishArtifact in Test := false

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomIncludeRepository := { _ => false }

licenses := Seq("DO WHAT YOU WANT TO PUBLIC LICENSE, Version 1" -> url("https://github.com/maxcellent/lamma/blob/master/LICENSE"))

homepage := Some(url("http://lamma.io"))

pomExtra := (
  <scm>
    <url>http://github.com/maxcellent/lamma.git</url>
    <connection>scm:git://github.com/maxcellent/lamma.git</connection>
  </scm>
    <developers>
      <developer>
        <id>maxcellent</id>
        <name>Max Zhu</name>
        <url>http://lamma.io</url>
      </developer>
    </developers>
  )