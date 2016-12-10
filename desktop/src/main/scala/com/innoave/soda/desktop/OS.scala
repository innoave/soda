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

import scala.util.Try

/** Provides basic attributes about the operation system an application
 *  is currently running on.
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
trait OS {
  import OS.Brand

  /** Get the name of the OS.
   *
   *  @return the name of the OS.
   */
  def name: Option[String] = sys.props.get("os.name")

  /** Get the version of the OS.
   *
   *  @return the version of the OS or None if the version can not be
   *          determined.
   */
  def version: Option[String] = sys.props.get("os.version")

  /** Get the brand of the OS.
   *
   *  @return the brand of the OS or Brand.Unknown if the brand can not be
   *          determined.
   */
  def brand: Brand.Brand = name match {
    case Some(osname) =>
      val lowername = osname.toLowerCase()
      if (lowername startsWith "windows")
        Brand.MsWin
      else if (lowername startsWith "mac os")
        Brand.MacOs
      else if (lowername startsWith "linux")
        Brand.Linux
      else
        Brand.Unknown
    case None =>
      Brand.Unknown
  }

  /** Get the major version of the OS.
   *
   *  The major version is usually the version number before the first dot.
   *  This assumes that the version string separates the major part and the
   *  minor part of the version by a dot.
   *
   *  @return the major version of the OS or None of the major version can not
   *          be determined.
   */
  def majorVersion: Option[Int] = version flatMap { vers =>
    val versnum = if (vers.startsWith("v") || vers.startsWith("V")) vers.substring(1) else vers
    val majorIndex = versnum.indexOf('.')
    if (majorIndex > 0) {
      Try(versnum.substring(0, majorIndex).toInt).toOption
    } else {
      Try(versnum.toInt).toOption
    }
  }

}

/** Companion object of the OS trait. It provides static access to the
 *  functions of the OS trait.
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
object OS extends OS {

  /** Enumeration of supported brands of OSes.
   *
   *  @since 0.1.0
   *  @author haraldmaida
   */
  object Brand extends Enumeration {
    type Brand = Value
    val Linux = Value("Linux")
    val MacOs = Value("MacOs")
    val MsWin = Value("MsWin")
    val Unknown = Value("Unknown")
  }

}
