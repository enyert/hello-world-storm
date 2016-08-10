name := "hello-world-storm"

version := "1.0"

mainClass in (Compile, run) := Some("topologies.TopologyMain")

val stormVersion = "0.9.5"


libraryDependencies ++= Seq(
  "org.apache.storm" % "storm-core" % stormVersion
    exclude("org.apache.zookeeper", "zookeeper")
    exclude("org.slf4j", "log4j-over-slf4j"),
  "org.apache.storm" % "storm-starter" % stormVersion % "test",
  "org.apache.storm" % "storm-kafka" % stormVersion
    exclude("org.apache.zookeeper", "zookeeper")
)

//libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _)

resolvers ++= Seq(
  "typesafe-repository" at "http://repo.typesafe.com/typesafe/releases/",
  "clojars-repository" at "https://clojars.org/repo"
)

scalaVersion := "2.11.8"
    