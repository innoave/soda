/*
 * Copyright (c) 2015, Innoave.com
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
package com.innoave.soda.desktop

import OS.Brand._

trait FileSystem {

  /**
   * <p>
   * Gets the reserved characters of the file system.
   * </p>
   *
   * @return the set of reserved characters
   */
  def reservedCharacters: Set[Char]

  val fileSeparator: String = java.io.File.separator

  val pathSeparator: String = java.io.File.pathSeparator

  /**
   * <p>
   * Gets the system's directory for temporary files.
   * </p>
   *
   * @return the system's directory for temporary files
   */
  def tempDir: String = System.getProperty("java.io.tmpdir")

  /**
   * <p>
   * Gets the home directory of the current user.
   * </p>
   *
   * @return the current users home directory
   */
  def userHomeDir: String = System.getProperty("user.home")

  /**
   * <p>
   * Gets the special folder path for user application settings.
   * </p>
   *
   * <p>For users on Windows XP, this will (likely) be:</p>
   * <pre>
   * C:\Documents and Settings\[USERNAME]\Application Data\[APPNAME]
   * </pre>
   *
   * <p>For users on Windows Vista, this will (likely) be:</p>
   * <pre>
   * C:\Users\[USERNAME]\AppData\Roaming\[APPNAME]
   * </pre>
   *
   * <p>For users on Mac OS X, this will (likely) be:</p>
   * <pre>
   * ~/Libary/Application Support/[APPNAME]
   * </pre>
   *
   * <p>For users on a *nix based OS, this will (likely) be:</p>
   * <pre>
   * /home/[USERNAME]/.[APPNAME]
   * </pre>
   *
   * @param appName name of the application
   * @return user specific application data directory
   */
  def userAppDataDir(appName: String): String

  /**
   * <p>
   * Gets the special folder path for local user application settings.
   * </p>
   *
   * <p>For users on Windows XP, this will (likely) be:</p>
   * <pre>
   * C:\Documents and Settings\[USERNAME]\Local Settings\Application Data\[APPNAME]
   * </pre>
   *
   * <p>For users on Windows Vista, this will (likely) be:</p>
   * <pre>
   * C:\Users\[USERNAME]\AppData\Local\[APPNAME]
   * </pre>
   *
   * <p>For users on Mac OS X, this will (likely) be:</p>
   * <pre>
   * ~/Libary/Caches/[APPNAME]
   * </pre>
   *
   * <p>For users on a *nix based OS, this will (likely) be:</p>
   * <pre>
   * /home/[USERNAME]/.[APPNAME]
   * </pre>
   *
   * @param appName name of the application
   * @return user specific local application data directory
   */
  def userLocalAppDataDir(appName: String): String

  /**
   * <p>
   * Gets the special folder path for user documents directory.
   * </p>
   *
   * <p>For users on Windows XP, this will (likely) be:</p>
   * <pre>
   * C:\Documents and Settings\[USERNAME]\My Documents
   * </pre>
   *
   * <p>For users on Windows Vista, this will (likely) be:</p>
   * <pre>
   * C:\Users\[USERNAME]\Documents
   * </pre>
   *
   * <p>For users on Mac OS X, this will (likely) be:</p>
   * <pre>
   * ~
   * </pre>
   *
   * <p>For users on a *nix based OS, this will (likely) be:</p>
   * <pre>
   * /home/[USERNAME]
   * </pre>
   *
   * @return user document directory
   */
  def userDocumentsDir: String

  /**
   * <p>
   * Gets the special folder path for user pictures directory.
   * </p>
   *
   * <p>For users on Windows XP, this will (likely) be:</p>
   * <pre>
   * C:\Documents and Settings\[USERNAME]\My Documents\My Pictures
   * </pre>
   *
   * <p>For users on Windows Vista, this will (likely) be:</p>
   * <pre>
   * C:\Users\[USERNAME]\Pictures
   * </pre>
   *
   * <p>For users on Mac OS X, this will (likely) be:</p>
   * <pre>
   * ~/Pictures
   * </pre>
   *
   * <p>For users on a *nix based OS, this will (likely) be:</p>
   * <pre>
   * /home/[USERNAME]/Pictures
   * </pre>
   *
   * @return user pictures directory
   */
  def userPicturesDir: String

  /**
   * <p>
   * Gets the special folder path for user music directory.
   * </p>
   *
   * <p>For users on Windows XP, this will (likely) be:</p>
   * <pre>
   * C:\Documents and Settings\[USERNAME]\My Documents\My Music
   * </pre>
   *
   * <p>For users on Windows Vista, this will (likely) be:</p>
   * <pre>
   * C:\Users\[USERNAME]\Music
   * </pre>
   *
   * <p>For users on Mac OS X, this will (likely) be:</p>
   * <pre>
   * ~/Music
   * </pre>
   *
   * <p>For users on a *nix based OS, this will (likely) be:</p>
   * <pre>
   * /home/[USERNAME]/Music
   * </pre>
   *
   * @return user music directory
   */
  def userMusicDir: String

  /**
   * <p>
   * Gets the special folder path for user videos directory.
   * </p>
   *
   * <p>For users on Windows XP, this will (likely) be:</p>
   * <pre>
   * C:\Documents and Settings\[USERNAME]\My Documents\My Videos
   * </pre>
   *
   * <p>For users on Windows Vista, this will (likely) be:</p>
   * <pre>
   * C:\Users\[USERNAME]\Videos
   * </pre>
   *
   * <p>For users on Mac OS X, this will (likely) be:</p>
   * <pre>
   * ~/Music
   * </pre>
   *
   * <p>For users on a *nix based OS, this will (likely) be:</p>
   * <pre>
   * /home/[USERNAME]/Videos
   * </pre>
   *
   * @return user videos directory
   */
  def userVideosDir: String

}

object FileSystem {

  def apply(): FileSystem = OS.brand match {
    case MsWin =>
      MsWinFileSystem
    case MacOs =>
      MacOsFileSystem
    case Linux =>
      LinuxFileSystem
    case Unknown =>
      UnknownFileSystem
  }

}

object UnknownFileSystem extends LinuxLikeFileSystem

trait LinuxLikeFileSystem extends FileSystem {

  override def reservedCharacters: Set[Char] = Set(':')

  override def userAppDataDir(appName: String): String =
     userHomeDir + "/." + appName

  override def userLocalAppDataDir(appName: String): String =
     userHomeDir + "/." + appName

  override def userDocumentsDir: String =
    userHomeDir + "/Documents"

  override def userPicturesDir: String =
    userHomeDir + "/Pictures"

  override def userMusicDir: String =
    userHomeDir + "/Music"

  override def userVideosDir: String =
    userHomeDir + "/Videos"

}

object LinuxFileSystem extends LinuxLikeFileSystem

trait MacOsLikeFileSystem extends LinuxLikeFileSystem {

  override def userAppDataDir(appName: String): String =
    userHomeDir + "/Library/Application Support/" + appName

  override def userLocalAppDataDir(appName: String): String =
    userHomeDir + "/Library/Application Support/" + appName

}

object MacOsFileSystem extends MacOsLikeFileSystem

trait MsWinLikeFileSystem extends FileSystem {

  override def reservedCharacters: Set[Char] = Set('<', '>', ':', '"', '/', '\\', '|', '?', '*')

  protected def appDataDir: Option[String] = sys.props.get("APPDATA")

  protected def localAppDataDir: Option[String] = sys.props.get("LOCALAPPDATA")

  protected def osMajorVersion: Option[Int] = OS.majorVersion

  override def userAppDataDir(appName: String): String = appDataDir match {
    case Some(appData) =>
      appData + "\\" + appName
    case None =>
      osMajorVersion match {
        case Some(majorVersion) =>
          if (majorVersion >= 6)
            userHomeDir + "\\AppData\\Roaming\\" + appName
          else if (majorVersion == 5)
            userHomeDir + "\\Application Data\\" + appName
          else
            userHomeDir + "\\." + appName
        case None =>
          userHomeDir + "\\." + appName
      }
  }

  override def userLocalAppDataDir(appName: String): String = localAppDataDir match {
    case Some(localAppData) =>
      localAppData + "\\" + appName
    case None =>
      osMajorVersion match {
        case Some(majorVersion) =>
          if (majorVersion >= 6)
            userHomeDir + "\\AppData\\Local\\" + appName
          else if (majorVersion == 5)
            userHomeDir + "\\Local Settings\\Application Data\\" + appName
          else
            userHomeDir + "\\." + appName
        case None =>
          userHomeDir + "\\." + appName
      }
  }

  override def userDocumentsDir: String = osMajorVersion match {
    case Some(majorVersion) =>
      if (majorVersion >= 6)
        userHomeDir + "\\Documents"
      else if (majorVersion == 5)
        userHomeDir + "\\My Documents"
      else
        userHomeDir + "\\Documents"
    case None =>
      userHomeDir + "\\Documents"
  }

  override def userPicturesDir: String = osMajorVersion match {
    case Some(majorVersion) =>
      if (majorVersion >= 6)
        userHomeDir + "\\Pictures"
      else if (majorVersion == 5)
        userHomeDir + "\\My Documents\\My Pictures"
      else
        userHomeDir + "\\Pictures"
    case None =>
      userHomeDir + "\\Pictures"
  }

  override def userMusicDir: String = osMajorVersion match {
    case Some(majorVersion) =>
      if (majorVersion >= 6)
        userHomeDir + "\\Music"
      else if (majorVersion == 5)
        userHomeDir + "\\My Documents\\My Music"
      else
        userHomeDir + "\\Music"
    case None =>
      userHomeDir + "\\Music"
  }

  override def userVideosDir: String = osMajorVersion match {
    case Some(majorVersion) =>
      if (majorVersion >= 6)
        userHomeDir + "\\Videos"
      else if (majorVersion == 5)
        userHomeDir + "\\My Documents\\My Videos"
      else
        userHomeDir + "\\Videos"
    case None =>
      userHomeDir + "\\Videos"
  }

}

object MsWinFileSystem extends MsWinLikeFileSystem
