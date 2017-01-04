// Link to external Scaladoc and Javadoc from Scaladoc.
// The solution is based on:
//     http://stackoverflow.com/a/35673212
//     http://stackoverflow.com/a/31322970
import sbt._
import sbt.Keys._
import scala.util.matching.Regex
import scala.util.matching.Regex.Match

object ScaladocMappings {

  /* You can print the classpath with `show compile:fullClasspath` in the SBT REPL.
   * From that list you can find the name of the jar for the managed dependency.
   */
  val externalJavadocMap = Map(
    "org.slf4j" -> "http://www.slf4j.org/apidocs/index.html"
  )

  /* The rt.jar file is located in the path stored in the sun.boot.class.path system property.
   * See the Oracle documentation at http://docs.oracle.com/javase/6/docs/technotes/tools/findingclasses.html.
   */
  val rtJar: String = System.getProperty("sun.boot.class.path").split(java.io.File.pathSeparator).collectFirst {
    case str: String if str.endsWith(java.io.File.separator + "rt.jar") => str
  }.get // fail hard if not found

  val javaApiUrl: String = "http://docs.oracle.com/javase/8/docs/api/index.html"

  val allExternalJavadocLinks: Seq[String] = javaApiUrl +: externalJavadocMap.values.toSeq

  def javadocLinkRegex(javadocURL: String): Regex = ("""\"(\Q""" + javadocURL + """\E)#([^"]*)\"""").r

  val fixJavaLinks: Match => String = m =>
    m.group(1) + "?" + m.group(2).replace(".", "/") + ".html"

  lazy val fixLinksToExternalJavadoc = Seq(
    // Override the task to fix the links to JavaDoc
    doc in Compile ~= { target: File =>
        (target ** "*.html").get.foreach { f => 
          val oldContent: String = IO.read(f)
          val newContent: String = allExternalJavadocLinks.filter({ javadocURL: String =>
            (javadocLinkRegex(javadocURL) findFirstIn oldContent).nonEmpty   
          }).foldLeft(oldContent) {
            case (content: String, javadocURL: String) =>
              //println(s"Fixing $javadocURL in $f.")
              javadocLinkRegex(javadocURL).replaceAllIn(content, fixJavaLinks)
          }
          IO.write(f, newContent)
        }
        target
    }
  )
  
  lazy val externalMappings = Seq(

    apiMappings ++= {
      /* based on http://stackoverflow.com/a/35673212 */
      def mappingsFor(organization: String, name: String, location: String, revision: (String) => String = identity): Seq[(File, URL)] =
        for {
          entry: Attributed[File] <- (fullClasspath in Compile).value
          module: ModuleID <- entry.get(moduleID.key)
          if module.organization == organization
          if module.name.startsWith(name)
        } yield entry.data -> url(location.format(revision(module.revision)))

      val mappings: Seq[(File, URL)] = 
          mappingsFor("com.beachape", "enumeratum", "https://www.javadoc.io/doc/com.beachape/enumeratum_2.12/%s/") ++
          mappingsFor("org.slf4j", "slf4j-api", "http://www.slf4j.org/apidocs/") ++
          Seq((file(rtJar) -> url(javaApiUrl)))

      //mappings foreach println

      mappings.toMap
    }

  )

}
