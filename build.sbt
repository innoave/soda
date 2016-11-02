/*
 * Copyright (c) 2016, Innoave.com
 * All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL INNOAVE.COM OR ITS CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import scala.xml._
import sbtrelease._

//
// Environment variables used by the build:
// JAR_BUILT_BY      - Name to be added to Jar metadata field "Built-By" (defaults to System.getProperty("user.name"))
//

name := "build-basal"
description := "Build definitions for Innoave Basal project"

val buildVersion = "0.1.0-SNAPSHOT"

//
// Project Modules
//

// l10n module
lazy val basalL10n = Project(
  id = "basal-l10n",
  base = file("l10n"),
  settings = commonSettings ++ ghpages.settings ++ publishSettings ++ Seq(
    description := "Innoave Basal Localization lib",
    libraryDependencies ++= Seq(
      scalatest % "test"
    )
  )
).enablePlugins(
  SiteScaladocPlugin
)

// logging module
lazy val basalLogging = Project(
  id = "basal-logging",
  base = file("logging"),
  settings = commonSettings ++ ghpages.settings ++ publishSettings ++ Seq(
    description := "Innoave Basal Logging API",
    libraryDependencies ++= Seq(
      scalatest % "test"
    )
  )
).enablePlugins(
  SiteScaladocPlugin
)

//
// Dependencies
//
lazy val scalatest = "org.scalatest" %% "scalatest" % "3.0.0"

// Root project is never published
publishArtifact := false
sourcesInBase := false

//
// Plugins
//
enablePlugins(
  GitBranchPrompt,
  GitVersioning
//  JekyllPlugin
)

//
// Common Settings
//
lazy val commonSettings = projectSettings ++ buildSettings

//
// Project Settings
//
lazy val projectSettings = Seq(
  organization := "com.innoave.basal",
  startYear := Some(2016),
  git.remoteRepo := "git@github.com:innoave/basal.git"
)

//
// Build Settings
//
lazy val buildSettings = Seq(
  crossScalaVersions := Seq("2.11.8", "2.12.0"),
  scalaVersion := crossScalaVersions.value.head,
  scalacOptions ++= Seq(
    "-target:jvm-1.8",
    "-encoding", "utf8",
    "-unchecked",
    "-deprecation",
//    "-optimise",
    "-feature",
    "-language:_",
    "-Xfatal-warnings",
    "-Xlint:_",
    "-Yno-adapted-args",
  "-Yinline-warnings",
    "-Ywarn-dead-code",        // N.B. doesn't work well with the ??? hole
    "-Ywarn-numeric-widen",
//    "-Ywarn-value-discard",
//    "-Ywarn-unused",
    "-Ywarn-unused-import"     // 2.11 only      
  ),
  javacOptions ++= Seq(
    "-target", "1.8",
    "-source", "1.8",
    "-Xlint:deprecation"
  ),
  javaVersionPrefix in javaVersionCheck := Some("1.8"),
  sourcesInBase := false,
//  unmanagedSourceDirectories in Compile := List((scalaSource in Compile).value),
//  unmanagedSourceDirectories in Test := List((scalaSource in Test).value),
//  libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-reflect" % _),
//  libraryDependencies <+= scalaVersion("org.scala-lang" % "scalap" % _),
//  scalacOptions in(Compile, doc) ++= Opts.doc.title("Innoave Scala Basal API"),
//  scalacOptions in(Compile, doc) ++= Opts.doc.version(buildVersion),
//  scalacOptions in(Compile, doc) += s"-doc-external-doc:${scalaInstance.value.libraryJar}#http://www.scala-lang.org/api/${scalaVersion.value}/",
//  scalacOptions in(Compile, doc) ++= Seq("-doc-footer", s"Innoave Scala Basal API v.$buildVersion"),
  autoAPIMappings := true,
  resolvers ++= Seq(
    Resolver.sonatypeRepo("snapshots"),
    Resolver.bintrayRepo("innoave", "maven")
  ),
  manifestSetting
)

lazy val manifestSetting = packageOptions += {
    Package.ManifestAttributes(
      "Created-By" -> "Simple Build Tool",
      "Built-By" -> Option(System.getenv("JAR_BUILT_BY")).getOrElse(System.getProperty("user.name")),
      "Build-Jdk" -> System.getProperty("java.version"),
      "Specification-Title" -> name.value,
      "Specification-Version" -> version.value,
      "Specification-Vendor" -> organization.value,
      "Implementation-Title" -> name.value,
      "Implementation-Version" -> version.value,
      "Implementation-Vendor-Id" -> organization.value,
      "Implementation-Vendor" -> organization.value
    )
}

//
// Git Versioning
//
lazy val gitRev = sys.process.Process("git rev-parse HEAD").lines_!.head

git.baseVersion := "0.0.0"
val VersionTagRegex = "^v([0-9]+.[0-9]+.[0-9]+)(-.*)?$".r
git.gitTagToVersionNumber := {
  case VersionTagRegex(v,"") => Some(v)
  case VersionTagRegex(v,s) => Some(s"$v$s")  
  case _ => None
}
git.useGitDescribe := true

//
// Release Settings
//
releaseCrossBuild := true
releaseProcess := ReleaseProcess.steps 
releasePublishArtifactsAction := PgpKeys.publishSigned.value
// use next version instead of current developer version
releaseVersion := {
    ver => Version(ver).map(_.withoutQualifier).map(_.bump(releaseVersionBump.value).string).getOrElse(versionFormatError)
}

//
// Publishing Settings
//
lazy val bintraySettings = Seq(
  publishMavenStyle := true,
  bintrayReleaseOnPublish := false,
  bintrayOrganization in bintray := None
)

lazy val publishSettings = bintraySettings ++ Seq(
  publishArtifact := true,
  publishArtifact in Test := false,
  // Metadata needed by Maven Central
  // See also http://maven.apache.org/pom.html#Developers
  homepage := Some(url("https://github.com/innoave/basal")),
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  scmInfo := Some(ScmInfo(
    url("https://github.com/innoave/basal"),
    "scm:git:git@github.com:innoave/basal.git")
  ),
  pomExtra := (
    <developers>
      <developer>
        <id>haraldmaida</id>
        <name>Harald Maida</name>
        <url>https://github.com/haraldmaida</url>
      </developer>
    </developers>
  )
)

//
// Project website
//
//sourceDirectory in Jekyll := baseDirectory.value / "website"
