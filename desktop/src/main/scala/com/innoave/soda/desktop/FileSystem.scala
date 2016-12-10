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

/** Represents a file system of the operation system an application is
 *  running on. It provides functions to retrieve attributes of the file
 *  system defined by the underlying OS.
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
trait FileSystem {

  /** Gets the reserved characters of the file system.
   *
   *  Reserved characters may not be used in path or file names.
   *
   *  @return the set of reserved characters
   */
  def reservedCharacters: Set[Char]

  /** Gets the file system specific file separator.
   *
   *  On Linux and Mac OS typically this is the `/` character.
   *
   *  On Windows typically this is the `\` character.
   *
   *  @return the file separator of the underlying file system.
   */
  def fileSeparator: String = java.io.File.separator

  /** Gets the file system specific path separator.
   *
   *  On Linux and Mac OS typically this is the `:` character.
   *
   *  On Windows typically this is the `;` character.
   *
   *  @return the path separator of the underlying file system.
   */
  def pathSeparator: String = java.io.File.pathSeparator

  /** Gets the system's directory for temporary files.
   *
   *  @return the system's directory for temporary files
   */
  def tempDir: String = System.getProperty("java.io.tmpdir")

  /** Gets the home directory of the current user.
   *
   *  @return the current users home directory
   */
  def userHomeDir: String = System.getProperty("user.home")

  /** Gets the special folder path for user application settings.
   *
   *  For users on Windows XP, this will (likely) be:
   *
   *  `C:\Documents and Settings\[USERNAME]\Application Data\[APPNAME]`
   *
   *  For users on Windows Vista or later versions, this will (likely) be:
   *
   *  `C:\Users\[USERNAME]\AppData\Roaming\[APPNAME]`
   *
   *  For users on Mac OS X, this will (likely) be:
   *
   *  `~/Libary/Application Support/[APPNAME]`
   *
   *  For users on a *nix based OS, this will (likely) be:
   *
   *  `/home/[USERNAME]/.[APPNAME]`
   *
   *  @param appName name of the application
   *  @return user specific application data directory
   */
  def userAppDataDir(appName: String): String

  /** Gets the special folder path for local user application settings.
   *
   *  For users on Windows XP, this will (likely) be:
   *
   *  `C:\Documents and Settings\[USERNAME]\Local Settings\Application Data\[APPNAME]`
   *
   *  `For users on Windows Vista or later versions, this will (likely) be:`
   *
   *  `C:\Users\[USERNAME]\AppData\Local\[APPNAME]`
   *
   *  For users on Mac OS X, this will (likely) be:
   *
   *  `~/Libary/Caches/[APPNAME]`
   *
   *  For users on a *nix based OS, this will (likely) be:
   *
   *  `/home/[USERNAME]/.[APPNAME]`
   *
   *  @param appName name of the application
   *  @return user specific local application data directory
   */
  def userLocalAppDataDir(appName: String): String

  /** Gets the special folder path for user documents directory.
   *
   *  For users on Windows XP, this will (likely) be:
   *
   *  `C:\Documents and Settings\[USERNAME]\My Documents`
   *
   *  For users on Windows Vista or later versions, this will (likely) be:
   *
   *  `C:\Users\[USERNAME]\Documents`
   *
   *  For users on Mac OS X, this will (likely) be:
   *
   *  `~`
   *
   *  For users on a *nix based OS, this will (likely) be:
   *
   *  `/home/[USERNAME]`
   *
   *  @return user document directory
   */
  def userDocumentsDir: String

  /** Gets the special folder path for user pictures directory.
   *
   *  For users on Windows XP, this will (likely) be:
   *
   *  `C:\Documents and Settings\[USERNAME]\My Documents\My Pictures`
   *
   *  For users on Windows Vista or later versions, this will (likely) be:
   *
   *  `C:\Users\[USERNAME]\Pictures`
   *
   *  For users on Mac OS X, this will (likely) be:
   *
   *  `~/Pictures`
   *
   *  For users on a *nix based OS, this will (likely) be:
   *
   *  `/home/[USERNAME]/Pictures`
   *
   *  @return user pictures directory
   */
  def userPicturesDir: String

  /** Gets the special folder path for user music directory.
   *
   *  For users on Windows XP, this will (likely) be:
   *
   *  `C:\Documents and Settings\[USERNAME]\My Documents\My Music`
   *
   *  For users on Windows Vista or later versions, this will (likely) be:
   *
   *  `C:\Users\[USERNAME]\Music`
   *
   *  For users on Mac OS X, this will (likely) be:
   *
   *  `~/Music`
   *
   *  For users on a *nix based OS, this will (likely) be:
   *
   *  `/home/[USERNAME]/Music`
   *
   *  @return user music directory
   */
  def userMusicDir: String

  /** Gets the special folder path for user videos directory.
   *
   *  For users on Windows XP, this will (likely) be:
   *
   *  `C:\Documents and Settings\[USERNAME]\My Documents\My Videos`
   *
   *  For users on Windows Vista or later versions, this will (likely) be:
   *
   *  `C:\Users\[USERNAME]\Videos`
   *
   *  For users on Mac OS X, this will (likely) be:
   *
   *  `~/Music`
   *
   *  For users on a *nix based OS, this will (likely) be:
   *
   *  `/home/[USERNAME]/Videos`
   *
   *  @return user videos directory
   */
  def userVideosDir: String

}

/** Factory object to get [[com.innoave.soda.desktop.FileSystem]]'s default
 *  implementation that represents the file system of the OS the application
 *  is currently running on.
 *
 *  @example Get the user's home directory on the current OS
 *  {{{
 *  FileSystem().userHomeDir
 *  }}}
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
object FileSystem {

  /** Returns a [[com.innoave.soda.desktop.FileSystem]] that represents the
   *  file system of the OS the application is running on.
   *
   *  @return the [[FileSystem]] representing the current OS's file system.
   */
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

/** Static access to [[com.innoave.soda.desktop.FileSystem]] functions for
 *  an unknown file system.
 *
 *  This implementation behaves the same way as a Linux like file system.
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
object UnknownFileSystem extends LinuxLikeFileSystem

/** Default implementation of [[com.innoave.soda.desktop.FileSystem]] for
 *  the file system usually used by Linux like OSes.
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
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

/** Static access to [[com.innoave.soda.desktop.FileSystem]] functions for
 *  the Linux file system.
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
object LinuxFileSystem extends LinuxLikeFileSystem

/** Default implementation of [[com.innoave.soda.desktop.FileSystem]] for
 *  the file system usually used by Apple Mac OS.
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
trait MacOsLikeFileSystem extends LinuxLikeFileSystem {

  override def userAppDataDir(appName: String): String =
    userHomeDir + "/Library/Application Support/" + appName

  override def userLocalAppDataDir(appName: String): String =
    userHomeDir + "/Library/Application Support/" + appName

}

/** Static access to [[com.innoave.soda.desktop.FileSystem]] functions for
 *  the Apple Mac OS file system.
 *
 *  This implementation supports Mac OS X.
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
object MacOsFileSystem extends MacOsLikeFileSystem

/** Default implementation of [[com.innoave.soda.desktop.FileSystem]] for
 *  the file system usually used by Microsoft Windows OS.
 *
 *  This implementation supports Windows versions XP, Vista, 7, 8, 10.
 *  Versions later than 10 are treated the same way as version 10.
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
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

/** Static access to [[com.innoave.soda.desktop.FileSystem]] functions for
 *  Microsoft Windows.
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
object MsWinFileSystem extends MsWinLikeFileSystem
