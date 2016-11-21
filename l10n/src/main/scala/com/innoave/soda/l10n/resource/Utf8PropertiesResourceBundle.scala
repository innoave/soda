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
package com.innoave.soda.l10n.resource

import java.util.{ResourceBundle => JResourceBundle}
import scala.util.control.NonFatal
import com.innoave.soda.l10n.BundleName
import com.innoave.soda.l10n.Locale
import com.innoave.soda.l10n.Message
import com.innoave.soda.l10n.Localized
import com.innoave.soda.l10n.ResourceBundle
import com.innoave.soda.l10n.DefineMessage
import com.innoave.soda.l10n.KeyNamingStrategy

trait Utf8PropertiesResourceBundleProducer {

  final def resourceBundleFor(bundleName: BundleName, locale: Locale): ResourceBundle =
    Utf8PropertiesResourceBundle(bundleName, locale)

}

final class Utf8PropertiesResourceBundle private[resource](val delegate: JResourceBundle) extends ResourceBundle {

  override def bundleName: BundleName =
    BundleName(delegate.getBaseBundleName)

  override def locale: Locale =
    Locale.fromJavaLocale(delegate.getLocale)

  override def stringFor(message: Message): String =
    _stringFor(message.key())

  override def stringFor[T](localized: Localized[T]): String =
    _stringFor(localized.key())

  private def _stringFor(key: String): String =
    try {
      delegate.getString(key)
    } catch {
      case NonFatal(error) =>
        s"!!!$key!!!"
    }

}

object Utf8PropertiesResourceBundle {

  def apply(bundleName: BundleName, locale: Locale): Utf8PropertiesResourceBundle =
    new Utf8PropertiesResourceBundle(JResourceBundle.getBundle(bundleName.value, locale.asJavaLocale, Utf8ResourceBundleControl))

  def stubFor(messages: DefineMessage): String =
    s"""#
      |# ${KeyNamingStrategy.simpleTypeName(messages.getClass)} : Message definitions
      |#
      |""".stripMargin +
    messages.values.iterator.map(m => m.key + "=\n").mkString

}
