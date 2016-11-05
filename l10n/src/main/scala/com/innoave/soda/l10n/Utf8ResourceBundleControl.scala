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
package com.innoave.soda.l10n

import java.io.InputStream
import java.io.InputStreamReader
import java.security.AccessController
import java.security.PrivilegedExceptionAction
import java.security.PrivilegedActionException
import java.util.{Arrays => JArrays}
import java.util.{List => JList}
import java.util.{Locale => JLocale}
import java.util.{PropertyResourceBundle => JPropertyResourceBundle}
import java.util.{ResourceBundle => JResourceBundle}

private[l10n] object Utf8ResourceBundleControl extends JResourceBundle.Control {

  val Utf8Properties = "utf8.properties"

  val FileExtension = "txt"

  override def getFormats(baseName: String): JList[String] =
    JArrays.asList(Utf8Properties)

  override def newBundle(
      baseName: String, locale: JLocale, format: String, loader: ClassLoader, reload: Boolean
      ): JResourceBundle = {
    val bundleName = toBundleName(baseName, locale)
    val resourceName = toResourceName(bundleName, FileExtension)

    def stream: Option[InputStream] =
      try {
        AccessController.doPrivileged {
          new PrivilegedExceptionAction[Option[InputStream]]() {
            override def run() = {
              if (reload) {
                for {
                  url <- Option(loader.getResource(resourceName))
                  connection <- Option(url.openConnection())
                } yield {
                  connection.setUseCaches(false)
                  connection.getInputStream
                }
              } else {
                Option(loader.getResourceAsStream(resourceName))
              }
            }
          }
        }
      } catch {
        case e: PrivilegedActionException =>
          throw e.getException
      }

    Option(format) match {

      case Some(Utf8Properties) =>

        (for {
          is <- stream
        } yield {
          val isr = new InputStreamReader(is, "UTF-8")
          try {
            new JPropertyResourceBundle(isr)
          } finally {
            isr.close()
          }
        }).orNull

      case _ => null

    }
  }

}
