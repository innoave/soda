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
package com.innoave.soda.desktop

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class FileSystemSpec extends FlatSpec with Matchers {

  def linuxFS(homedir: String, tempdir: String) = new LinuxLikeFileSystem {
    override def userHomeDir: String = homedir
    override def tempDir: String = tempdir
  }

  def macosFS(homedir: String, tempdir: String) = new MacOsLikeFileSystem {
    override def userHomeDir: String = homedir
    override def tempDir: String = tempdir
  }

  def mswinFS(version: Option[Int], homedir: String, tempdir: String) = new MsWinLikeFileSystem {
    override def userHomeDir: String = homedir
    override def tempDir: String = tempdir
    override def appDataDir: Option[String] = None
    override def localAppDataDir: Option[String] = None
    override def osMajorVersion: Option[Int] = version
  }

  "LinuxLikeFileSystem" should "return Linux like file path" in {

    val fs = linuxFS("/home/joda", "/var/temp")

    fs.reservedCharacters shouldBe Set(':')
    fs.tempDir shouldBe "/var/temp"
    fs.userHomeDir shouldBe "/home/joda"
    fs.userAppDataDir("anapp1") shouldBe "/home/joda/.anapp1"
    fs.userLocalAppDataDir("anapp2") shouldBe "/home/joda/.anapp2"
    fs.userDocumentsDir shouldBe "/home/joda/Documents"
    fs.userPicturesDir shouldBe "/home/joda/Pictures"
    fs.userMusicDir shouldBe "/home/joda/Music"
    fs.userVideosDir shouldBe "/home/joda/Videos"

  }

  "MacOsLikeFileSystem" should "return Mac OS X like file path" in {

    val fs = macosFS("/home/joda", "/var/temp")

    fs.reservedCharacters shouldBe Set(':')
    fs.tempDir shouldBe "/var/temp"
    fs.userHomeDir shouldBe "/home/joda"
    fs.userAppDataDir("anapp1") shouldBe "/home/joda/Library/Application Support/anapp1"
    fs.userLocalAppDataDir("anapp2") shouldBe "/home/joda/Library/Application Support/anapp2"
    fs.userDocumentsDir shouldBe "/home/joda/Documents"
    fs.userPicturesDir shouldBe "/home/joda/Pictures"
    fs.userMusicDir shouldBe "/home/joda/Music"
    fs.userVideosDir shouldBe "/home/joda/Videos"

  }

  "MsWinLikeFileSystem with no version specified" should "return default file path" in {

    val fs = mswinFS(None, "C:\\Documents and Settings\\joda", "C:\\temp")

    fs.reservedCharacters shouldBe Set('<', '>', ':', '"', '/', '\\', '|', '?', '*')
    fs.tempDir shouldBe "C:\\temp"
    fs.userHomeDir shouldBe "C:\\Documents and Settings\\joda"
    fs.userAppDataDir("anapp1") shouldBe "C:\\Documents and Settings\\joda\\.anapp1"
    fs.userLocalAppDataDir("anapp2") shouldBe "C:\\Documents and Settings\\joda\\.anapp2"
    fs.userDocumentsDir shouldBe "C:\\Documents and Settings\\joda\\Documents"
    fs.userPicturesDir shouldBe "C:\\Documents and Settings\\joda\\Pictures"
    fs.userMusicDir shouldBe "C:\\Documents and Settings\\joda\\Music"
    fs.userVideosDir shouldBe "C:\\Documents and Settings\\joda\\Videos"

  }

  "MsWinLikeFileSystem of version 4" should "return default file path" in {

    val fs = mswinFS(Some(4), "C:\\Documents and Settings\\joda", "C:\\temp")

    fs.reservedCharacters shouldBe Set('<', '>', ':', '"', '/', '\\', '|', '?', '*')
    fs.tempDir shouldBe "C:\\temp"
    fs.userHomeDir shouldBe "C:\\Documents and Settings\\joda"
    fs.userAppDataDir("anapp1") shouldBe "C:\\Documents and Settings\\joda\\.anapp1"
    fs.userLocalAppDataDir("anapp2") shouldBe "C:\\Documents and Settings\\joda\\.anapp2"
    fs.userDocumentsDir shouldBe "C:\\Documents and Settings\\joda\\Documents"
    fs.userPicturesDir shouldBe "C:\\Documents and Settings\\joda\\Pictures"
    fs.userMusicDir shouldBe "C:\\Documents and Settings\\joda\\Music"
    fs.userVideosDir shouldBe "C:\\Documents and Settings\\joda\\Videos"

  }

  "MsWinLikeFileSystem of version 5" should "return Win XP like file path" in {

    val fs = mswinFS(Some(5), "C:\\Documents and Settings\\joda", "C:\\temp")

    fs.reservedCharacters shouldBe Set('<', '>', ':', '"', '/', '\\', '|', '?', '*')
    fs.tempDir shouldBe "C:\\temp"
    fs.userHomeDir shouldBe "C:\\Documents and Settings\\joda"
    fs.userAppDataDir("anapp1") shouldBe "C:\\Documents and Settings\\joda\\Application Data\\anapp1"
    fs.userLocalAppDataDir("anapp2") shouldBe "C:\\Documents and Settings\\joda\\Local Settings\\Application Data\\anapp2"
    fs.userDocumentsDir shouldBe "C:\\Documents and Settings\\joda\\My Documents"
    fs.userPicturesDir shouldBe "C:\\Documents and Settings\\joda\\My Documents\\My Pictures"
    fs.userMusicDir shouldBe "C:\\Documents and Settings\\joda\\My Documents\\My Music"
    fs.userVideosDir shouldBe "C:\\Documents and Settings\\joda\\My Documents\\My Videos"

  }

  "MsWinLikeFileSystem of version 6" should "return Win Vista like file path" in {

    val fs = mswinFS(Some(6), "C:\\Users\\joda", "C:\\windows\\temp")

    fs.reservedCharacters shouldBe Set('<', '>', ':', '"', '/', '\\', '|', '?', '*')
    fs.tempDir shouldBe "C:\\windows\\temp"
    fs.userHomeDir shouldBe "C:\\Users\\joda"
    fs.userAppDataDir("anapp1") shouldBe "C:\\Users\\joda\\AppData\\Roaming\\anapp1"
    fs.userLocalAppDataDir("anapp2") shouldBe "C:\\Users\\joda\\AppData\\Local\\anapp2"
    fs.userDocumentsDir shouldBe "C:\\Users\\joda\\Documents"
    fs.userPicturesDir shouldBe "C:\\Users\\joda\\Pictures"
    fs.userMusicDir shouldBe "C:\\Users\\joda\\Music"
    fs.userVideosDir shouldBe "C:\\Users\\joda\\Videos"

  }

}
