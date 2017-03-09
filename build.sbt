name := "free2move"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.8.8" % "test",
                            "com.twitter" %% "finagle-http" % "6.42.0",
                            "com.typesafe" % "config" % "1.3.1",
                            // dependency to akka necessary for task 3
                            "com.typesafe.akka" % "akka-actor_2.11" % "2.4.17")
