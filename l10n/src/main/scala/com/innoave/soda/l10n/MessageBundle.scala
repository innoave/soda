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

import java.text.MessageFormat
import java.util.ResourceBundle

import scala.util.control.NonFatal

class MessageBundle(delegate: ResourceBundle) {

  def raw(message: Message): String =
    _pattern(message.key())

  def formatted[T](message: Message, args: Any*)(implicit locale: Locale = Locale.default): String =
    new MessageFormat(_pattern(message.key()), locale.asJava).format(
        args.map(_.asInstanceOf[java.lang.Object]).toArray,
        new StringBuffer(), null
        ).toString

  private def _pattern(key: String): String =
    try {
      delegate.getString(key)
    } catch {
      case NonFatal(error) =>
        s"!!!$key!!!"
    }

}

object MessageBundle {

  def read(bundleName: String): MessageBundle =
    new MessageBundle(ResourceBundle.getBundle(bundleName, Utf8ResourceBundleControl))

  def read(bundleName: String, locale: Locale): MessageBundle =
    new MessageBundle(ResourceBundle.getBundle(bundleName, locale.asJava, Utf8ResourceBundleControl))

  def stubFor(messages: Messages): String =
    messages.values.map(_.key).mkString("=\n")

}
