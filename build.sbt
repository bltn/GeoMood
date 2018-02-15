name := "geomood"
 
version := "1.0" 
      
lazy val `geomood` = (project in file(".")).enablePlugins(PlayJava)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.11.11"

libraryDependencies ++= Seq( javaJdbc , cache , javaWs )
libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.6"
libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "4.0.6"
libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.8.0"
libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.8.0" classifier "models"
libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.8.0" classifier "models-english"

libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.6.2"
libraryDependencies += "org.jongo" % "jongo" % "1.3.0"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )