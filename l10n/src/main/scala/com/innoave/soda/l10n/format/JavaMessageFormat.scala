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
package com.innoave.soda.l10n.format

import java.text.{MessageFormat => JMessageFormat}
import com.innoave.soda.l10n.Locale
import com.innoave.soda.l10n.MessageFormat

trait JavaMessageFormatProducer {

  final def messageFormatFor(pattern: String, locale: Locale): MessageFormat =
    JavaMessageFormat(pattern, locale)

}

final class JavaMessageFormat(val delegate: JMessageFormat) extends MessageFormat {

  override def locale: Locale =
    Locale.fromJavaLocale(delegate.getLocale)

  override def format(args: Any*): String =
    delegate.format(args.map(_.asInstanceOf[java.lang.Object]).toArray, new StringBuffer(), null).toString

  override def format(args: Array[_]): String =
    delegate.format(args.map(_.asInstanceOf[java.lang.Object]), new StringBuffer(), null).toString

}

object JavaMessageFormat {

  def apply(pattern: String, locale: Locale): JavaMessageFormat =
    new JavaMessageFormat(new JMessageFormat(pattern, locale.asJavaLocale))

}
